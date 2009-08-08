package net.kinwo.httpgrep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.kinwo.httpgrep.logging.WCLog;
import net.kinwo.httpgrep.querymethod.DomainpondMethod;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;

/**
 * Manage the source list for query
 * 
 * @author henry
 */
public class SourceManager {

	public static final String FILE = "FILE";

	private static final String inFileName = "keywords.txt";

	public static final String WEB = "WEB";

	private static List<String> fromDomainpond() {
		HttpClient client = new HttpClient();
		List<String> queryList = new ArrayList<String>();
		HttpMethod queryMethod = DomainpondMethod.getInstance().getMethod();

		try {
			int statusCode = client.executeMethod(queryMethod);

			if (statusCode != HttpStatus.SC_OK) {
				WCLog.logger.info("Method failed: " + queryMethod.getStatusLine());
			}

			String content = queryMethod.getResponseBodyAsString();
			StringTokenizer tok = new StringTokenizer(content, "\r\n");
			while (tok.hasMoreElements()) {
				String token = tok.nextToken();
				token = token.replaceAll("<br>", "");
				queryList.add(token);
			}

		} catch (IOException e) {
			WCLog.logger.fatal("Failed to download file.", e);
		}

		queryMethod.releaseConnection();
		return queryList;

	}

	private static List<String> fromFile() {
		List<String> list = new ArrayList<String>();

		try {
			BufferedReader bf = new BufferedReader(new FileReader(inFileName));
			String theLine = "";
			while ((theLine = bf.readLine()) != null) {
				list.add(theLine);
			}

		} catch (Exception e) {
			WCLog.logger.fatal("Exception caught while processing file=" + inFileName, e);
		}

		return list;

	}

	/**
	 * @return
	 */
	private static List<String> fromWeb() {
		return fromDomainpond();
	}

	/**
	 * @param srcType
	 * @return
	 */
	public static List<String> select(String srcType) {
		if (FILE.equals(srcType)) {
			return fromFile();
		}

		return fromWeb();
	}

	/**
	 * @return
	 */
	public static String supportedSrcString() {
		return FILE + " or " + WEB;
	}

	/**
	 * @param srcType
	 * @return
	 */
	public static boolean valid(String srcType) {
		if (FILE.equals(srcType) || WEB.equals(srcType)) {
			return true;
		}

		return false;
	}

}