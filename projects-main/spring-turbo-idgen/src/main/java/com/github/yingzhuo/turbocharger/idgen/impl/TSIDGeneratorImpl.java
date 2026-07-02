package com.github.yingzhuo.turbocharger.idgen.impl;

import com.github.f4b6a3.tsid.TsidCreator;
import com.github.yingzhuo.turbocharger.idgen.TSIDGenerator;

public class TSIDGeneratorImpl implements TSIDGenerator {

	@Override
	public long generateLong() {
		return TsidCreator.getTsid().toLong();
	}

	@Override
	public String generateString() {
		return TsidCreator.getTsid().toString();
	}

}
