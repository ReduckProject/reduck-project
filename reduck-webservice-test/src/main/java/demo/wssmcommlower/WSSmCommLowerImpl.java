package demo.wssmcommlower;

import net.reduck.AESTool;

/**
 * WSSmCommLower的接口实现类，安全设备可以参照此类中的逻辑进行代码实现
 * @author hx
 * @date 2015年11月1日
 */
public class WSSmCommLowerImpl implements WSSmCommLower {
	
	private String localDeviceID = "";//由安全管理软件通过SDMI_Config下发的设备ID
	
	private static String initialVector = "0000000000000000"; //初始化向量，必须为长度为16的字符串

	@Override
	public String queryConfig(String oprCode) {
		//1、采用自身的deviceID进行MD5运算得到解密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、使用解密密钥对收到的数据进行解密
		try {
			String realOprCode = AESTool.decrypt(oprCode, key, initialVector);
		} catch (Exception e) {
		}
		//3、根据具体的操作码进行结果查询，并拼装XML
		String xml = "此处应该根据具体的操作码生成真正的XML";
		//4、对拼装好的XML加密后返回给安全管理软件
		try {
			String encryptedXML = AESTool.encrypt(xml, key, initialVector);
			return encryptedXML;
		} catch (Exception e) {
		}
		//若错误，返回null
		return null;
	}

	@Override
	public String queryPolicy(String oprCode) {
		//1、采用自身的deviceID进行MD5运算得到解密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、使用解密密钥对收到的数据进行解密
		try {
			String realOprCode = AESTool.decrypt(oprCode, key, initialVector);
		} catch (Exception e) {
		}
		//3、根据具体的操作码进行结果查询，并拼装XML
		String xml = "此处应该根据具体的操作码生成真正的XML";
		//4、对拼装好的XML加密后返回给安全管理软件
		try {
			String encryptedXML = AESTool.encrypt(xml, key, initialVector);
			return encryptedXML;
		} catch (Exception e) {
		}
		//若错误，返回null
		return null;
	}

	@Override
	public String queryView(String oprCode) {
		//1、采用自身的deviceID进行MD5运算得到解密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、使用解密密钥对收到的数据进行解密
		try {
			String realOprCode = AESTool.decrypt(oprCode, key, initialVector);
		} catch (Exception e) {
		}
		//3、根据具体的操作码进行结果查询，并拼装XML
		String xml = "此处应该根据具体的操作码生成真正的XML";
		//4、对拼装好的XML加密后返回给安全管理软件
		try {
			String encryptedXML = AESTool.encrypt(xml, key, initialVector);
			return encryptedXML;
		} catch (Exception e) {
		}
		//若错误，返回null
		return null;
	}

	@Override
	public int fillConfig(String oprCode, String configXml) {
		if (oprCode.equals("SDMI_Config_1.00")){
			//1、采用默认密钥解密收到的数据
			String key = "12345678901234567890123456789012";
			try {
				String realConfigXML = AESTool.decrypt(configXml, key, initialVector);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//2、根据安全管理软件配置的DeviceID更新本地数据，用以进行后续通信
			//添加更新本地DeviceID的代码
		}else{
			//1、采用自身的deviceID进行MD5运算得到解密密钥
			String key = AESTool.getMD5(localDeviceID);
			//2、使用解密密钥对收到的数据进行解密
			try {
				String realOprCode = AESTool.decrypt(oprCode, key, initialVector);
				String realConfigXML = AESTool.decrypt(configXml, key, initialVector);
			} catch (Exception e) {
			}
			//3、根据收到的操作码和xml内容进行参数配置，并根据数据上报配置重新调整各类数据上报的机制
			//添加进行参数配置的代码
		}
		return 0;
	}

	@Override
	public int fillPolicy(String oprCode, String policyXml) {
		//1、采用自身的deviceID进行MD5运算得到解密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、使用解密密钥对收到的数据进行解密
		try {
			String realOprCode = AESTool.decrypt(oprCode, key, initialVector);
			String realPolicyXML = AESTool.decrypt(policyXml, key, initialVector);
		} catch (Exception e) {
		}
		//3、根据收到的操作码和xml内容进行策略配置
		//添加进行策略配置的代码
		return 0;
	}

	@Override
	public int fillCommand(String oprCode, String commandXml) {
		//1、采用自身的deviceID进行MD5运算得到解密密钥
		String key = AESTool.getMD5(localDeviceID);
		//2、使用解密密钥对收到的数据进行解密
		try {
			String realOprCode = AESTool.decrypt(oprCode, key, initialVector);
			String realCommandXML = AESTool.decrypt(commandXml, key, initialVector);
		} catch (Exception e) {
		}
		//3、根据收到的操作码和xml内容执行操作命令
		//添加执行操作命令的代码
		return 0;
	}

}
