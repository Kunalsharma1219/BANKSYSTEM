package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class Deposit extends JFrame implements ActionListener {
    String pin;
    JTextField textField;
    JButton depositButton, backButton;
    JComboBox<String> categoryBox;

    Deposit(String pin) {
        this.pin = pin;

        setTitle("Deposit");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Custom panel with background image
        JPanel contentPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgIcon = new ImageIcon("/Users/kunal/Documents/bank/Bank Management System/src/icon/bg1.jpeg");
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        // Label
        JLabel label = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setBounds(180, 80, 500, 30);
        contentPanel.add(label);

        // Text Field
        textField = new JTextField();
        textField.setFont(new Font("Poppins", Font.PLAIN, 18));
        textField.setBounds(180, 120, 400, 40);
        contentPanel.add(textField);

        // Category dropdown
        JLabel categoryLabel = new JLabel("SELECT CATEGORY");
        categoryLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBounds(180, 180, 300, 30);
        contentPanel.add(categoryLabel);

        String[] categories = {"Salary", "Gift", "Refund", "Other"};
        categoryBox = new JComboBox<>(categories);
        categoryBox.setFont(new Font("Poppins", Font.PLAIN, 16));
        categoryBox.setBounds(180, 220, 400, 35);
        contentPanel.add(categoryBox);

        // Deposit Button
        depositButton = new JButton("DEPOSIT");
        depositButton.setBounds(180, 280, 180, 45);
        depositButton.setFont(new Font("Poppins", Font.BOLD, 16));
        depositButton.setBackground(new Color(67, 160, 71));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.setOpaque(true);
        depositButton.setBorderPainted(false);
        contentPanel.add(depositButton);
        depositButton.addActionListener(this);

        // Back Button
        backButton = new JButton("BACK");
        backButton.setBounds(400, 280, 180, 45);
        backButton.setFont(new Font("Poppins", Font.BOLD, 16));
        backButton.setBackground(new Color(229, 57, 53));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        contentPanel.add(backButton);
        backButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String amount = textField.getText();
            String category = (String) categoryBox.getSelectedItem();
            Date date = new Date();

            if (e.getSource() == depositButton) {
                if (amount.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter the amount to deposit");
                } else {
                    Connect c = new Connect();
                    c.statement.executeUpdate("insert into bank values('" + pin + "', '" + date + "', 'Deposit', '" + amount + "', '" + category + "')");
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully under '" + category + "'");
                    dispose();
                    new main_Class(pin);
                }
            } else if (e.getSource() == backButton) {
                dispose();
                new main_Class(pin);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Deposit("1234");
    }
}
