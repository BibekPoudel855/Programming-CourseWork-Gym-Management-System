public abstract class GymMember {
//    class Instance variables
    protected int id;
    protected String DOB;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String membershipStartDate;
    protected int attendance;
    protected double loyaltyPoints;
    protected boolean activeStatus;
    // Constructor
    public GymMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
        this.loyaltyPoints = 0;
        this.attendance = 0;
        this.activeStatus = false;
    }
    // abstract method Mark Attendance
    public abstract void markAttendance();

    // activateMembership() method sets activeStatus to true when the membership needs to be activated or renewed
    public void activateMembership(){
        this.activeStatus = true;
    }

    //  deactivateMembership() sets activeStatus to false if the membership needs to be deactivated or paused.
    public void deactivateMembership(){
        if(activeStatus){
            this.activeStatus = false;
            System.out.println(id + "Member Deactivated");
        }else{
            System.out.println(id + "Member Already Activated");
        }
    }

    // resetMember() resets member details
    public void resetMember(){
        this.activeStatus = false;
        this.attendance = 0;
        this.loyaltyPoints = 0;
    }
//    getter Methods
    public int getId() {
        return id;
    }

    public String getDOB() {
        return DOB;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getMembershipStartDate() {
        return membershipStartDate;
    }

    public int getAttendance() {
        return attendance;
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    //  display method shows member all details
    public void display(){
        System.out.println("ID:- " + this.id);
        System.out.println("Full Name:- " + this.name);
        System.out.println("Address:- " + this.location);
        System.out.println("Phone Number:- " + this.phone);
        System.out.println("Email Address:- " + this.email);
        System.out.println("Gender:- " + this.gender);
        System.out.println("Date of Birth:- " + this.DOB);
        System.out.println("Membership Start Date:- " + this.membershipStartDate);
        System.out.println("Total Attendance:- " + this.attendance);
        System.out.println("Total Loyalty Points:- " + this.loyaltyPoints);
        System.out.println("Active Status:- " + this.activeStatus);
    }
}
