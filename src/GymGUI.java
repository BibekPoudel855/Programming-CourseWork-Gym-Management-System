// importing all needed packages and library for tthis class
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/* this is main class for gym gui with public access modifier
 * this class is handles all gui part of this systme
 * it handles all user input and display data
 * it is also handles all event of buttons
 * it is also handles all data validation from user if enterd incorrect
 */
public class GymGUI {
    // instance variables for gui class to access from other all methiods
    // main frame or window variable
    private JFrame frame;
    // input fields instance variable for user input
    private JTextField idField, nameField, locationField, phoneField, emailField, referralSourceField, trainerNameField,
            paidAmountField, removalReasonField, regularPriceField, premiumChargeField, discountField;
    // radio button  instance variable for gender selection
    private JRadioButton maleRadio, femaleRadio;
    // plan drop down instance variable for regular member basic, standard, deluxe
    private JComboBox<String> planComboBox;
    // button group instance variable for radio button to make select only one at a time
    private ButtonGroup genderGroup;
    // buttons instance variable to get access from anywhere in class
    private JButton addRegularButton, addPremiumButton, activateMembershipButton, deactivateMembershipButton,
            markAttendanceButton, upgradePlanButton, calculateDiscountButton, payDueAmountButton,
            displayButton, clearButton, saveToFileButton, readFromFileButton, revertRegularMemberButton,
            revertPremiumMemberButton;
    // dropw down instance variable for dob and membership start date
    private JComboBox<String> dobDayComboBox, dobMonthComboBox, dobYearComboBox;
    private JComboBox<String> msStartDayComboBox, msStartMonthComboBox, msStartYearComboBox;
    private JPanel displayPanel;
    private JTextArea displayTextArea;
    // list for all member
    private ArrayList<GymMember> memberList = new ArrayList<>();
    // color for button
    private Color buttonColor = new Color(65, 102, 213);
    // font for text
    private final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 18);

    // table header string format for display and to save file
    // this is static and constant, which can be accessed from anywhere in class withoit object
    // kept here to avoid duplication when display and save to file
    private static final String TABLE_HEADER = String.format(
        "%-5s %-22s %-30s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-15s %-15s %-15s %-20s %-20s %-20s\n",
        "ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Membership Start", "Plan", "Price", "Attendance",
        "Loyalty", "Active", "Eligible Upgrade", "FullPay", "Discount", "PersonalTrainer", "Net Amount Paid", "Removal Reason"
        );
        
    // this is separator line for table header and other data
    // repeat method will multiply string with number of time
    private static final String TABLE_HEADER_LINE = "=".repeat(325) + "\n";
    // this is table row format for display and to save file
    private static final String TABLE_ROW_FORMAT =
    "%-5s %-22s %-30s %-15s %-35s %-8s %-12s %-18s %-12s %-10s %-10s %-10s %-15s %-15s %-15s %-15s %-20s %-20s %-20s\n";

    // this is constructor run immediately when object is created
    public GymGUI() {
        makeMainFrame();
    }

    // make the main method which makes or creates container and keeps other components and add all panels
    private void makeMainFrame() {
        // create main frame or container
        frame = new JFrame("Giant Gym Management System");
        // close app and stops program running background when cross is click
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set window size
        frame.setSize(1000, 700);
        // set layout for main component borderLayout 
        frame.setLayout(new BorderLayout(10, 10));

        // call mehtod  to make all panels
        // method returns object of panel and stores in variables
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel displayPanel = createDisplayPanel();

        // add returned panel from method to window
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(displayPanel, BorderLayout.CENTER);

        // add all button event
        addButtonEventListner();
        // make windwo to show
        frame.setVisible(true);
    }

    // make input panel for user data
    private JPanel createInputPanel() {
        // creating main inpput panel
        JPanel inputPanel = new JPanel();
        // setting layout grid with 2 column and 0 row
        inputPanel.setLayout(new GridLayout(0, 2, 5, 5));
        // keeping border border to input panel and keeping title
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Fields"));

        //////// creating input  fields and storing in instance variable //////////
        idField = createInputField(inputPanel, "ID :");
        nameField = createInputField(inputPanel, "Name :");
        locationField = createInputField(inputPanel, "Location :");
        phoneField = createInputField(inputPanel, "Phone :");
        emailField = createInputField(inputPanel, "Email:");

        // gender radio button
        JLabel genderLabel = new JLabel("Gender :");
        genderLabel.setFont(MAIN_FONT);
        inputPanel.add(genderLabel);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        maleRadio.setFont(MAIN_FONT);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setFont(MAIN_FONT);
        // keeping both radio button in group to make select only one at a time
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        inputPanel.add(genderPanel);

        // date of birth combo box
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

        // membership start date combo box
        years = generateYears(2024, 2025);
        JLabel msStartLabel = new JLabel("Membership Start Date :");
        msStartLabel.setFont(MAIN_FONT);
        inputPanel.add(msStartLabel);
        JPanel msStartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // keeping all days, months and years in combo box
        msStartDayComboBox = new JComboBox<>(days);
        msStartMonthComboBox = new JComboBox<>(months);
        msStartYearComboBox = new JComboBox<>(years);
        // setting main font
        msStartDayComboBox.setFont(MAIN_FONT);
        msStartMonthComboBox.setFont(MAIN_FONT);
        msStartYearComboBox.setFont(MAIN_FONT);
        // addiing to panel
        msStartPanel.add(msStartDayComboBox);
        msStartPanel.add(msStartMonthComboBox);
        msStartPanel.add(msStartYearComboBox);
        inputPanel.add(msStartPanel);
        // keeping referral source and trainer name field
        referralSourceField = createInputField(inputPanel, "Referral Source");
        trainerNameField = createInputField(inputPanel, "Trainer Name");

        // plan combo box for regular member
        String[] planOptions = { "Basic", "Standard", "Deluxe" };
        JLabel planLabel = new JLabel("Select Plan :");
        planLabel.setFont(MAIN_FONT);
        inputPanel.add(planLabel);
        planComboBox = new JComboBox<>(planOptions);
        planComboBox.setFont(MAIN_FONT);
        ///////// event for when user select plan, update price for their plan //////////
        planComboBox.addActionListener(e -> regularPlan());
        inputPanel.add(planComboBox);

        // keeping paid amount and removal reason field
        paidAmountField = createInputField(inputPanel, "Paid Amount :");
        removalReasonField = createInputField(inputPanel, "Removal Reason:");

        // non changable fields for prices and discount
        regularPriceField = createInputField(inputPanel, "Regular Plan Price:");
        regularPriceField.setText("6500");
        regularPriceField.setEditable(false);

        premiumChargeField = createInputField(inputPanel, "Premium Plan Charge:");
        premiumChargeField.setText("50000");
        premiumChargeField.setEditable(false);

        discountField = createInputField(inputPanel, "Discount Amount:");
        discountField.setText("0");
        discountField.setEditable(false);
        // returnig input panel
        return inputPanel;
    }

    // this method update price field when plan is changed
    public void regularPlan() {
        if (planComboBox.getSelectedIndex() == 0) {
            regularPriceField.setText("6500.00");
        } else if (planComboBox.getSelectedIndex() == 1) {
            regularPriceField.setText("12500.00");
        } else {
            regularPriceField.setText("18500.00");
        }
    }

    // make panel for all buttons
    private JPanel createButtonPanel() {
        // creating main button panel
        JPanel buttonPanel = new JPanel();
        // with grid layout with 1 column and 0 row
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));
        // storing button pannel returns from method in instance variable
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
        // returning button panel
        return buttonPanel;
    }

    // make input field with label
    public JTextField createInputField(JPanel panel, String labelText) {
        // it is mehtod which takes panel and label text as parameter
        // and returns text field with label
        // which provide code reusability
        JLabel inputLabel = new JLabel(labelText);
        inputLabel.setFont(MAIN_FONT);
        panel.add(inputLabel);

        JTextField inputTextField = new JTextField();
        inputTextField.setFont(MAIN_FONT);
        panel.add(inputTextField);
        // returning text field
        return inputTextField;
    }

    // make button and add to panel
    public JButton createButton(JPanel panel, String text) {
        // method which create button and add to text then add to panel
        JButton button = new JButton(text);
        // set button color and font
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFont(MAIN_FONT);
        panel.add(button);
        // returning button
        return button;
    }

    // make panel to show data
    public JPanel createDisplayPanel() {
        // creating main display panel
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setFont(MAIN_FONT);
        // keeping border and title
        displayPanel.setBorder(BorderFactory.createTitledBorder("Data"));

        // creating text area to display data
        displayTextArea = new JTextArea();
        displayTextArea.setEditable(false);
        // keeping monospaced font & make table looks good
        displayTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayTextArea.setCaretPosition(0);
        JScrollPane scrollPane = new JScrollPane(displayTextArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        // returning display panel to main method
        // which will be added to main frame
        return displayPanel;
    }

    // generate days and storing to array
    public String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        // return days array so that it can be used in combo box
        return days;
    }

    // generate year and storing to array
    public String[] generateYears(int fromYear, int toYear) {
        // it takes from year and to year as parameter
        // it will generate years from from year to to year
        String[] years = new String[toYear - fromYear + 1];
        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(fromYear + i);
        }
        // return array with years
        return years;
    }

    // check if user input is valid
    public boolean validatePersonalInformation() {
        // gettring value from input field and storing to array
        String[] details = getInputFieldDatas();
        // extracting each field data from array
        // trimming to remove extra spaces
        String idStr = details[0];
        String name = details[1];
        String location = details[2];
        String dob = details[6];
        String membershipStart = details[7];
        String phone = details[3];
        String email = details[4];
        String gender = details[5];

        // id validation
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "ID is Empty");
            idField.requestFocus();
            idField.setText("");
            return false;
        }
        try {
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                JOptionPane.showMessageDialog(frame, "ID must be positive number");
                idField.requestFocus();
                idField.setText("");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "ID must be number");
            idField.requestFocus();
            idField.setText("");
            return false;
        }
        // name validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name is Empty");
            nameField.requestFocus();
            return false;
        }
        if (name.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Name must be at least 3 characters");
            nameField.requestFocus();
            return false;
        }
        // checking if name has number or not
        if (name.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Name must only character");
            nameField.requestFocus();
            return false;
        }
        // location validation
        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Location is Empty");
            locationField.requestFocus();
            return false;
        }
        if (location.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Enter valid location");
            locationField.requestFocus();
            return false;
        }
        // checking if location has number or not
        if(location.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Location must only character");
            locationField.requestFocus();
            return false;
        }
        // phone validation
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Phone is Empty");
            phoneField.requestFocus();
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
        // email validation
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Email is Empty");
            emailField.requestFocus();
            return false;
        }
        // checking if email has correct format or not
        if (email.length() < 7 || !email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid email");
            emailField.requestFocus();
            return false;
        }
        // gender validation
        if (gender.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please select gender");
            return false;
        }
        // dob validation
        // checking if membership start date is before dob or not
        try {
        // splitting date based  on / into an array and checking if dob is before membership start date
        String[] dobParts = dob.split("/");
        String[] msStartParts = membershipStart.split("/");
        int dobYear = Integer.parseInt(dobParts[2]);
        int msStartYear = Integer.parseInt(msStartParts[2]);
        if (msStartYear < dobYear){
            JOptionPane.showMessageDialog(frame, "Membership start date must be after DOB");
            return false;
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Please select DOB");
            return false;
        }
        
        // returns when all fields are valid
        return true;
    }

    // get id from field and check valid
    private Integer getValidatedId() {
        // method which return id from field 
        // and check if it is valid or not
        // if not valid then show message and return null
        // if valid then return id
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter ID");
            idField.requestFocus();
            idField.setText("");
            return null;
        }
        try {
            int id = Integer.parseInt(idStr);
            if (id <= 0) {
                JOptionPane.showMessageDialog(frame, "ID must be positive number");
                idField.requestFocus();
                idField.setText("");
                return null;
            }
            return id;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "ID must be a number");
            idField.requestFocus();
            idField.setText("");
            return null;
        }
    }

    // method whic extract input field data and return as array
    private String[] getInputFieldDatas() {
        // extracting input field data and storing to array
        // trimming to remove extra spaces
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        // ternary operator to check which radio button is selected
        String gender = maleRadio.isSelected() ? "Male" : (femaleRadio.isSelected() ? "Female" : "");
        // formatting date of birth and membership start date
        String dob = dobDayComboBox.getSelectedItem() + "/" +
                    dobMonthComboBox.getSelectedItem() + "/" +
                    dobYearComboBox.getSelectedItem();
        String membershipStart = msStartDayComboBox.getSelectedItem() + "/" +
                                msStartMonthComboBox.getSelectedItem() + "/" +
                                msStartYearComboBox.getSelectedItem();
        String referralSource = referralSourceField.getText().trim();
        String trainerName = trainerNameField.getText().trim();
        // returning all field data array
        return new String[] {
            id, name, location, phone, email, gender, dob, membershipStart, referralSource, trainerName
        };
    }
    
    // add member to list
    private Boolean addMemberToList(GymMember member) {
        // it chekcks if member is added to list or not
        // if added then return true else false
        try {
            memberList.add(member);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // find member by id
    private GymMember findMemberById(int id) {
        // it takes id as parameter and checks if member is in list or not
        for (GymMember member : memberList) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    // add all button event listeners
    private void addButtonEventListner() {
        // Add Regular Member button event
        addRegularButton.addActionListener(e -> {
            try {
                // get input field data and validate
                String[] details = getInputFieldDatas();
                if (!validatePersonalInformation()) {
                    return;
                }
                // validating referral source
                String referralSource = details[8];
                if (referralSource.length() < 3) {
                    JOptionPane.showMessageDialog(frame, "Referral Source must be at least 3 characters");
                    referralSourceField.requestFocus();
                    return;
                }
                // getting validated id 
                Integer id = getValidatedId();
                if (id == null) {
                    return;
                }
                // checking if id is duplicate or not
                if (findMemberById(id) != null) {
                    JOptionPane.showMessageDialog(frame, "Member ID Duplicate");
                    return;
                }
                // creating regular member object and adding to list
                RegularMember member = new RegularMember(
                        id, details[1], details[2], details[3], details[4], details[5],
                        details[6], details[7], referralSource
                );
                addMemberToList(member);
                // show message if added successfully
                JOptionPane.showMessageDialog(frame, "Regular member added successfully");
                clearInputFields();
            } catch (Exception ex) {
                // show message if something went wrong
                JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        });

        // Add Premium Member button event
        addPremiumButton.addActionListener(e -> {
            try {
                // get input field data and validate
                // check if all fields are valid
                String[] details = getInputFieldDatas();
                if (!validatePersonalInformation()) {
                    return;
                }
                // validating trainer name
                // checking if  less than 3 characters
                String trainerName = details[9];
                if (trainerName.length() < 3) {
                    JOptionPane.showMessageDialog(frame, "Trainer Name must be at least 3 characters");
                    trainerNameField.requestFocus();
                    return;
                }
                // getting validated id
                Integer id = getValidatedId();
                if (id == null) {
                    return;
                }
                // checking if id is duplicate or not
                if (findMemberById(id) != null) {
                    JOptionPane.showMessageDialog(frame, "Member ID Duplicate");
                    return;
                }
                // creating premium member object and adding to list
                PremiumMember member = new PremiumMember(
                        id, details[1], details[2], details[3], details[4], details[5],
                        details[6], details[7], trainerName
                );
                addMemberToList(member);
                // show message if added successfully
                JOptionPane.showMessageDialog(frame, "Premium member added successfully");
                clearInputFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        });
    
        // Activate Membership button event
        activateMembershipButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            // calling method to activate membership
            member.activateMembership();
            JOptionPane.showMessageDialog(frame, "Membership activated");
        });

        // Deactivate Membership button event
        deactivateMembershipButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            // calling method to deactivate membership
            member.deactivateMembership();
            JOptionPane.showMessageDialog(frame, "Membership deactivated");
        });

        // Mark Attendance button event
        markAttendanceButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            // checking if member is active or not
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            // calling method to mark attendance
            member.markAttendance();
            JOptionPane.showMessageDialog(frame, "Attendance marked succesfully");
        });


        // Upgrade Plan button event
        upgradePlanButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            // checking if member is regular or not
            if (!(member instanceof RegularMember)) {
                JOptionPane.showMessageDialog(frame, "Member is not a Regular Member");
                return;
            }
            // checking if member is eligible for upgrade or not
            // checking if member is active or not
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            // calling method by casting to regular member
            // because upgrade plan is only for regular member
            RegularMember regularMember = (RegularMember) member;
            String selectedPlan = (String) planComboBox.getSelectedItem();
            String resultUpgradePlanResult = regularMember.upgradePlan(selectedPlan);
            // displayigg result
            JOptionPane.showMessageDialog(frame, resultUpgradePlanResult);
        });

        // Calculate Discount button event
        calculateDiscountButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            // checking if member is premium or not
            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(frame, "Member not Premium Member");
                return;
            }
            // checking if member is eligible for discount or not by active status
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            // calling method to calculate discount from premium member because it is only for premium member
            // other wise if it was regular member there will be error
            PremiumMember premiumMember = (PremiumMember) member;
            String calculateDiscountResult = premiumMember.calculateDiscount();
            JOptionPane.showMessageDialog(frame, calculateDiscountResult);
            discountField.setText(String.valueOf(premiumMember.getDiscountAmount()));
        });

        // Pay Due Amount button event
        payDueAmountButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            /* validating paid amount
             if paid amount is empty or not valid number
             then show error message*/
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
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found");
                return;
            }
            // checking if member is premium or not
            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(frame, "Member not Premium Member");
                return;
            }
            // checking if member is eligible for payment or not by active status
            if (!member.isActiveStatus()) {
                JOptionPane.showMessageDialog(frame, "Member not active");
                return;
            }
            /*  calling method to pay due amount from premium member because it is only for premium member
             other wise if it was regular member there will be error */
            PremiumMember premiumMember = (PremiumMember) member;
            String result = premiumMember.payDueAmount(paidAmount);
            JOptionPane.showMessageDialog(frame, result);
            clearInputFields();
        });

        // Revert Regular Member button event
        revertRegularMemberButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null || !(member instanceof RegularMember)) {
                JOptionPane.showMessageDialog(frame, "Current Id member is not Regular Member");
                return;
            }
            /* checking removal reason field 
               if it is empty then keeping Not Provided */
            String reason = removalReasonField.getText();
            if (reason.isEmpty()) {
                reason = "Not Provided";
            }
            /* casting to regular member
             because this revert regular member is only for regular member */
            RegularMember regularMember = (RegularMember) member;
            // calling method to revert regular member
            regularMember.revertRegularMember(reason);
            JOptionPane.showMessageDialog(frame, "Regular Member reverted successfully");
            clearInputFields();
        });

        // Revert Premium Member button event
        
        revertPremiumMemberButton.addActionListener(e -> {
            // get validated id
            Integer id = getValidatedId();
            if (id == null) {
                return;
            }
            // checking if member already exists or not
            GymMember member = findMemberById(id);
            if (member == null || !(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(frame, "Current Id member is not Premium Member");
                return;
            }
            /* casting to premium member
            because this revert premium member is only for premium member */
            PremiumMember premiumMember = (PremiumMember) member;
            premiumMember.revertPremiumMember();
            JOptionPane.showMessageDialog(frame, "Premium Member reverted successfully");
            clearInputFields();
        });

        // Display button event
        displayButton.addActionListener(e -> {
            // checking if member list is empty or not
            if (memberList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No members to display");
                return;
            }
            // creating string builder to store all member info
            StringBuilder sb = new StringBuilder();
            // and keeping header and header line
            sb.append(TABLE_HEADER);
            sb.append(TABLE_HEADER_LINE);
            // iterating to list and getting each member info then appending to string builder
            for (GymMember member : memberList) {
                sb.append(generateMemberInfoRow(member));
            }
            // setting text area to string builder
            displayTextArea.setText(sb.toString());
            displayTextArea.setCaretPosition(0);
        });

        // Save to File button event
        saveToFileButton.addActionListener(e -> {
            // checking if member list is empty or not
            if (memberList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No members to save");
                return;
            }
            // creating file and writing to file in append mode

            try {
                File file = new File("MemberDetails.txt");
                boolean fileExists = file.exists();
                // append mode by setting true
                FileWriter fileWriter = new FileWriter(file, true); 

                /*checking if file is empty or not
                 if empty then writing header and header line
                 otherwise only writing member info */
                if (!fileExists || file.length() == 0) {
                    fileWriter.write(TABLE_HEADER);
                    fileWriter.write(TABLE_HEADER_LINE);
                }
                // writing each member info to file
                for (GymMember member : memberList) {
                    fileWriter.write(generateMemberInfoRow(member));
                }
                fileWriter.close();
                // showring message if file is saved successfully
                JOptionPane.showMessageDialog(frame, "Member details saved to FILE MemberDetails.txt");
            } catch (IOException er) {
                // show message if file not found
                JOptionPane.showMessageDialog(frame, "An error occurred while saving.");
            }
        });

        // Clear button event
        clearButton.addActionListener(e -> {
            // calling method to clear all input fields
            clearInputFields();
        });

        // Read from File button event
        readFromFileButton.addActionListener(e -> {
            File file = new File("MemberDetails.txt");
            // checking if file exists or not
            if (!file.exists() || file.length() == 0) {
                JOptionPane.showMessageDialog(frame, "Member details not found");
                return;
            }
            // reading file and appending to string builder
            try {
                Scanner myReader = new Scanner(file);
                StringBuilder memberDetailRow = new StringBuilder();
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    memberDetailRow.append(data).append("\n");
                }
                myReader.close();
                // setting text area to string builder
                displayTextArea.setText(memberDetailRow.toString());
            } catch (FileNotFoundException err) {
                // show message if file not found
                JOptionPane.showMessageDialog(frame, "An error occurred.");
            }
        });
    }

    // make row for member info for display and file
    private String generateMemberInfoRow(GymMember member) {
        // setting default values because if member is not premium or regular
        String plan = "NA", price = "NA", fullPayment = "NA", discount = "NA", personalTrainer = "NA", netAmountPaid = "NA", removalReason = "NA";
        String eligibleForUpgrade = "No";
        // getting all member info then storing to variables
        int attendance = member.getAttendance();
        double loyaltyPoints = member.getLoyaltyPoints();
        boolean active = member.isActiveStatus();
        String gender = member.getGender();
        String dob = member.getDOB();
        String startDate = member.getMembershipStartDate();
        // setting status to active or inactive based on active status
        String status;
        if (active) {
            status = "Active";
        } else {
            status = "Inactive";
        }
        // checking if member is regular or premium
        // if regular then getting all regular member info
        if (member instanceof RegularMember) {
            RegularMember regularMemberObj = (RegularMember) member;
            plan = regularMemberObj.getPlan();
            price = String.valueOf(regularMemberObj.getPrice());
            if (active && attendance >= 30) {
                eligibleForUpgrade = "Yes";
            }
            // Show removal reason if set, else NA
            removalReason = regularMemberObj.getRemovalReason();
            if (removalReason == null || removalReason.trim().isEmpty()) {
                removalReason = "NA";
            }
            netAmountPaid = "NA";
        // checking if member is premium then getting all premium member info
        } else if (member instanceof PremiumMember) {
            PremiumMember premiumMemberObj = (PremiumMember) member;
            plan = "Premium";
            price = String.valueOf(premiumMemberObj.getPremiumCharge());
            // getting full payment status
            if (premiumMemberObj.isFullPayment()) {
                fullPayment = "true";
            } else {
                fullPayment = "false";
            }
            if (premiumMemberObj.isFullPayment()) {
                discount = String.valueOf(premiumMemberObj.getDiscountAmount());
            } else {
                discount = "0";
            }
            personalTrainer = premiumMemberObj.getPersonalTrainer();
            netAmountPaid = String.valueOf(premiumMemberObj.getPaidAmount());
            removalReason = "NA"; 
    }
    // formatting member detail in tablular row
    String memberDetailRow = String.format(
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
        status,
        eligibleForUpgrade,
        fullPayment,
        discount,
        personalTrainer,
        netAmountPaid,
        removalReason
    );
    // returning member detail row
    // which will be used to display and save to file
    return memberDetailRow;
}
    // clear all input fields
    private void clearInputFields() {
        // clearing all input fields

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
        // setting default value to combo box
        dobDayComboBox.setSelectedIndex(0);
        dobMonthComboBox.setSelectedIndex(0);
        dobYearComboBox.setSelectedIndex(0);
        msStartDayComboBox.setSelectedIndex(0);
        msStartMonthComboBox.setSelectedIndex(0);
        msStartYearComboBox.setSelectedIndex(0);
        planComboBox.setSelectedIndex(0);
        regularPriceField.setText("6500");
        displayTextArea.setText("");
        discountField.setText("0");
    }

    // main method to run program
    // entry point of this system
    public static void main(String[] args) {
        new GymGUI();
    }
}
