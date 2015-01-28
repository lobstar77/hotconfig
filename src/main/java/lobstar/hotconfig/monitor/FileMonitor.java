package lobstar.hotconfig.monitor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import lobstar.hotconfig.config.ConfigBuilder;
import lobstar.hotconfig.config.HotConfig;
import lobstar.hotconfig.log.ConfigLog;

public class FileMonitor {
	private static Logger LOG = ConfigLog.getLogger(ConfigBuilder.class);
	
	private ScheduledThreadPoolExecutor poolExecutor;
	private int heartbeat = 1;
	private HotConfig hotConfig;
	
	public FileMonitor() {
		poolExecutor = new ScheduledThreadPoolExecutor(1);
		Thread monitor = new MonitorThread();
		poolExecutor.scheduleAtFixedRate(monitor, 0, heartbeat, TimeUnit.SECONDS);
		
	}
	
	public void setHotConfig(HotConfig hotConfig){
		this.hotConfig = hotConfig;
	}
	
	private class MonitorThread extends Thread {
		public void run() {
			try {
				ConfigBuilder.build();
				if("1".equals(ConfigBuilder.isFresh().trim())){
					if(hotConfig != null) {
						hotConfig.reload();						
					}
				}
			} catch (Exception e) {
				LOG.error("config build error !",e);
			}
		}
	}
}
