package com.km.geosolr;

import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;

import org.apache.solr.analysis.BaseTokenizerFactory;

public class GeoHashSolrTokenizerFactory extends BaseTokenizerFactory {

	@Override
	public TokenStream create(Reader input) {
		return new GeoHashSolrTokenizer(input);
	}
}