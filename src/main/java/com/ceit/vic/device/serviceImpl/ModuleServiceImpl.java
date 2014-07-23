package com.ceit.vic.device.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
	@Override
	public String getModulesLocation() {
		return modulesLocation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> moduleStatus(String xmlPath) throws Exception {
		file = new File(xmlPath);
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(file);
		rootElm = document.getRootElement();
		System.out.println(rootElm.getName());
		List<Element> moduleElmList = rootElm.elements();
		List<Module> moduleList = new ArrayList<Module>();
		String serverIp = getServerIp();
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
					if (project.getType().equals("web")) {
						module.setUrl("http://"+serverIp+":8080/"
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
		String serverIp = getServerIp();
		Deployable ejb = null, web = null;
		String webURL = null;
		Module module = moduleMap.get(moduleId);
		List<Project> projects = module.getProjects();
		for (Project project : projects) {
			if (project.getType().equals("ejb")) {
				ejb = new EJB(modulesLocation + project.getName() + ".jar");
			} else if (project.getType().equals("web")) {
				web = new WAR(modulesLocation + project.getName() + ".war");
				webURL = "http://"+serverIp+":8080/" + project.getName()
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
		module.setLoaded("false");
		moduleMap.put(moduleId, module);
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
		OutputFormat   format   =   OutputFormat.createPrettyPrint(); 
		format.setEncoding( "UTF-8"); 
		XMLWriter writer  = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document);
		writer.close();
		moduleMap.put(module.getId(), module);
	}

	@Override
	public void deleteModule(String moduleId){
		Module module = moduleMap.get(moduleId);
		System.out.println(module+"************"+module.getLoaded());
		if (module.getLoaded().equals("true")) {
			try {
				undeploy(moduleId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (Project project : module.getProjects()) {
			if (project.getType().equals("ejb")) {
				File file = new File(modulesLocation+project.getName()+".jar");
				System.out.println("删除对应的ejb工程"+modulesLocation+project.getName()+".jar");
				if (file.isDirectory()) {
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					file.delete();
				}
			}else if (project.getType().equals("web")) {
				File file = new File(modulesLocation+project.getName()+".war");
				System.out.println("删除对应的web工程"+modulesLocation+project.getName()+".war");
				if (file.isDirectory()) {
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					file.delete();
				}
			}
		}
		Element element = rootElm.elementByID(moduleId);
		rootElm.remove(element);
		OutputFormat outFmt = new OutputFormat("\t", true);
		outFmt.setEncoding("UTF-8");
		XMLWriter output;
		try {
			output = new XMLWriter(new FileOutputStream(file), outFmt);
			output.write(document);
			output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		moduleMap.remove(module.getId());
	}

	@Override
	public void updateModule(Module module) throws Exception {
		Module module2 = moduleMap.get(module.getId());
		Project ejbProject = null,webProject = null;
		for (Project project : module2.getProjects()) {
			if (project.getType().equals("ejb")) {
				ejbProject = project;
			}else {
				webProject = project;
			}
		}
		if (module.getProjects().size()==0) {
			module.setProjects(module2.getProjects());
		}else if (module.getProjects().size()==1) {
			List<Project> projects = module.getProjects();
			if (projects.get(0).getType().equals("ejb")) {
				projects.add(webProject);
			}else {
				projects.add(ejbProject);
			}
			module.setProjects(projects);
		}
		deleteModule(module.getId());
		Element oldModuleElement = rootElm.elementByID(module.getId());
		rootElm.remove(oldModuleElement);
		
		Element moduleElement = rootElm.addElement("module");
		moduleElement.addAttribute("ID", module.getId())
				.addAttribute("name", module.getName())
				.addAttribute("loaded", module.getLoaded());
		
		for (Project project : module.getProjects()) {
				Element projectElement = moduleElement.addElement("project");
				projectElement.addAttribute("name",project.getName() );
				projectElement.addAttribute("type",project.getType() );
		}
		
		moduleMap.put(module.getId(), module);
		OutputFormat outFmt = new OutputFormat("\t", true);
		outFmt.setEncoding("UTF-8");
		XMLWriter output = new XMLWriter(new FileOutputStream(file), outFmt);
		output.write(document);
		output.close();
	}
	@Override
	public Module getModuleById(String moduleId) {
		return moduleMap.get(moduleId);
	}
	@Override
	public String getJbossLocation() {
		return jbossLocation;
	}
	@Override
	public void updateLocation(String jbossLocation, String modulesLocation) throws Exception {
		Element moduleElement = rootElm.elementByID("jbossLocation");
		moduleElement.setText(jbossLocation);
		Element moduleElement1 = rootElm.elementByID("modulesLocation");
		moduleElement1.setText(modulesLocation);
		OutputFormat outFmt = new OutputFormat("\t", true);
		outFmt.setEncoding("UTF-8");
		XMLWriter output = new XMLWriter(new FileOutputStream(file), outFmt);
		output.write(document);
		output.close();
	}
	@Override
	public String getServerIp(){  
		System.out.println("***");
	    try {  
	    	InetAddress inet = InetAddress.getLocalHost();

	        String hostAddress=inet.getHostAddress();
	        /*Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();  
	        InetAddress ip = null;  
	        while (netInterfaces.hasMoreElements()) {  
	            NetworkInterface ni = (NetworkInterface) netInterfaces  
	                    .nextElement();  
	            ip = (InetAddress) ni.getInetAddresses().nextElement();  
	            SERVER_IP = ip.getHostAddress();  
	            if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()  
	                    && ip.getHostAddress().indexOf(":") == -1) {  
	                SERVER_IP = ip.getHostAddress();  
	                break;  
	            } else {  
	                ip = null;  
	            }  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	     */ 
	        return hostAddress;
	      }catch (Exception e) {  
		        e.printStackTrace();  
		    }  
	       return null;
	   } 

}
