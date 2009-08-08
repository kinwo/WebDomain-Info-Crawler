package com.kinwo.httpgrep.notify;

import java.util.Date;

import junit.framework.TestCase;
import net.kinwo.httpgrep.notify.EmailNotifier;

/**
 * @author henry
 */
public class EmailNotifierTest extends TestCase {

	/*
	 * Class under test for void sendTextEmail(String, String)
	 */
	public void testSendTextEmailStringString() {
		boolean caught = false;

		try {
			EmailNotifier.sendTextEmail("test-" + new Date(), "test");
		} catch (Exception e) {
			caught = true;
		}

		assertFalse("Exception is caught while sending email.", caught);
	}

}
