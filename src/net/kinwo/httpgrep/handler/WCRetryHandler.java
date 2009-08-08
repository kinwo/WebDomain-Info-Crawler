package net.kinwo.httpgrep.handler;

import org.apache.commons.httpclient.DefaultMethodRetryHandler;

/**
 * Retry handler for http request
 * 
 * @author henry
 */
public class WCRetryHandler extends DefaultMethodRetryHandler {

	private static WCRetryHandler handler = null;

	public static WCRetryHandler getInstance() {
		if (handler == null) {
			// Provide custom retry handler is necessary
			handler = new WCRetryHandler();
			handler.setRequestSentRetryEnabled(false);
			handler.setRetryCount(7);
		}

		return handler;
	}

}