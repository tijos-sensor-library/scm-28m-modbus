
import java.io.IOException;
import java.util.Iterator;

import cocis.SCM28M;
import tibox.NB200;
import tijos.framework.component.modbus.rtu.ModbusClient;
import tijos.framework.component.serialport.TiSerialPort;
import tijos.framework.devicecenter.TiUART;
import tijos.framework.util.Delay;
import tijos.framework.util.json.JSONObject;

/**
 * CoAP测试例程， 建议使用中国移动 NB卡 该例程可使用钛极云测试平台进行测试
 */
public class TiBOXNB200 {
	public static void main(String[] args) {

		try {

			// 启动NB-IoT网络
			NB200.networkStartup();
			
			// 连接COAP服务器
			NB200.networkCoAPConnect("coap://coap.tijos.net:5683");

			// 通讯参数 9600,8,1,N
			TiSerialPort rs485 = NB200.getRS485(9600, 8, 1, TiUART.PARITY_EVEN);

			// MODBUS 客户端
			ModbusClient modbusRtu = new ModbusClient(rs485);

			// SCM28M,  地址1
			SCM28M scm28m = new SCM28M(modbusRtu, 1);
			
			// 防止程序退出
			//int count = 0;
			while (true) //count++ < 10) 
			{

				int ret = scm28m.readAllRegisters();
				if (ret == 0) {
					System.out.println(scm28m.toString());
					// 上报平台
					reportSensor(scm28m.toString());
				}

				commandProcess(scm28m);
				
				System.out.println("running..." + System.currentTimeMillis());
				Delay.msDelay(1000 * 10); // 10秒处理一次
					
				System.out.println("after deley");
			}

		} catch (Exception e) {
			// 有任何异常都会进行捕捉并打印，实际应用中应进行错误处理
			e.printStackTrace();

		}
		
		System.out.println("exit...");
		System.exit(0);
	}

	/**
	 * 数据上报
	 * 
	 * @throws IOException
	 */
	public static void reportSensor(String sensorData) throws IOException {

		try {
			// 产品标识
			String product = "SCM-28M";

			String dataUri = "/topic/" + product + "/" + NB200.networkGetIMEI() + "/data";

			// 发送数据到指定的资源路径
			NB200.networkCoAPPOST(dataUri, sensorData);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 命令处理 NB-IOT的PSM模式下只有在上传之后才能获取下发指令
	 */
	public static void commandProcess(SCM28M scm28m) throws IOException {

		// 产品标识
		String product = "SCM-28M";

		String cmdUri = "/topic/" + product + "/" + NB200.networkGetIMEI() + "/cmd";
		// 获取云端命令
		String cmd = NB200.networkCoAPGET(cmdUri);
		if (cmd == null || cmd.length() == 0)
			return;

		// 有命令来自云端, 需进行处理
		try {
			JSONObject jObject = new JSONObject(cmd);
			
			
			Iterator<String>iter = jObject.keys();
						
			 while(iter.hasNext()){

				 String key = (String) iter.next();
				 int value =  jObject.getInt(key);
			  
				 int ret = scm28m.writeRegister(key, value); 
				 if(ret < 0) {
					 System.out.println("Failed to write register " + key + " value " + value + " ret " + ret);
				 }
			 }
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
