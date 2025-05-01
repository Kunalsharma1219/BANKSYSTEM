package bank.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Signup extends JFrame implements ActionListener {
    JRadioButton r1, r2, m1, m2, m3;
    JButton next, back;
    JTextField textName, textFname, textEmail, textAdd, textcity, textState, textPin, textMobile;
    JDateChooser dateChooser;
    Random ran = new Random();
    long first4 = (ran.nextLong() % 9000L) + 1000L;
    String formNo = "" + Math.abs(first4);

    Color backgroundColor = new Color(245, 248, 255);
    Color primaryColor = new Color(33, 64, 125);
    Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
    Font titleFont = new Font("Segoe UI", Font.BOLD, 28);

    Signup() {
        setTitle("Bank Account Application - Page 1");
        setLayout(null);
        getContentPane().setBackground(backgroundColor);

        JLabel formTitle = new JLabel("APPLICATION FORM NO. " + formNo);
        formTitle.setFont(titleFont);
        formTitle.setForeground(primaryColor);
        formTitle.setBounds(200, 20, 500, 40);
        add(formTitle);

        JLabel pageLabel = new JLabel("Page 1: Personal Details");
        pageLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageLabel.setForeground(primaryColor);
        pageLabel.setBounds(250, 70, 400, 30);
        add(pageLabel);

        addLabelAndField("Name:", textName = new JTextField(), 130);
        addLabelAndField("Father's Name:", textFname = new JTextField(), 180);

        addLabel("Gender:", 230);
        r1 = new JRadioButton("Male");
        r2 = new JRadioButton("Female");
        styleRadioButton(r1, 230, 310);
        styleRadioButton(r2, 230, 400);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(r1);
        genderGroup.add(r2);

        addLabel("Date of Birth:", 280);
        dateChooser = new JDateChooser();
        dateChooser.setBounds(300, 280, 400, 30);
        dateChooser.setFont(labelFont);
        add(dateChooser);

        addLabelAndField("Email Address:", textEmail = new JTextField(), 330);

        addLabel("Marital Status:", 380);
        m1 = new JRadioButton("Married");
        m2 = new JRadioButton("Unmarried");
        m3 = new JRadioButton("Other");
        styleRadioButton(m1, 380, 310);
        styleRadioButton(m2, 380, 420);
        styleRadioButton(m3, 380, 550);
        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(m1);
        maritalGroup.add(m2);
        maritalGroup.add(m3);

        addLabelAndField("Address:", textAdd = new JTextField(), 430);
        addLabelAndField("City:", textcity = new JTextField(), 480);
        addLabelAndField("Pin Code:", textPin = new JTextField(), 530);
        addLabelAndField("Mobile Number:", textMobile = new JTextField(), 580);
        addLabelAndField("State:", textState = new JTextField(), 630);

        next = new JButton("Next");
        next.setFont(labelFont);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.setBounds(620, 690, 100, 35);
        next.addActionListener(this);
        add(next);

        back = new JButton("Back");
        back.setFont(labelFont);
        back.setBackground(Color.WHITE);
        back.setForeground(Color.BLACK);
        back.setBounds(100, 690, 100, 35);
        back.addActionListener(this);
        add(back);

        setSize(850, 800);
        setLocation(300, 50);
        setVisible(true);
    }

    private JLabel addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(primaryColor);
        label.setBounds(100, y, 200, 30);
        add(label);
        return label;
    }

    private void addLabelAndField(String labelText, JTextField textField, int y) {
        addLabel(labelText, y);
        textField.setBounds(300, y, 400, 30);
        textField.setFont(labelFont);
        add(textField);
    }

    private void styleRadioButton(JRadioButton rb, int y, int x) {
        rb.setBounds(x, y, 100, 30);
        rb.setFont(labelFont);
        rb.setForeground(primaryColor);
        rb.setBackground(backgroundColor);
        add(rb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            String name = textName.getText();
            String fname = textFname.getText();
            String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
            String gender = r1.isSelected() ? "Male" : r2.isSelected() ? "Female" : null;
            String email = textEmail.getText();
            String marital = m1.isSelected() ? "Married" : m2.isSelected() ? "Unmarried" : m3.isSelected() ? "Other" : null;
            String address = textAdd.getText();
            String city = textcity.getText();
            String pincode = textPin.getText();
            String mobile = textMobile.getText();
            String state = textState.getText();

            try {
                if (name.equals("") || fname.equals("") || dob.equals("") || gender == null || email.equals("") ||
                        marital == null || address.equals("") || city.equals("") || pincode.equals("") || mobile.equals("") || state.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                } else if (!pincode.matches("\\d{6}")) {
                    JOptionPane.showMessageDialog(null, "Pin Code must be exactly 6 digits");
                } else if (!mobile.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(null, "Mobile Number must be exactly 10 digits");
                } else {
                    Connect c = new Connect();
                    String q = "INSERT INTO signup VALUES('" + formNo + "', '" + name + "', '" + fname + "', '" + dob + "', '" + gender + "', '" + email + "', '" + marital + "', '" + address + "', '" + city + "', '" + pincode + "', '" + mobile + "', '" + state + "')";
                    c.statement.executeUpdate(q);
                    new Signup2(formNo);
                    setVisible(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}