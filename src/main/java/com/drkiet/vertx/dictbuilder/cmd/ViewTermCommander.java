package com.drkiet.vertx.dictbuilder.cmd;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drkiet.vertx.dictbuilder.model.Dictionary;
import com.drkiet.vertx.dictbuilder.model.Term;
import com.drkiet.windowbuilder.ViewTerm;
import com.drkiet.windowbuilder.ViewTermCommands;

public class ViewTermCommander implements ViewTermCommands {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewTermCommander.class);

	private Dictionary dictionary;
	private ViewTerm window;

	public ViewTermCommander(Dictionary dictionary, ViewTerm window) {
		this.dictionary = dictionary;
		this.window = window;
	}

	@Override
	public void load() {
		LOGGER.info("loading text: {}", window.getTerm());
		Term term = new Term();
		term.setTerm(window.getTerm());
		term = dictionary.read(term);
		if (term != null) {
			StringBuilder sb = new StringBuilder("Definition:\n");
			for (String str : term.getDefinition()) {
				sb.append(str).append('\n');
			}
			sb.append("---\n");

			if (term.getNote() != null && !term.getNote().isEmpty()) {
				sb.append("Note:\n");
				for (String str : term.getNote()) {
					sb.append(str).append('\n');
				}
				sb.append("---\n");
			}

			if (term.getSources() != null && !term.getSources().isEmpty()) {
				sb.append("Sources:\n");
				for (String str : term.getSources()) {
					sb.append(str).append('\n');
				}
				sb.append("---\n");
			}

			Locale loc = new Locale("en", "US");
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
			for (Date date : term.getLastUpdated()) {
				sb.append(dateFormat.format(date)).append('\n');
			}

			window.setDefinition(sb.toString());
		}
	}

	@Override
	public void prev() {
		// TODO Auto-generated method stub

	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
