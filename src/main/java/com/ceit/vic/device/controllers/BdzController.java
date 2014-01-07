package com.ceit.vic.device.controllers;




import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.device.models.Module;
import com.ceit.vic.device.service.ModuleService;

@Controller
public class BdzController {
	static Logger logger = Logger.getLogger(BdzController.class);
	@Autowired
	ModuleService moduleService;
	@RequestMapping("/modules")
	public ModelAndView modules() throws Exception {
		ModelAndView mav = new ModelAndView("MyJsp"); 
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String pathname = request.getSession().getServletContext().getRealPath("/");//D:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\filemanager\
		System.out.println(pathname);
		List<Module> moduleList = moduleService.moduleStatus(pathname+"WEB-INF/classes/modules.xml");
		mav.addObject("moduleList",moduleList);
		return mav;
	}
	@ResponseBody
	@RequestMapping("/deploy/{moduleId}")
	public String deploy(@PathVariable String moduleId) throws Exception {
		System.out.println("moduleId:"+moduleId);
		moduleService.deploy(moduleId);
		return "OK";
	}
	
	@ResponseBody
	@RequestMapping("/undeploy/{moduleId}")
	public String undeploy(@PathVariable String moduleId) throws Exception {
		System.out.println("moduleId:"+moduleId);
		moduleService.undeploy(moduleId);
		return "OK";
	}
}
