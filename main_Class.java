package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class main_Class extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9; // Removed b10
    String pin;
    Font customFont;

    main_Class(String pin) {
        this.pin = pin;

        // Load custom font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Poppins-Regular.ttf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            customFont = new Font("Segoe UI", Font.PLAIN, 18);
        }

        setTitle(" SERVICES ");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Background Panel with image
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgIcon = new ImageIcon("src/icon/background.jpg");
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);

        JLabel welcome = new JLabel("Welcome!", SwingConstants.CENTER);
        welcome.setForeground(Color.BLACK);
        welcome.setFont(customFont.deriveFont(Font.BOLD, 28f));
        headerPanel.add(welcome);

        JLabel label = new JLabel("Select Your Service", SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(customFont.deriveFont(Font.BOLD, 22f));
        headerPanel.add(label);

        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel cardPanel = new JPanel(new GridLayout(4, 3, 25, 25));
        cardPanel.setBackground(new Color(255, 255, 255, 180));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        Color green = new Color(67, 160, 71);
        Color greenHover = new Color(56, 142, 60);
        Color red = new Color(229, 57, 53);
        Color redHover = new Color(211, 47, 47);

        b1 = createIconButton("Deposit", "src/icon/deposit.png", green, greenHover);
        b2 = createIconButton("Cash Withdrawal", "src/icon/withdraw.png", green, greenHover);
        b3 = createIconButton("Fast Cash", "src/icon/fastcash.png", green, greenHover);
        b4 = createIconButton("Bank Statement", "src/icon/Mini-Statement.png", green, greenHover);
        b5 = createIconButton("PIN Change", "src/icon/pin.png", green, greenHover);
        b6 = createIconButton("Balance Enquiry", "src/icon/balace_enquiry.png", green, greenHover);
        b8 = createIconButton("Cards", "src/icon/card.png", green, greenHover);
        b9 = createIconButton("Loan", "src/icon/loan.png", green, greenHover);
        b7 = createIconButton("Exit", "src/icon/exit.jpg", red, redHover);

        cardPanel.add(b1);
        cardPanel.add(b2);
        cardPanel.add(b3);
        cardPanel.add(b4);
        cardPanel.add(b5);
        cardPanel.add(b6);
        cardPanel.add(b8);
        cardPanel.add(b9);
        cardPanel.add(b7);

        backgroundPanel.add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createIconButton(String text, String iconPath, Color bg, Color hover) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton btn = new JButton(text, icon) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isArmed() ? hover : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }

            protected void paintBorder(Graphics g) {
                g.setColor(bg.darker());
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
            }
        };

        btn.setFont(customFont.deriveFont(Font.BOLD, 18f));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            new Deposit(pin); setVisible(false);
        } else if (e.getSource() == b2) {
            new Withdrawl(pin); setVisible(false);
        } else if (e.getSource() == b3) {
            new FastCash(pin); setVisible(false);
        } else if (e.getSource() == b4) {
            new mini(pin);
        } else if (e.getSource() == b5) {
            new Pin(pin); setVisible(false);
        } else if (e.getSource() == b6) {
            new BalanceEnquiry(pin); setVisible(false);
        } else if (e.getSource() == b8) {
            new CardsPage(pin); setVisible(false);
        } else if (e.getSource() == b9) {
            new LoanApplication(pin); setVisible(false);
        } else if (e.getSource() == b7) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new main_Class("");
    }
}
