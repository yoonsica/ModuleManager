package com.ceit.vic.device.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ceit.vic.device.models.Module;
import com.ceit.vic.device.models.Project;
import com.ceit.vic.device.service.ModuleService;

@Controller
@RequestMapping("/modules")
public class ModuleController {
	static Logger logger = Logger.getLogger(ModuleController.class);
	@Autowired
	ModuleService moduleService;
	
	@ResponseBody
	@RequestMapping("/moduleList")
	public List<Module> moduleList() throws Exception {
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/location.properties");
		prop.load(in);
		System.out.println(prop.getProperty("module"));
		List<Module> moduleList = moduleService.moduleStatus(prop.getProperty("module"));
		return moduleList;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteModules",produces="text/plain;charset=UTF-8")
	public String deleteModules(String[] idArray) throws Exception {
		for (String i : idArray) {
			moduleService.deleteModule(i);
		}
		return "删除成功";
	}
	
	@ResponseBody
	@RequestMapping(value="/deployModules",produces="text/plain;charset=UTF-8")
	public String deployModules(String[] idArray) throws Exception {
		for (String i : idArray) {
			moduleService.deploy(i);
		}
		return "部署成功！";
	}
	
	@ResponseBody
	@RequestMapping(value="/undeployModules",produces="text/plain;charset=UTF-8")
	public String undeployModules(String[] idArray) throws Exception {
		for (String i : idArray) {
			moduleService.undeploy(i);
		}
		return "卸载成功！";
	}
	
	@RequestMapping("/status")
	public ModelAndView modules() throws Exception {
		ModelAndView mav = new ModelAndView("index");
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/location.properties");
		prop.load(in);
		System.out.println(prop.getProperty("module"));
		/*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String pathname = request.getSession().getServletContext()
				.getRealPath("/");*/// D:\Program Files\Apache Software
									// Foundation\Tomcat
									// 7.0\webapps\filemanager\
		List<Module> moduleList = moduleService.moduleStatus(prop.getProperty("module"));
		mav.addObject("moduleList", moduleList);
		return mav;
	}

	@ResponseBody
	@RequestMapping("/deploy/{moduleId}")
	public String deploy(@PathVariable String moduleId) throws Exception {
		System.out.println("moduleId:" + moduleId);
		moduleService.deploy(moduleId);
		return "OK";
	}

	@ResponseBody
	@RequestMapping("/undeploy/{moduleId}")
	public String undeploy(@PathVariable String moduleId) throws Exception {
		System.out.println("moduleId:" + moduleId);
		moduleService.undeploy(moduleId);
		return "OK";
	}

	@RequestMapping("/addModule")
	public String addModule(@RequestParam("ejb") CommonsMultipartFile ejbFile,
			@RequestParam("web") CommonsMultipartFile webFile,
			@RequestParam("moduleId") String moduleId,
			@RequestParam("moduleName") String moduleName) throws Exception {
		List<Project> projects = new ArrayList<Project>();
		// 判断文件是否存在
		if (!ejbFile.isEmpty()) {
			String path = moduleService.getModulesLocation() + ejbFile.getOriginalFilename();
			File localFile = new File(path);
			Project ejbProject = new Project();
			ejbProject.setName(localFile.getName().split(".jar")[0]);
			System.out.println("localfile"+localFile.getName());
			ejbProject.setType("ejb");
			projects.add(ejbProject);
			try {
				if (!localFile.exists()) {
					System.out.println("导入ejb文件到仓库");
					ejbFile.transferTo(localFile);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 判断文件是否存在
		if (!webFile.isEmpty()) {
			String path = moduleService.getModulesLocation() + webFile.getOriginalFilename();
			File localFile = new File(path);
			Project webProject = new Project();
			webProject.setName(localFile.getName().split(".war")[0]);
			webProject.setType("web");
			projects.add(webProject);
			try {
				if (!localFile.exists()) {
					webFile.transferTo(localFile);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Module module = new Module();
		module.setId(moduleId);
		module.setLoaded("false");
		module.setName(moduleName);
		module.setProjects(projects);
		moduleService.addModule(module);
		return "redirect:/modules/status";
	}

	@RequestMapping("/updateModule")
	public ModelAndView updateModule(@RequestParam("ejb") CommonsMultipartFile ejbFile,
			@RequestParam("web") CommonsMultipartFile webFile,
			@RequestParam("moduleId") String moduleId,
			@RequestParam("moduleName") String moduleName) throws Exception {
		List<Project> projects = new ArrayList<Project>();

		// 判断文件是否存在
		if (!ejbFile.isEmpty()) {
			String path = moduleService.getModulesLocation() + ejbFile.getOriginalFilename();
			File localFile = new File(path);
			Project ejbProject = new Project();
			ejbProject.setName(localFile.getName());
			ejbProject.setType("ejb");
			projects.add(ejbProject);
			try {
				ejbFile.transferTo(localFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 判断文件是否存在
		if (!webFile.isEmpty()) {
			String path = moduleService.getModulesLocation() + webFile.getOriginalFilename();
			File localFile = new File(path);
			Project webProject = new Project();
			webProject.setName(localFile.getName());
			webProject.setType("web");
			projects.add(webProject);
			try {
				webFile.transferTo(localFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Module module = new Module();
		module.setId(moduleId);
		module.setLoaded("false");
		module.setName(moduleName);
		module.setProjects(projects);
		moduleService.updateModule(module);
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping("/deleteModule/{moduleId}")
	public String deleteModule(@PathVariable("moduleId") String moduleId) throws Exception {
		moduleService.deleteModule(moduleId);
		return "redirect:/modules/status";
	}
	
	@RequestMapping("/toUpdateModule/{moduleId}")
	public ModelAndView toUpdateModule(@PathVariable String moduleId){
		ModelAndView mav = new ModelAndView("update");
		//去service取得module对象
		mav.addObject("module", moduleService.getModuleById(moduleId));
		return mav;
	}
	
	@RequestMapping("/multiDeploy/")
	public String multiDeploy() throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
        //获得参数数组
        String moduleIds[]=request.getParameterValues("moduleId");
        for (String moduleId : moduleIds) {
    		moduleService.deploy(moduleId);
		}
		return "redirect:/modules/status";
	}
	
	@RequestMapping("/multiUnDeploy/{moduleIds}")
	public String multiUnDeploy(@PathVariable String[] moduleIds) throws Exception{
		/*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
        //获得参数数组
        String moduleIds[]=request.getParameterValues("moduleIds");*/
        for (String moduleId : moduleIds) {
        	System.out.println(moduleId);
    		moduleService.undeploy(moduleId);
		}
		return "redirect:/modules/status";
	}
	
	@RequestMapping("/toSetLocation")
	public ModelAndView toSetLocation(){
		ModelAndView mav = new ModelAndView("setLocation");
		//去service取得module对象
		mav.addObject("jbossLocation", moduleService.getJbossLocation());
		mav.addObject("modulesLocation",moduleService.getModulesLocation());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/setLocation")
	public String setLocation(@RequestParam String jbossLocation,
			@RequestParam String modulesLocation) throws Exception {
		System.out.println(jbossLocation+"||"+modulesLocation);
		moduleService.updateLocation(jbossLocation,modulesLocation);
		return "设置成功！";
	}

}
