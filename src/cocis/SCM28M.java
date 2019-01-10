package cocis;

import java.util.Iterator;

import tijos.framework.component.modbus.rtu.ModbusClient;
import tijos.framework.util.json.JSONObject;
import tijos.framework.util.logging.Logger;

/**
 * Cocis Electronics  SCM-28M module 
 * http://www.chinacocis.com/index.php?_m=mod_product&_a=view&p_id=933
 * @author lemon
 *
 */
public class SCM28M {

	String [] registerNames = new String[] {"T1_timer_data",
			"T2_timer_data", 
			"T3_timer_data",
			"T4_timer_data",
			"T5_timer_data",
			"T6_timer_data",
			"T7_timer_data",
			"T8_timer_data",
			"T9_timer_data",
			"T10_timer_data", //9
			"T1_timer",
			"T2_timer",
			"T3_timer",
			"T4_timer",
			"T5_timer",
			"T6_timer",
			"T7_timer",
			"T8_timer",
			"T9_timer",		//18
			"I1_analog", 
			"I2_analog", 
			 "R1_temp_sensor", //21
			 "R2_temp_sensor",
			 "R3_temp_sensor",
			 "R4_temp_sensor",
			 "R5_temp_sensor",
			 "R6_temp_sensor",
			 "X1_DI", //27
			 "X2_DI",
			 "X3_DI",
			 "X4_DI",
			 "X5_DI",
			 "X6_DI",
			 "X7_DI",
			 "X8_DI",
			 "X9_DI",
			 "X10_DI",
			 "X11_DI",
			 "X12_DI",
			 "Y1_DO",	//39
			 "Y2_DO",
			 "Y3_DO",
			 "Y4_DO",
			 "Y5_DO",
			 "Y6_DO",
			 "Y7_DO",
			 "Y8_DO"};
	Object [] registerValues = new Object[registerNames.length];
	
	int address;
	ModbusClient modbusRtu;

 
	/**
	 * Initialize with modbus rtu
	 * @param modbusRtu
	 * @param address modbus address
	 */
	public SCM28M(ModbusClient modbusRtu, int address) {
		this.modbusRtu = modbusRtu;
		this.address = address;
	}

