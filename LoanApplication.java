package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class LoanApplication extends JFrame implements ActionListener {
    String pin;
    JComboBox<String> loanTypeBox;
    JTextField amountField, durationField, rateField, emiField;
    JButton calculateBtn, applyBtn, backBtn;

    LoanApplication(String pin) {
        this.pin = pin;

        setTitle("Loan Application");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titleLabel = new JLabel("Apply for a Loan");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        titleLabel.setBounds(150, 20, 300, 30);
        add(titleLabel);

        JLabel typeLabel = new JLabel("Loan Type:");
        typeLabel.setBounds(50, 80, 100, 25);
        add(typeLabel);

        String[] types = {"Personal", "Home", "Education", "Car"};
        loanTypeBox = new JComboBox<>(types);
        loanTypeBox.setBounds(180, 80, 200, 25);
        add(loanTypeBox);

        JLabel amountLabel = new JLabel("Loan Amount (Rs):");
        amountLabel.setBounds(50, 120, 150, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(180, 120, 200, 25);
        add(amountField);

        JLabel durationLabel = new JLabel("Duration (months):");
        durationLabel.setBounds(50, 160, 150, 25);
        add(durationLabel);

        durationField = new JTextField();
        durationField.setBounds(180, 160, 200, 25);
        add(durationField);

        JLabel rateLabel = new JLabel("Interest Rate (% per year):");
        rateLabel.setBounds(50, 200, 180, 25);
        add(rateLabel);

        rateField = new JTextField();
        rateField.setBounds(230, 200, 150, 25);
        add(rateField);

        JLabel emiLabel = new JLabel("Monthly EMI:");
        emiLabel.setBounds(50, 240, 150, 25);
        add(emiLabel);

        emiField = new JTextField();
        emiField.setBounds(180, 240, 200, 25);
        emiField.setEditable(false);
        add(emiField);

        calculateBtn = new JButton("Calculate EMI");
        calculateBtn.setBounds(50, 290, 150, 35);
        calculateBtn.addActionListener(this);
        add(calculateBtn);

        applyBtn = new JButton("Apply for Loan");
        applyBtn.setBounds(220, 290, 160, 35);
        applyBtn.addActionListener(this);
        add(applyBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(180, 350, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        getContentPane().setBackground(new Color(245, 248, 255));

        // Check if the user already has an active loan application
        checkExistingLoanStatus();

        setVisible(true);
    }

    // Method to check if the user has already applied for a loan
    private void checkExistingLoanStatus() {
        try {
            Connect c = new Connect();
            String query = "SELECT status FROM loan WHERE pin = ? AND status = 'Pending'";
            PreparedStatement pst = c.connection.prepareStatement(query);
            pst.setString(1, pin);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "You already have an active loan application pending.\nPlease wait for it to be processed before applying for a new one.", "Loan Application Pending", JOptionPane.WARNING_MESSAGE);
                applyBtn.setEnabled(false); // Disable the apply button if there is a pending loan
            }

            rs.close();
            pst.close();
            c.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateBtn) {
            try {
                double principal = Double.parseDouble(amountField.getText());
                int months = Integer.parseInt(durationField.getText());
                double annualRate = Double.parseDouble(rateField.getText());

                double monthlyRate = annualRate / 12 / 100;
                double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                        (Math.pow(1 + monthlyRate, months) - 1);

                emiField.setText(String.format("%.2f", emi));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers");
            }
        } else if (e.getSource() == applyBtn) {
            try {
                String type = (String) loanTypeBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                int duration = Integer.parseInt(durationField.getText());
                double rate = Double.parseDouble(rateField.getText());
                double emi = Double.parseDouble(emiField.getText());

                Connect c = new Connect();
                LocalDate today = LocalDate.now();

                String query = "INSERT INTO loan (pin, type, amount, interest_rate, duration, emi, status, applied_on) " +
                        "VALUES ('" + pin + "', '" + type + "', '" + amount + "', '" + rate + "', '" + duration +
                        "', '" + emi + "', 'Pending', '" + today + "')";

                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Loan Application Submitted Successfully!");
                setVisible(false);
                new main_Class(pin);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please calculate EMI before applying.");
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new main_Class(pin);
        }
    }

    public static void main(String[] args) {
        new LoanApplication("1234");
    }
}