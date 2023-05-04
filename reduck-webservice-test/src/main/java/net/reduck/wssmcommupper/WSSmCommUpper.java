/**
 * 安全设备信息上报Web Service接口定义
 */
package net.reduck.wssmcommupper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author 陈思萌
 *
 */
@WebService
public interface WSSmCommUpper {
	
	/*
     * 接口：reportConfig
     * 参数：deviceId-硬件唯一编号/oprCode-操作类型编码/configXml-参数配置XML内容
     * 返回：参见返回代码规则
     * 功能：安全设备向安全管理软件上报参数配置信息
     * 作者：陈思萌
     */
	@WebMethod
	int reportConfig(@WebParam String deviceId, @WebParam String oprCode, @WebParam String configXml);
	
	/*
     * 接口：reportPolicy
     * 参数：deviceId-设备唯一编号/oprCode-操作类型编码/policyXml-安全策略XML内容
     * 返回：参见返回代码规则
     * 功能：安全设备向安全管理软件上报安全策略信息
     * 作者：陈思萌
     */
	@WebMethod
	int reportPolicy(@WebParam String deviceId, @WebParam String oprCode, @WebParam String policyXml);
	
	/*
     * 接口：reportHealthView
     * 参数：deviceId-设备唯一编号/oprCode-操作类型编码/viewXml-安全视图XML内容
     * 返回：参见返回代码规则
     * 功能：安全设备向安全管理软件上报系统健康视图信息
     * 作者：陈思萌
     */
	@WebMethod
	int reportHealthView(@WebParam String deviceId, @WebParam String oprCode, @WebParam String viewXml);
	
	/*
     * 接口：reportView
     * 参数：deviceId-设备唯一编号/oprCode-操作类型编码/viewXml-安全视图XML内容
     * 返回：参见返回代码规则
     * 功能：安全设备向安全管理软件上报安全视图信息
     * 作者：陈思萌
     */
	@WebMethod
	int reportView(@WebParam String deviceId, @WebParam String oprCode, @WebParam String viewXml);
	
	/*
     * 接口：reportEvent
     * 参数：deviceId-设备唯一编号/oprCode-操作类型编码/eventXml-事件XML内容
     * 返回：参见返回代码规则
     * 功能：安全设备向安全管理软件上报安全事件
     * 作者：陈思萌
     */
	@WebMethod
	int reportEvent(@WebParam String deviceId, @WebParam String oprCode, @WebParam String eventXml);
	
	/*
     * 接口：reportLog
     * 参数：deviceId-设备唯一编号/oprCode-操作类型编码/logXml-日志XML内容
     * 返回：参见返回代码规则
     * 功能：安全设备向安全管理软件上报系统日志
     * 作者：陈思萌
     */
	@WebMethod
	int reportLog(@WebParam String deviceId, @WebParam String oprCode, @WebParam String logXml);
}