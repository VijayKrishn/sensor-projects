/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version: 1.3.21
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package ccsd.ti;


public class ExperimentConfig implements 
	org.concord.sensor.ExperimentConfig
 {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected ExperimentConfig(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected void finalize() {
    delete();
  }

  public void delete() {
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      NativeBridgeJNI.delete_ExperimentConfig(swigCPtr);
    }
    swigCPtr = 0;
  }

  protected static long getCPtr(ExperimentConfig obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

	public boolean isValid()
	{
		return getValid() == 1;
	}
	
	public boolean getExactPeriod()
	{
		return getExactPeriodUChar() == 1;
	}
	
	public org.concord.sensor.SensorConfig [] getSensorConfigs()
	{
		int num = getNumSensorConfigs();
		SensorConfig [] configs = new SensorConfig [num];
		for(int i=0; i<num; i++) {
			configs[i] = getSensorConfig(i);
		}
		
		return configs;
	}

  public void setValid(short valid) {
    NativeBridgeJNI.set_ExperimentConfig_valid(swigCPtr, valid);
  }

  public short getValid() {
    return NativeBridgeJNI.get_ExperimentConfig_valid(swigCPtr);
  }

  public void setInvalidReason(String invalidReason) {
    NativeBridgeJNI.set_ExperimentConfig_invalidReason(swigCPtr, invalidReason);
  }

  public String getInvalidReason() {
    return NativeBridgeJNI.get_ExperimentConfig_invalidReason(swigCPtr);
  }

  public void setPeriod(float period) {
    NativeBridgeJNI.set_ExperimentConfig_period(swigCPtr, period);
  }

  public float getPeriod() {
    return NativeBridgeJNI.get_ExperimentConfig_period(swigCPtr);
  }

  public void setExactPeriodUChar(short exactPeriod) {
    NativeBridgeJNI.set_ExperimentConfig_exactPeriodUChar(swigCPtr, exactPeriod);
  }

  public short getExactPeriodUChar() {
    return NativeBridgeJNI.get_ExperimentConfig_exactPeriodUChar(swigCPtr);
  }

  public void setNumberOfSamples(int numberOfSamples) {
    NativeBridgeJNI.set_ExperimentConfig_numberOfSamples(swigCPtr, numberOfSamples);
  }

  public int getNumberOfSamples() {
    return NativeBridgeJNI.get_ExperimentConfig_numberOfSamples(swigCPtr);
  }

  public void setDataReadPeriod(float dataReadPeriod) {
    NativeBridgeJNI.set_ExperimentConfig_dataReadPeriod(swigCPtr, dataReadPeriod);
  }

  public float getDataReadPeriod() {
    return NativeBridgeJNI.get_ExperimentConfig_dataReadPeriod(swigCPtr);
  }

  public void setDeviceName(String deviceName) {
    NativeBridgeJNI.set_ExperimentConfig_deviceName(swigCPtr, deviceName);
  }

  public String getDeviceName() {
    return NativeBridgeJNI.get_ExperimentConfig_deviceName(swigCPtr);
  }

  public void setNumSensorConfigs(int numSensorConfigs) {
    NativeBridgeJNI.set_ExperimentConfig_numSensorConfigs(swigCPtr, numSensorConfigs);
  }

  public int getNumSensorConfigs() {
    return NativeBridgeJNI.get_ExperimentConfig_numSensorConfigs(swigCPtr);
  }

  public void setSensorConfigArray(SensorConfig sensorConfigArray) {
    NativeBridgeJNI.set_ExperimentConfig_sensorConfigArray(swigCPtr, SensorConfig.getCPtr(sensorConfigArray));
  }

  public SensorConfig getSensorConfigArray() {
    long cPtr = NativeBridgeJNI.get_ExperimentConfig_sensorConfigArray(swigCPtr);
    return (cPtr == 0) ? null : new SensorConfig(cPtr, false);
  }

  public SensorConfig getSensorConfig(int i) {
    long cPtr = NativeBridgeJNI.ExperimentConfig_getSensorConfig(swigCPtr, i);
    return (cPtr == 0) ? null : new SensorConfig(cPtr, false);
  }

  public void createSensorConfigArray(int size) {
    NativeBridgeJNI.ExperimentConfig_createSensorConfigArray(swigCPtr, size);
  }

  public void setSensorConfig(SensorConfig sConfig, int i) {
    NativeBridgeJNI.ExperimentConfig_setSensorConfig(swigCPtr, SensorConfig.getCPtr(sConfig), i);
  }

  public ExperimentConfig() {
    this(NativeBridgeJNI.new_ExperimentConfig(), true);
  }

}
