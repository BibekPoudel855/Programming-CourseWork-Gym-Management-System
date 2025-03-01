public class RegularMember extends GymMember{
    private final int attendanceLimit = 30;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

//    Constructor
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
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

    }
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