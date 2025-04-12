import javax.swing.*;
import java.awt.*;

public class GymGUI {
    // instance variables
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, referralSourceField, trainerNameField, paidAmountField, removalReasonField, regularPriceField, premiumChargeField, discountField;
    private JRadioButton maleRadio, femaleRadio;
    private JComboBox<String> planComboBox;
    private JTextArea displayArea;
    private JButton addRegularButton, addPremiumButton, activateMembershipButton, deactivateMembershipButton, markAttendanceButton, upgradePlanButton, calculateDiscountButton, revertMemberButton, payDueAmountButton, displayButton, clearButton, saveToFileButton, readFromFileButton;
    private JComboBox<String> dobDayComboBox, dobMonthComboBox, dobYearComboBox;
    private JComboBox<String> msStartDayComboBox, msStartMonthComboBox, msStartYearComboBox;

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

        /* creating gender label and gender radio button like male and female
           and adding them to the input panel
           we added genderPanel because we want to add two radio buttons in one line
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
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

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
        String[] planOptions = {"Basic", "Standard", "Deluxe"};
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

    // method which generate years and return string array by method String.valueOf()
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
        revertMemberButton = createButton(buttonPanel, "Revert Member");
        payDueAmountButton = createButton(buttonPanel, "Pay Due Amount");
        displayButton = createButton(buttonPanel, "Display");
        clearButton = createButton(buttonPanel, "Clear");
        saveToFileButton = createButton(buttonPanel, "Save to File");
        readFromFileButton = createButton(buttonPanel, "Read from File");

        return buttonPanel;
    }

    // method which handles all buttons event
    private void addButtonEventListner() {
        // add regular member button event
        addRegularButton.addActionListener(e -> {
            System.out.println("Add Regular Member button clicked");
        });

        // add premium member button event
        addPremiumButton.addActionListener(e -> {
            // Add logic for adding a premium member
            System.out.println("Add Premium Member button clicked");
        });

        // activate membership button event
        activateMembershipButton.addActionListener(e -> {
            System.out.println("Activate Membership button clicked");
        });

        // deactivate membership button event
        deactivateMembershipButton.addActionListener(e -> {
            System.out.println("Deactivate Membership button clicked");
        });

        // mark attendance button event
        markAttendanceButton.addActionListener(e -> {
            System.out.println("Mark Attendance button clicked");
        });

        // upgrade plan button event
        upgradePlanButton.addActionListener(e -> {
            System.out.println("Upgrade Plan button clicked");
        });

        // calculate discount button event
        calculateDiscountButton.addActionListener(e -> {
            System.out.println("Calculate Discount button clicked");
        });

        // revert member button event
        revertMemberButton.addActionListener(e -> {
            System.out.println("Revert Member button clicked");
        });

        // pay due amount button event
        payDueAmountButton.addActionListener(e -> {
            System.out.println("Pay Due Amount button clicked");
        });

        // display button event
        displayButton.addActionListener(e -> {
            System.out.println("Display button clicked");
        });

        // clear button event
        clearButton.addActionListener(e -> {
            System.out.println("Clear button clicked");
        });

        // save to File button event
        saveToFileButton.addActionListener(e -> {
            System.out.println("Save to File button clicked");
        });
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

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(MAIN_FONT);

        displayPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        // IT MAKE display area scrollable when overflow text
        displayPanel.add(new JScrollPane(displayArea));

        return displayPanel;
    }

    // entry main method
    public static void main(String[] args) {
        // creating object if GymGUI
        new GymGUI();
    }
}