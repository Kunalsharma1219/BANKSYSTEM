package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.Timer;

public class Login extends JFrame implements ActionListener {

    JTextField textFieldAccNo, captchaInputField;
    JPasswordField passwordFieldPin;
    JButton buttonSignIn, buttonClear, buttonSignUp, buttonClose, regenerateCaptcha;
    JLabel captchaDisplay;
    String currentCaptcha;
    int failedAttempts = 0;
    Timer cooldownTimer;
    int cooldownSeconds = 30;

    public Login() {
        super("Bank Management System");
        setSize(850, 520);
        setLocation(450, 200);
        setUndecorated(true);

        BackgroundPanel background = new BackgroundPanel("/Users/kunal/Documents/bank/Bank Management System/src/icon/login.jpeg");
        background.setLayout(null);
        setContentPane(background);

        JLabel labelTitle = new JLabel("WELCOME TO FINTECH BANK");
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitle.setBounds(230, 30, 450, 40);
        background.add(labelTitle);

        JLabel labelAccNo = new JLabel("Account No:");
        labelAccNo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelAccNo.setForeground(Color.BLACK);
        labelAccNo.setBounds(200, 100, 150, 30);
        background.add(labelAccNo);

        textFieldAccNo = new JTextField();
        textFieldAccNo.setBounds(350, 100, 300, 30);
        textFieldAccNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        background.add(textFieldAccNo);

        JLabel labelPin = new JLabel("PIN:");
        labelPin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelPin.setForeground(Color.BLACK);
        labelPin.setBounds(200, 160, 150, 30);
        background.add(labelPin);

        passwordFieldPin = new JPasswordField();
        passwordFieldPin.setBounds(350, 160, 300, 30);
        passwordFieldPin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        background.add(passwordFieldPin);

        JLabel captchaLabel = new JLabel("Enter CAPTCHA:");
        captchaLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        captchaLabel.setForeground(Color.BLACK);
        captchaLabel.setBounds(200, 220, 150, 30);
        background.add(captchaLabel);

        captchaDisplay = new JLabel();
        captchaDisplay.setFont(new Font("Consolas", Font.BOLD, 20));
        captchaDisplay.setForeground(new Color(25, 25, 112));
        captchaDisplay.setBounds(350, 220, 150, 30);
        background.add(captchaDisplay);

        regenerateCaptcha = new JButton("↻");
        regenerateCaptcha.setBounds(510, 220, 50, 30);
        regenerateCaptcha.setFocusPainted(false);
        regenerateCaptcha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        regenerateCaptcha.setToolTipText("Refresh CAPTCHA");
        regenerateCaptcha.addActionListener(this);
        background.add(regenerateCaptcha);

        captchaInputField = new JTextField();
        captchaInputField.setBounds(350, 260, 300, 30);
        captchaInputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        background.add(captchaInputField);

        buttonSignIn = new JButton("SIGN IN");
        buttonSignIn.setBounds(350, 310, 100, 35);
        styleButton(buttonSignIn);
        background.add(buttonSignIn);

        buttonClear = new JButton("CLEAR");
        buttonClear.setBounds(480, 310, 100, 35);
        styleButton(buttonClear);
        background.add(buttonClear);

        buttonSignUp = new JButton("SIGN UP");
        buttonSignUp.setBounds(350, 360, 230, 35);
        styleButton(buttonSignUp);
        background.add(buttonSignUp);

        buttonClose = new JButton("X");
        buttonClose.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buttonClose.setForeground(Color.WHITE);
        buttonClose.setBackground(Color.RED);
        buttonClose.setBounds(800, 10, 35, 25);
        buttonClose.setFocusPainted(false);
        buttonClose.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        buttonClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        background.add(buttonClose);

        buttonSignIn.addActionListener(this);
        buttonClear.addActionListener(this);
        buttonSignUp.addActionListener(this);
        buttonClose.addActionListener(this);

        generateCaptcha();
        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
    }

    private void generateCaptcha() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder captcha = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            captcha.append(chars.charAt(rand.nextInt(chars.length())));
        }
        currentCaptcha = captcha.toString();
        captchaDisplay.setText(currentCaptcha);
    }

    private void startCooldown() {
        setLoginFieldsEnabled(false);
        cooldownSeconds = 30;
        cooldownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                captchaDisplay.setText("Try after " + cooldownSeconds-- + "s");
                if (cooldownSeconds < 0) {
                    cooldownTimer.stop();
                    setLoginFieldsEnabled(true);
                    failedAttempts = 0;
                    generateCaptcha();
                }
            }
        });
        cooldownTimer.start();
    }

    private void setLoginFieldsEnabled(boolean enabled) {
        textFieldAccNo.setEnabled(enabled);
        passwordFieldPin.setEnabled(enabled);
        captchaInputField.setEnabled(enabled);
        buttonSignIn.setEnabled(enabled);
        buttonClear.setEnabled(enabled);
        regenerateCaptcha.setEnabled(enabled);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == buttonSignIn) {
                String acc_no = textFieldAccNo.getText().trim();
                String pin = passwordFieldPin.getText().trim();
                String captchaInput = captchaInputField.getText().trim();

                if (!captchaInput.equals(currentCaptcha)) {
                    JOptionPane.showMessageDialog(null, "CAPTCHA doesn't match.");
                    generateCaptcha();
                    return;
                }

                Connect c = new Connect();
                String q = "select * from login where acc_no = '" + acc_no + "' and pin = '" + pin + "'";
                ResultSet rs = c.statement.executeQuery(q);
                if (rs.next()) {
                    setVisible(false);
                    new main_Class(pin);
                } else {
                    failedAttempts++;
                    JOptionPane.showMessageDialog(null, "Incorrect Account No or PIN");
                    if (failedAttempts >= 2) {
                        startCooldown();
                    }
                }
            } else if (e.getSource() == buttonClear) {
                textFieldAccNo.setText("");
                passwordFieldPin.setText("");
                captchaInputField.setText("");
            } else if (e.getSource() == buttonSignUp) {
                new Signup();
                setVisible(false);
            } else if (e.getSource() == buttonClose) {
                System.exit(0);
            } else if (e.getSource() == regenerateCaptcha) {
                generateCaptcha();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

// Background image panel
class BackgroundPanel extends JPanel {
    private Image bg;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        this.bg = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}