	/**
	 * read timer data registers, 10 registers
	 * @return
	 */
	public int readTimerData()
	{
		Logger.info("SCM28M", "readTimerData ");
		
		// Input Register 开始地址
		int startAddr = 0;
		
		int count = 10;
		
		int result = 0;

		// 读取Holding Register
		modbusRtu.InitReadHoldingsRequest(address, startAddr, count);

		try {
			result = modbusRtu.execRequest();

			if (result == ModbusClient.RESULT_OK) {

				int responseAddr = modbusRtu.getResponseAddress();
				int responseCount = modbusRtu.getResponseCount();
				
				for(int i = 0; i < responseCount; i ++)
				{
					registerValues[startAddr + i] = modbusRtu.getResponseRegister(responseAddr + i);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			result = -1;
		}
		
		return (int)result;
	}

	public int readTimer()
	{
		Logger.info("SCM28M", "readTimer ");
		
		// Input Register 开始地址
		int startAddr = 10;
		
		int count = 9;
		
		int result = 0;

		// 读取Holding Register
		modbusRtu.InitReadHoldingsRequest(address, startAddr, count);

		try {
			result = modbusRtu.execRequest();

			if (result == ModbusClient.RESULT_OK) {

				int responseAddr = modbusRtu.getResponseAddress();
				int responseCount = modbusRtu.getResponseCount();
				
				for(int i = 0; i < responseCount; i ++)
				{
					registerValues[startAddr + i] = modbusRtu.getResponseRegister(responseAddr + i);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			result = -1;
		}
		
		return (int)result;
	}
	
	public int readAnalog()
	{
		Logger.info("SCM28M", "readAnalog ");
		
		// Input Register 开始地址
		int startAddr = 19;
		
		int count = 2;
		
		int result = 0;

		// 读取Holding Register
		modbusRtu.InitReadHoldingsRequest(address, startAddr, count);

		try {
			result = modbusRtu.execRequest();

			if (result == ModbusClient.RESULT_OK) {

				int responseAddr = modbusRtu.getResponseAddress();
				int responseCount = modbusRtu.getResponseCount();
				
				for(int i = 0; i < responseCount; i ++)
				{
					registerValues[startAddr + i] = modbusRtu.getResponseRegister(responseAddr + i) * 20.0 / 4000;;
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			result = -1;
		}
		
		return (int)result;
	}
	
	public int readTempSensor()
	{
		Logger.info("SCM28M", "readTempSensor ");
		
		// Input Register 开始地址
		int startAddr = 21;
		
		int count = 6;
		
		int result = 0;

		// 读取Holding Register
		modbusRtu.InitReadHoldingsRequest(address, startAddr, count);

		try {
			result = modbusRtu.execRequest();

			if (result == ModbusClient.RESULT_OK) {

				int responseAddr = modbusRtu.getResponseAddress();
				int responseCount = modbusRtu.getResponseCount();
				
				System.out.println("responseAddr " + responseAddr + " responseCount " + responseCount);
				
				for(int i = 0; i < responseCount; i ++)
				{
					registerValues[startAddr + i] = (modbusRtu.getResponseRegister(responseAddr + i) - 500) * 0.1;
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			result = -1;
		}
		
		return (int)result;
	}
	
	public int readDI()
	{
		Logger.info("SCM28M", "readDI ");
		
		// Input Register 开始地址
		int startAddr = 27;
		
		int count = 12;
		
		int result = 0;

		// 读取Holding Register
		modbusRtu.InitReadHoldingsRequest(address, startAddr, count);

		try {
			result = modbusRtu.execRequest();

			if (result == ModbusClient.RESULT_OK) {

				int responseAddr = modbusRtu.getResponseAddress();
				int responseCount = modbusRtu.getResponseCount();
				
				for(int i = 0; i < responseCount; i ++)
				{
					registerValues[startAddr + i] = modbusRtu.getResponseRegister(responseAddr + i);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			result = -1;
		}
		
		return (int)result;
	}
	
	public int readDO()
	{
	Logger.info("SCM28M", "readDO ");
		
		// Input Register 开始地址
		int startAddr = 39;
		
		int count = 8;
		
		int result = 0;

		// 读取Holding Register
		modbusRtu.InitReadHoldingsRequest(address, startAddr, count);

		try {
			result = modbusRtu.execRequest();

			if (result == ModbusClient.RESULT_OK) {

				int responseAddr = modbusRtu.getResponseAddress();
				int responseCount = modbusRtu.getResponseCount();
				
				for(int i = 0; i < responseCount; i ++)
				{
					registerValues[startAddr + i] = modbusRtu.getResponseRegister(responseAddr + i);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			result = -1;
		}
		
		return (int)result;
	}
	
	/**
	 * 读取所有寄存器并转换为实际的数据
	 */
	public int readAllRegisters() {
		Logger.info("SCM28M", "readAllRegisters ");
		
		int ret = 0;
		ret = this.readTimerData();
		if(ret != 0)
			return ret;
		ret = this.readTimer();
		if(ret != 0)
			return ret;
		ret = this.readAnalog();
		if(ret != 0)
			return ret;
		ret = this.readTempSensor();
		if(ret != 0)
			return ret;
		ret = this.readDI();
		if(ret != 0)
			return ret;
		ret = this.readDO();
		if(ret != 0)
			return ret;
		
		return ret;
	}
	

	/**
	 * id start from 0
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public int writeTimerData(int id, int value) {

		Logger.info("SCM28M", "writeTimerData " + id + " value " + value);

		int startAddr = 0;
		modbusRtu.InitWriteRegisterRequest(this.address, startAddr + id, value);
		try {
			int result = modbusRtu.execRequest();
			if (result != ModbusClient.RESULT_OK) {
				System.out.println("Modbus Error: result = " + result);
			}

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * id start from 0
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public int writeTimer(int id, int value) {

		Logger.info("SCM28M", "writeTimer " + id + " value " + value);

		int startAddr = 10;
		modbusRtu.InitWriteRegisterRequest(this.address, startAddr + id, value);
		try {
			int result = modbusRtu.execRequest();
			if (result != ModbusClient.RESULT_OK) {
				System.out.println("Modbus Error: result = " + result);
			}

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * write DO, start from 0
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public int writeDO(int id, int value) {

		int startAddr = 40;
		modbusRtu.InitWriteRegisterRequest(this.address, startAddr + id, value);
		try {
			int result = modbusRtu.execRequest();
			if (result != ModbusClient.RESULT_OK) {
				System.out.println("Modbus Error: result = " + result);
			}

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}

	}
	
	/**
	 * find the register by name 
	 * @param key
	 * @return -1 not found, >= 0 index of the key string
	 */
	private int getRegisterAddr(String key)
	{		
		for(int i = 0; i < this.registerNames.length ; i++) {
			if(this.registerNames[i].equals(key)) 
				return i;
		}
		
		return -1;
	}
	
	/**
	 * write register value by key
	 * @param key
	 * @param value
	 * @return
	 */
	public int writeRegister(String key, int value) {
				
		Logger.info("SCM28M", "writeRegister " + key + " value " + value);
		int idx = getRegisterAddr(key);
		if(idx < 0)
			return -1; //not found
		
		//Tx_timer_data
		if(idx < 9)//T1_timer_data
		{
			return writeTimerData(idx, value);
		}
		
		//Tx_timer
		if(idx < 18)
		{
			return this.writeTimer(idx - 10, value);
		}
		
		//Yx_DO
		if(idx >= 39)
		{
			return this.writeDO(idx - 39,	value);
		}
		
		
		return -2; //not allow write
	}
	
	
	@Override
	public String toString() {
	
		JSONObject jObject = new JSONObject();
		
		for(int i = 0; i < registerNames.length ; i++)
		{
			jObject.put(registerNames[i], this.registerValues[i]);
		}
		
	
		return jObject.toString();				
	}

	public static void main(String[] args) {

		SCM28M scm = new SCM28M(null, 1);
		
		for(int i= 0 ; i < scm.registerValues.length ; i++)
		{
			scm.registerValues[i] = i;
		}
		
		System.out.println(scm.toString());
		
		JSONObject jObject = new JSONObject(scm.toString());
		Iterator<String>iter = jObject.keys();
		
		 while(iter.hasNext()){
		  String str = (String) iter.next();
		  
		  System.out.println(str + " value " + jObject.getInt(str));
		 }
		 
		 System.out.println("total mem " + System.getRuntime().totalMemory() + " free mem " + System.getRuntime().freeMemory());
	}

}
