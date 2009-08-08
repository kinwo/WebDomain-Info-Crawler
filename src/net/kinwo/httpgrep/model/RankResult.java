package net.kinwo.httpgrep.model;

import org.apache.commons.lang.StringUtils;

/**
 * RankResult
 * 
 * @author henry
 */
public class RankResult {

	private String keyword = "";

	private long rankNumber = 0;

	private String title = "";

	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @return Returns the rankNumber.
	 */
	public long getRankNumber() {
		return rankNumber;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	public boolean isFound() {
		return !StringUtils.isEmpty(keyword);
	}

	/**
	 * @param keyword
	 *            The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @param rankNumber
	 *            The rankNumber to set.
	 */
	public void setRankNumber(long rankNumber) {
		this.rankNumber = rankNumber;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(keyword + "  -");
		strBuff.append(rankNumber);
		strBuff.append("-");

		return strBuff.toString();
	}
}