import java.util.Scanner;
import java.time.LocalDate;
import java.util.Calendar;


public class Policy extends Vehicle{
    private int policyId, accountId, dlx, vehicleNum;  
    private String effectMonth, effectDay, effectYear, effectDateStr;
    // private String expireMonth, expireDay, expireYear, expireDateStr;
    private LocalDate effectDate, expireDate;

    private double policyPremium;      

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

    public void createPolicy(CustomerAccount repoAccount){
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

            if (dateValid(effectYear, effectMonth, effectDay)){
                this.effectYear = effectYear;
                this.effectMonth = String.format("%02d", Integer.parseInt(effectMonth));
                this.effectDay = String.format("%02d", Integer.parseInt(effectDay));
                this.effectDateStr = this.effectYear + "-" + this.effectMonth +"-" + this.effectDay;
                this.effectDate = LocalDate.parse(this.effectDateStr);
                this.expireDate = effectDate.plusMonths(6);

                System.out.println(effectDate);
                System.out.println(expireDate);
                
                inputLoop = false; 
            }

        } 
        
    }

    public int askForPolicyNumber(){
        int policyNumber = 0;
        boolean inputLoop = true;
        while(inputLoop){
            System.out.print("Enter (6-Digit) POLICY NUMBER: ");
            String policyNumberStr = sc.nextLine();
            if(isNumeric(policyNumberStr) && policyNumberStr.length() == 6){
                inputLoop = false;
                policyNumber = Integer.parseInt(policyNumberStr);
                return policyNumber;
            }
            else{
                System.out.println("\nInvalid: Please input 6-DIGIT NUMBER ONLY.\n");
                inputLoop = true;
            }
        }
        return policyNumber;
    }

    public String askForUpdatedDate(){
        String updatedExpDate = null;
        boolean inputLoop = true;
        while(inputLoop) {
            System.out.print("Change expiration YEAR from " + this.expireDate.getYear() + " to : ");
            String updateYearStr = sc.nextLine();
            System.out.print("Change expiration Month from [" + (this.expireDate.getMonthValue()) + "] " + this.expireDate.getMonth() + " to [1-12]: ");
            String updateMonthStr = sc.nextLine();
            System.out.print("Change expiration Day from " + this.expireDate.getDayOfMonth() + " to [1-31]: ");
            String updateDayStr = sc.nextLine();

            if(dateValid(updateYearStr, updateMonthStr, updateDayStr)){
                inputLoop = false;
                updatedExpDate = updateYearStr + "-" + String.format( "%02d", Integer.parseInt(updateMonthStr)) +"-" + String.format( "%02d",Integer.parseInt(updateDayStr));
            }
            else{
                System.out.println("\nInvalid: Please input valid Date!\n");
                inputLoop = true;
            }

        }
        return updatedExpDate;
    }

    public boolean askForPolicyAccept(){
        boolean accept = false;
        boolean inputLoop = true;
        sc = new Scanner(System.in);

        while(inputLoop){
            System.out.println("DO YOU ACCEPT AND ACKNOWLEDGED THE PREMIUM INDICATED FOR THE POLICY?");
            System.out.println("Enter [0] - for NO\n"
                              +"      [1] - for YES");
            System.out.print("Enter your choice [0/1] : ");
            String choice = sc.nextLine();
            if(isNumeric(choice)){

                if(choice.equals("0")){
                    accept =  false;
                    inputLoop = false;
                }
                else if(choice.equals("1")){
                    accept = true;
                    inputLoop = false;
                }
                else {
                    System.out.println("\nInvalid: Out of Choices. Please choose 0 or 1 ONLY\n");
                    inputLoop = true;
                }    
            }
            else{
                System.out.println("\nInvalid: Please input number only!\n");
                inputLoop = true;
            }
        }
        return accept;
        
    }

    public boolean isNumeric(String inputString) {
        //check for null and empty string
        if (inputString == null || inputString.length() == 0) {
          return false;
        }
        try {
          Integer.parseInt(inputString);
          return true;
        } catch (NumberFormatException exception) {
          return false;
        }
    }

    public boolean dateValid(String inputYearStr, String inputMonthStr, String inputDayStr){
        boolean validInput = false;
        if(isNumeric(inputMonthStr) && isNumeric(inputYearStr) && isNumeric(inputDayStr)){
                validInput =  true;
        }
        else{
            System.out.println("\n Invalid Date: Please input a valid number format DATE!\n" );
            validInput = false;
        }
        return validInput;
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