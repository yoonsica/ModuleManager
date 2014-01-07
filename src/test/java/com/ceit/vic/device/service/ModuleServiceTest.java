package com.ceit.vic.device.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import com.ceit.vic.device.models.Module;
import com.ceit.vic.device.models.Project;
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
