import javax.swing.*;
import java.awt.*;

public class GymGUI {
    // instance variables
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, dobField;
    private JRadioButton maleRadio, femaleRadio;
    private JTextField membershipStartField, referralSourceField, trainerNameField, paidAmountField, removalReasonField,regularPriceField, premiumChargeField, discountField;
    private JComboBox<String> planComboBox;
    private JTextArea displayArea;
    private JButton addRegularButton, addPremiumButton, activateMembershipButton, deactivateMembershipButton,markAttendanceButton, upgradePlanButton, calculateDiscountButton, revertMemberButton,payDueAmountButton, displayButton, clearButton,saveToFileButton, readFromFileButton;
    
    
    // color variable
    private Color buttonColor = new Color(0, 120, 215);
    //  font variable
    private final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 18);

    // constructor to make GUI
    public GymGUI() {
        makeMainFrame();
    }

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

        // adding panels to the main frame in diferent positions
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(displayPanel, BorderLayout.CENTER);

        // add event listeners to buttons 
        addButtonEventListner();
        // making frame visible
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        // creating input panel in grid layout row and column
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Personal Details"));

        // creating and adding input fields
        idField = createInputField(inputPanel, "ID :");
        nameField = createInputField(inputPanel, "Name :");
        locationField = createInputField(inputPanel, "Location :");
        phoneField = createInputField(inputPanel, "Phone :");
        emailField = createInputField(inputPanel, "Email:");
        dobField = createInputField(inputPanel, "DOB :");

        /* creating gender label and gender radion button like male and femae
           and adding them to the input panel
           we added genderPanel becasue i want to add two radio buttons in one line
         */
        JLabel genderLabel = new JLabel("Gender :");
        genderLabel.setFont(MAIN_FONT);

        inputPanel.add(genderLabel);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        maleRadio.setFont(MAIN_FONT);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setFont(MAIN_FONT);
        
        // kept in group because we make only one  button can be selected at a time
        ButtonGroup genderGroup = new ButtonGroup();
        // adding to group
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        // adding to panel
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);

        // adding gender panel to input panel
        inputPanel.add(genderPanel);
        // Membership Details
        membershipStartField = createInputField(inputPanel, "Membership Start Date ");
        referralSourceField = createInputField(inputPanel, "Referral Source");
        trainerNameField = createInputField(inputPanel, "Trainer Name ");

        // Plan Selection
        String[] planOptions = {"Basic", "Standard", "Deluxe"};
        inputPanel.add(new JLabel("Select Plan :"));
        planComboBox = new JComboBox<>(planOptions);
        inputPanel.add(planComboBox);

        // Payment and Removal
        paidAmountField = createInputField(inputPanel, "Paid Amount :");
        removalReasonField = createInputField(inputPanel, "Removal Reason:");

        // Pricing Information
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

    private JTextField createInputField(JPanel panel, String labelText) {
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
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        // Add buttons
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


    private JButton createButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFont(MAIN_FONT);
        panel.add(button);
        return button;
    }

    private JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(MAIN_FONT);

        displayPanel.setBorder(BorderFactory.createTitledBorder("Displaying Portal"));
        // IT MAKE display area scrollable when overflow text 
        displayPanel.add(new JScrollPane(displayArea));

        return displayPanel;
    }


    public static void main(String[] args) {
        new GymGUI();
    }
}