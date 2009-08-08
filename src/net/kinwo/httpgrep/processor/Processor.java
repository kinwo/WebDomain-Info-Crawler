package net.kinwo.httpgrep.processor;

import net.kinwo.httpgrep.model.RankResult;

/**
 * Processor
 */
public interface Processor {

	public RankResult process(String rawData, String searchFor);

}
