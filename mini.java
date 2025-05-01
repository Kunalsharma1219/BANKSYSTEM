package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton exitButton;
    JLabel cardLabel, balanceLabel, insightLabel;
    JTable transactionTable;
    DefaultTableModel model;

    mini(String pin) {
        this.pin = pin;
        setTitle("Bank Statement");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bgColor = new Color(245, 248, 255);
        Color panelColor = new Color(255, 255, 255);
        Color accentColor = new Color(0, 102, 204);

        JLabel header = new JLabel("BANK STATEMENT", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(accentColor);
        header.setOpaque(true);
        header.setBackground(bgColor);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(panelColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(centerPanel, BorderLayout.CENTER);

        cardLabel = new JLabel("Account Number: XXXX-XXXX-XXXX-1234");
        cardLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cardLabel.setForeground(new Color(60, 60, 60));
        centerPanel.add(cardLabel, BorderLayout.NORTH);

        String[] columnNames = {"Date", "Type", "Amount (Rs)"};
        model = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(model);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        transactionTable.setRowHeight(25);
        transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        transactionTable.getTableHeader().setBackground(bgColor);
        transactionTable.getTableHeader().setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridLayout(3, 1));
        footer.setBackground(panelColor);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        add(footer, BorderLayout.SOUTH);

        balanceLabel = new JLabel("Your Total Balance is Rs 0");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balanceLabel.setForeground(new Color(0, 128, 0));
        footer.add(balanceLabel);

        insightLabel = new JLabel("AI Insight: ");
        insightLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        insightLabel.setForeground(new Color(120, 60, 60));
        footer.add(insightLabel);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitButton.setBackground(accentColor);
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(100, 35));
        exitButton.addActionListener(this);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(panelColor);
        btnPanel.add(exitButton);
        footer.add(btnPanel);

        getContentPane().setBackground(bgColor);

        loadData();

        setVisible(true);
    }

    private void loadData() {
        Map<String, Integer> categoryMap = new HashMap<>();
        try {
            Connect c = new Connect();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM login WHERE pin = '" + pin + "'");
            if (rs.next()) {
                String cardNumber = rs.getString("acc_no");
                cardLabel.setText("Card Number: " + cardNumber.substring(0, 4) + "XXXXXXXX" + cardNumber.substring(12));
            }

            rs = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            int balance = 0;
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amount = rs.getString("amount");
                String category = rs.getString("category"); // ✅ Get category from DB

                model.addRow(new Object[]{date, type, amount});

                int amt = Integer.parseInt(amount);
                if (type.equalsIgnoreCase("Deposit")) {
                    balance += amt;
                } else {
                    balance -= amt;
                    if (category != null && !category.isEmpty()) {
                        categoryMap.put(category, categoryMap.getOrDefault(category, 0) + amt);
                    }
                }
            }

            balanceLabel.setText("Your Total Balance is Rs " + balance);

            if (!categoryMap.isEmpty()) {
                String topCategory = Collections.max(categoryMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                int topSpent = categoryMap.get(topCategory);
                insightLabel.setText("AI Insight: You are spending the most on '" + topCategory + "' (Rs " + topSpent + "). Consider budgeting!");
            } else {
                insightLabel.setText("AI Insight: No spending category data available.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("1234");
    }
}