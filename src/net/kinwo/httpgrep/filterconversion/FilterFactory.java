package net.kinwo.httpgrep.filterconversion;

/**
 * Filter factory
 * 
 * 
 * @author henry
 */
public class FilterFactory {

	public static Filter getDefaultFilter() {
		return DefaultFilter.getInstance();
	}

}
