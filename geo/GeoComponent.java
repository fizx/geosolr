package com.km.geosolr;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.spatial.geohash.GeoHashDistanceFilter;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

public class GeoComponent extends SearchComponent {

	@Override
	public void prepare(ResponseBuilder rb) throws IOException {
		if (rb.getFilters() == null) rb.setFilters(new ArrayList<Query>());
		Query geoFilter = newGeoFilter(rb);
		if (geoFilter != null) rb.getFilters().add(geoFilter);
	}

	private Query newGeoFilter(ResponseBuilder rb) {
		SolrParams params = rb.req.getParams();
		String field = params.get("gf");
		Float lat = params.getFloat("lat");
		Float lng = params.getFloat("lng");
		Float radius = params.getFloat("radius");
		if (field != null && lat != null && lng != null && radius != null) {
			QueryWrapperFilter filter = new QueryWrapperFilter(new MatchAllDocsQuery());
			return new FilteredQuery(new MatchAllDocsQuery(), new GeoHashDistanceFilter(filter, lat, lng, radius, field));
		} else {
			return null;
		}
	}

	@Override
	public void process(ResponseBuilder rb) throws IOException {
		// Nothing to see here, move along.
	}

	@Override
	public String getDescription() {
		return "geo";
	}

	@Override
	public String getVersion() {
		return "$Revision$";
	}

	@Override
	public String getSourceId() {
		return "$Id$";
	}

	@Override
	public String getSource() {
		return "$URL$";
	}

	@Override
	public URL[] getDocs() {
		return null;
	}
}