package net.kinwo.httpgrep.model;

/**
 * Keyword model
 * 
 * @author henry
 */
public class Keyword {

	private String keyword = "";

	public Keyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return keyword;
	}
}
