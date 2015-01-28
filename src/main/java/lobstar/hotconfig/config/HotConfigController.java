package lobstar.hotconfig.config;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lobstar.hotconfig.log.ConfigLog;

import org.slf4j.Logger;

public class HotConfigController {
	
	private static Logger LOG = ConfigLog.getLogger(ConfigBuilder.class);
	
	private ScheduledThreadPoolExecutor poolExecutor;
	
	
	private static final String runPropertiesFileName = "run.properties";
	
	public static final String FRESH = "fresh";
	
	private ConfigFactory factory;
	
	private HotConfig hotConfig;
	
	public HotConfigController(ConfigFactory factory) {
		this.factory = factory;
		this.poolExecutor = new ScheduledThreadPoolExecutor(1);
	}
	
	
	private Properties config;
	
	public synchronized HotConfigController build() throws Exception{
		InputStream propertiesIn = null;
		try {
			Properties properties = new Properties();
			propertiesIn = ConfigBuilder.class.getClassLoader().getResourceAsStream(runPropertiesFileName);
			
			properties.load(propertiesIn);
			
			this.config = properties;	
			
			
		}
			finally {
				if(propertiesIn != null) {
					propertiesIn.close();
				}
			}
			return this;
			
	}
	
	private synchronized void freshConfig() throws Exception{
		
		InputStream propertiesIn = null;
		OutputStream propertiesOut = null;
		
		try{
			Properties properties = new Properties();
			propertiesIn = ConfigBuilder.class.getClassLoader().getResourceAsStream(runPropertiesFileName);
			propertiesOut = new FileOutputStream(ConfigBuilder.class.getClassLoader().getResource(runPropertiesFileName).getPath());
			properties.load(propertiesIn);
			
			properties.setProperty(FRESH, "0");
			
			properties.store(propertiesOut, "");
			propertiesOut.flush();
		}
		finally {
			if(propertiesOut != null) {
				propertiesOut.close();
			}
			if(propertiesIn != null) {
				propertiesIn.close();
			}
		}
		
	}
	
	
	public void startMonitor(){
		buildHotConfig();
		poolExecutor.scheduleAtFixedRate(new ConfigMonitor(), 0, 5, TimeUnit.SECONDS);
	}
	
	
	private class ConfigMonitor extends Thread {

		public void run() {
			try {
				build();
				
				if("1".equals(config.getProperty(FRESH))) {
					synchronized (HotConfigController.this) {
						HotConfigController.this.hotConfig.reload();
						freshConfig();
					}
					
					
					
				}
			} catch (Exception e) {
				LOG.error("",e);
			}
		}
	}
	
	
	
	private synchronized void buildHotConfig(){
		if(this.factory != null) {
			this.hotConfig = this.factory.getInstance();
			this.hotConfig.load();
		}
	}
	
	public synchronized Map<String,Object> getConfig(){
		if(this.hotConfig != null) {
			return this.hotConfig.getConfig();
		}else {
			return null;
		}
	}
	
}
