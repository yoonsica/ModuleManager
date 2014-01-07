package com.ceit.vic.device.service;

import java.util.List;
import com.ceit.vic.device.models.Module;

public interface ModuleService {
	/**
	 * 根据modules.xml文件获取模块集合
	 * @param xmlPath modules.xml路径
	 * @return 返回module集合，供jsp调用
	 */
	public List<Module> moduleStatus(String xmlPath) throws Exception;
	/**
	 * 部署指定id的模块
	 * @param moduleId 模块id
	 */
	public void deploy(String moduleId) throws Exception;
	/**
	 * 卸载指定id模块
	 * @param moduleId
	 * @throws Exception
	 */
	public void undeploy(String moduleId) throws Exception;
	/**
	 * 在xml里新增一个模块
	 * @param module
	 * @throws Exception
	 */
	public void addModule(Module module) throws Exception;
	/**
	 * 根据模块id删除模块
	 * @param moduleId
	 * @throws Exception 
	 */
	public void deleteModule(String moduleId);
	/**
	 * 更新模块
	 * @param module
	 * @throws Exception
	 */
	public void updateModule(Module module) throws Exception;
	String getModulesLocation();
}
