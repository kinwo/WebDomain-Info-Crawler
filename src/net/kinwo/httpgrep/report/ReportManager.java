package net.kinwo.httpgrep.report;

import java.io.PrintWriter;
import java.util.List;

import net.kinwo.httpgrep.model.RankResult;
import net.kinwo.httpgrep.report.formatter.DefaultFormatter;
import net.kinwo.httpgrep.report.formatter.Formatter;

/**
 * @author henry
 */
public class ReportManager {

	public static void writeDefaultReport(List<RankResult> results, PrintWriter writer) {
		Formatter formatter = DefaultFormatter.getInstance();
		formatter.process(results, writer);
	}
}