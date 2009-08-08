package net.kinwo.httpgrep.querymethod;

import net.kinwo.httpgrep.handler.WCRetryHandler;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author henry
 */
public class OvertureMethod {

	private static OvertureMethod method = null;

	private static String url = "http://inventory.overture.com/d/searchinventory/suggestion/";

	public static OvertureMethod getInstance() {
		if (method == null) {
			method = new OvertureMethod();
		}

		return method;
	}

	public HttpMethod getMethod(String keyword) {

		PostMethod post = new PostMethod(url);
		post.setMethodRetryHandler(WCRetryHandler.getInstance());
		NameValuePair[] data = { new NameValuePair("mkt", "us"), new NameValuePair("lang", "en_us"),
				new NameValuePair("term", keyword) };
		post.setRequestBody(data);

		return post;
	}

}