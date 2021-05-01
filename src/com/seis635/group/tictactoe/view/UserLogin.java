package com.seis635.group.tictactoe.view;

import com.seis635.group.tictactoe.logic.GameStart;
import com.seis635.group.tictactoe.logic.GuestGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JButton signUpButton;
    private JButton guestButton;
    private JButton quitButton;
    private JLabel label;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public UserLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(450, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(450, 392, 180, 73);
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://database-1-group-8.civvwd6ongdl.us-east-1.rds.amazonaws.com:3306/tic-tac-toe?"
                            + "user=admin&password=group8final");

                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Select username, password from infotable where username=? and password=?");

                    st.setString(1, userName);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
                        JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");

                        new GameStart(userName, password);

                    } else {
                        JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        contentPane.add(btnNewButton);

        /**
         Create new account
         */
        signUpButton = new JButton("New Account");
        signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        signUpButton.setBounds(720, 392, 180, 73);
        signUpButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            CreateNewUser frame = new CreateNewUser();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });

        contentPane.add(signUpButton);

        /**
         Play as a guest
         */
        guestButton = new JButton("Guest");
        guestButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        guestButton.setBounds(150, 392, 180, 73);
        guestButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new GuestGame("Guest");

            }
        });
        contentPane.add(guestButton);


        /**
         Quit application
         */
        quitButton = new JButton("Exit");
        quitButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        quitButton.setBounds(400, 480, 300, 73);
        quitButton.setForeground(Color.RED);
        quitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        contentPane.add(quitButton);

    }

}
