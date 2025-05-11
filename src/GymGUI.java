// importing 
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class GymGUI {
    // instance variables
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, referralSourceField, trainerNameField,
            paidAmountField, removalReasonField, regularPriceField, premiumChargeField, discountField;
    private JRadioButton maleRadio, femaleRadio;
    private JComboBox<String> planComboBox;
    private ButtonGroup genderGroup;
    private JButton addRegularButton, addPremiumButton, activateMembershipButton, deactivateMembershipButton,
            markAttendanceButton, upgradePlanButton, calculateDiscountButton, payDueAmountButton,
            displayButton, clearButton, saveToFileButton, readFromFileButton, revertRegularMemberButton,
            revertPremiumMemberButton;
    private JComboBox<String> dobDayComboBox, dobMonthComboBox, dobYearComboBox;
    private JComboBox<String> msStartDayComboBox, msStartMonthComboBox, msStartYearComboBox;
    private JPanel displayPanel;
    private JTextArea displayTextArea;
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
        genderGroup = new ButtonGroup();
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
        // dob panel 
        JPanel dobPanel = new JPanel(new FlowLayout());
        String[] days = generateDays();
        String[] years = generateYears(1950, 2025);
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

        // member start date panel
        years = generateYears(2024, 2025);
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

        // referral source and trainer name fields
        referralSourceField = createInputField(inputPanel, "Referral Source");
        trainerNameField = createInputField(inputPanel, "Trainer Name");

        // combobox input field
        String[] planOptions = { "Basic", "Standard", "Deluxe" };
        JLabel planLabel = new JLabel("Select Plan :");
        planLabel.setFont(MAIN_FONT);
        inputPanel.add(planLabel);
        planComboBox = new JComboBox<>(planOptions);
        planComboBox.setFont(MAIN_FONT);

        // event will occur when user select or changes plan 
        planComboBox.addActionListener(e -> regularPlan()); // update price on selection
        inputPanel.add(planComboBox);

        // paid amount and removal reason fields
        paidAmountField = createInputField(inputPanel, "Paid Amount :");
        removalReasonField = createInputField(inputPanel, "Removal Reason:");

        // price details
        regularPriceField = createInputField(inputPanel, "Regular Plan Price:");
        regularPriceField.setText("6500");
        regularPriceField.setEditable(false);

        // premium charge details
        premiumChargeField = createInputField(inputPanel, "Premium Plan Charge:");
        premiumChargeField.setText("50000");
        premiumChargeField.setEditable(false);

        // discount amount details
        discountField = createInputField(inputPanel, "Discount Amount:");
        discountField.setText("0");
        discountField.setEditable(false);

        // return inputPanel to main frame
        return inputPanel;
    }

    // method to update the price based on the selected plan
    // it is called when user select plan
    public void regularPlan() {
        if (planComboBox.getSelectedIndex() == 0) {
            regularPriceField.setText("6500.00");
        } else if (planComboBox.getSelectedIndex() == 1) {
            regularPriceField.setText("12500.00");
        } else {
            regularPriceField.setText("18500.00");
        }
    }
    // method which generate day and return string array by method String.valueOf()
    public String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        return days;
    }

    // method which generate years and return string array by method
    // String.valueOf()
    public String[] generateYears(int fromYear, int toYear) {
        String[] years = new String[toYear - fromYear + 1];
        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(fromYear + i);
        }
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

        // calling createButton method to create buttons
        // and adding them to the button panel
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

    //mehotd which validate only personal information of input fields
    public boolean validatePersonalInformation() {
    // ID: not empty, must be positive integer
    String idText = idField.getText().trim();
    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "ID is Empty");
        idField.requestFocus();
        return false;
    }
    try {
        int id = Integer.parseInt(idText);
        if (id <= 0) {
            JOptionPane.showMessageDialog(frame, "ID must be a positive number");
            idField.requestFocus();
            return false;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "ID must be a number");
        idField.requestFocus();
        return false;
    }

    // Name: not empty, at least 3 characters
    if (nameField.getText().trim().length() < 3) {
        JOptionPane.showMessageDialog(frame, "Name must be at least 3 characters");
        nameField.requestFocus();
        return false;
    }

    // Location: not empty, at least 3 characters
    if (locationField.getText().trim().length() < 3) {
        JOptionPane.showMessageDialog(frame, "Enter valid location");
        locationField.requestFocus();
        return false;
    }

    try {
        String phone = phoneField.getText().trim();
        if (!(phone.length() == 10 )) {
            JOptionPane.showMessageDialog(frame, "Phone must be exactly 10 digits");
            phoneField.requestFocus();
            return false;
        }
        Long.parseLong(phone);

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Phone number must be a number");
        phoneField.requestFocus();
        return false;
    }
    // Email: not empty, at least 5 characters, must contain '@'
    String email = emailField.getText().trim();
    if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
        JOptionPane.showMessageDialog(frame, "Please enter a valid email");
        emailField.requestFocus();
        return false;
    }

    // Gender: must be selected
    if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
        JOptionPane.showMessageDialog(frame, "Please select gender");
        return false;
    }

    return true;
}
private Integer getValidatedId() {
    String idText = idField.getText().trim();
    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Please enter ID");
        idField.requestFocus();
        return null;
    }
    try {
        int id = Integer.parseInt(idText);
        if (id <= 0) {
            JOptionPane.showMessageDialog(frame, "ID must be a positive number");
            idField.requestFocus();
            return null;
        }
        return id;
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "ID must be a number");
        idField.requestFocus();
        return null;
    }
}
private boolean isIdInFile (int id) {
    File file = new File("MemberDetails.txt");
    if (!file.exists()) return false;
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty() || line.startsWith("-") || line.startsWith("ID")) continue;
            String[] parts = line.trim().split("\\s+");
            if (parts.length > 0) {
                try {
                    int fileId = Integer.parseInt(parts[0]);
                    if (fileId == id) return true;
                } catch (NumberFormatException ignored) {}
            }
        }
    } catch (Exception ignored) {}
    return false;
}

    // method which handles all buttons event
    private void addButtonEventListner() {
        // Add Regular Member button event
        addRegularButton.addActionListener(e -> {
        try{

            if (validatePersonalInformation() == false) {
                return;
            }

            if (referralSourceField.getText().trim().isEmpty() || referralSourceField.getText().trim().length() < 3) {
                JOptionPane.showMessageDialog(frame, "Please enter Referral Source");
                referralSourceField.requestFocus();
                return;
            }

            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            if (findMemberById(id) != null || isIdInFile(id)) {
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
            memberList.add(member);
            JOptionPane.showMessageDialog(frame, "Regular member added successfully");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        });

        // Add Premium Member button event
        addPremiumButton.addActionListener(e -> {
            try {
                if (validatePersonalInformation() == false) {
                    return;
                }

            if (trainerNameField.getText().trim().isEmpty() || trainerNameField.getText().trim().length() < 3) {
                JOptionPane.showMessageDialog(frame, "Please enter Trainer Name");
                trainerNameField.requestFocus();
                return;
            }

            Integer id = getValidatedId();
            if (id == null) return;
            if (findMemberById(id) != null || isIdInFile(id)) {
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
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        });

        // Activate Membership button event
        activateMembershipButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            member.activateMembership();
            JOptionPane.showMessageDialog(frame, "Membership activated");
        });

        // Deactivate Membership button event
        deactivateMembershipButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            member.deactivateMembership();
            JOptionPane.showMessageDialog(frame, "Membership deactivated");
        });

        // Mark Attendance button event
        markAttendanceButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
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
        });

        // Upgrade Plan button event
        upgradePlanButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            if (!(member instanceof RegularMember)) {
                JOptionPane.showMessageDialog(frame, "Member is not a Regular Member");
                return;
            }
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            RegularMember regularMember = (RegularMember) member;
            String selectedPlan = (String) planComboBox.getSelectedItem();
            String resultUpgradePlanResult = regularMember.upgradePlan(selectedPlan);
            JOptionPane.showMessageDialog(frame, resultUpgradePlanResult);
        });

        // Calculate Discount button event
        calculateDiscountButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(frame, "Member not Premium Member");
                return;
            }
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            PremiumMember premiumMember = (PremiumMember) member;
            String calculateDiscountResult = premiumMember.calculateDiscount();
            JOptionPane.showMessageDialog(frame, calculateDiscountResult);
            discountField.setText(String.valueOf(premiumMember.getDiscountAmount()));
        });

        // Pay Due Amount button event
        payDueAmountButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            String paidAmountStr = paidAmountField.getText().trim();
            if (paidAmountStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter Paid Amount");
                paidAmountField.requestFocus();
                return;
            }
            double paidAmount;
            try {
                paidAmount = Double.parseDouble(paidAmountStr);
                if (paidAmount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Enter valid amount");
                    paidAmountField.requestFocus();
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid paid amount");
                paidAmountField.requestFocus();
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(frame, "Member not Premium Member");
                return;
            }
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            PremiumMember premiumMember = (PremiumMember) member;
            String result = premiumMember.payDueAmount(paidAmount);
            JOptionPane.showMessageDialog(frame, result);
        });

        // Revert Regular Member button event
        revertRegularMemberButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null || !(member instanceof RegularMember)) {
                JOptionPane.showMessageDialog(frame, "Current Id member is not Regular Member");
                return;
            }
            String reason = removalReasonField.getText();
            if (reason.isEmpty()) {
                reason = "Not Provided";
            }
            RegularMember regularMember = (RegularMember) member;
            regularMember.revertRegularMember(reason);
            memberList.remove(member);
            JOptionPane.showMessageDialog(frame, "Regular Member reverted successfully");
        });

        // Revert Premium Member button event
        revertPremiumMemberButton.addActionListener(e -> {
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            GymMember member = findMemberById(id);
            if (member == null || !(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(frame, "Current Id member is not Premium Member");
                return;
            }
            PremiumMember premiumMember = (PremiumMember) member;
            premiumMember.revertPremiumMember();
            memberList.remove(member);
            JOptionPane.showMessageDialog(frame, "Premium Member reverted successfully");
        });

        // Display button event
displayButton.addActionListener(e -> {
    if (memberList.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No members to display");
        return;
    }

    StringBuilder sb = new StringBuilder();

    String header = String.format(
        "%-5s %-22s %-18s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-12s %-15s %-20s\n",
        "ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Membership Start", "Plan", "Price", "Attendance",
        "Loyalty", "Active", "FullPay", "Discount", "PersonalTrainer"
    );
    String separator = "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
    sb.append(separator);
    sb.append(header);
    sb.append(separator);

    for (GymMember member : memberList) {
        String plan = "", price = "", fullPayment = "NA", discount = "NA", personalTrainer = "NA";
        int attendance = member.getAttendance();
        double loyaltyPoints = member.getLoyaltyPoints();
        boolean active = member.isActiveStatus();
        String gender = member.getGender();
        String dob = member.getDOB();
        String startDate = member.getMembershipStartDate();

        if (member instanceof RegularMember) {
            RegularMember rm = (RegularMember) member;
            plan = rm.getPlan();
            price = String.valueOf(rm.getPrice());
        } else if (member instanceof PremiumMember) {
            PremiumMember pm = (PremiumMember) member;
            plan = "Premium";
            price = String.valueOf(pm.getPremiumCharge());
            fullPayment = pm.isFullPayment() ? "true" : "false";
            discount = pm.isFullPayment() ? String.valueOf(pm.getDiscountAmount()) : "0";
            personalTrainer = pm.getPersonalTrainer();
        }

        String line = String.format(
            "%-5s %-22s %-18s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-12s %-15s %-20s\n",
            member.getId(),
            member.getName(),
            member.getLocation(),
            member.getPhone(),
            member.getEmail(),
            gender,
            dob,
            startDate,
            plan,
            price,
            attendance,
            String.format("%.2f", loyaltyPoints),
            active ? "Active" : "Inactive",
            fullPayment,
            discount,
            personalTrainer
        );
        sb.append(line);
    }
    sb.append(separator);

    displayTextArea.setText(sb.toString());
    displayTextArea.setCaretPosition(0);
});
        // Clear button event
        clearButton.addActionListener(e -> {
            clearInputFields();
            displayTextArea.setText("");
        });

        // Save to File button event
        // Save to File button event
saveToFileButton.addActionListener(e -> {
    if (memberList.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No members to save");
        return;
    }
    try {
        File file = new File("MemberDetails.txt");
        boolean fileExists = file.exists();
        FileWriter saveToFileWriter = new FileWriter(file, true); // append mode

        String header = String.format(
            "%-5s %-22s %-18s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-12s %-15s %-20s\n",
            "ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Membership Start", "Plan", "Price", "Attendance",
            "Loyalty", "Active", "FullPay", "Discount", "PersonalTrainer"
        );
        String separator = "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
        if (!fileExists || file.length() == 0) {
            saveToFileWriter.write(separator);
            saveToFileWriter.write(header);
            saveToFileWriter.write(separator);
        }

        for (GymMember member : memberList) {
            String plan = "", price = "", fullPayment = "NA", discount = "NA", personalTrainer = "NA";
            int attendance = member.getAttendance();
            double loyaltyPoints = member.getLoyaltyPoints();
            boolean active = member.isActiveStatus();
            String gender = member.getGender();
            String dob = member.getDOB();
            String startDate = member.getMembershipStartDate();

            if (member instanceof RegularMember) {
                RegularMember rm = (RegularMember) member;
                plan = rm.getPlan();
                price = String.valueOf(rm.getPrice());
            } else if (member instanceof PremiumMember) {
                PremiumMember pm = (PremiumMember) member;
                plan = "Premium";
                price = String.valueOf(pm.getPremiumCharge());
                fullPayment = pm.isFullPayment() ? "true" : "false";
                discount = pm.isFullPayment() ? String.valueOf(pm.getDiscountAmount()) : "0";
                personalTrainer = pm.getPersonalTrainer();
            }

            String line = String.format(
                "%-5s %-22s %-18s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-12s %-15s %-20s\n",
                member.getId(),
                member.getName(),
                member.getLocation(),
                member.getPhone(),
                member.getEmail(),
                gender,
                dob,
                startDate,
                plan,
                price,
                attendance,
                String.format("%.2f", loyaltyPoints),
                active ? "Active" : "Inactive",
                fullPayment,
                discount,
                personalTrainer
            );
            saveToFileWriter.write(line);
        }
        saveToFileWriter.write(separator);
        saveToFileWriter.close();
        JOptionPane.showMessageDialog(frame, "Member details saved to MemberDetails.txt");
    } catch (IOException er) {
        JOptionPane.showMessageDialog(frame, "An error occurred while saving.");
        er.printStackTrace();
    }
});
        // Read from File button event
        readFromFileButton.addActionListener(e -> {
            try {
                File file = new File("MemberDetails.txt");
                if (!file.exists() || file.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "Member details not found");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append("\n");
                }
                scanner.close();

                displayTextArea.setText(sb.toString());
                displayTextArea.setCaretPosition(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error reading from file: " + ex.getMessage());
            }
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
        // Clear text fields
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        referralSourceField.setText("");
        trainerNameField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");

        // Deselect radio buttons
        genderGroup.clearSelection();

        // Reset combo boxes
        dobDayComboBox.setSelectedIndex(0);
        dobMonthComboBox.setSelectedIndex(0);
        dobYearComboBox.setSelectedIndex(0);
        msStartDayComboBox.setSelectedIndex(0);
        msStartMonthComboBox.setSelectedIndex(0);
        msStartYearComboBox.setSelectedIndex(0);

        // Reset plan combo box and price field
        planComboBox.setSelectedIndex(0);
        regularPriceField.setText("6500.00");
    }

    // method which create buttonZ
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
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setFont(MAIN_FONT);
        displayPanel.setBorder(BorderFactory.createTitledBorder("Data"));

        displayTextArea = new JTextArea();
        displayTextArea.setFont(MAIN_FONT);
        displayTextArea.setEditable(false);
        displayTextArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(displayTextArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        return displayPanel;
    }

    // entry main method
    public static void main(String[] args) {
        // creating object if GymGUI
        new GymGUI();
    }
}