/*
 *  Copyright (C) 2004  The Concord Consortium, Inc.,
 *  10 Concord Crossing, Concord, MA 01742
 *
 *  Web Site: http://www.concord.org
 *  Email: info@concord.org
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * END LICENSE */

/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version: 1.3.21
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package ccsd.ti;


public class SensorConfig implements 
	org.concord.sensor.SensorConfig
 {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected SensorConfig(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected void finalize() {
    delete();
  }

  public void delete() {
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      NativeBridgeJNI.delete_SensorConfig(swigCPtr);
    }
    swigCPtr = 0;
  }

  protected static long getCPtr(SensorConfig obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

	public boolean isConfirmed()
	{
		return getConfirmed() == 1;
	}
	
	public String getUnit()
	{
		return getUnitStr();
	}

  public void setConfirmed(short confirmed) {
    NativeBridgeJNI.set_SensorConfig_confirmed(swigCPtr, confirmed);
  }

  public short getConfirmed() {
    return NativeBridgeJNI.get_SensorConfig_confirmed(swigCPtr);
  }

  public void setType(int type) {
    NativeBridgeJNI.set_SensorConfig_type(swigCPtr, type);
  }

  public int getType() {
    return NativeBridgeJNI.get_SensorConfig_type(swigCPtr);
  }

  public void setStepSize(float stepSize) {
    NativeBridgeJNI.set_SensorConfig_stepSize(swigCPtr, stepSize);
  }

  public float getStepSize() {
    return NativeBridgeJNI.get_SensorConfig_stepSize(swigCPtr);
  }

  public void setRequiredMax(float requiredMax) {
    NativeBridgeJNI.set_SensorConfig_requiredMax(swigCPtr, requiredMax);
  }

  public float getRequiredMax() {
    return NativeBridgeJNI.get_SensorConfig_requiredMax(swigCPtr);
  }

  public void setRequiredMin(float requiredMin) {
    NativeBridgeJNI.set_SensorConfig_requiredMin(swigCPtr, requiredMin);
  }

  public float getRequiredMin() {
    return NativeBridgeJNI.get_SensorConfig_requiredMin(swigCPtr);
  }

  public void setPort(int port) {
    NativeBridgeJNI.set_SensorConfig_port(swigCPtr, port);
  }

  public int getPort() {
    return NativeBridgeJNI.get_SensorConfig_port(swigCPtr);
  }

  public void setPortName(String portName) {
    NativeBridgeJNI.set_SensorConfig_portName(swigCPtr, portName);
  }

  public String getPortName() {
    return NativeBridgeJNI.get_SensorConfig_portName(swigCPtr);
  }

  public void setName(String name) {
    NativeBridgeJNI.set_SensorConfig_name(swigCPtr, name);
  }

  public String getName() {
    return NativeBridgeJNI.get_SensorConfig_name(swigCPtr);
  }

  public void setUnitStr(String unitStr) {
    NativeBridgeJNI.set_SensorConfig_unitStr(swigCPtr, unitStr);
  }

  public String getUnitStr() {
    return NativeBridgeJNI.get_SensorConfig_unitStr(swigCPtr);
  }

  public void setNumSensorParams(int numSensorParams) {
    NativeBridgeJNI.set_SensorConfig_numSensorParams(swigCPtr, numSensorParams);
  }

  public int getNumSensorParams() {
    return NativeBridgeJNI.get_SensorConfig_numSensorParams(swigCPtr);
  }

  public void setSensorParams(SensorParam sensorParams) {
    NativeBridgeJNI.set_SensorConfig_sensorParams(swigCPtr, SensorParam.getCPtr(sensorParams));
  }

  public SensorParam getSensorParams() {
    long cPtr = NativeBridgeJNI.get_SensorConfig_sensorParams(swigCPtr);
    return (cPtr == 0) ? null : new SensorParam(cPtr, false);
  }

  public String getSensorParam(String key) {
    return NativeBridgeJNI.SensorConfig_getSensorParam(swigCPtr, key);
  }

  public SensorConfig() {
    this(NativeBridgeJNI.new_SensorConfig(), true);
  }

}
