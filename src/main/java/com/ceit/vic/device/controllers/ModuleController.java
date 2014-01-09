package com.ceit.vic.device.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping("/status")
	public ModelAndView modules() throws Exception {
		ModelAndView mav = new ModelAndView("MyJsp");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String pathname = request.getSession().getServletContext()
				.getRealPath("/");// D:\Program Files\Apache Software
									// Foundation\Tomcat
									// 7.0\webapps\filemanager\
		System.out.println(pathname);
		List<Module> moduleList = moduleService.moduleStatus(pathname
				+ "WEB-INF/classes/modules.xml");
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
	public String updateModule(@RequestParam("ejb") CommonsMultipartFile ejbFile,
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
		return "redirect:/modules/status";
	}
	
	@RequestMapping("/deleteModule/{moduleId}")
	public String deleteModule(@PathVariable("moduleId") String moduleId) throws Exception {
		moduleService.deleteModule(moduleId);
		return "redirect:/modules/status";
	}
	
	@RequestMapping("/toUpdateModule/{moduleId}")
	public ModelAndView toUpdateModule(@PathVariable String moduleId){
		ModelAndView mav = new ModelAndView("update");
		/*Module module = new Module();
		module.setId("aa");
		module.setName("aaa");
		module.setLoaded("true");
		List<Project> projects = new ArrayList<Project>();
		projects.add(new Project("ejb", "ejb"));
		projects.add(new Project("web","web"));
		module.setProjects(projects);*/
		//去service取得module对象
		mav.addObject("module", moduleService.getModuleById(moduleId));
		return mav;
	}
	
}
