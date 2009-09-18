package com.km.geosolr;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.spatial.geohash.GeoHashUtils;

public class GeoHashSolrTokenizer extends Tokenizer {
	private boolean	first	= true;

	public GeoHashSolrTokenizer(Reader input) {
		super(input);
	}

	public final Token next() throws IOException {
		if (!first) return null;
		first = false;

		char[] buf = new char[100];
		int len = input.read(buf);
		String[] pair = new String(buf, 0, len).split(",");
		if (pair.length == 2) {
			Double lat = Double.valueOf(pair[0]);
			Double lng = Double.valueOf(pair[1]);
			String hash = GeoHashUtils.encode(lat, lng);
			return new Token(hash, 0, hash.length());
		} else {
			return null;
		}
	}
}
