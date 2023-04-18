package net.reduck;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import net.reduck.wssmcommupper.WSSmCommUpper;

/**
 * 向安全管理软件上报数据的demo， 安全设备可以参照此类进行代码实现
 * @author hx
 * @date 2015年11月1日
 */
public class ReportDemo {

	private String SMS_IP = "127.0.0.1";//安全管理软件通过SDMI_Config下发的安管IP
	private String SMS_PORT = "80";//安全管理软件通过SDMI_Config下发的安管端口
	private String SMS_WS_PATH = "/WSSmCommUpper/WSSmCommUpper";//安全管理软件的WebService路径

	private String localDeviceID = "";//安全管理软件通过SDMI_Config下发的设备ID

	private static String initialVector = "0000000000000000"; //初始化向量，必须为长度为16的字符串

	public ReportDemo() {
		String oprCode = "操作码";
		String XML = "需要上报的XML";
		//上报策略
		reportPolicy(localDeviceID, oprCode, XML);
		//上报系统状态视图（心跳）
		reportHealthView(localDeviceID, oprCode, XML);
		//上报其余视图
		reportView(localDeviceID, oprCode, XML);
		//上报告警
		reportEvent(localDeviceID, oprCode, XML);
		//上报日志
		reportLog(localDeviceID, oprCode, XML);
	}


	private int reportLog(String deviceId, String oprCode, String logXml){
		//1、采用自身的deviceID进行MD5运算得到加密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、加密操作码和XML两个参数
		try {
			oprCode = AESTool.encrypt(oprCode, key, initialVector);
			logXml = AESTool.encrypt(logXml, key, initialVector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3、发送给安全管理软件
		String address = "http://" + SMS_IP + ":"+ SMS_PORT + SMS_WS_PATH;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WSSmCommUpper.class);
		factory.setAddress(address);
		WSSmCommUpper remoteServer = (WSSmCommUpper) factory.create();
		int ret = remoteServer.reportLog(deviceId, oprCode, logXml);
		return ret;
	}

	private int reportEvent(String deviceId, String oprCode, String eventXml){
		//1、采用自身的deviceID进行MD5运算得到加密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、加密操作码和XML两个参数
		try {
			oprCode = AESTool.encrypt(oprCode, key, initialVector);
			eventXml = AESTool.encrypt(eventXml, key, initialVector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3、发送给安全管理软件
		String address = "http://" + SMS_IP + ":"+ SMS_PORT + SMS_WS_PATH;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WSSmCommUpper.class);
		factory.setAddress(address);
		WSSmCommUpper remoteServer = (WSSmCommUpper) factory.create();
		int ret = remoteServer.reportEvent(deviceId, oprCode, eventXml);
		return ret;
	}

	private int reportPolicy(String deviceId, String oprCode, String policyXml){
		//1、采用自身的deviceID进行MD5运算得到加密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、加密操作码和XML两个参数
		try {
			oprCode = AESTool.encrypt(oprCode, key, initialVector);
			policyXml = AESTool.encrypt(policyXml, key, initialVector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3、发送给安全管理软件
		String address = "http://" + SMS_IP + ":"+ SMS_PORT + SMS_WS_PATH;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WSSmCommUpper.class);
		factory.setAddress(address);
		WSSmCommUpper remoteServer = (WSSmCommUpper) factory.create();
		int ret = remoteServer.reportPolicy(deviceId, oprCode, policyXml);
		return ret;
	}

	private int reportView(String deviceId, String oprCode, String viewXml){
		//1、采用自身的deviceID进行MD5运算得到加密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、加密操作码和XML两个参数
		try {
			oprCode = AESTool.encrypt(oprCode, key, initialVector);
			viewXml = AESTool.encrypt(viewXml, key, initialVector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3、发送给安全管理软件
		String address = "http://" + SMS_IP + ":"+ SMS_PORT + SMS_WS_PATH;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WSSmCommUpper.class);
		factory.setAddress(address);
		WSSmCommUpper remoteServer = (WSSmCommUpper) factory.create();
		int ret = remoteServer.reportView(deviceId, oprCode, viewXml);
		return ret;
	}

	private int reportHealthView(String deviceId, String oprCode, String viewXml){
		//1、采用自身的deviceID进行MD5运算得到加密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、加密操作码和XML两个参数
		try {
			oprCode = AESTool.encrypt(oprCode, key, initialVector);
			viewXml = AESTool.encrypt(viewXml, key, initialVector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3、发送给安全管理软件
		String address = "http://" + SMS_IP + ":"+ SMS_PORT + SMS_WS_PATH;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WSSmCommUpper.class);
		factory.setAddress(address);
		WSSmCommUpper remoteServer = (WSSmCommUpper) factory.create();
		int ret = remoteServer.reportHealthView(deviceId, oprCode, viewXml);
		return ret;
	}

	public static void main(String[] args){
		ReportDemo rd = new ReportDemo();
	}

}
