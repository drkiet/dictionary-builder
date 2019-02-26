package com.drkiet.vertx.dictbuilder;

import java.io.IOException;

import javax.swing.SwingUtilities;

import com.drkiet.vertx.dictbuilder.main.MainFrame;

public class SwingApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mainFrame = new MainFrame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
