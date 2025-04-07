import javax.swing.*;
import java.awt.*;

public class GymGUI {
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, dobField;
    private JRadioButton maleRadio, femaleRadio;
    private JTextField membershipStartField, referralSourceField, trainerNameField, paidAmountField, removalReasonField;
    private JComboBox<String> planCombo;
    private JTextField regularPriceField, premiumChargeField, discountField;
    private JTextArea displayArea;
    private JButton addRegularButton, addPremiumButton, activateMembershipButton, deactivateMembershipButton;
    private JButton markAttendanceButton, upgradePlanButton, calculateDiscountButton, revertMemberButton;
    private JButton payDueAmountButton, displayButton, clearButton, saveToFileButton, readFromFileButton;

    public GymGUI() {
        initializeUI();
    }

    private void initializeUI() {
        // Main frame setup
        frame = new JFrame("Giant Gym Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout(10, 10));

        // Create panels
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel displayPanel = createDisplayPanel();

        // Add panels to frame
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(displayPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Personal Details"));

        // Set font
        Font inputFont = new Font("Arial", Font.PLAIN, 12);

        // Personal Details
        idField = new JTextField();
        addLabelAndField(panel, "ID (Required):", idField, inputFont);

        nameField = new JTextField();
        addLabelAndField(panel, "Name (Required):", nameField, inputFont);

        locationField = new JTextField();
        addLabelAndField(panel, "Location (Required):", locationField, inputFont);

        phoneField = new JTextField();
        addLabelAndField(panel, "Phone (Required):", phoneField, inputFont);

        emailField = new JTextField();
        addLabelAndField(panel, "Email:", emailField, inputFont);

        dobField = new JTextField();
        addLabelAndField(panel, "DOB (Required):", dobField, inputFont);

        // Gender Radio Buttons
        panel.add(new JLabel("Gender (Required):"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        panel.add(genderPanel);

        // Membership Details
        membershipStartField = new JTextField();
        addLabelAndField(panel, "Membership Start Date (Required):", membershipStartField, inputFont);

        referralSourceField = new JTextField();
        addLabelAndField(panel, "Referral Source (Required):", referralSourceField, inputFont);

        trainerNameField = new JTextField();
        addLabelAndField(panel, "Trainer Name (Premium):", trainerNameField, inputFont);

        // Plan Selection
        panel.add(new JLabel("Select Plan (Regular):"));
        planCombo = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});
        panel.add(planCombo);

        // Payment and Removal
        paidAmountField = new JTextField();
        addLabelAndField(panel, "Paid Amount (Premium):", paidAmountField, inputFont);

        removalReasonField = new JTextField();
        addLabelAndField(panel, "Removal Reason:", removalReasonField, inputFont);

        // Pricing Information
        regularPriceField = new JTextField("6500");
        regularPriceField.setEditable(false);
        addLabelAndField(panel, "Regular Plan Price:", regularPriceField, inputFont);

        premiumChargeField = new JTextField("50000");
        premiumChargeField.setEditable(false);
        addLabelAndField(panel, "Premium Plan Charge:", premiumChargeField, inputFont);

        discountField = new JTextField("0");
        discountField.setEditable(false);
        addLabelAndField(panel, "Discount Amount:", discountField, inputFont);

        return panel;
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label);
        field.setFont(font);
        panel.add(field);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));

        // Button styling
        Color buttonColor = new Color(0, 120, 215); // Blue color
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        // Add buttons manually
        addRegularButton = new JButton("Add Regular Member");
        addRegularButton.setBackground(buttonColor);
        addRegularButton.setForeground(Color.WHITE);
        addRegularButton.setFont(buttonFont);
        addRegularButton.addActionListener(e -> {});
        panel.add(addRegularButton);

        addPremiumButton = new JButton("Add Premium Member");
        addPremiumButton.setBackground(buttonColor);
        addPremiumButton.setForeground(Color.WHITE);
        addPremiumButton.setFont(buttonFont);
        addPremiumButton.addActionListener(e -> {});
        panel.add(addPremiumButton);

        activateMembershipButton = new JButton("Activate Membership");
        activateMembershipButton.setBackground(buttonColor);
        activateMembershipButton.setForeground(Color.WHITE);
        activateMembershipButton.setFont(buttonFont);
        activateMembershipButton.addActionListener(e -> {});
        panel.add(activateMembershipButton);

        deactivateMembershipButton = new JButton("Deactivate Membership");
        deactivateMembershipButton.setBackground(buttonColor);
        deactivateMembershipButton.setForeground(Color.WHITE);
        deactivateMembershipButton.setFont(buttonFont);
        deactivateMembershipButton.addActionListener(e -> {});
        panel.add(deactivateMembershipButton);

        markAttendanceButton = new JButton("Mark Attendance");
        markAttendanceButton.setBackground(buttonColor);
        markAttendanceButton.setForeground(Color.WHITE);
        markAttendanceButton.setFont(buttonFont);
        markAttendanceButton.addActionListener(e -> {});
        panel.add(markAttendanceButton);

        upgradePlanButton = new JButton("Upgrade Plan");
        upgradePlanButton.setBackground(buttonColor);
        upgradePlanButton.setForeground(Color.WHITE);
        upgradePlanButton.setFont(buttonFont);
        upgradePlanButton.addActionListener(e -> {});
        panel.add(upgradePlanButton);

        calculateDiscountButton = new JButton("Calculate Discount");
        calculateDiscountButton.setBackground(buttonColor);
        calculateDiscountButton.setForeground(Color.WHITE);
        calculateDiscountButton.setFont(buttonFont);
        calculateDiscountButton.addActionListener(e -> {});
        panel.add(calculateDiscountButton);

        revertMemberButton = new JButton("Revert Member");
        revertMemberButton.setBackground(buttonColor);
        revertMemberButton.setForeground(Color.WHITE);
        revertMemberButton.setFont(buttonFont);
        revertMemberButton.addActionListener(e -> {});
        panel.add(revertMemberButton);

        payDueAmountButton = new JButton("Pay Due Amount");
        payDueAmountButton.setBackground(buttonColor);
        payDueAmountButton.setForeground(Color.WHITE);
        payDueAmountButton.setFont(buttonFont);
        payDueAmountButton.addActionListener(e -> {});
        panel.add(payDueAmountButton);

        displayButton = new JButton("Display");
        displayButton.setBackground(buttonColor);
        displayButton.setForeground(Color.WHITE);
        displayButton.setFont(buttonFont);
        displayButton.addActionListener(e -> {});
        panel.add(displayButton);

        clearButton = new JButton("Clear");
        clearButton.setBackground(buttonColor);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(buttonFont);
        clearButton.addActionListener(e -> {});
        panel.add(clearButton);

        saveToFileButton = new JButton("Save to File");
        saveToFileButton.setBackground(buttonColor);
        saveToFileButton.setForeground(Color.WHITE);
        saveToFileButton.setFont(buttonFont);
        saveToFileButton.addActionListener(e -> {});
        panel.add(saveToFileButton);

        readFromFileButton = new JButton("Read From File");
        readFromFileButton.setBackground(buttonColor);
        readFromFileButton.setForeground(Color.WHITE);
        readFromFileButton.setFont(buttonFont);
        readFromFileButton.addActionListener(e -> {});
        panel.add(readFromFileButton);

        return panel;
    }

    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Displaying User Details"));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        new GymGUI();
    }
}