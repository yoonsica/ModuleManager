package com.ceit.vic.device.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.deployable.Deployable;
import org.codehaus.cargo.container.deployable.EJB;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.container.deployer.Deployer;
import org.codehaus.cargo.container.jboss.JBoss42xInstalledLocalContainer;
import org.codehaus.cargo.container.jboss.JBossExistingLocalConfiguration;
import org.codehaus.cargo.container.jboss.JBossInstalledLocalDeployer;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import com.ceit.vic.device.models.Module;
import com.ceit.vic.device.models.Project;
import com.ceit.vic.device.service.ModuleService;

@Component("moduleService")
public class ModuleServiceImpl implements ModuleService {
	String jbossLocation, modulesLocation;
	Document document;
	Element rootElm;
	File file;
	Map<String, Module> moduleMap = new HashMap<String, Module>();
	InstalledLocalContainer container;

	public ModuleServiceImpl() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> moduleStatus(String xmlPath) throws Exception {
		System.out.println(xmlPath);
		file = new File(xmlPath);
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(file);
		rootElm = document.getRootElement();
		System.out.println(rootElm.getName());
		List<Element> moduleElmList = rootElm.elements();
		List<Module> moduleList = new ArrayList<Module>();
		for (Element moduleElement : moduleElmList) {
			if (moduleElement.attributeValue("ID").equals("jbossLocation")) {
				jbossLocation = moduleElement.getTextTrim();
			} else if (moduleElement.attributeValue("ID").equals(
					"modulesLocation")) {
				modulesLocation = moduleElement.getTextTrim();
			} else {
				Module module = new Module();
				module.setName(moduleElement.attributeValue("name"));
				module.setId(moduleElement.attributeValue("ID"));
				module.setLoaded(moduleElement.attributeValue("loaded"));
				List<Element> projectElmList = moduleElement.elements();
				List<Project> projectList = new ArrayList<Project>();
				for (Element projectElement : projectElmList) {
					Project project = new Project();
					project.setName(projectElement.attributeValue("name"));
					project.setType(projectElement.attributeValue("type"));
					if (project.getName().equals("web")) {
						module.setUrl("http://localhost:8080/"
								+ project.getName() + "/index.jsp");
					}
					projectList.add(project);
				}
				module.setProjects(projectList);
				moduleList.add(module);
				moduleMap.put(module.getId(), module);
			}
		}
		container = new JBoss42xInstalledLocalContainer(
				new JBossExistingLocalConfiguration(jbossLocation
						+ "server/default"));
		container.setHome(jbossLocation);
		return moduleList;
	}

	@Override
	public void deploy(String moduleId) throws Exception {
		/*
		 * InstalledLocalContainer container = new
		 * JBoss42xInstalledLocalContainer( new
		 * JBossExistingLocalConfiguration(jbossLocation+"server/default"));
		 * container.setHome(jbossLocation);
		 */
		Deployable ejb = null, web = null;
		String webURL = null;
		Module module = moduleMap.get(moduleId);
		List<Project> projects = module.getProjects();
		for (Project project : projects) {
			if (project.getType().equals("ejb")) {
				ejb = new EJB(modulesLocation + project.getName() + ".jar");
			} else if (project.getType().equals("web")) {
				web = new WAR(modulesLocation + project.getName() + ".war");
				webURL = "http://localhost:8080/" + project.getName()
						+ "/index.jsp";
			}
		}
		// container.start();
		Deployer deployer = new JBossInstalledLocalDeployer(container);
		if (ejb != null) {
			System.out.println("开始部署ejb........");
			deployer.deploy(ejb);
			System.out.println("结束部署ejb........");
		}
		if (web != null) {
			System.out.println("开始部署web........");
			// deployer.deploy(web, new URLDeployableMonitor(new URL(webURL)));
			deployer.deploy(web);
			System.out.println("结束部署web........请访问" + webURL);
		}
		Element moduleElement = rootElm.elementByID(moduleId);
		moduleElement.attribute("loaded").setValue("true");
		OutputFormat outFmt = new OutputFormat("\t", true);
		outFmt.setEncoding("UTF-8");
		XMLWriter output = new XMLWriter(new FileOutputStream(file), outFmt);
		output.write(document);
		output.close();
		System.out.println("xml更新完毕");
	}

	@Override
	public void undeploy(String moduleId) throws Exception {
		Module module = moduleMap.get(moduleId);
		List<Project> projects = module.getProjects();
		Deployable web = null;
		for (Project project : projects) {
			if (project.getType().equals("web")) {
				web = new WAR(modulesLocation + project.getName() + ".war");
			}
		}
		Deployer deployer = new JBossInstalledLocalDeployer(container);
		deployer.undeploy(web);
		System.out.println("undeploy" + module.getName());
		Element moduleElement = rootElm.elementByID(moduleId);
		moduleElement.attribute("loaded").setValue("false");
		OutputFormat outFmt = new OutputFormat("\t", true);
		outFmt.setEncoding("UTF-8");
		XMLWriter output = new XMLWriter(new FileOutputStream(file), outFmt);
		output.write(document);
		output.close();
	}

	@Override
	public void addModule(Module module)throws Exception {
		Element moduleElement = rootElm.addElement("module");
		moduleElement.addAttribute("ID", module.getId())
				.addAttribute("name", module.getName())
				.addAttribute("loaded", module.getLoaded());
		for (Project project : module.getProjects()) {
			Element projectElement = moduleElement.addElement("project");
			projectElement.addAttribute("name",project.getName() );
			projectElement.addAttribute("type",project.getType() );
		}
		OutputFormat outFmt = new OutputFormat("\t", true);
		outFmt.setEncoding("UTF-8");
		XMLWriter output = new XMLWriter(new FileOutputStream(file), outFmt);
		output.write(document);
		output.close();
	}

}
