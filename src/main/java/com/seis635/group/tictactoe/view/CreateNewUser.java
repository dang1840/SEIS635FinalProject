package com.seis635.group.tictactoe.view;

import com.seis635.group.tictactoe.TicTacToe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * User Registration using Swing
 * @author javaguides.net
 *
 */
public class CreateNewUser extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(CreateNewUser.class);

    private JPanel contentPane;
    private JTextField name;
    private JTextField username;
    private JPasswordField passwordField;
    private JButton btnNewButton;


    /**
     * Create the frame.
     */

    public CreateNewUser() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 800, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewUserRegister = new JLabel("New Account Register");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 42));
        lblNewUserRegister.setBounds(210, 52, 400, 50);
        contentPane.add(lblNewUserRegister);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblName.setBounds(158, 152, 99, 43);
        contentPane.add(lblName);


        name = new JTextField();
        name.setFont(new Font("Tahoma", Font.PLAIN, 32));
        name.setBounds(314, 151, 228, 50);
        contentPane.add(name);
        name.setColumns(10);


        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(158, 229, 99, 29);
        contentPane.add(lblUsername);

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 32));
        username.setBounds(314, 221, 228, 50);
        contentPane.add(username);
        username.setColumns(10);



        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(158, 315, 99, 24);
        contentPane.add(lblPassword);


        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(314, 305, 228, 50);
        contentPane.add(passwordField);

        btnNewButton = new JButton("Register");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = name.getText();

                String userName = username.getText();

                String password = passwordField.getText();

                String msg = "" + playerName;
                msg += " \n";

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://database-1-group-8.civvwd6ongdl.us-east-1.rds.amazonaws.com:3306/tic-tac-toe?"
							+ "user=admin&password=group8final");

                    String query = "INSERT INTO infotable (username, password, name, win, lose, tie) values('" + userName + "','" + password + "','" + playerName  + "'" + ",0,0,0)";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btnNewButton, "This is already exist");
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton,
                                "Welcome, " + msg + "Your account is successfully created");
                    }
                    connection.close();
                    dispose();
                } catch (Exception exception) {
                    LOGGER.info(exception.toString());

                    if (exception.toString().equals("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry " + "'" + userName + "'" + " for key 'infotable.username_UNIQUE'")){
                        JOptionPane.showMessageDialog(btnNewButton, "Username is already exist");
                    }

                    if (exception.toString().equals("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry " + "'" + password + "'" + " for key 'infotable.password_UNIQUE'")){
                        JOptionPane.showMessageDialog(btnNewButton, "Password is already exist");
                    }

                    if (exception.toString().equals("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry " + "'" + playerName + "'" + " for key 'infotable.name_UNIQUE'")){
                        JOptionPane.showMessageDialog(btnNewButton, "com.seis635.group.tictactoe.player.Player name is already exist");
                    }
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnNewButton.setBounds(300, 447, 259, 74);
        contentPane.add(btnNewButton);
    }
}
