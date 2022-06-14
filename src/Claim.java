import java.time.LocalDate;
import java.util.Scanner;

public class Claim {
    private int claimId;
    private String claimAlphaNum, addAccHappen, descOfAcc, descOfDamage;
    private int policyId, accountId;
    private double estimatedCostOfRep;
    private LocalDate dateOfAcc; 
    
    public Claim(){
    }

    public Claim(int claimId, int policyId, int accountId, String dateOfAccStr, String addAccHappen, String descOfAcc, String descOfDamage,
             double estimatedCostOfRep) {
        this.claimId = claimId;
        this.policyId = policyId;
        this.accountId = accountId;
        this.dateOfAcc = LocalDate.parse(dateOfAccStr);
        this.addAccHappen = addAccHappen;
        this.descOfAcc = descOfAcc;
        this.descOfDamage = descOfDamage;
        this.estimatedCostOfRep = estimatedCostOfRep;        
    }

    Scanner sc = new Scanner(System.in);

    public void createClaim(Policy repoPolicy){
        this.policyId = repoPolicy.getPolicyId();
        this.accountId = repoPolicy.getAccountId();
        
        boolean inputLoop = true;
        while(inputLoop){
            System.out.print("Enter YEAR Accident happened [YYYY] : ");
            String inputYearStr = sc.nextLine();
            System.out.print("Enter MONTH Accident happened [MM] : ");
            String inputMonthStr = sc.nextLine();
            System.out.print("Enter DAY Accident happened [DD] : ");
            String inputDayStr = sc.nextLine();
            if(dateValid(inputYearStr, inputMonthStr, inputDayStr)){
                String dateOfAccStr = inputYearStr + "-" + String.format("%02d", Integer.parseInt(inputMonthStr)) + "-" + String.format("%02d", Integer.parseInt(inputDayStr));
                this.dateOfAcc = LocalDate.parse(dateOfAccStr);
                inputLoop = false;
            }
            else{
                System.out.println("\nInvalid: Please input valid Date base on format!\n");
                inputLoop = true;
            }
            System.out.print("Enter ADDRESS WHERE ACCIDENT HAPPENED : ");
            this.addAccHappen = sc.nextLine();
            System.out.print("Enter DESCRIPTION OF ACCIDENT : ");
            this.descOfAcc = sc.nextLine();
            System.out.print("Enter DESCRIPTION OF DAMAGE : ");
            this.descOfDamage = sc.nextLine();
            
            inputLoop = true;
            while(inputLoop){
                System.out.print("Enter Estimated COST OF REPAIR ($)  : ");
                String estimatedCostStr = sc.nextLine();
                if(isNumeric(estimatedCostStr)){
                    this.estimatedCostOfRep = Double.parseDouble(estimatedCostStr);
                    inputLoop = false;
                }
                else{
                    System.out.println("\nInvalid: Please Enter a valid Number!\n");
                    inputLoop = true;
                }
            }
        }
    }

    public String toClaimNumberConfig(int claimId){
        String claimNumber = String.format("C%05d",claimId);
        return claimNumber;
    }

    public int toClaimIdConfig(String claimNumber){
        String claimIdStr = claimNumber.substring(1, claimNumber.length());
        int claimId = Integer.parseInt(claimIdStr);
        return claimId;
    }

    public String askForClaimNumber(){
        boolean inputLoop = true;
        String claimNumber = null;
        while(inputLoop){
            System.out.print("Enter (Cxxxxxx) CLAIM NUMBER [ex.: C12345] : ");
            claimNumber = sc.nextLine();
            String claimIdStr = claimNumber.substring(1, claimNumber.length());
            if((claimNumber.charAt(0) == 'c' || claimNumber.charAt(0) == 'C') && claimNumber.length() == 6 && isNumeric(claimIdStr)){;
                inputLoop = false;
            }
            else{
                System.out.println("\nPlease enter a valid Claim Number (Cxxxxx) [ex.: C12345]\n");
                inputLoop = true;
            }
        }
        return claimNumber;
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
          
            if(inputMonthStr.length() > 2 && inputDayStr.length() > 2 && inputYearStr.length() != 4){
                System.out.println("\n Invalid Date: Please follow the input format for date!\n" );
                validInput = false;
                return validInput;
            }
            else{
                validInput = true;
                return validInput;
            }
        }
        else{
            System.out.println("\n Invalid Date: Please input a valid number format DATE!\n" );
            validInput = false;
            return false;
        }
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getClaimAlphaNum() {
        return claimAlphaNum;
    }

    public void setClaimAlphaNum(String claimAlphaNum) {
        this.claimAlphaNum = claimAlphaNum;
    }

    public String getAddAccHappen() {
        return addAccHappen;
    }

    public void setAddAccHappen(String addAccHappen) {
        this.addAccHappen = addAccHappen;
    }

    public String getDescOfAcc() {
        return descOfAcc;
    }

    public void setDescOfAcc(String descOfAcc) {
        this.descOfAcc = descOfAcc;
    }

    public String getDescOfDamage() {
        return descOfDamage;
    }

    public void setDescOfDamage(String descOfDamage) {
        this.descOfDamage = descOfDamage;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getEstimatedCostOfRep() {
        return estimatedCostOfRep;
    }

    public void setEstimatedCostOfRep(double estimatedCostOfRep) {
        this.estimatedCostOfRep = estimatedCostOfRep;
    }

    public LocalDate getDateOfAcc() {
        return dateOfAcc;
    }

    public void setDateOfAcc(LocalDate dateOfAcc) {
        this.dateOfAcc = dateOfAcc;
    }
    
}

