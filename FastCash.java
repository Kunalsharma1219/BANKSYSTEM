package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Load and set background
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/icon/bg1.jpeg"));
        Image bgImage = bgIcon.getImage().getScaledInstance(1550, 830, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 1550, 830);
        add(background);

        // ✅ Heading
        JLabel label = new JLabel("SELECT WITHDRAWAL AMOUNT");
        label.setBounds(480, 130, 700, 40);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 26));
        label.setOpaque(false);
        background.add(label);

        // ✅ Create buttons
        b1 = createStyledButton("Rs. 100");
        b2 = createStyledButton("Rs. 500");
        b3 = createStyledButton("Rs. 1000");
        b4 = createStyledButton("Rs. 2000");
        b5 = createStyledButton("Rs. 5000");
        b6 = createStyledButton("Rs. 10000");
        b7 = createStyledButton("BACK");

        // ✅ Button placement (grid-like)
        b1.setBounds(450, 200, 180, 40);
        b2.setBounds(750, 200, 180, 40);
        b3.setBounds(450, 260, 180, 40);
        b4.setBounds(750, 260, 180, 40);
        b5.setBounds(450, 320, 180, 40);
        b6.setBounds(750, 320, 180, 40);
        b7.setBounds(600, 400, 180, 40); // Center back button

        // ✅ Add to background
        background.add(b1);
        background.add(b2);
        background.add(b3);
        background.add(b4);
        background.add(b5);
        background.add(b6);
        background.add(b7);

        setSize(1550, 1080);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b7) {
            setVisible(false);
            new main_Class(pin);
        } else {
            String amount = ((JButton) e.getSource()).getText().substring(4);
            Connect c = new Connect();
            Date date = new Date();
            try {
                ResultSet resultSet = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
                int balance = 0;
                while (resultSet.next()) {
                    if (resultSet.getString("type").equalsIgnoreCase("Deposit")) {
                        balance += Integer.parseInt(resultSet.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(resultSet.getString("amount"));
                    }
                }

                if (balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                // ✅ FIXED SQL INSERT QUERY (column names added)
                c.statement.executeUpdate("INSERT INTO bank (pin, date, type, amount) VALUES('" + pin + "', '" + date + "', 'Withdrawal', '" + amount + "')");
                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            setVisible(false);
            new main_Class(pin);
        }
    }

    public static void main(String[] args) {
        new FastCash("1234");
    }
}