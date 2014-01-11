package com.aussipvp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.aussipvp.util.DatabaseConnection;

public class GuiLauncher extends JFrame {

	private JPanel contentPane;
	private JTextField usn;
	private JTextField psw;
	private JLabel lblIpAddress;

	private GuiLauncher(String[] args) {
		createWindow();
	}

	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(270, 200);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		usn = new JTextField();
		usn.setBounds(52, 30, 165, 28);
		contentPane.add(usn);
		usn.setColumns(10);

		JLabel lblName = new JLabel("Username:");
		lblName.setBounds(100, 14, 77, 16);
		contentPane.add(lblName);

		psw = new JTextField();
		psw.setBounds(52, 96, 165, 28);
		contentPane.add(psw);
		psw.setColumns(10);

		lblIpAddress = new JLabel("Password:");
		lblIpAddress.setBounds(100, 76, 77, 16);
		contentPane.add(lblIpAddress);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usn.getText().equals(null) || usn.getText().equals("")) {
					return;
				} else if (psw.getText().equals(null) || psw.getText().equals("")) {
					return;
				} else if ((psw.getText().equals(null) || psw.getText().equals("")) && (usn.getText().equals(null) || usn.getText().equals(""))) {
					return;
				} else if (checkPasswordAndUsername(usn.getText(), psw.getText())) {
					return;
				} else {
					dispose();
					Launcher.start();
				}
			}
		});
		btnLogin.setBounds(76, 130, 117, 29);
		contentPane.add(btnLogin);
	}

	public boolean checkPasswordAndUsername(String username, String password) {
		DatabaseConnection dbc = new DatabaseConnection();
		try {
			String query = "SELECT * FROM mangosgame";
			dbc.assignResultSet(dbc.getStatement().executeQuery(query));
			while (dbc.getResultSet().next()) {
				if (username == dbc.getResultSet().getString("username")) {
					if (password == dbc.getResultSet().getString("password")) {
						return true;
					} else return false;
				} else return false;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getCause());
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		//GuiLauncher frame = new GuiLauncher(args);
		//frame.setVisible(true);
		Launcher.start();
	}
}
