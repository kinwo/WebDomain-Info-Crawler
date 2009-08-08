package net.kinwo.httpgrep.report.formatter;

import java.io.PrintWriter;
import java.util.List;

import net.kinwo.httpgrep.model.RankResult;

/**
 * @author henry
 */
public class DefaultFormatter implements Formatter {

	private static DefaultFormatter formatter = null;

	public static DefaultFormatter getInstance() {
		if (formatter == null) {
			formatter = new DefaultFormatter();
		}

		return formatter;
	}

	private DefaultFormatter() {

	}

	public void process(List<RankResult> results, PrintWriter writer) {
		if (results.size() > 0) {
			writer.println("####### Potential domains report with Overture result #######");
		} else {
			writer.println("No potential domains found.");
		}

		for (RankResult element : results) {
			writer.println(element.toString());
		}

		if (results.size() > 0) {
			writer.println();
			writer.println("####### Potential domains report with domain name only #######");
		}

		for (RankResult element : results) {
			writer.println(element.getKeyword());
		}
	}

}
