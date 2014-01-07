package com.ceit.vic.device.controllers;

public class Test {
	public static void main(String[] args) throws Exception {
		
		/*InstalledLocalContainer container = new JBoss42xInstalledLocalContainer(
				new JBossExistingLocalConfiguration("E:/jboss-4.2.3.GA/server/default"));
		container.setHome("E:/jboss-4.2.3.GA");

		container.start();

		// Here you are assured the container is started.
		//先部署相关的ejb文件，不然报错
		Deployable war = new WAR("D:/modules/BDZinfoWeb.war");
		Deployer deployer = new JBossInstalledLocalDeployer(container);
		deployer.deploy(war);

		// Here you are NOT sure the WAR has finished deploying. To be sure you
		// need to use a DeployableMonitor to monitor the deployment. For
		// example
		// the following code deploys the WAR and wait until it is available to
		// serve requests (the URL should point to a resource inside your WAR):
		deployer.deploy(war, new URLDeployableMonitor(new URL(
				"http://localhost:8080/BDZinfoWeb/index.jsp")));

		container.stop();

		// Here you are assured the container is stopped.
*/	}
}
