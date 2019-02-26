package com.drkiet.vertx.dictbuilder.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public class DisplayPanel extends JPanel {
	private static final long serialVersionUID = 3944562928059751574L;
	private JLabel termLabel;
	private JTextField termTextField;
	private JLabel defLabel;
	private JTextPane defTextPane;
	private JLabel noteLabel;
	private JTextPane noteTextPane;
	private JLabel sourcesLabel;
	private JTextPane sourcesTextPane;
	private JButton loadButton;
	private JButton searchButton;

	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public DisplayPanel() {
		initialize();
//		makeComponents();
//		Border innerBorder = BorderFactory.createTitledBorder("Create/Edit");
//		Border outterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
//		setBorder(BorderFactory.createCompoundBorder(outterBorder, innerBorder));
//
//		layoutComponents();
	}

	private void initialize() {
		textField = new JTextField();
		textField.setBounds(128, 28, 86, 20);
		add(textField);
		textField.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(65, 31, 46, 14);
		add(lblName);

		JLabel lblPhone = new JLabel("Phone #");
		lblPhone.setBounds(65, 68, 46, 14);
		add(lblPhone);

		textField_1 = new JTextField();
		textField_1.setBounds(128, 65, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel lblEmailId = new JLabel("Email Id");
		lblEmailId.setBounds(65, 115, 46, 14);
		add(lblEmailId);

		textField_2 = new JTextField();
		textField_2.setBounds(128, 112, 247, 17);
		add(textField_2);
		textField_2.setColumns(10);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(65, 162, 46, 14);
		add(lblAddress);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(126, 157, 212, 40);
		add(textArea_1);

		JButton btnClear = new JButton("Clear");

		btnClear.setBounds(312, 387, 89, 23);
		add(btnClear);

		JLabel lblSex = new JLabel("Sex");
		lblSex.setBounds(65, 228, 46, 14);
		add(lblSex);

		JLabel lblMale = new JLabel("Male");
		lblMale.setBounds(128, 228, 46, 14);
		add(lblMale);

		JLabel lblFemale = new JLabel("Female");
		lblFemale.setBounds(292, 228, 46, 14);
		add(lblFemale);

		JRadioButton radioButton = new JRadioButton("");
		radioButton.setBounds(337, 224, 109, 23);
		add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(162, 224, 109, 23);
		add(radioButton_1);

		JLabel lblOccupation = new JLabel("Occupation");
		lblOccupation.setBounds(65, 288, 67, 14);
		add(lblOccupation);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Select");
		comboBox.addItem("Business");
		comboBox.addItem("Engineer");
		comboBox.addItem("Doctor");
		comboBox.addItem("Student");
		comboBox.addItem("Others");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		comboBox.setBounds(180, 285, 91, 20);
		add(comboBox);

		JButton btnSubmit = new JButton("submit");

		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(65, 387, 89, 23);
		add(btnSubmit);

		btnSubmit.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().isEmpty() || (textField_1.getText().isEmpty())
						|| (textField_2.getText().isEmpty()) || (textArea_1.getText().isEmpty())
						|| ((radioButton_1.isSelected()) && (radioButton.isSelected()))
						|| (comboBox.getSelectedItem().equals("Select")))
					JOptionPane.showMessageDialog(null, "Data Missing");
				else
					JOptionPane.showMessageDialog(null, "Data Submitted");
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText(null);
				textField_2.setText(null);
				textField.setText(null);
				textArea_1.setText(null);
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				comboBox.setSelectedItem("Select");

			}
		});

	}

	public void makeComponents() {
		termLabel = new JLabel("Term");
		termTextField = new JTextField(20);
		defLabel = new JLabel("Definition");
		defTextPane = new JTextPane();
		noteLabel = new JLabel("Note");
		noteTextPane = new JTextPane();
		sourcesLabel = new JLabel("Sources");
		sourcesTextPane = new JTextPane();

		loadButton = new JButton("Save");
		searchButton = new JButton("Cancel");
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
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(termLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(termTextField, gc);

		// Always do the following to avoid future confusion :)
		// Reference Book Name:
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(defLabel, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(defTextPane, gc);

	}
}
