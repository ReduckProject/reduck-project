package net.reduck.wssmcommlower;

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
				System.out.println(realConfigXML);
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

	public static void main(String[] args) {
		String code = "SDMI_Config_1.00";

		String data = "c310d2bdace750a103a8633757796038b0bd297c8e3c070a965151720b51f650060163f3a75378c0ae182feb2926bffdc4bc6ffb05adba9670dafb636308c0c09dd9d816ff5fe8109b0280ec72791cb53b25edbbdb17f192b17fae6629cee9fbd4f89d391e3514433987700a6f93c6962ad087530d6f3848725a64ed5d560c13ff15e143e87a8449a30036a334fead7dd67f0fedc93b85fb1ba0fabeec9829b78050bfecd4aaccf11b7a7873440fddef6f64305d05c93cabdd163993ab448519df5a7f5f1b8c428de5e800c256a591dcf901a47204c0ae71a8a3d3e5b125d0c032c57b691955f8f73b2ad153c65ad1b6cc24f9bfeb1bb06039fa34367b47e95b6869865802c9a0805b2633e874e784c8d8f69af236c4db787f47f5aebf68a71306ed5737ecff850acb5ac6c2862476ee6c54c6b42fc3e1ccbea9d4d8f1897d85a5206d25272e2a31fde295eaf94d3e56"
;

		new WSSmCommLowerImpl().fillConfig(code, data);
	}

}
