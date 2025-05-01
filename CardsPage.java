package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class CardsPage extends JFrame implements ActionListener {
    String pin;
    JButton debitBtn, creditBtn, platinumBtn, backBtn, okBtn;
    JTextArea benefitsArea;
    String selectedCardType = "";

    boolean canApply = true;
    LocalDate expiryDate = null;

    public CardsPage(String pin) {
        this.pin = pin;

        setTitle("Choose Your Card");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Choose Your Card Type");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(200, 30, 400, 30);
        add(title);

        debitBtn = new JButton("Debit Card");
        debitBtn.setBounds(80, 100, 150, 40);
        debitBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        debitBtn.addActionListener(this);
        add(debitBtn);

        creditBtn = new JButton("Credit Card");
        creditBtn.setBounds(260, 100, 150, 40);
        creditBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        creditBtn.addActionListener(this);
        add(creditBtn);

        platinumBtn = new JButton("Platinum Card");
        platinumBtn.setBounds(440, 100, 150, 40);
        platinumBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        platinumBtn.addActionListener(this);
        add(platinumBtn);

        benefitsArea = new JTextArea();
        benefitsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        benefitsArea.setBounds(80, 160, 510, 200);
        benefitsArea.setLineWrap(true);
        benefitsArea.setWrapStyleWord(true);
        benefitsArea.setEditable(false);
        benefitsArea.setBorder(BorderFactory.createTitledBorder("Card Benefits"));
        add(benefitsArea);

        okBtn = new JButton("OK");
        okBtn.setBounds(160, 380, 120, 35);
        okBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        okBtn.addActionListener(this);
        add(okBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(380, 380, 120, 35);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backBtn.addActionListener(this);
        add(backBtn);

        checkExistingCardStatus(); // Check on launch

        setVisible(true);
    }

    private void checkExistingCardStatus() {
        try {
            Connect conn = new Connect();
            // Query to check for active cards (expiry_date > today)
            String query = "SELECT ca.card_type, ca.issued_date, ca.expiry_date, ca.card_number, s.name " +
                    "FROM card_applications ca JOIN signup s ON ca.pin = s.pin_code " +
                    "WHERE ca.pin = ? AND ca.expiry_date > CURDATE()"; // Ensures we get only active cards
            PreparedStatement pst = conn.connection.prepareStatement(query);
            pst.setString(1, pin);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                canApply = false; // User already has an active card

                String name = rs.getString("name");
                String cardType = rs.getString("card_type");
                String cardNumber = rs.getString("card_number");
                LocalDate issuedDate = rs.getDate("issued_date").toLocalDate();
                expiryDate = rs.getDate("expiry_date").toLocalDate();

                String message = String.format(
                        "🔒 You already have an active card.\n\n" +
                                "👤 Name: %s\n" +
                                "💳 Card Type: %s\n" +
                                "🔢 Card Number: %s\n" +
                                "📅 Issued On: %s\n" +
                                "📆 Expiry Date: %s\n\n" +
                                "⛔ You can reapply only after this card expires.",
                        name, cardType, cardNumber, issuedDate, expiryDate
                );

                JOptionPane.showMessageDialog(this, message, "Card Already Active", JOptionPane.INFORMATION_MESSAGE);
                disableCardButtons(); // Disable the buttons to prevent reapplication
            } else {
                // No active cards or the card has expired
                canApply = true;
            }

            rs.close();
            pst.close();
            conn.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableCardButtons() {
        debitBtn.setEnabled(false);
        creditBtn.setEnabled(false);
        platinumBtn.setEnabled(false);
        okBtn.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == debitBtn) {
            selectedCardType = "Debit Card";
            benefitsArea.setText("Debit Card Benefits:\n\n- Instant access to your bank account\n- No interest charges\n- Use at ATMs and online\n- SMS alerts and spending control");
        } else if (e.getSource() == creditBtn) {
            selectedCardType = "Credit Card";
            benefitsArea.setText("Credit Card Benefits:\n\n- Build credit score\n- Cashback and reward points\n- Buy now, pay later\n- Fraud protection");
        } else if (e.getSource() == platinumBtn) {
            selectedCardType = "Platinum Card";
            benefitsArea.setText("Platinum Card Benefits:\n\n- Premium travel and shopping perks\n- Higher withdrawal and spend limits\n- Dedicated support\n- Complimentary insurance");
        } else if (e.getSource() == okBtn) {
            if (!canApply) {
                JOptionPane.showMessageDialog(this, "You cannot apply until your current card expires.", "Not Allowed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (selectedCardType.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a card type first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Connect conn = new Connect();
                    LocalDate issuedDate = LocalDate.now();
                    LocalDate expiry = issuedDate.plusYears(3); // Validity of 3 years

                    String cardNumber = "XXXX-XXXX-XXXX-" + (int)(Math.random() * 9000 + 1000);

                    String insertQuery = "INSERT INTO card_applications (pin, card_type, issued_date, expiry_date, card_number) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pst = conn.connection.prepareStatement(insertQuery);
                    pst.setString(1, pin);
                    pst.setString(2, selectedCardType);
                    pst.setDate(3, java.sql.Date.valueOf(issuedDate));
                    pst.setDate(4, java.sql.Date.valueOf(expiry));
                    pst.setString(5, cardNumber);
                    pst.executeUpdate();

                    pst.close();
                    conn.connection.close();

                    JOptionPane.showMessageDialog(this,
                            "🎉 You have successfully applied for a " + selectedCardType + ".\n" +
                                    "💳 Card Number: " + cardNumber + "\n" +
                                    "📆 Valid Until: " + expiry + "\n\n" +
                                    "Your card will be delivered to your registered address.",
                            "Card Applied",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    new main_Class(pin);
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new main_Class(pin);
        }
    }
}