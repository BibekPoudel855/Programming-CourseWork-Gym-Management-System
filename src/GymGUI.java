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

    // Table formatting constants
private static final String TABLE_HEADER = String.format(
    "%-5s %-22s %-18s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-12s %-15s %-20s %-15s\n",
    "ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Membership Start", "Plan", "Price", "Attendance",
    "Loyalty", "Active", "FullPay", "Discount", "PersonalTrainer", "Net Amount Paid"
);
private static final String TABLE_HEADER_LINE = "=".repeat(280) + "\n";
private static final String TABLE_ROW_FORMAT =
    "%-5s %-22s %-18s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-12s %-15s %-20s %-15s\n";
    // constructor to make GUI
    public GymGUI() {
        makeMainFrame();
    }

    private void makeMainFrame() {
        frame = new JFrame("Giant Gym Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel displayPanel = createDisplayPanel();

        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(displayPanel, BorderLayout.CENTER);

        addButtonEventListner();
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Fields"));

        idField = createInputField(inputPanel, "ID :");
        nameField = createInputField(inputPanel, "Name :");
        locationField = createInputField(inputPanel, "Location :");
        phoneField = createInputField(inputPanel, "Phone :");
        emailField = createInputField(inputPanel, "Email:");

        JLabel genderLabel = new JLabel("Gender :");
        genderLabel.setFont(MAIN_FONT);
        inputPanel.add(genderLabel);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        maleRadio.setFont(MAIN_FONT);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setFont(MAIN_FONT);
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        inputPanel.add(genderPanel);

        JLabel dobLabel = new JLabel("DOB :");
        dobLabel.setFont(MAIN_FONT);
        inputPanel.add(dobLabel);
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

        referralSourceField = createInputField(inputPanel, "Referral Source");
        trainerNameField = createInputField(inputPanel, "Trainer Name");

        String[] planOptions = { "Basic", "Standard", "Deluxe" };
        JLabel planLabel = new JLabel("Select Plan :");
        planLabel.setFont(MAIN_FONT);
        inputPanel.add(planLabel);
        planComboBox = new JComboBox<>(planOptions);
        planComboBox.setFont(MAIN_FONT);
        planComboBox.addActionListener(e -> regularPlan());
        inputPanel.add(planComboBox);

        paidAmountField = createInputField(inputPanel, "Paid Amount :");
        removalReasonField = createInputField(inputPanel, "Removal Reason:");

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

    public void regularPlan() {
        if (planComboBox.getSelectedIndex() == 0) {
            regularPriceField.setText("6500.00");
        } else if (planComboBox.getSelectedIndex() == 1) {
            regularPriceField.setText("12500.00");
        } else {
            regularPriceField.setText("18500.00");
        }
    }

    public String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        return days;
    }

    public String[] generateYears(int fromYear, int toYear) {
        String[] years = new String[toYear - fromYear + 1];
        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(fromYear + i);
        }
        return years;
    }

    public JTextField createInputField(JPanel panel, String labelText) {
        JLabel inputLabel = new JLabel(labelText);
        inputLabel.setFont(MAIN_FONT);
        panel.add(inputLabel);

        JTextField inputTextField = new JTextField();
        inputTextField.setFont(MAIN_FONT);
        panel.add(inputTextField);

        return inputTextField;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));

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

    public boolean validatePersonalInformation() {
        String[] details = getInputFieldDatas();
        String idStr = details[0];
        String name = details[1];
        String location = details[2];
        String phone = details[3];
        String email = details[4];
        String gender = details[5];

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "ID is Empty");
            idField.requestFocus();
            return false;
        }
        try {
            int id = Integer.parseInt(idStr);
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

        if (name.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Name must be at least 3 characters");
            nameField.requestFocus();
            return false;
        }

        if (location.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Enter valid location");
            locationField.requestFocus();
            return false;
        }

        try {
            if (!(phone.length() == 10)) {
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

        if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid email");
            emailField.requestFocus();
            return false;
        }

        if (gender.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select gender");
            return false;
        }
        return true;
    }

    private Integer getValidatedId() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter ID");
            idField.requestFocus();
            return null;
        }
        try {
            int id = Integer.parseInt(idStr);
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

    private String[] getInputFieldDatas() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String gender = maleRadio.isSelected() ? "Male" : (femaleRadio.isSelected() ? "Female" : "");
        String dob = dobDayComboBox.getSelectedItem() + "/" +
                    dobMonthComboBox.getSelectedItem() + "/" +
                    dobYearComboBox.getSelectedItem();
        String membershipStart = msStartDayComboBox.getSelectedItem() + "/" +
                                msStartMonthComboBox.getSelectedItem() + "/" +
                                msStartYearComboBox.getSelectedItem();
        String referralSource = referralSourceField.getText().trim();
        String trainerName = trainerNameField.getText().trim();

        return new String[] {
            id, name, location, phone, email, gender, dob, membershipStart, referralSource, trainerName
        };
    }
    
    private boolean isIdInFile(int id) {
        File file = new File("MemberDetails.txt");
        if (!file.exists()) return false;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("ID") || line.startsWith("=")) continue;
                String[] parts = line.split("\\s+");
                if (parts.length > 0 && parts[0].matches("\\d+")) {
                    if (Integer.parseInt(parts[0]) == id) return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }
    private Boolean addMemberToList(GymMember member) {
        memberList.add(member);
        return true;
    }
    private void addButtonEventListner() {
        // Add Regular Member button event
            addRegularButton.addActionListener(e -> {
                try {
                    String[] details = getInputFieldDatas();
                    if (!validatePersonalInformation()) {
                        return;
                    }
                    String referralSource = details[8];
                    if (referralSource.isEmpty() || referralSource.length() < 3) {
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
            RegularMember member = new RegularMember(
                    id, details[1], details[2], details[3], details[4], details[5],
                    details[6], details[7], referralSource
            );
            addMemberToList(member);
            JOptionPane.showMessageDialog(frame, "Regular member added successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Something went wrong");
        }
    });

        // Add Premium Member button event
    addPremiumButton.addActionListener(e -> {
        try {
            String[] details = getInputFieldDatas();
            if (!validatePersonalInformation()) {
                return;
            }
            String trainerName = details[9];
            if (trainerName.isEmpty() || trainerName.length() < 3) {
                JOptionPane.showMessageDialog(frame, "Please enter Trainer Name");
                trainerNameField.requestFocus();
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
            PremiumMember member = new PremiumMember(
                    id, details[1], details[2], details[3], details[4], details[5],
                    details[6], details[7], trainerName
            );
            addMemberToList(member);
            JOptionPane.showMessageDialog(frame, "Premium member added successfully");
        } catch (Exception ex) {
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
    sb.append(TABLE_HEADER);
    sb.append(TABLE_HEADER_LINE);
    for (GymMember member : memberList) {
        String plan = "", price = "", fullPayment = "NA", discount = "NA", personalTrainer = "NA", netAmountPaid = "NA";
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
            netAmountPaid = String.valueOf(pm.getPaidAmount());
        }

        String line = String.format(
            TABLE_ROW_FORMAT,
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
            personalTrainer,
            netAmountPaid
        );
        sb.append(line);
    }
    displayTextArea.setText(sb.toString());
    displayTextArea.setCaretPosition(0);
});

        // Clear button event
        clearButton.addActionListener(e -> {
            clearInputFields();
            
        });

        // Save to File button event (only one, no duplicate)
        saveToFileButton.addActionListener(e -> {
    if (memberList.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No members to save");
        return;
    }
    try {
        File file = new File("MemberDetails.txt");
        boolean fileExists = file.exists();
        FileWriter saveToFileWriter = new FileWriter(file, true); // append mode

        if (!fileExists || file.length() == 0) {
            saveToFileWriter.write(TABLE_HEADER);
            saveToFileWriter.write(TABLE_HEADER_LINE);
        }

        for (GymMember member : memberList) {
            String plan = "", price = "", fullPayment = "NA", discount = "NA", personalTrainer = "NA", netAmountPaid = "NA";
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
                netAmountPaid = String.valueOf(pm.getPaidAmount());
            }

            String line = String.format(
                TABLE_ROW_FORMAT,
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
                personalTrainer,
                netAmountPaid
            );
            saveToFileWriter.write(line);
        }
        saveToFileWriter.close();
        JOptionPane.showMessageDialog(frame, "Member details saved to FILE MemberDetails.txt");
    } catch (IOException er) {
        JOptionPane.showMessageDialog(frame, "An error occurred while saving." + er.getMessage());
    }
});

        // Read from File button event
    readFromFileButton.addActionListener(e -> {
        File file = new File("MemberDetails.txt");
        if (!file.exists() || file.length() == 0) {
            JOptionPane.showMessageDialog(frame, "Member details not found");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            displayTextArea.setText(sb.toString());
            displayTextArea.setCaretPosition(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading from file: " + ex.getMessage());
        }
    });
}

    private GymMember findMemberById(int id) {
        for (GymMember member : memberList) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        referralSourceField.setText("");
        trainerNameField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        genderGroup.clearSelection();
        dobDayComboBox.setSelectedIndex(0);
        dobMonthComboBox.setSelectedIndex(0);
        dobYearComboBox.setSelectedIndex(0);
        msStartDayComboBox.setSelectedIndex(0);
        msStartMonthComboBox.setSelectedIndex(0);
        msStartYearComboBox.setSelectedIndex(0);
        planComboBox.setSelectedIndex(0);
        regularPriceField.setText("6500.00");
        displayTextArea.setText("");
    }

    public JButton createButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFont(MAIN_FONT);
        panel.add(button);
        return button;
    }

    public JPanel createDisplayPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setFont(MAIN_FONT);
        displayPanel.setBorder(BorderFactory.createTitledBorder("Data"));

        displayTextArea = new JTextArea();
        // displayTextArea.setFont(MAIN_FONT);
        displayTextArea.setEditable(false);
        displayTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Use Monospaced font
        displayTextArea.setCaretPosition(0);
        JScrollPane scrollPane = new JScrollPane(displayTextArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        return displayPanel;
    }

    public static void main(String[] args) {
        new GymGUI();
    }
}