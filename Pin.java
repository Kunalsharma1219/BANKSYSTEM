package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pin extends JFrame implements ActionListener {
    JButton b1, b2;
    JPasswordField p1, p2;
    String pin;

    Pin(String pin) {
        this.pin = pin;

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bg1.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        // Heading
        JLabel label1 = new JLabel("CHANGE YOUR PIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 22));
        label1.setBounds(600, 140, 400, 35);
        l3.add(label1);

        // New PIN label
        JLabel label2 = new JLabel("New PIN:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(500, 200, 150, 25);
        l3.add(label2);

        // New PIN field
        p1 = new JPasswordField();
        p1.setBackground(new Color(65, 125, 128));
        p1.setForeground(Color.WHITE);
        p1.setFont(new Font("Raleway", Font.BOLD, 22));
        p1.setBounds(650, 200, 180, 30);
        l3.add(p1);

        // Re-enter PIN label
        JLabel label3 = new JLabel("Re-Enter New PIN:");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("System", Font.BOLD, 16));
        label3.setBounds(500, 250, 200, 25);
        l3.add(label3);

        // Re-enter PIN field
        p2 = new JPasswordField();
        p2.setBackground(new Color(65, 125, 128));
        p2.setForeground(Color.WHITE);
        p2.setFont(new Font("Raleway", Font.BOLD, 22));
        p2.setBounds(650, 250, 180, 30);
        l3.add(p2);

        // CHANGE button
        b1 = new JButton("CHANGE");
        b1.setBounds(600, 320, 150, 40);
        b1.setBackground(new Color(255, 255, 255, 30)); // semi-transparent white
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("System", Font.BOLD, 16));
        b1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        b1.setFocusPainted(false);
        b1.addActionListener(this);
        l3.add(b1);

        // BACK button
        b2 = new JButton("BACK");
        b2.setBounds(600, 370, 150, 40);
        b2.setBackground(new Color(255, 255, 255, 30)); // semi-transparent white
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("System", Font.BOLD, 16));
        b2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        b2.setFocusPainted(false);
        b2.addActionListener(this);
        l3.add(b2);

        // JFrame setup
        setSize(1550, 1080);
        setLayout(null);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String pin1 = p1.getText();
            String pin2 = p2.getText();

            if (!pin1.equals(pin2)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;
            }

            if (e.getSource() == b1) {
                if (p1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter New PIN");
                    return;
                }
                if (p2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Re-Enter New PIN");
                    return;
                }

                Connect c = new Connect();
                String q1 = "update bank set pin = '" + pin1 + "' where pin = '" + pin + "'";
                String q2 = "update login set pin = '" + pin1 + "' where pin = '" + pin + "'";
                String q3 = "update signupthree set pin = '" + pin1 + "' where pin = '" + pin + "'";

                c.statement.executeUpdate(q1);
                c.statement.executeUpdate(q2);
                c.statement.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new main_Class(pin1); // Open with new pin
            } else if (e.getSource() == b2) {
                dispose();
                new main_Class(pin);;
            }

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Pin("");
    }
}