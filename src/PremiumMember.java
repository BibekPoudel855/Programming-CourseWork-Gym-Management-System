public class PremiumMember extends GymMember {
    //    class Instance variables
    boolean isFullPayment;
    double paidAmount;
    double discountAmount;
    String personalTrainer;
    // final constant variable which is initialized in constructor
    final double  premiumCharge;
    // Constructor
    public PremiumMember(int id, String name, String location, String phone,
                         String email, String gender, String DOB, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.premiumCharge = 50000;
        this.paidAmount = 0;
        this.isFullPayment = false;
        this.discountAmount = 0;
        this.personalTrainer = personalTrainer;
    }
    // implementation of markAttendance() method which is abstract in GymMember Class
    @Override
    public void markAttendance() {
        if (this.isActiveStatus()) {
            this.attendance++;
            this.loyaltyPoints += 10;
        }
    }
    // getter methods of all instance variables
    public double getPremiumCharge() {
        return premiumCharge;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public boolean isFullPayment() {
        return isFullPayment;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    /* method which invoked when user pay amount and it validates some unintentional condition
    like pay amount less than 0 , pay more amount , duplication of payment etc*/
    public String payDueAmount(double paidAmount) {
        if(paidAmount<=0){
            return "You Cant Pay Money in Negative Value";
        }
        if(this.isFullPayment){
            return "Your Payment is Already Full No Need to Pay";
        }
        double paidAmountTemp = this.paidAmount + paidAmount;
        if(paidAmountTemp > premiumCharge){
            return "You Cannot Pay More Than Premium Charge.";
        }
        this.paidAmount = paidAmountTemp;
        if(this.paidAmount == premiumCharge){
            this.isFullPayment = true;
        }
        if(isFullPayment){
        return "You Sucessfully Paid " + paidAmount + ".";
        }else{
           return "You Paid " + paidAmount +  "and" + calculateRemainingAmount();
        }
    }
    // method which calculate remaining
    public String calculateRemainingAmount(){
        double remainingAmount = premiumCharge - this.paidAmount;
        return "Your Due Amount is" + remainingAmount + ".";
    }
    // method which calculate discount based on payment made by user
    public void calculateDiscount(){
        if(this.isFullPayment){
            this.discountAmount = premiumCharge * 10 / 100;
            System.out.println("You Got" + this.discountAmount + " Discount.");
        }else{
            System.out.println("You Cannot get any Discount.");
        }
    }
    // method which revert premium member
    public void revertPremiumMember(){
        super.resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.discountAmount = 0;
        this.paidAmount = 0;
        System.out.println("Member Revert Complete");
    }
    // method which displays data
    public void display(){
        super.display();
        System.out.println("Personal Trainer:- " + personalTrainer);
        System.out.println("Paid Amount:- " + paidAmount);
        if (isFullPayment) {
            System.out.println("You Paid Full Payment.");
            System.out.println("Discount Amount:- " + discountAmount);
        }else{
            System.out.println(calculateRemainingAmount());
        }
    }
}


