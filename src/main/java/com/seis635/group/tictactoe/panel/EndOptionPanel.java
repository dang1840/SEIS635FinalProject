package com.seis635.group.tictactoe.panel;

import javax.swing.*;
import java.awt.*;


public class EndOptionPanel extends JFrame {
    public JButton rematchBtn;
    public JButton closeBtn;
    private JLabel resultLbl;

    private Font lblFont = new Font("Calibri", Font.BOLD, 50);
    private Font btnFont = new Font("Calibri", Font.PLAIN, 25);

    public EndOptionPanel() {

        rematchBtn = new JButton("Rematch");
        closeBtn = new JButton("Close");
        resultLbl = new JLabel("Game Result", SwingConstants.CENTER);


        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        resultLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(resultLbl);
        add(Box.createRigidArea(new Dimension(0, 10)));

        rematchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(rematchBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(closeBtn);

        resultLbl.setFont(lblFont);
        rematchBtn.setFont(btnFont);
        closeBtn.setFont(btnFont);

        closeBtn.addActionListener(arg0 -> {
            // TODO Auto-generated method stub
            System.exit(0);
        });
    }

    public void setResult(String result) {
        resultLbl.setText(result);
    }

}
