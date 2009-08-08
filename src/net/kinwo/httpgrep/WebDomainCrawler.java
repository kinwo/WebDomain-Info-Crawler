package net.kinwo.httpgrep;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.kinwo.httpgrep.filterconversion.Filter;
import net.kinwo.httpgrep.filterconversion.FilterFactory;
import net.kinwo.httpgrep.logging.WCLog;
import net.kinwo.httpgrep.model.Keyword;
import net.kinwo.httpgrep.model.RankResult;
import net.kinwo.httpgrep.notify.EmailNotifier;
import net.kinwo.httpgrep.processor.OvertureProcessor;
import net.kinwo.httpgrep.processor.Processor;
import net.kinwo.httpgrep.querymethod.OvertureMethod;
import net.kinwo.httpgrep.report.ReportManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;

/**
 * 
 * Multi-threaded WebDomainCrawler
 * 
 * TODO - add ErrorList for recovery retry
 * 
 * @author henry
 * 
 */
public class WebDomainCrawler extends Thread {

	private static final int DURATION = 1000 * 60 * 5;

	private static final String EXT = ".txt";

	private static final boolean ISAPPEND = true;

	public static final String MYNAME = "WebDomainCrawler Ver 1.0 - Robot";

	private static final String OUTFILENAME = "rankings";

	private static final int threadSize = 1;

	public static void main(String args[]) {
		WCLog.logger.info("#### Start WebDomainCrawler ####");
		String srcType = SourceManager.FILE;
		if (args.length >= 1) {
			srcType = args[0];
		} else {
			throw new IllegalArgumentException("Source type argument is required.");
		}

		if (!SourceManager.valid(srcType)) {
			throw new IllegalArgumentException("Please enter valid source type string. Supported string: "
					+ SourceManager.supportedSrcString());
		}

		List<String> queryList = SourceManager.select(srcType);
		int size = queryList.size();

		assert threadSize > 0 : "Thread size must be > 0";

		// declaring threads
		WebDomainCrawler[] threads = new WebDomainCrawler[threadSize];
		int partitionSize = size / threadSize;

		for (int i = 0; i < threads.length; i++) {
			int startIndex = i * partitionSize;
			int lastIndex = startIndex + partitionSize - 1;

			if (i == threads.length - 1) {
				lastIndex = size - 1;
			}

			threads[i] = new WebDomainCrawler(queryList, startIndex, lastIndex, MYNAME + " >> Thread " + i, OUTFILENAME + i
					+ EXT);
		}

		// start threads
		for (WebDomainCrawler thread : threads) {
			thread.start();
		}

		try {
			for (WebDomainCrawler thread : threads) {
				thread.join();
			}
		} catch (InterruptedException e) {
			WCLog.logger.fatal("Thread exception caught: ", e);
		}

		WCLog.logger.info("#### End WebDomainCrawler in main ####");
		System.exit(0);
	}

	private List<RankResult> caughtList = new ArrayList<RankResult>();

	private int from = 0;

	private String myRealFileName = "";

	private String myRealName = "";

	private List<String> queryList = null;

	private int to = 0;

	public WebDomainCrawler(List<String> queryList, int from, int to, String myRealName, String myRealFileName) {
		this.queryList = queryList;
		this.from = from;
		this.to = to;
		this.myRealName = myRealName;
		this.myRealFileName = myRealFileName;
	}

	/**
	 * @param client
	 * @param out
	 * @param keyword
	 * @param method
	 * @throws IOException
	 * @throws HttpException
	 */
	private void processMethod(HttpClient client, PrintWriter out, Keyword keyword) {
		HttpMethod method = null;

		try {
			method = OvertureMethod.getInstance().getMethod(keyword.getKeyword());
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				WCLog.logger.info(myRealName + " - Method failed: " + method.getStatusLine());
			}

			String responseBody = method.getResponseBodyAsString();

			Processor processor = OvertureProcessor.getInstance();
			RankResult result = processor.process(responseBody, keyword.getKeyword());
			if (result.isFound()) {
				EmailNotifier.sendTextEmail("Potential Domain Found!! (" + result.toString() + ")", result.toString());
				WCLog.logger.info(myRealName + " - " + result);
				out.println(result.toString());
				out.flush();
				caughtList.add(result);
			}

		} catch (Exception e) {
			WCLog.logger.fatal(myRealName + " - Exception caugh while processing.keyword=" + keyword, e);
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public void run() {
		try {
			startExecute();
		} catch (IOException e) {
			WCLog.logger.fatal(myRealName + " - Exception caught while processing...", e);
		}
	}

	public void startExecute() throws IOException {
		HttpClient client = new HttpClient();
		PrintWriter out = null;
		Date lastDate = new Date();

		try {
			// open FileWriter
			BufferedWriter bout = new BufferedWriter(new FileWriter(myRealFileName, ISAPPEND));
			out = new PrintWriter(bout);
			out.println("--------- Start executing at " + lastDate.toString() + "....................");
			int numProcessed = to - from + 1;
			out.println("Total query size: " + numProcessed);
			String startSummary = myRealName + " is going to processe " + numProcessed + " number of domains ( from " + from
					+ " to " + to + " at " + new Date() + " )";
			out.println(startSummary);
			out.flush();
			WCLog.logger.info(startSummary);

			EmailNotifier.sendTextEmail(myRealName + " says I start working at " + new Date().toString(),
					"I am going to processe " + numProcessed + " domains ( from " + from + " to " + to + " )");

			int cnt = 0;

			for (int i = from; i <= to; i++) {
				Keyword keyword = new Keyword(queryList.get(i));

				WCLog.logger.info(myRealName + "-search ( " + keyword + " )");

				// filtering
				Filter filter = FilterFactory.getDefaultFilter();
				if (filter.applyFilter(keyword)) {
					processMethod(client, out, keyword);
				}

				cnt++;
				// logging domains processed
				Date curDate = new Date();
				if (curDate.getTime() - lastDate.getTime() >= DURATION) {
					double percent = (cnt * 1.0 / numProcessed) * 100;
					out.println("##### At " + new Date() + ", I have processed " + cnt + " domains with percentage of "
							+ percent + "%");
					out.flush();
					lastDate = curDate;
				}
			}

			out.println("---------- Finish executing at " + new Date() + "....................");

			StringWriter emailStrWriter = new StringWriter();
			PrintWriter emailPrintWriter = new PrintWriter(emailStrWriter);
			emailPrintWriter.println("I have processed " + numProcessed + " domains ( from " + from + " to " + to + " )");
			emailPrintWriter.println();

			ReportManager.writeDefaultReport(caughtList, emailPrintWriter);
			String subject = myRealName + " says I finish it at " + new Date();

			EmailNotifier.sendTextEmail(subject, emailStrWriter.getBuffer().toString());

		} finally {
			out.flush();
			out.close();
		}
	}

}