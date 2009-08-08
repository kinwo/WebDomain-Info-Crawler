package net.kinwo.httpgrep.filterconversion;

import net.kinwo.httpgrep.model.Keyword;

/**
 * @henry
 */
public class DefaultFilter implements Filter {

	private static DefaultFilter filter = null;

	public static DefaultFilter getInstance() {
		if (filter == null) {
			filter = new DefaultFilter();
		}
		return filter;
	}

	private DefaultFilter() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.kinwo.httpgrep.filterconversion.Filter#filter(net.kinwo.httpgrep.
	 * model.Keyword)
	 */
	public boolean applyFilter(Keyword keyword) {
		convertToLowerCaseAndTrim(keyword);
		return true;
	}

	/**
	 * @param keyword
	 */
	private void convertToLowerCaseAndTrim(Keyword keyword) {
		keyword.setKeyword(keyword.getKeyword().toLowerCase().trim());
	}

}
