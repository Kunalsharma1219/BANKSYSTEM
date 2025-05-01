package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class BalanceEnquiry extends JFrame implements ActionListener {

    String pin;
    JLabel balanceLabel;
    JButton backButton;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Load image from src/icon/bg1.jpeg
        ImageIcon bgIcon = null;
        try {
            bgIcon = new ImageIcon(getClass().getResource("/icon/bg1.jpeg")); // Must be in src/icon/
            Image bgImage = bgIcon.getImage().getScaledInstance(1550, 830, Image.SCALE_SMOOTH);
            bgIcon = new ImageIcon(bgImage);
        } catch (Exception e) {
            System.out.println("❌ Background image not found! Check if /src/icon/bg1.jpeg exists.");
        }

        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1550, 830);
        add(background);

        JLabel titleLabel = new JLabel("Your Current Balance is:");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("System", Font.BOLD, 22));
        titleLabel.setBounds(430, 180, 400, 35);
        background.add(titleLabel);

        balanceLabel = new JLabel();
        balanceLabel.setForeground(Color.YELLOW);
        balanceLabel.setFont(new Font("System", Font.BOLD, 22));
        balanceLabel.setBounds(430, 220, 400, 35);
        background.add(balanceLabel);

        backButton = new JButton("Back");
        backButton.setBounds(700, 406, 150, 35);
        backButton.setBackground(new Color(65, 125, 128));
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("System", Font.BOLD, 16));
        backButton.addActionListener(this);
        background.add(backButton);

        // ✅ Balance calculation
        int balance = 0;
        try {
            Connect c = new Connect();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            while (rs.next()) {
                if (rs.getString("type").equalsIgnoreCase("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        balanceLabel.setText("Rs " + balance);

        setSize(1550, 1080);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new main_Class(pin); // Make sure main_Class exists and works
    }

    public static void main(String[] args) {
        new BalanceEnquiry("1234");
    }
}