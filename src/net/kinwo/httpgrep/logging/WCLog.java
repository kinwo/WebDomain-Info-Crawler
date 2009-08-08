package net.kinwo.httpgrep.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logging facility
 * 
 * @author henry
 */
public class WCLog {

	public static Log logger = LogFactory.getLog("net.kinwo.httpgrep");

	static {
		Logger.getLogger("net.kinwo.httpgrep").setLevel(Level.INFO);
		Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.SEVERE);
	}

}
