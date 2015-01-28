package lobstar.hotconfig.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lobstar.hotconfig.log.ConfigLog;

import org.slf4j.Logger;

public class ConfigBuilder {
	
	private static Logger LOG = ConfigLog.getLogger(ConfigBuilder.class);
	
	private static String runPropertiesFileName = "run.properties";
	
	public static final String FRESH = "fresh";
	
	private static Properties config;
	
	public static void setFile(String file){
		runPropertiesFileName = file;
	}
	
	public synchronized static void build() throws Exception{
		Properties properties = new Properties();
		InputStream propertiesIn = ConfigBuilder.class.getClassLoader().getResourceAsStream(runPropertiesFileName);
		
		properties.load(propertiesIn);

		config = properties;	
		
	}
	
	
	static {
		try {
			build();		
		} catch (Exception e) {
			LOG.error("build log config error !",e);
		}
	}
	
	public synchronized static String isFresh(){
		return config.getProperty(FRESH);
	}
	
}
