package com.drkiet.vertx.dictbuilder;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drkiet.vertx.dictbuilder.cmd.EditTermCommander;
import com.drkiet.vertx.dictbuilder.cmd.ViewTermCommander;
import com.drkiet.vertx.dictbuilder.helpers.FileHelper;
import com.drkiet.vertx.dictbuilder.model.Dictionary;
import com.drkiet.vertx.dictbuilder.model.Term;
import com.drkiet.windowbuilder.EditTerm;
import com.drkiet.windowbuilder.EditTermCommands;
import com.drkiet.windowbuilder.TermToolTip;
import com.drkiet.windowbuilder.ViewTerm;

/**
 * TermEditor is a Window Builder application that allows me to create and/or
 * edit dictionary terms in a local database.
 * 
 * <code>
 * -Ddictionary.folder=c:/book-catalog/dictionaries
 * -Ddictionary.name=accounting
 * -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory
 * </code>
 * 
 * @author ktran
 *
 */
public class TermViewer {
	private static final Logger LOGGER = LoggerFactory.getLogger(TermViewer.class);

	public static void main(String... args) {
		new TermViewer();
	}

	private String dictionaryName;
	private String dictionaryFolder;
	private Dictionary dictionary;
	private ViewTerm window;

	public TermViewer() {
		LOGGER.info("Welcome to a Dictionary Term Editor program.");

		dictionaryName = FileHelper.getDictionaryName();
		dictionaryFolder = FileHelper.getDictionaryFolder();
		createDictionary();

		try {
			window = new ViewTerm();

			window.setTermToolTip(new TermToolTip() {
				@Override
				public List<String> getToolTip(String termText) {
					return dictionary.getTerms(termText);
				}

			});

			window.setDictionaryName(dictionaryName);
			window.setCommands(new ViewTermCommander(dictionary, window));
			window.open();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createDictionary() {
		StringBuilder dictionaryName = new StringBuilder(FileHelper.getDictionaryFolder());
		dictionaryName.append(File.separator).append(FileHelper.getDictionaryName());
		dictionary = new Dictionary(dictionaryName.toString(), true);
	}
}
