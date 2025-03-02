public class RegularMember extends GymMember{
    private final int attendanceLimit = 30;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

//    Constructor
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource) {
        //calling parent class constructor
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
//        setting instance variable
        this.isEligibleForUpgrade = false;
        this.plan = "basic";
        this.price = 6500;
        this.removalReason = "";
        this.referralSource = referralSource;
    }

    // Implementation of abstract method Mark Attendance
    @Override
    public void markAttendance(){
        this.loyaltyPoints += 5;
        this.attendance++;

//        if(getAttendance() >= attendanceLimit){
//            this.isEligibleForUpgrade = true;
//        }
    }
//method that returns plan price based on price of plan
    public double getPlanPrice(String plan){
        plan = plan.toLowerCase();
        switch (plan) {
            case "basic" :
                return 6500;
            case "standard" :
                return 12500;
            case "deluxe" :
                return 18500;
            default: return -1;
        }
    }
    public String upgradePlan(String plan){
        if(this.plan.toLowerCase().equals(plan.toLowerCase())){
            return "Same Plan Selected";
        }
        if(!isEligibleForUpgrade){
            return "User not eligible for upgrade plan. User Should complete attendance";
        }


        double planPrice = getPlanPrice(plan);
        if(planPrice == -1){
            return "Wrong PLan Selected. Please try again";
        }


        this.plan = plan.toLowerCase();
        this.price = planPrice;
        return  "Plan Upgraded to ".concat(plan).concat("Successfully") ;

    }

//    getter methods
    public int getAttendanceLimit() {
        return attendanceLimit;
    }

    public boolean isEligibleForUpgrade() {
        return isEligibleForUpgrade;
    }

    public String getRemovalReason() {
        return removalReason;
    }

    public String getPlan() {
        return plan;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public double getPrice() {
        return price;
    }
}