package com.kinwo.httpgrep.querymethod;

import java.io.IOException;

import junit.framework.TestCase;
import net.kinwo.httpgrep.logging.WCLog;
import net.kinwo.httpgrep.querymethod.DomainpondMethod;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;

/**
 * Unit test for DomainpondMethod
 * 
 * @author henry
 */
public class DomainpondMethodTest extends TestCase {

	public void testPercent() {
		int cnt = 10;
		int numProcessed = 151;

		double percent = (cnt * 1.0 / numProcessed) * 100;
		assertTrue(percent > 0);
	}

	public void testQuery() {
		HttpClient client = new HttpClient();
		HttpMethod method = DomainpondMethod.getInstance().getMethod();
		boolean caught = false;

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				WCLog.logger.info("Method failed: " + method.getStatusLine());
			}

			WCLog.logger.info(method.getResponseBodyAsString());

		} catch (IOException e) {
			WCLog.logger.fatal("Failed to download file.", e);
			caught = true;
		} finally {
			// Release the connection.
			method.releaseConnection();
		}

		assertFalse("No exception should be caught.", caught);

	}

}