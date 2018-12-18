package org.smile921.official;

import java.io.IOException;

import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;

public class StanfordTweetsDataSetInputFormat implements InputFormat {

	public StanfordTweetsDataSetInputFormat(String string) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(Configuration parameters) {
		// TODO Auto-generated method stub

	}

	@Override
	public BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputSplit[] createInputSplits(int minNumSplits) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputSplitAssigner getInputSplitAssigner(InputSplit[] inputSplits) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(InputSplit split) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean reachedEnd() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object nextRecord(Object reuse) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
