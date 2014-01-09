package com.ceit.vic.device.service;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import com.ceit.vic.device.serviceImpl.ModuleServiceImpl;
public class ModuleServiceTest {

	ModuleService service = new ModuleServiceImpl();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsLoaded() throws DocumentException {
		/*List<String> list = Arrays.asList("BDZinfoWeb","BDZinfoEjb");
		service.isLoaded(list);*/
	}
	@Test
	public void testModuleStatus() throws Exception{
		//service.moduleStatus("src/main/resources/modules.xml");
		//service.undeploy("BDZInfo");
		//service.deleteModule("BDZInfo");
	}
}
