package com.drkiet.vertx.dictbuilder.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Each term is unique. No duplicate whatsoever.
 * 
 * @author ktran
 *
 */
public class Term implements Serializable {
	private static final long serialVersionUID = -2449109900512513350L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Term.class);

	@JsonProperty("term")
	private String term;

	@JsonProperty("definition")
	private List<String> definition;

	@JsonProperty("note")
	private List<String> note;

	@JsonProperty("sources")
	private List<String> sources;

	@JsonProperty("last_updated")
	private List<Date> lastUpdated;

	public Term(String term, String definition, String note, String sources) {
		this.term = term;
		this.definition = parseParagraph(definition);
		this.note = parseParagraph(note);
		this.sources = parseParagraph(sources);
		lastUpdated = new ArrayList<Date>();
		lastUpdated.add(new Date());
	}

	public Term() {
		lastUpdated = new ArrayList<Date>();
		lastUpdated.add(new Date());
	}

	private List<String> parseParagraph(String src) {
		StringTokenizer st = new StringTokenizer(src, "\n");
		List<String> strings = new ArrayList<String>();

		while (st.hasMoreElements()) {
			strings.add(st.nextToken());
		}
		return strings;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<String> getDefinition() {
		return definition;
	}

	public void setDefinition(List<String> definition) {
		this.definition = definition;
	}

	public List<String> getNote() {
		return note;
	}

	public void setNote(List<String> note) {
		this.note = note;
	}

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	public List<Date> getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(List<Date> lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * Return the object in JSON string.
	 * 
	 */
	public String toString() {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error in marshaling object to json {}", e);
		}
		return null;
	}

	public static Term parse(String json) {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.readValue(new StringReader(json), Term.class);
		} catch (JsonParseException e) {
			LOGGER.error("Error in unmarshaling object to json {}", e);
		} catch (JsonMappingException e) {
			LOGGER.error("Error in unmarshaling object to json {}", e);
		} catch (IOException e) {
			LOGGER.error("Error in unmarshaling object to json {}", e);
		}
		return null;
	}
}
