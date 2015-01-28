package lobstar.hotconfig.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lobstar.hotconfig.config.HotConfig;

public class TestConfig implements HotConfig{
	private Map<String, Object> map = null;
	

	public void reload() {
		map = new HashMap<String, Object>();
		map.put("time", new Date());
		
	}

	public void load() {
		map = new HashMap<String, Object>();
		map.put("time", new Date());
		
	}

	public Map<String, Object> getConfig() {
		return map;
	}
	
}
