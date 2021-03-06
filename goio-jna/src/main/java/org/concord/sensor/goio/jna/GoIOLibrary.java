/*
 * TBD: 
 * 1) Write proper header comment
 * 2) Add exceptions
 * 3) 
 * 4) Comment
 * 5) Add vendor & type to GoIOInterface
 */



package org.concord.sensor.goio.jna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.FunctionMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class GoIOLibrary
{
	static int [] GO_DEVICES = {
			GoIOJNALibrary.SKIP_DEFAULT_PRODUCT_ID,  // GoLink
			GoIOJNALibrary.USB_DIRECT_TEMP_DEFAULT_PRODUCT_ID, // GoTemp
 			GoIOJNALibrary.CYCLOPS_DEFAULT_PRODUCT_ID, // GoMotion
			GoIOJNALibrary.MINI_GC_DEFAULT_PRODUCT_ID  // MiniGasChromatograph
			}; 
	

	protected GoIOJNALibrary goIOLibrary;

	public boolean initLibrary()
	{
		NativeHelper.removeTemporaryFiles();
		
		File nativeLibFile = getNativeLibraryFromJar();
		String nativeLibPath = nativeLibFile.getAbsolutePath();
		
		FunctionMapper functMapper = new FunctionMapper(){

			// This actually isn't necessary because the function names are the same
			// but in the future we might want to reduce reduancy and map:
			// abcd  to GoIO_Abcd
			public String getFunctionName(NativeLibrary library, Method method) {
				return method.getName();
				// return "GoIO_" + method.getName().substring(0,1).toUpperCase() + 
				// method.getName().substring(1);
			}
			
		};

		
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(Library.OPTION_FUNCTION_MAPPER, functMapper);
		options.put(Library.OPTION_STRUCTURE_ALIGNMENT, Structure.ALIGN_NONE);
		goIOLibrary = (GoIOJNALibrary) Native.loadLibrary(nativeLibPath, 
				GoIOJNALibrary.class, options);

        NativeHelper.deleteNativeLibrary(nativeLibFile);
        
		return goIOLibrary != null;		
	};

	
	public int uninit()
	{
		//System.err.println("GoIOInterface: cleaning up");
		
		int ret = goIOLibrary.GoIO_Uninit();
		goIOLibrary = null;
		return ret;
	}
		

	public int init(){		
		return goIOLibrary.GoIO_Init();
	}
	
	public boolean isGoDeviceAttached() {
		for (int i=0; i<GO_DEVICES.length; i++) {
			int number = goIOLibrary.GoIO_UpdateListOfAvailableDevices(
					GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID,
					GO_DEVICES[i]
					);
			if(number > 0){
				return true;
			}
		}
		
		return false;
	}
	
	public GoIOSensor getSensor(int productId, int index){
		int number = goIOLibrary.GoIO_UpdateListOfAvailableDevices(
				GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID,
				productId
				);
		if(number <= index){
			return null;
		}
		
		GoIOSensor sensor = new GoIOSensor(goIOLibrary);
		sensor.init(productId, index);
		return sensor;					
	}

	public GoIOSensor getFirstSensor() {
		for(int i=0; i<GO_DEVICES.length; i++){			
			GoIOSensor sensor = getSensor(GO_DEVICES[i], 0);
			if(sensor != null) {
				return sensor;
			}
		}
		
		return null;
	}
	
	public boolean sensorSetMeasurementPeriod(GoIOSensor sensor,double desiredPeriod, int timeoutMs)
	{
		int ret = goIOLibrary.GoIO_Sensor_SetMeasurementPeriod(sensor.hDevice,desiredPeriod,timeoutMs);	
		
		return 0 == ret;
	}
	
	
	public boolean sensorSendCmd(
			GoIOSensor sensor,	
			byte cmd,		
			byte [] pParams,			
			int nParamBytes,
			Pointer pRespBuf,			
			int []pnRespBytes,
			int timeoutMs)	
	{
		
		
		int ret = goIOLibrary.GoIO_Sensor_SendCmdAndGetResponse(
				sensor.hDevice,
				cmd,		
				pParams,			
				nParamBytes,
				pRespBuf,			
				pnRespBytes,
				timeoutMs);
		
		return ret==0;
		
	}
	

	public boolean sensorStartCollectingData(
			GoIOSensor sensor)
	{
		boolean ret = false;
		
		byte [] pParams = null;
		Pointer pRespBuf =null;
		int []pnRespBytes = null;
		
		ret = sensorSendCmd(sensor,
									GoIOJNALibrary.SKIP_CMD_ID_START_MEASUREMENTS, 							 
									pParams, 
									0, //null,
									pRespBuf, //null, 
									pnRespBytes, //null
									GoIOJNALibrary.SKIP_TIMEOUT_MS_DEFAULT
									);

		return ret;
		
	}
	
	
	public int[] sensorReadRawMeasuements(
			GoIOSensor sensor,
			int maxCount)
	{
		int [] pMeasurementsBuf = new int[maxCount];
		
		int ngot  = goIOLibrary.GoIO_Sensor_ReadRawMeasurements(
					sensor.hDevice,		//[in] handle to open sensor.
					pMeasurementsBuf,	//[out] ptr to loc to store measurements.
					maxCount);	//[in] maximum number of measurements to copy to pMeasurementsBuf. See warning above.

		int [] retbuf = new int [ngot];
		System.arraycopy(pMeasurementsBuf, 0, retbuf, 0, ngot);
		return retbuf;	
	}
	
	
	//End API
	//Helper functions:
	
	
	protected boolean getDeviceName(char []deviceName, int nameLength, int []pVendorId, int []pProductId)
	{
		/*
		 * FIX: (Ponder)
		 * This function is more or less from GoIO_DeviceCheck.cpp
		 * It seems weird to check for 4 devices
		 * only to return the one that was found first...
		 * 
		 */
		boolean bFoundDevice = true; //Must be true for code to work
		
		deviceName[0] = 0;
		int VDV_ID = GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID;
		
		int numSkips     = updateDeviceListEntry(VDV_ID, GoIOJNALibrary.PROBE_GOLINK); 
		int numJonahs    = updateDeviceListEntry(VDV_ID, GoIOJNALibrary.PROBE_USB_TEMPERATURE);
		int numCyclopses = updateDeviceListEntry(VDV_ID, GoIOJNALibrary.PROBE_GOMOTION);
		int numMiniGCs   = updateDeviceListEntry(VDV_ID, GoIOJNALibrary.MINI_GC_DEFAULT_PRODUCT_ID);

		
		do //not a loop: Used in stead of else if 
		{

			if(numSkips>0)
			{
				pVendorId[0] = GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID;
				pProductId[0]= GoIOJNALibrary.PROBE_GOLINK;		
				break;
			}
			
			if(numJonahs>0)
			{
				pVendorId[0] = GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID;
				pProductId[0]= GoIOJNALibrary.PROBE_USB_TEMPERATURE;				
				break;
			}
			
			if(numCyclopses>0)
			{
				pVendorId[0] = GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID;
				pProductId[0]= GoIOJNALibrary.PROBE_GOMOTION;		
				break;
			}
			
			if(numMiniGCs>0)
			{
				pVendorId[0] = GoIOJNALibrary.VERNIER_DEFAULT_VENDOR_ID;
				pProductId[0]= GoIOJNALibrary.PROBE_MINI_GAS_CHROMATOGRAPH;				
				break;
			}
			
			//default, no device found:
			bFoundDevice = false;
			
		}while(false);
		
		if(bFoundDevice)
			goIOLibrary.GoIO_GetNthAvailableDeviceName(deviceName, nameLength, pVendorId[0], pProductId[0], 0);
		
		return bFoundDevice;
	}
	

	
	protected Pointer sensorOpen(char []pDeviceName, int vendorId, int productId)
	{
		return goIOLibrary.GoIO_Sensor_Open(pDeviceName, vendorId, productId, 0); //last arg 0 in all examples...		
	}

	
	protected int updateDeviceListEntry(int vendor, int device_id)
	{
		return goIOLibrary.GoIO_UpdateListOfAvailableDevices(vendor, device_id);		
	}
	
	//FIX: Copied from LabQuestLibrary, then modified:	
    private static File getNativeLibraryFromJar() {
        String libname = getNativeLibraryName();
        String resourceName = getNativeLibraryResourcePath() + "/" + libname;
        URL url = GoIOLibrary.class.getResource(resourceName);
                
        if (url == null) {
            throw new UnsatisfiedLinkError("GoIO (" + resourceName 
                                           + ") not found in resource path");
        }
    
        File lib = null;
        InputStream is = Native.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new Error("Can't obtain jnidispatch InputStream");
        }
        
        FileOutputStream fos = null;
        try {
            // Suffix is required on windows, or library fails to load
            // Let Java pick the suffix
            lib = File.createTempFile("jna", null);
            fos = new FileOutputStream(lib);
            int count;
            byte[] buf = new byte[1024];
            while ((count = is.read(buf, 0, buf.length)) > 0) {
                fos.write(buf, 0, count);
            }
        }
        catch(IOException e) {
            throw new Error("Failed to create temporary file for jnidispatch library: " + e);
        }
        finally {
            try { is.close(); } catch(IOException e) { }
            if (fos != null) {
                try { fos.close(); } catch(IOException e) { }
            }
        }
        return lib;
    }
    

    //FIX: Copied from LabQuestLibrary, then modified:
    private static String getNativeLibraryName() {
        if (Platform.isWindows()) {
        	return "GoIO_DLL.dll";
        }
        else if (Platform.isMac()) {
        	return "libGoIO_DLL.dylib";
        }
        return null;
    }
    
    //FIX: Copied from LabQuestLibrary
    private static String getNativeLibraryResourcePath() {
        String arch = System.getProperty("os.arch").toLowerCase();
        String osPrefix;
        if (Platform.isWindows()) {
            osPrefix = "win32_" + arch;
        }
        else if (Platform.isMac()) {
            osPrefix = "darwin";
        }
        else if (Platform.isLinux()) {
            if ("x86".equals(arch)) {
                arch = "i386";
            }
            else if ("x86_64".equals(arch)) {
                arch = "amd64";
            }
            osPrefix = "linux_" + arch;
        }
        else if (Platform.isSolaris()) {
            osPrefix = "sunos_" + arch;
        }
        else {
            osPrefix = System.getProperty("os.name").toLowerCase();
            int space = osPrefix.indexOf(" ");
            if (space != -1) {
                osPrefix = osPrefix.substring(0, space);
            }
            osPrefix += "-" + arch;
        }
        return "/org/concord/sensor/goio/jna/" + osPrefix; //path == package name
    }
    
    private static class NativeHelper {
    	private static void deleteNativeLibrary(File file) {
    		if (file.delete()) {
    			return;
    		}
    		markTemporaryFile(file);
    	}

    	private static void markTemporaryFile(File file) {
    		try {
    			File marker = new File(file.getParentFile(), file.getName() + ".x");
    			marker.createNewFile();
    		}
    		catch(IOException e) { e.printStackTrace(); }
    	}

    	private static void removeTemporaryFiles() {
    		File dir;
    		try {
    			dir = File.createTempFile("jna", ".x").getParentFile();
    			FilenameFilter filter = new FilenameFilter() {
    				public boolean accept(File dir, String name) {
    					return name.endsWith(".x") && name.indexOf("jna") != -1;
    				}
    			};
    			File[] files = dir.listFiles(filter);
    			for (int i=0;files != null && i < files.length;i++) {
    				File marker = files[i];
    				String name = marker.getName();
    				name = name.substring(0, name.length()-2);
    				File target = new File(marker.getParentFile(), name);
    				if (!target.exists() || target.delete()) {
    					marker.delete();
    				}
    			}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
}

