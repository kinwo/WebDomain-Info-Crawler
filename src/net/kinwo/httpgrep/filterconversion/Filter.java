package net.kinwo.httpgrep.filterconversion;

import net.kinwo.httpgrep.model.Keyword;

/**
 * Filter Interface
 * 
 * @author henry
 */
public interface Filter {

	public boolean applyFilter(Keyword keyword);
}
