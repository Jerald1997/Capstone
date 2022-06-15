import java.util.Scanner;
import java.time.LocalDate;

public class Policy extends Vehicle{
    private int policyId, accountId, dlx, vehicleNum;  
    private String effectMonth, effectDay, effectYear, effectDateStr;
    private LocalDate effectDate, expireDate;
    private double policyPremium;   
    PASUIException except = new PASUIException();   

    // a constructor that accepts data according to columns of policy table of database
    public Policy (int policyId, int accountId, String effectDateStr, String expiretDateStr, double policyPremium, int vehicleNum){
        this.policyId = policyId;
        this.accountId = accountId;
        this.effectDate = LocalDate.parse(effectDateStr);
        this.expireDate = LocalDate.parse(expiretDateStr);
        this.policyPremium = policyPremium;
        this.vehicleNum = vehicleNum;

    }

    public Policy(){

    }

    Scanner sc = new Scanner(System.in);

    public void createPolicy(CustomerAccount repoAccount){   // method filling policy object by user input and data from CustomerAccount object
        this.accountId = repoAccount.getAccountId();
        boolean inputLoop = true;
        while(inputLoop){                                      // effective date input (must be correct format)
            System.out.println("==Please Provide details for Policy Record CREATION==");
            System.out.print("Enter number for the YEAR OF EFFECTIVITY (YYYY) : ");
            this.effectYear = sc.next();
            System.out.print("Enter number representing the MONTH OF EFFECTIVITY (1-12): ");
            this.effectMonth = sc.next();
            System.out.print("Enter number for the DAY OF EFFECTIVITY (1-31): ");
            this.effectDay = sc.next();

            if(except.isNumeric(effectMonth, 1, 12) && except.isNumeric(effectDay, 1, 31)){
                this.effectMonth = String.format("%02d", Integer.parseInt(effectMonth));
                this.effectDay = String.format("%02d", Integer.parseInt(effectDay));

                this.effectDateStr = this.effectYear + "-" + this.effectMonth +"-" + this.effectDay;
                if (except.dateValid(effectDateStr, "now", "9999-01-01")){
                    this.effectDate = LocalDate.parse(this.effectDateStr);
                    this.expireDate = effectDate.plusMonths(6);                
                    inputLoop = false; 
                }
            }
            else{
                inputLoop = true;
            }

        } 
        
    }

    public int askForPolicyNumber(){            // ask user for policy number and return
        int policyNumber = 0;
        boolean inputLoop = true;
        while(inputLoop){
            System.out.print("Enter (6-Digit) POLICY NUMBER: ");
            String policyNumberStr = sc.nextLine();
            if(except.isNumeric(policyNumberStr, 1, 999999)){
                inputLoop = false;
                policyNumber = Integer.parseInt(policyNumberStr);
                return policyNumber;
            }
            else{
                inputLoop = true;
            }
        }
        return policyNumber;
    }

    public String askForUpdatedDate(){          // ask user for update expiration date and return
        String updatedExpDate = null;
        boolean inputLoop = true;
        while(inputLoop) {
            System.out.print("Change expiration YEAR from " + this.expireDate.getYear() + " to : ");
            String updateYearStr = sc.nextLine();
            System.out.print("Change expiration Month from [" + (this.expireDate.getMonthValue()) + "] " + this.expireDate.getMonth() + " to [1-12]: ");
            String updateMonthStr = sc.nextLine();
            System.out.print("Change expiration Day from " + this.expireDate.getDayOfMonth() + " to [1-31]: ");
            String updateDayStr = sc.nextLine();

            if(except.isNumeric(updateMonthStr, 1, 12) && except.isNumeric(updateDayStr, 1, 31)){
                updatedExpDate = updateYearStr + "-" + String.format( "%02d", Integer.parseInt(updateMonthStr)) +"-" + String.format( "%02d",Integer.parseInt(updateDayStr));
                String expString = this.getExpireDate().toString();
                if(except.dateValid(updatedExpDate, "now", expString)){
                    inputLoop = false;
                }
                else{
                    inputLoop = true;
                }
            }

        }
        return updatedExpDate;
    }

    public boolean askForPolicyAccept(){    // ask user for acceptance confirmation and return TRUE if user ACCEPT
        boolean accept = false;
        boolean inputLoop = true;
        sc = new Scanner(System.in);

        while(inputLoop){
            System.out.println("DO YOU ACCEPT AND ACKNOWLEDGED THE PREMIUM INDICATED FOR THE POLICY?");
            System.out.println("Enter [0] - for NO\n"
                              +"      [1] - for YES");
            System.out.print("Enter your choice [0/1] : ");
            String choice = sc.nextLine();
            if(except.isNumeric(choice, 0, 1)){

                if(choice.equals("0")){
                    accept =  false;
                    inputLoop = false;
                }
                else if(choice.equals("1")){
                    accept = true;
                    inputLoop = false;
                }
                else {
                    System.out.println("\nPlease choose 0 or 1 ONLY..\n");
                    inputLoop = true;
                }    
            }
            else{
                inputLoop = true;
            }
        }
        return accept;
        
    }

    public String toPolicyNumConfig(int policyId){
        String policyNumber = String.format("%06d", accountId); 
        return policyNumber;
    }

    public int getPolicyId() {
        return policyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = LocalDate.parse(expireDate);
    }

    public LocalDate getEffectDate() {
        return effectDate;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public void setDlx(int dlx) {
        this.dlx = dlx;
    }

    public int getDlx() {
        return dlx;
    }

    public double getPolicyPremium() {
        return policyPremium;
    }

    public void setPolicyPremium(double policyPremium) {
        this.policyPremium = policyPremium;
    }

    public int getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(int vehicleNum) {
        this.vehicleNum = vehicleNum;
    }
    
}