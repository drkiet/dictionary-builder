package com.drkiet.vertx.dictbuilder.main;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.drkiet.vertx.dictbuilder.helpers.FileHelper;
import com.drkiet.vertx.dictbuilder.model.Dictionary;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 3722198948182784846L;
	private FormPanel formPanel = new FormPanel();
	private ToolbarPanel toolbarPanel = new ToolbarPanel();
	private DisplayPanel displayPanel = new DisplayPanel();
	private InfoPanel infoPanel = new InfoPanel();
	private Dictionary dictionary;

	public MainFrame() throws IOException {
		super("Dictionary Builder");
		manageLayout();
		createDictionary();
	}

	private void createDictionary() {
		StringBuilder dictionaryName = new StringBuilder(FileHelper.getDictionaryFolder());
		dictionaryName.append(File.separator).append(FileHelper.getDictionaryName());
		dictionary = new Dictionary(dictionaryName.toString(), false);
	}

	private void manageLayout() {
		setLayout(new BorderLayout());
		add(formPanel, BorderLayout.WEST);
		add(toolbarPanel, BorderLayout.NORTH);
		add(displayPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.SOUTH);

		setSize(800, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
