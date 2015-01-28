package lobstar.hotconfig.config;

import lobstar.hotconfig.log.ConfigLog;

import org.slf4j.Logger;

public class ConfigFactory {
	private static Logger LOG = ConfigLog.getLogger(ConfigBuilder.class);
	private Class<? extends HotConfig> clazz;
	
	private HotConfig instance;
	
	public ConfigFactory(Class<? extends HotConfig> clazz) {
		this.clazz = clazz;
	}
	
	public HotConfig getInstance(){
		if(instance == null) {
			try {
				synchronized (this) {
					if(instance == null) {
						instance = clazz.newInstance();						
					}
				}
			} catch (Exception e) {
				LOG.error("",e);
			} 
		}
		return instance;
	}
}
