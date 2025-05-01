package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup2 extends JFrame implements ActionListener {

    JComboBox<String> nationalityBox, categoryBox, incomeBox, educationBox, occupationBox;
    JTextField panField, aadharField;
    JRadioButton seniorYes, seniorNo;
    ButtonGroup seniorGroup;
    JButton nextButton;
    String formNo;

    public Signup2(String formNo) {
        super("APPLICATION FORM");
        this.formNo = formNo;

        setLayout(null);
        setSize(850, 750);
        setLocation(450, 80);
        getContentPane().setBackground(new Color(245, 246, 250));

        JLabel heading1 = new JLabel("Page 2");
        heading1.setFont(new Font("Raleway", Font.BOLD, 24));
        heading1.setForeground(new Color(44, 62, 80));
        heading1.setBounds(300, 30, 600, 30);
        add(heading1);

        JLabel heading2 = new JLabel("Additional Details");
        heading2.setFont(new Font("Raleway", Font.BOLD, 24));
        heading2.setForeground(new Color(44, 62, 80));
        heading2.setBounds(300, 60, 600, 30);
        add(heading2);

        // Combo Boxes
        String[] nationalities = {"Indian", "NRI", "Other"};
        String[] categories = {"General", "OBC", "SC", "ST", "Other"};
        String[] incomes = {"Null", "<1,50,000", "<2,50,000", "5,00,000", "Upto 10,00,000", "Above 10,00,000"};
        String[] educationLevels = {"Non-Graduate", "Graduate", "Post-Graduate", "Doctorate", "Others"};
        String[] occupations = {"Salaried", "Self-Employed", "Business", "Student", "Retired", "Government Job", "Other"};

        nationalityBox = createStyledComboBox("Nationality :", nationalities, 120);
        categoryBox = createStyledComboBox("Category :", categories, 170);
        incomeBox = createStyledComboBox("Income :", incomes, 220);
        educationBox = createStyledComboBox("Educational :", educationLevels, 270);
        occupationBox = createStyledComboBox("Occupation :", occupations, 320);

        panField = createStyledTextField("PAN Number :", 370);
        aadharField = createStyledTextField("Aadhar Number :", 420);

        // Senior Citizen
        createLabel("Senior Citizen :", 470);
        seniorYes = createStyledRadioButton("Yes", 350, 470);
        seniorNo = createStyledRadioButton("No", 460, 470);
        seniorGroup = new ButtonGroup();
        seniorGroup.add(seniorYes);
        seniorGroup.add(seniorNo);

        // Form No Display
        JLabel formNoLabel = new JLabel("Form No :");
        formNoLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        formNoLabel.setForeground(new Color(44, 62, 80));
        formNoLabel.setBounds(700, 10, 100, 30);
        add(formNoLabel);

        JLabel formNoValue = new JLabel(formNo);
        formNoValue.setFont(new Font("Raleway", Font.BOLD, 14));
        formNoValue.setForeground(new Color(44, 62, 80));
        formNoValue.setBounds(770, 10, 80, 30);
        add(formNoValue);

        // Next Button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Raleway", Font.BOLD, 14));
        nextButton.setBackground(Color.WHITE);
        nextButton.setForeground(Color.BLACK);
        nextButton.setBounds(570, 620, 100, 30);
        nextButton.addActionListener(this);
        add(nextButton);

        setVisible(true);
    }

    // --------- Styled Helper Methods -----------

    private JComboBox<String> createStyledComboBox(String labelText, String[] items, int y) {
        createLabel(labelText, y);
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(350, y, 320, 30);
        comboBox.setFont(new Font("Raleway", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(44, 62, 80));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        add(comboBox);
        return comboBox;
    }

    private JTextField createStyledTextField(String labelText, int y) {
        createLabel(labelText, y);
        JTextField field = new JTextField();
        field.setFont(new Font("Raleway", Font.PLAIN, 14));
        field.setBounds(350, y, 320, 30);
        field.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        field.setBackground(Color.WHITE);
        add(field);
        return field;
    }

    private JLabel createLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Raleway", Font.BOLD, 18));
        label.setForeground(new Color(44, 62, 80));
        label.setBounds(100, y, 200, 30);
        add(label);
        return label;
    }

    private JRadioButton createStyledRadioButton(String text, int x, int y) {
        JRadioButton radio = new JRadioButton(text);
        radio.setFont(new Font("Raleway", Font.PLAIN, 14));
        radio.setBackground(new Color(245, 246, 250));
        radio.setForeground(new Color(44, 62, 80));
        radio.setBounds(x, y, 100, 30);
        add(radio);
        return radio;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nationality = (String) nationalityBox.getSelectedItem();
        String category = (String) categoryBox.getSelectedItem();
        String income = (String) incomeBox.getSelectedItem();
        String education = (String) educationBox.getSelectedItem();
        String occupation = (String) occupationBox.getSelectedItem();
        String pan = panField.getText().trim();
        String aadhar = aadharField.getText().trim();
        String seniorCitizen = seniorYes.isSelected() ? "Yes" : seniorNo.isSelected() ? "No" : "";

        try {
            if (pan.isEmpty() || aadhar.isEmpty() || seniorCitizen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the required fields.", "Missing Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!pan.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
                JOptionPane.showMessageDialog(this, "Invalid PAN format. Example: ABCDE1234F", "Invalid PAN", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!aadhar.matches("\\d{12}")) {
                JOptionPane.showMessageDialog(this, "Aadhar must be a 12-digit number.", "Invalid Aadhar", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connect c = new Connect();
            String query = "INSERT INTO signuptwo (form_no, nationality, category, income, education, occupation, pan_no, aadhar_no, senior_citizen) " +
                    "VALUES ('" + formNo + "', '" + nationality + "', '" + category + "', '" + income + "', '" +
                    education + "', '" + occupation + "', '" + pan + "', '" + aadhar + "', '" + seniorCitizen + "')";
            c.statement.executeUpdate(query);

            setVisible(false);
            new Signup3(formNo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Signup2("");
    }
}