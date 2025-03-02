public class PremiumMember extends GymMember {
    final double  premiumCharge;
    String personalTrainer;
    boolean isFullPayment;
    double paidAmount;
    double discountAmount;

    public PremiumMember(int id, String name, String location, String phone,
                         String email, String gender, String DOB, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.premiumCharge = 50000;
        this.paidAmount = 0;
        this.isFullPayment = false;
        this.discountAmount = 0;
        this.personalTrainer = personalTrainer;
    }

//    getter methods

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
            double remainingAmount = premiumCharge - this.paidAmount;
            return "You Sucessfully Paid " + paidAmount + " Your Due Amount is" + remainingAmount + ".";
        }
    }

    public void calculateDiscount(){
        if(this.isFullPayment){
            this.discountAmount = premiumCharge * 10 / 100;
            System.out.println("You Got" + this.discountAmount + "% Discount.");
        }
    }
}

