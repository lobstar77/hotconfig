package lobstar.hotconfig.test;

import lobstar.hotconfig.config.ConfigFactory;
import lobstar.hotconfig.config.HotConfigController;

public class TestMonitor {

	public static void main(String[] args) throws Exception {
		ConfigFactory factory = new ConfigFactory(TestConfig.class);
		HotConfigController configController = new HotConfigController(factory);
		configController.build().startMonitor();;
		while(true) {
			System.out.println(configController.getConfig());
			Thread.sleep(100);
		}
	}
}
