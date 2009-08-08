package net.kinwo.httpgrep.processor;

import java.util.StringTokenizer;

import net.kinwo.httpgrep.model.RankResult;

/**
 * Overture processor
 * 
 * @author henry
 */
public class OvertureProcessor implements Processor {

	private static OvertureProcessor processor = null;

	public static OvertureProcessor getInstance() {
		if (processor == null) {
			processor = new OvertureProcessor();
		}
		return processor;
	}

	public OvertureProcessor() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.kinwo.httpgrep.processor.Processor#process()
	 */
	public RankResult process(String rawData, String searchFor) {
		StringTokenizer tok = new StringTokenizer(rawData, "\n\r");
		String lastStr = "";
		RankResult rank = new RankResult();

		while (tok.hasMoreElements()) {
			String element = (String) tok.nextElement();
			if (element.indexOf(">&nbsp;" + searchFor + "<") >= 0) {
				int lastIndex = lastStr.lastIndexOf("<");
				int firstIndex = lastStr.lastIndexOf(">&nbsp;");
				String rankStr = lastStr.substring(firstIndex, lastIndex);
				rankStr = rankStr.replaceAll(">&nbsp;", "");

				rank.setRankNumber(Long.parseLong(rankStr));
				rank.setKeyword(searchFor);
				break;
			}

			lastStr = element;
		}

		return rank;

	}
}