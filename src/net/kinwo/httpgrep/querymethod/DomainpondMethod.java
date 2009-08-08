package net.kinwo.httpgrep.querymethod;

import net.kinwo.httpgrep.handler.WCRetryHandler;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author henry
 */
public class DomainpondMethod {

	private static DomainpondMethod method = null;

	private static String url = "http://www.domainpond.com/cgi-bin/search.pl";

	public static DomainpondMethod getInstance() {
		if (method == null) {
			method = new DomainpondMethod();
		}

		return method;
	}

	public HttpMethod getMethod() {

		PostMethod post = new PostMethod(url);
		post.setMethodRetryHandler(WCRetryHandler.getInstance());
		NameValuePair[] data = {
				new NameValuePair("dotnet", "ON"),
				new NameValuePair("dotcom", "ON"),
				// new NameValuePair("dotorg", "ON"),
				new NameValuePair("expwindow", "today"),
				// new NameValuePair("expwindow", "archive/archive-7"),
				new NameValuePair("m_month", ""),
				new NameValuePair("m_day", ""),
				new NameValuePair("year", "2003"),
				new NameValuePair("expall", "expired"),
				// new NameValuePair("soonall", "soon"),
				// new NameValuePair("nonum", "ON"),
				// new NameValuePair("nodash", "ON"),
				new NameValuePair("minsize", "1"), new NameValuePair("maxsize", "35"), new NameValuePair("startpos", "0"),
				new NameValuePair("total", "0"), new NameValuePair("textonly", "ON"), new NameValuePair("notext", ""),
				new NameValuePair("max_results", "50000") };
		post.setRequestBody(data);

		return post;
	}

}