package lobstar.hotconfig.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLog {
	public static Logger getLogger(Class<?> clazz){
		return LoggerFactory.getLogger(clazz);
	}
}
