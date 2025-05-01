package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Signup3 extends JFrame implements ActionListener {

    JRadioButton r1, r2, r3, r4;
    JCheckBox c1, c2, c3, c4, c5, c6;
    JButton submitBtn, cancelBtn;
    String formno;
    JPasswordField pinField;

    Signup3(String formno) {
        this.formno = formno;

        setTitle("New Account Application - Page 3");
        getContentPane().setBackground(new Color(240, 248, 255));
        setLayout(null);

        JLabel title = new JLabel("Page 3:");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBounds(280, 30, 400, 30);
        add(title);

        JLabel subtitle = new JLabel("Account Details");
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        subtitle.setBounds(280, 60, 400, 30);
        add(subtitle);

        JLabel accTypeLabel = new JLabel("Account Type:");
        accTypeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        accTypeLabel.setBounds(100, 120, 200, 30);
        add(accTypeLabel);

        r1 = new JRadioButton("Saving Account");
        r2 = new JRadioButton("Fixed Deposit Account");
        r3 = new JRadioButton("Current Account");
        r4 = new JRadioButton("Recurring Deposit Account");

        ButtonGroup group = new ButtonGroup();
        group.add(r1); group.add(r2); group.add(r3); group.add(r4);

        styleRadioButton(r1, 100, 160);
        styleRadioButton(r2, 350, 160);
        styleRadioButton(r3, 100, 200);
        styleRadioButton(r4, 350, 200);

        //  Benefits shown when radio button clicked
        r1.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Saving Account Benefits:\n- Higher Interest Rates\n- Easy Withdrawals\n- Suitable for daily savings."));
        r2.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Fixed Deposit Benefits:\n- Higher Returns\n- Fixed Interest Rate\n- Good for long-term investments."));
        r3.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Current Account Benefits:\n- Unlimited Transactions\n- Suitable for Businesses\n- Overdraft Facility."));
        r4.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Recurring Deposit Benefits:\n- Regular Saving Habit\n- Good Interest Rates\n- Ideal for Future Planning."));

        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        cardLabel.setBounds(100, 270, 200, 30);
        add(cardLabel);

        JLabel cardInfo = new JLabel("(Your 16-digit Card Number)");
        cardInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cardInfo.setBounds(100, 295, 200, 20);
        add(cardInfo);

        JLabel cardNumber = new JLabel("XXXX-XXXX-XXXX-4841");
        cardNumber.setFont(new Font("SansSerif", Font.BOLD, 18));
        cardNumber.setBounds(330, 270, 300, 30);
        add(cardNumber);

        JLabel cardHint = new JLabel("(It would appear on ATM card/cheque book and statements)");
        cardHint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cardHint.setBounds(330, 295, 400, 20);
        add(cardHint);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        pinLabel.setBounds(100, 340, 200, 30);
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setFont(new Font("SansSerif", Font.BOLD, 18));
        pinField.setBounds(330, 340, 200, 30);
        add(pinField);

        // 👉 Allow only 4 digits in PIN field while typing
        pinField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || pinField.getPassword().length >= 4) {
                    e.consume(); // don't allow more input
                }
            }
        });

        JLabel pinInfo = new JLabel("(4-digit Password)");
        pinInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pinInfo.setBounds(100, 365, 200, 20);
        add(pinInfo);

        JLabel serviceLabel = new JLabel("Services Required:");
        serviceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        serviceLabel.setBounds(100, 410, 200, 30);
        add(serviceLabel);

        c1 = new JCheckBox("ATM CARD");
        c2 = new JCheckBox("Internet Banking");
        c3 = new JCheckBox("Mobile Banking");
        c4 = new JCheckBox("EMAIL Alerts");
        c5 = new JCheckBox("Cheque Book");
        c6 = new JCheckBox("E-Statement");

        styleCheckBox(c1, 100, 450);
        styleCheckBox(c2, 350, 450);
        styleCheckBox(c3, 100, 490);
        styleCheckBox(c4, 350, 490);
        styleCheckBox(c5, 100, 530);
        styleCheckBox(c6, 350, 530);

        JCheckBox declaration = new JCheckBox("I hereby declare that the above entered details are correct to the best of my knowledge.", true);
        declaration.setBackground(new Color(240, 248, 255));
        declaration.setFont(new Font("SansSerif", Font.PLAIN, 12));
        declaration.setBounds(100, 580, 600, 20);
        add(declaration);

        JLabel formLabel = new JLabel("Form No:");
        formLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        formLabel.setBounds(700, 10, 100, 30);
        add(formLabel);

        JLabel formDisplay = new JLabel(formno);
        formDisplay.setFont(new Font("SansSerif", Font.BOLD, 14));
        formDisplay.setBounds(770, 10, 60, 30);
        add(formDisplay);

        submitBtn = new JButton("Submit");
        styleButton(submitBtn, 250, 630);
        submitBtn.addActionListener(this);
        add(submitBtn);

        cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, 400, 630);
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        setSize(850, 750);
        setLocation(400, 40);
        setVisible(true);
    }

    private void styleRadioButton(JRadioButton rb, int x, int y) {
        rb.setFont(new Font("SansSerif", Font.PLAIN, 16));
        rb.setBackground(new Color(240, 248, 255));
        rb.setBounds(x, y, 250, 30);
        add(rb);
    }

    private void styleCheckBox(JCheckBox cb, int x, int y) {
        cb.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cb.setBackground(new Color(240, 248, 255));
        cb.setBounds(x, y, 200, 30);
        add(cb);
    }

    private void styleButton(JButton btn, int x, int y) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBounds(x, y, 120, 35);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String atype = null;
        if (r1.isSelected()) atype = "Saving Account";
        else if (r2.isSelected()) atype = "Fixed Deposit Account";
        else if (r3.isSelected()) atype = "Current Account";
        else if (r4.isSelected()) atype = "Recurring Deposit Account";

        Random ran = new Random();
        long first7 = (ran.nextLong() % 90000000L) + 1409963000000000L;
        String acc_no = "" + Math.abs(first7);

        String pin = new String(pinField.getPassword());
        if (!pin.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 4-digit PIN.");
            return;
        }

        String fac = "";
        if (c1.isSelected()) fac += "ATM CARD ";
        if (c2.isSelected()) fac += "Internet Banking ";
        if (c3.isSelected()) fac += "Mobile Banking ";
        if (c4.isSelected()) fac += "EMAIL Alerts ";
        if (c5.isSelected()) fac += "Cheque Book ";
        if (c6.isSelected()) fac += "E-Statement ";

        try {
            if (e.getSource() == submitBtn) {
                if (atype == null || atype.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select an account type.");
                } else {
                    Connect c1 = new Connect();
                    String q1 = "INSERT INTO signupthree VALUES('" + formno + "', '" + atype + "','" + acc_no + "','" + pin + "','" + fac + "')";
                    String q2 = "INSERT INTO login VALUES('" + formno + "','" + acc_no + "','" + pin + "')";
                    c1.statement.executeUpdate(q1);
                    c1.statement.executeUpdate(q2);
                    JOptionPane.showMessageDialog(null, "Account Number: " + acc_no + "\nPin: " + pin);
                    new Deposit(pin);
                    setVisible(false);
                }
            } else if (e.getSource() == cancelBtn) {
                System.exit(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Signup3("");
    }
}