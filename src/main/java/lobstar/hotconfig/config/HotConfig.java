package lobstar.hotconfig.config;

import java.util.Map;

public interface HotConfig {
	public void reload();
	
	public void load();
	
	public Map<String, Object> getConfig();
}
