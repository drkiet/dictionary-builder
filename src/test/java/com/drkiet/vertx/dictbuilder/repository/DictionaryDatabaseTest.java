package com.drkiet.vertx.dictbuilder.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.drkiet.vertx.dictbuilder.helpers.FileHelper;
import com.drkiet.vertx.dictbuilder.model.Dictionary;
import com.drkiet.vertx.dictbuilder.model.Term;

public class DictionaryDatabaseTest {

	private Dictionary dictionary;
	private Term term;
	private Term updatingTerm;

	@Before
	public void setup() {
		StringBuilder dictionaryName = new StringBuilder(FileHelper.getDictionaryFolder());
		dictionaryName.append(File.separator).append(FileHelper.getDictionaryName());
		dictionary = new Dictionary(dictionaryName.toString(), false);

		term = new Term("Special purpose governments",
				"Independent school districts, public colleges and universities, and special districts are special purpose governments—governments that provide only a single function or a limited number of functions (such as education, drainage and flood control, irrigation, soil and water conservation, fire protection, and water supply). Special purpose governments have the power to levy and collect taxes and to raise revenues from other sources as provided by state laws to finance the services they provide.",
				"My note goes here",
				"Accounting for Governmental and Nonprofit Entities, Reck, Lowensohn & Wilson - 17th ed.");
		updatingTerm = new Term("Special purpose governments",
				"Independent school districts, public colleges and universities, and special districts are special purpose governments—governments that provide only a single function or a limited number of functions (such as education, drainage and flood control, irrigation, soil and water conservation, fire protection, and water supply). Special purpose governments have the power to levy and collect taxes and to raise revenues from other sources as provided by state laws to finance the services they provide.",
				"Da best goes over there.",
				"Accounting for Governmental and Nonprofit Entities, Reck, Lowensohn & Wilson - 17th ed.");

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testAllTerms() {
		dictionary.create(term);
		List<String> terms = dictionary.readAll();
		for (String term : terms) {
			System.out.println("term: " + term);
		}
		dictionary.delete(term);
		assertThat(terms, not(empty()));
	}

	@Test
	public void createTerm() {
		dictionary.create(term);
		Term myTerm = dictionary.read(term);
		dictionary.delete(term);
		assertThat(myTerm.getTerm(), equalTo(term.getTerm()));
	}

	@Test
	public void readTerm() {
		dictionary.create(term);
		Term myTerm = dictionary.read(term);
		assertThat(myTerm.getTerm(), equalTo(term.getTerm()));
	}

	@Test
	public void updateTerm() {
		dictionary.create(term);
		dictionary.update(updatingTerm);
		Term myTerm = dictionary.read(term);
		dictionary.delete(myTerm);
		assertThat(myTerm.getNote(), equalTo(updatingTerm.getNote()));
	}

	@Test
	public void deleteTerm() {
		dictionary.create(term);
		dictionary.delete(term);
		Term myTerm = dictionary.read(term);
		assertThat(myTerm, nullValue());
	}

}
