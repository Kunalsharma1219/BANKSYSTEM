package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    String pin;
    TextField textField;
    JComboBox<String> categoryBox;
    JButton b1, b2;

    Withdrawl(String pin) {
        this.pin = pin;

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/icon/bg1.jpeg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1550, 830, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 1550, 830);
        add(background);

        JLabel label1 = new JLabel("MAXIMUM WITHDRAWAL IS RS. 10,000");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        label1.setBounds(480, 160, 700, 35);
        background.add(label1);

        JLabel label2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        label2.setBounds(480, 200, 500, 35);
        background.add(label2);

        textField = new TextField();
        textField.setBackground(new Color(0, 0, 0, 120));
        textField.setForeground(Color.WHITE);
        textField.setBounds(480, 250, 320, 30);
        textField.setFont(new Font("Segoe UI", Font.BOLD, 20));
        background.add(textField);

        JLabel categoryLabel = new JLabel("SELECT CATEGORY");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        categoryLabel.setBounds(480, 290, 500, 35);
        background.add(categoryLabel);

        categoryBox = new JComboBox<>(new String[]{"Shopping", "Food", "Travel", "Bills", "Others"});
        categoryBox.setBounds(480, 330, 320, 30);
        categoryBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        background.add(categoryBox);

        b1 = createStyledButton("WITHDRAW");
        b1.setBounds(510, 380, 150, 40);
        background.add(b1);

        b2 = createStyledButton("BACK");
        b2.setBounds(670, 380, 150, 40);
        background.add(b2);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                String amount = textField.getText();
                String category = (String) categoryBox.getSelectedItem();
                Date date = new Date();

                if (amount.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw");
                    return;
                }

                Connect c = new Connect();
                ResultSet rs = c.statement.executeQuery("select * from bank where pin = '" + pin + "'");
                int balance = 0;

                while (rs.next()) {
                    if (rs.getString("type").equalsIgnoreCase("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }

                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                c.statement.executeUpdate("insert into bank(pin, date, type, amount, category) values('" + pin + "', '" + date + "', 'Withdrawl', '" + amount + "', '" + category + "')");
                JOptionPane.showMessageDialog(null, "Rs. " + amount + " debited successfully");
                setVisible(false);
                new main_Class(pin);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == b2) {
            setVisible(false);
            new main_Class(pin);
        }
    }

    public static void main(String[] args) {
        new Withdrawl("1234");
    }
}
