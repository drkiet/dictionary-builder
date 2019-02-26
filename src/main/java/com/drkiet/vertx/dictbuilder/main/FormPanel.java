package com.drkiet.vertx.dictbuilder.main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.Document;

import com.drkiet.vertx.dictbuilder.helpers.FileHelper;
import com.drkiet.vertx.dictbuilder.main.ReaderListener.Command;

public class FormPanel extends JPanel {
	private static final long serialVersionUID = 2763332951574685960L;
	private JLabel searchTextLabel;
	private JTextField searchTextField;
	private JButton searchButton;
	private String refName;
	private ReaderListener readerListener;
	private Document document = null;
	private JButton goToPageNoButton;
	private JTextField pageNoTextField;
	private JLabel pageNoTextLabel;
	private JComboBox<String> translationComboBox;
	private JLabel refNameLabel;
	private JComboBox<String> refNameComboBox;
	private JButton loadButton = null;

	public Document getDocument() {
		return document;
	}

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		makeComboBoxes();
		makeComponents();

		setListeners();

		Border innerBorder = BorderFactory.createTitledBorder("Settings");
		Border outterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outterBorder, innerBorder));

		layoutComponents();
	}

	public void setListeners() {

		loadButton.addActionListener((ActionEvent actionEvent) -> {
			refName = (String) refNameComboBox.getSelectedItem();
			readerListener.invoke(Command.LOAD_REF);
		});

		searchButton.addActionListener((ActionEvent actionEvent) -> {
			readerListener.invoke(Command.SEARCH);
		});

		goToPageNoButton.addActionListener((ActionEvent actionEvent) -> {
			readerListener.invoke(Command.GOTO);
		});
	}

	public void makeComponents() {
		searchTextLabel = new JLabel("Text");
		searchTextField = new JTextField(10);
		pageNoTextLabel = new JLabel("Page No.");
		pageNoTextField = new JTextField(10);

		loadButton = new JButton("Load Ref.");
		searchButton = new JButton("Search");
		goToPageNoButton = new JButton("Go to");
	}

	public void makeComboBoxes() {
		ComboboxToolTipRenderer renderer = new ComboboxToolTipRenderer();

		// Reference books.
		List<String> fileNames = getListOfReferences();

		refNameLabel = new JLabel("Ref. book: ");
		refNameComboBox = new JComboBox<String>();
		refNameComboBox.setPrototypeDisplayValue("default text here");

		for (String fileName : fileNames) {
			refNameComboBox.addItem(fileName);
		}

		renderer = new ComboboxToolTipRenderer();
		refNameComboBox.setRenderer(renderer);
		renderer.setTooltips(fileNames);
	}

	private List<String> getListOfReferences() {
		return FileHelper.getFileNames(FileHelper.getReferenceFolder());
	}

	private List<String> getListOfDictionaries() {
		return FileHelper.getFileNames(FileHelper.getDictionaryFolder());
	}

	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		//// FIRST ROW /////////////
		gc.gridy = 0;

		// Always do the following to avoid future confusion :)
		// Reference Book Name:
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(refNameLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(refNameComboBox, gc);

		//// next row /////////////
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = .2;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(loadButton, gc);

		// Always do the following to avoid future confusion :)
		// Search Text:
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(searchTextLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(searchTextField, gc);

		//// next row /////////////
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = .2; // 5;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(searchButton, gc);

		// Always do the following to avoid future confusion :)
		// Goto page:
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(pageNoTextLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(pageNoTextField, gc);

		//// next row /////////////
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 5;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(goToPageNoButton, gc);

		disableGoto();
	}

	public void disableGoto() {
		pageNoTextField.setEnabled(false);
		goToPageNoButton.setEnabled(false);
	}

	public void enableGoto() {
		pageNoTextField.setEnabled(true);
		goToPageNoButton.setEnabled(true);
	}

	public void setReaderListener(ReaderListener readerListener) {
		this.readerListener = readerListener;
	}

	public String getSearchText() {
		return searchTextField.getText();
	}

	public int getGotoPageNo() {
		if (!pageNoTextField.getText().trim().isEmpty()) {
			return Integer.valueOf(pageNoTextField.getText());
		}
		return -1;
	}

	public String getselectedTranslation() {
		return (String) translationComboBox.getSelectedItem();
	}

	public String getRefBook() {
		return refName;
	}
}
