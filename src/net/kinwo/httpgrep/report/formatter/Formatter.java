package net.kinwo.httpgrep.report.formatter;

import java.io.PrintWriter;
import java.util.List;

import net.kinwo.httpgrep.model.RankResult;

/**
 * @author henry
 */
public interface Formatter {

	public void process(List<RankResult> results, PrintWriter writer);
}
