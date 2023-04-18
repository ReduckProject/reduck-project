/**
 * 安全管理软件信息获取/策略下发Web Service接口定义
 */
package net.reduck.wssmcommlower;

import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author 陈思萌
 *
 */
@WebService
@Service
public interface WSSmCommLower {
	
	/*
     * 接口：queryConfig
     * 参数：oprCode-操作类型编码
     * 返回：返回参数配置XML内容
     * 功能：安全管理软件向安全设备查询参数配置信息
     * 作者：陈思萌
     */
	@WebMethod
	String queryConfig(@WebParam String oprCode);
	
	/*
     * 接口：queryPolicy
     * 参数：oprCode-操作类型编码
     * 返回：返回安全策略XML内容
     * 功能：安全管理软件向安全设备查询安全策略信息
     * 作者：陈思萌
     */
	@WebMethod
	String queryPolicy(@WebParam String oprCode);
	
	/*
     * 接口：queryView
     * 参数：oprCode-操作类型编码
     * 返回：返回安全视图XML内容
     * 功能：安全管理软件向安全设备查询安全视图信息
     * 作者：陈思萌
     */
	@WebMethod
	String queryView(@WebParam String oprCode);
	
	/*
     * 接口：fillConfig
     * 参数：oprCode-操作类型编码/configXml-参数配置XML内容
     * 返回：参见返回代码规则
     * 功能：安全管理软件向安全设备下发参数配置信息
     * 作者：陈思萌
     */
	@WebMethod
	int fillConfig(@WebParam String oprCode, @WebParam String configXml);
	
	/*
     * 接口：fillPolicy
     * 参数：oprCode-操作类型编码/policyXml-安全策略XML内容
     * 返回：参见返回代码规则
     * 功能：安全管理软件向安全设备下发安全策略信息
     * 作者：陈思萌
     */
	@WebMethod
	int fillPolicy(@WebParam String oprCode, @WebParam String policyXml);
	
	/*
     * 接口：fillCommand
     * 参数：oprCode-操作类型编码/commandXml-控制命令XML内容
     * 返回：参见返回代码规则
     * 功能：安全管理软件向安全设备下发控制命令信息
     * 作者：陈思萌
     */
	@WebMethod
	int fillCommand(@WebParam String oprCode, @WebParam String commandXml);
	
}