import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GymGUI {
    // instance variables
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, referralSourceField, trainerNameField,
            paidAmountField, removalReasonField, regularPriceField, premiumChargeField, discountField;
    private JRadioButton maleRadio, femaleRadio;
    private JComboBox<String> planComboBox;
    private JTextArea displayArea;
    private JButton addRegularButton, addPremiumButton, activateMembershipButton, deactivateMembershipButton,
            markAttendanceButton, upgradePlanButton, calculateDiscountButton, payDueAmountButton,
            displayButton, clearButton, saveToFileButton, readFromFileButton, revertRegularMemberButton,
            revertPremiumMemberButton;
    private JComboBox<String> dobDayComboBox, dobMonthComboBox, dobYearComboBox;
    private JComboBox<String> msStartDayComboBox, msStartMonthComboBox, msStartYearComboBox;

    // Arraylist to store member details
    private ArrayList<GymMember> memberList = new ArrayList<>();
    // color variable
    private Color buttonColor = new Color(65, 102, 213);
    // font variable
    private final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 18);
    // constructor to make GUI
    public GymGUI() {
        makeMainFrame();
    }
    // method which creates frame
    private void makeMainFrame() {
        // create main frame
        frame = new JFrame("Giant Gym Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout(10, 10));

        // creating panels for input, buttons, and display
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel displayPanel = createDisplayPanel();

        // adding panels to the main frame in different positions
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(displayPanel, BorderLayout.CENTER);

        // add event listeners to buttons
        addButtonEventListner();
        // making frame visible
        frame.setVisible(true);
    }

    // method which create input panels
    private JPanel createInputPanel() {
        // creating input panel in grid layout row and column
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Fields"));

        // creating and adding input fields
        idField = createInputField(inputPanel, "ID :");
        nameField = createInputField(inputPanel, "Name :");
        locationField = createInputField(inputPanel, "Location :");
        phoneField = createInputField(inputPanel, "Phone :");
        emailField = createInputField(inputPanel, "Email:");

        /*
         * creating gender label and gender radio button like male and female
         * and adding them to the input panel
         * we added genderPanel because we want to add two radio buttons in one line
         */
        JLabel genderLabel = new JLabel("Gender :");
        genderLabel.setFont(MAIN_FONT);

        inputPanel.add(genderLabel);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        maleRadio.setFont(MAIN_FONT);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setFont(MAIN_FONT);

        // kept in group because we make only one button can be selected at a time
        ButtonGroup genderGroup = new ButtonGroup();
        // adding to group
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        // adding to panel
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);

        // adding gender panel to input panel
        inputPanel.add(genderPanel);

        // DOB field with ComboBoxes
        JLabel dobLabel = new JLabel("DOB :");
        dobLabel.setFont(MAIN_FONT);
        inputPanel.add(dobLabel);

        JPanel dobPanel = new JPanel(new FlowLayout());
        String[] days = generateDays();
        String[] years = generateYears(1900, 2025);
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        dobDayComboBox = new JComboBox<>(days);
        dobMonthComboBox = new JComboBox<>(months);
        dobYearComboBox = new JComboBox<>(years);

        dobDayComboBox.setFont(MAIN_FONT);
        dobMonthComboBox.setFont(MAIN_FONT);
        dobYearComboBox.setFont(MAIN_FONT);

        dobPanel.add(dobDayComboBox);
        dobPanel.add(dobMonthComboBox);
        dobPanel.add(dobYearComboBox);

        inputPanel.add(dobPanel);

        // Membership Start Date field with ComboBoxes
        JLabel msStartLabel = new JLabel("Membership Start Date :");
        msStartLabel.setFont(MAIN_FONT);
        inputPanel.add(msStartLabel);

        JPanel msStartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        msStartDayComboBox = new JComboBox<>(days);
        msStartMonthComboBox = new JComboBox<>(months);
        msStartYearComboBox = new JComboBox<>(years);

        msStartDayComboBox.setFont(MAIN_FONT);
        msStartMonthComboBox.setFont(MAIN_FONT);
        msStartYearComboBox.setFont(MAIN_FONT);

        msStartPanel.add(msStartDayComboBox);
        msStartPanel.add(msStartMonthComboBox);
        msStartPanel.add(msStartYearComboBox);

        inputPanel.add(msStartPanel);

        referralSourceField = createInputField(inputPanel, "Referral Source");
        trainerNameField = createInputField(inputPanel, "Trainer Name");

        // combobox input field
        String[] planOptions = { "Basic", "Standard", "Deluxe" };
        JLabel planLabel = new JLabel("Select Plan :");
        planLabel.setFont(MAIN_FONT);
        inputPanel.add(planLabel);
        planComboBox = new JComboBox<>(planOptions);
        planComboBox.setFont(MAIN_FONT);
        inputPanel.add(planComboBox);

        paidAmountField = createInputField(inputPanel, "Paid Amount :");
        removalReasonField = createInputField(inputPanel, "Removal Reason:");

        // price details
        regularPriceField = createInputField(inputPanel, "Regular Plan Price:");
        regularPriceField.setText("6500");
        regularPriceField.setEditable(false);

        premiumChargeField = createInputField(inputPanel, "Premium Plan Charge:");
        premiumChargeField.setText("50000");
        premiumChargeField.setEditable(false);

        discountField = createInputField(inputPanel, "Discount Amount:");
        discountField.setText("0");
        discountField.setEditable(false);

        return inputPanel;
    }

    // method which generate day and return string array by method String.valueOf()
    public String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        System.out.println(days[days.length - 1]);
        return days;
    }

    // method which generate years and return string array by method
    // String.valueOf()
    public String[] generateYears(int fromYear, int toYear) {
        String[] years = new String[toYear - fromYear + 1];
        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(fromYear + i);
        }
        System.out.println(years[years.length - 1]);
        return years;
    }

    // method which create input fields
    public JTextField createInputField(JPanel panel, String labelText) {
        JLabel inputLabel = new JLabel(labelText);
        inputLabel.setFont(MAIN_FONT);
        panel.add(inputLabel);

        JTextField inputTextField = new JTextField();
        inputTextField.setFont(MAIN_FONT);
        panel.add(inputTextField);

        return inputTextField;
    }

    // method which create buttons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));

        // adding buttons
        addRegularButton = createButton(buttonPanel, "Add Regular Member");
        addPremiumButton = createButton(buttonPanel, "Add Premium Member");

        activateMembershipButton = createButton(buttonPanel, "Activate Membership");
        deactivateMembershipButton = createButton(buttonPanel, "Deactivate Membership");
        markAttendanceButton = createButton(buttonPanel, "Mark Attendance");
        upgradePlanButton = createButton(buttonPanel, "Upgrade Plan");
        calculateDiscountButton = createButton(buttonPanel, "Calculate Discount");
        revertRegularMemberButton = createButton(buttonPanel, "Revert Regular Member");
        revertPremiumMemberButton = createButton(buttonPanel, "Revert Premium Member");
        payDueAmountButton = createButton(buttonPanel, "Pay Due Amount");
        displayButton = createButton(buttonPanel, "Display");
        clearButton = createButton(buttonPanel, "Clear");
        saveToFileButton = createButton(buttonPanel, "Save to File");
        readFromFileButton = createButton(buttonPanel, "Read from File");

        return buttonPanel;
    }

    // method which handles all buttons event
    private void addButtonEventListner() {
        // Add Regular Member button event
        addRegularButton.addActionListener(e -> {
            try {
                // Validating fields
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
                        locationField.getText().isEmpty() || phoneField.getText().isEmpty() ||
                        (!maleRadio.isSelected() && !femaleRadio.isSelected()) ||
                        referralSourceField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input is Empty");
                    return;
                }

                int id = Integer.parseInt(idField.getText());
                if (findMemberById(id) != null) {
                    JOptionPane.showMessageDialog(frame, "Member ID Duplicate");
                    return;
                }

                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String dob = dobDayComboBox.getSelectedItem() + "/" +
                        dobMonthComboBox.getSelectedItem() + "/" +
                        dobYearComboBox.getSelectedItem();
                String startDate = msStartDayComboBox.getSelectedItem() + "/" +
                        msStartMonthComboBox.getSelectedItem() + "/" +
                        msStartYearComboBox.getSelectedItem();

                RegularMember member = new RegularMember(
                        id, nameField.getText(), locationField.getText(),
                        phoneField.getText(), emailField.getText(), gender,
                        dob, startDate, referralSourceField.getText());
                // Adding member to the list
                memberList.add(member);
                JOptionPane.showMessageDialog(frame, "Regular member added successfully");
            } catch (Exception ex) { // Catching all exceptions
                JOptionPane.showMessageDialog(frame, "An error occurred: ");
            }
        });

        // Add Premium Member button event
        addPremiumButton.addActionListener(e -> {
            try {
                // Validate required fields
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
                        locationField.getText().isEmpty() || phoneField.getText().isEmpty() ||
                        (!maleRadio.isSelected() && !femaleRadio.isSelected()) ||
                        trainerNameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input field empty");
                    return;
                }

                int id = Integer.parseInt(idField.getText());
                if (findMemberById(id) != null) {
                    JOptionPane.showMessageDialog(frame, "Member ID Duplicate");
                    return;
                }

                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String dob = dobDayComboBox.getSelectedItem() + "/" +
                        dobMonthComboBox.getSelectedItem() + "/" +
                        dobYearComboBox.getSelectedItem();
                String startDate = msStartDayComboBox.getSelectedItem() + "/" +
                        msStartMonthComboBox.getSelectedItem() + "/" +
                        msStartYearComboBox.getSelectedItem();

                PremiumMember member = new PremiumMember(
                        id, nameField.getText(), locationField.getText(),
                        phoneField.getText(), emailField.getText(), gender,
                        dob, startDate, trainerNameField.getText());

                memberList.add(member);
                JOptionPane.showMessageDialog(frame, "Premium member added successfully");
            } catch (Exception ex) { // Catching all exceptions
                JOptionPane.showMessageDialog(frame, "An error occurred: ");
            }
        });

        // Activate Membership button event
        activateMembershipButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                GymMember member = findMemberById(id);

                if (member == null) {
                    JOptionPane.showMessageDialog(frame, "Member not found");
                    return;
                }

                member.activateMembership();
                JOptionPane.showMessageDialog(frame, "Membership activated");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong in ID");
            }
        });

        // Deactivate Membership button event
        deactivateMembershipButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                GymMember member = findMemberById(id);

                if (member == null) {
                    JOptionPane.showMessageDialog(frame, "Member not found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                member.deactivateMembership();
                JOptionPane.showMessageDialog(frame, "Membership deactivated");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong in ID");
            }
        });

        // Mark Attendance button event
        markAttendanceButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                GymMember member = findMemberById(id);

                if (member == null) {
                    JOptionPane.showMessageDialog(frame, "Member not found");
                    return;
                }

                if (!member.isActiveStatus()) {
                    JOptionPane.showMessageDialog(frame, "Member not active");
                    return;
                }

                member.markAttendance();
                JOptionPane.showMessageDialog(frame, "Attendance marked succesfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong in ID");
            }
        });

        // Upgrade Plan button event
        upgradePlanButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Coming Soon");
        });

        // Calculate Discount button event
        calculateDiscountButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Coming Soon");
        });

        // Pay Due Amount button event
        payDueAmountButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Coming Soon");
        });

        // Revert Regular Member button event
        revertRegularMemberButton.addActionListener(e -> {
            try {
                if(idField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(frame, "ID empty");
                    return;
                }
                int id = Integer.parseInt(idField.getText());
                GymMember member = findMemberById(id);

                if (member == null || !(member instanceof RegularMember)) {
                    JOptionPane.showMessageDialog(frame, "Current Id member is not Regular Member");
                    return;
                }

                String reason = removalReasonField.getText();
                ((RegularMember) member).revertRegularMember(reason);
                memberList.remove(member);
                JOptionPane.showMessageDialog(frame, "Regular Member reverted successfully");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong in ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Revert Premium Member button event
        revertPremiumMemberButton.addActionListener(e -> {
            try {
                if(idField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(frame, "ID empty");
                    return;
                }
                int id = Integer.parseInt(idField.getText());
                GymMember member = findMemberById(id);

                if (member == null || !(member instanceof PremiumMember)) {
                    JOptionPane.showMessageDialog(frame, "Current Id member is not Regular Member");
                    return;
                }

                ((PremiumMember) member).revertPremiumMember();
                memberList.remove(member);
                JOptionPane.showMessageDialog(frame, "Premium Member reverted successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong in ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Display button event
        displayButton.addActionListener(e -> {
            if (memberList.isEmpty()) {
                displayArea.setText("Members not found");
                return;
            }

            String output = "";
            for (GymMember member : memberList) {
                output += "______________________________________________\n";
                output += "Member ID: " + member.getId() + "\n";
                output += "Name: " + member.getName() + "\n";
                if (member instanceof PremiumMember) {
                    output += "Type : Premium \n";
                } else {
                    output += "Type : Regular \n";
                }
                output += "Location: " + member.getLocation() + "\n";
                output += "Phone: " + member.getPhone() + "\n";
                output += "Email: " + member.getEmail() + "\n";
                output += "Gender: " + member.getGender() + "\n";
                output += "Date of Birth: " + member.getDOB() + "\n";
                output += "Membership Start Date: " + member.getMembershipStartDate() + "\n";
                if (member.isActiveStatus()) {
                    output += "Status: Active \n";
                } else {
                    output += "Status: Inactive \n";
                }
                output += "Attendance: " + member.getAttendance() + "\n";
                output += "Loyalty Points: " + member.getLoyaltyPoints() + "\n";

                if (member instanceof RegularMember) {
                    RegularMember regMember = (RegularMember) member;
                    output += "Plan: " + regMember.getPlan() + "\n";
                    output += "Price: " + regMember.getPrice() + "\n";
                    output += "Referral Source: " + regMember.getReferralSource() + "\n";
                    if (!regMember.getRemovalReason().isEmpty()) {
                        output += "Removal Reason: " + regMember.getRemovalReason() + "\n";
                    }
                } else if (member instanceof PremiumMember) {
                    PremiumMember premMember = (PremiumMember) member;
                    output += "Personal Trainer: " + premMember.getPersonalTrainer() + "\n";
                    output += "Payment: " + premMember.getPaidAmount() + "/" + premMember.getPremiumCharge() + "\n";
                    output += "Discount: " + premMember.getDiscountAmount() + "\n";
                }
                output += "\n";
            }
            displayArea.setText(output);
        });

        // Clear button event
        clearButton.addActionListener(e -> clearInputFields());

        // Save to File button event (to be implemented)
        saveToFileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Coming Soon");
        });

        // Read from File button event (to be implemented)
        readFromFileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Coming Soon");
        });
    }

    // method which find member by id
    private GymMember findMemberById(int id) {
        for (GymMember member : memberList) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    // method which clears all input fields
    private void clearInputFields() {
        // clearing text fields
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        referralSourceField.setText("");
        trainerNameField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");

        // uncheking radio buttons
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);

        // reseting comboboxex
        dobDayComboBox.setSelectedIndex(0);
        dobMonthComboBox.setSelectedIndex(0);
        dobYearComboBox.setSelectedIndex(0);
        msStartDayComboBox.setSelectedIndex(0);
        msStartMonthComboBox.setSelectedIndex(0);
        msStartYearComboBox.setSelectedIndex(0);
        // clearing display section
        displayArea.setText("");
    }

    // method which create button
    public JButton createButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFont(MAIN_FONT);
        panel.add(button);
        return button;
    }

    // method which create display panel which is in center in ui
    public JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        // Initialize the instance variable displayArea
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(MAIN_FONT);

        displayPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        // Make display area scrollable when overflow text
        displayPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        return displayPanel;
    }

    // entry main method
    public static void main(String[] args) {
        // creating object if GymGUI
        new GymGUI();
    }
}