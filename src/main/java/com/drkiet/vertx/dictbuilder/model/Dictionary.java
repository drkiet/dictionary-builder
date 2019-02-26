package com.drkiet.vertx.dictbuilder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drkiet.vertx.dictbuilder.repository.DictionaryDatabase;

/**
 * A dictionary is stored in a folder with a dictionaryName. In the folder,
 * multiple files are stored with the following naming
 * 
 * dictionary_folder/dictionary_name/ ... <code>
 * convention:
 * dictionary_name_a.dat
 * ...
 * dictionary_name_z.dat
 * </code>
 * 
 * @author ktran
 *
 */
public class Dictionary implements Serializable {
	private static final long serialVersionUID = -2227440775861090983L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Dictionary.class);

	private DictionaryDatabase db;
	private List<String> terms = new ArrayList<String>();

	/**
	 * 
	 * @param dictionaryName
	 */
	public Dictionary(String dictionaryName, boolean readOnly) {
		openDatabase(dictionaryName, readOnly);
		terms = db.getTerms();
		for (String term : terms) {
			LOGGER.info("term: {}", term);
		}
	}

	private void openDatabase(String dictionaryName, boolean readOnly) {
		db = new DictionaryDatabase(dictionaryName, readOnly);
	}

	public void create(Term term) {
		db.create(term);
		terms = db.getTerms();
	}

	public void delete(Term term) {
		db.delete(term);
		terms = db.getTerms();
	}

	public void update(Term term) {
		db.update(term);
	}

	public Term read(Term term) {
		return db.read(term);
	}

	public List<String> readAll() {
		return terms;
	}

	public List<String> getTerms(String termText) {
		List<String> matchingTerms = new ArrayList<String>();
		for (String term : terms) {
			if (term.toLowerCase().contains(termText)) {
				matchingTerms.add(term);
			}
		}
		return matchingTerms;
	}
}
