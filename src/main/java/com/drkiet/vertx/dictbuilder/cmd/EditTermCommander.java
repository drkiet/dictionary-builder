package com.drkiet.vertx.dictbuilder.cmd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drkiet.vertx.dictbuilder.model.Dictionary;
import com.drkiet.vertx.dictbuilder.model.Term;
import com.drkiet.windowbuilder.EditTerm;
import com.drkiet.windowbuilder.EditTermCommands;

public class EditTermCommander implements EditTermCommands {
	private static final Logger LOGGER = LoggerFactory.getLogger(EditTermCommander.class);
	private EditTerm window;
	private Dictionary dictionary;

	public EditTermCommander(Dictionary dictionary, EditTerm window) {
		this.window = window;
		this.dictionary = dictionary;
	}

	@Override
	public void update() {
		System.out.println("Saved!");
		LOGGER.info("Updating text: {}, {}, {}, {}", window.getTerm(), window.getDefinition(), window.getNote(),
				window.getSource());
		Term term = new Term(window.getTerm(), window.getDefinition(), window.getNote(), window.getSource());
		dictionary.update(term);
		term = dictionary.read(term);
		if (term != null) {
			window.setDefinition(makeParagraphs(term.getDefinition()));
			window.setNote(makeParagraphs(term.getNote()));
			window.setSource(makeParagraphs(term.getSources()));
		}
	}

	@Override
	public void cancel() {
		window.clear();
	}

	@Override
	public void close() {
		System.out.println("Close!");
		window.close();
	}

	@Override
	public void create() {
		LOGGER.info("Creating text: {}, {}, {}, {}", window.getTerm(), window.getDefinition(), window.getNote(),
				window.getSource());
		Term term = new Term(window.getTerm(), window.getDefinition(), window.getNote(), window.getSource());
		dictionary.create(term);
	}

	@Override
	public void read() {
		LOGGER.info("Deleting text: {}, {}, {}, {}", window.getTerm(), window.getDefinition(), window.getNote(),
				window.getSource());
		Term term = new Term();
		term.setTerm(window.getTerm());
		term = dictionary.read(term);
		if (term != null) {
			window.setDefinition(makeParagraphs(term.getDefinition()));
			window.setNote(makeParagraphs(term.getNote()));
			window.setSource(makeParagraphs(term.getSources()));
		}
	}

	private String makeParagraphs(List<String> paras) {
		StringBuilder sb = new StringBuilder();
		for (String para : paras) {
			sb.append(para).append('\n');
		}
		return sb.toString();
	}

	@Override
	public void delete() {
		LOGGER.info("Deleting text: {}, {}, {}, {}", window.getTerm(), window.getDefinition(), window.getNote(),
				window.getSource());
		Term term = new Term();
		term.setTerm(window.getTerm());
		dictionary.delete(term);
		window.clear();
	}
}
