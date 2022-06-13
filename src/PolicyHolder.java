import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Scanner;


public class PolicyHolder {

    private int policyHolderId, policyId, accountId;

    private LocalDate dob, dateDxIssued;

    private String address, drivLicNum, firstName, lastName;    

    Scanner sc = new Scanner(System.in);

    public PolicyHolder(){

    }

    public PolicyHolder(int policyHolderId, int policyId, int accountId, String firstName, String lastName, String address, String dob,
                         String drivLicNum, String dateDxIssued) {
            this.policyHolderId = policyHolderId;
            this.policyId = policyId;
            this.accountId = accountId;
            this.dob = LocalDate.parse(dob);
            this.dateDxIssued = LocalDate.parse(dateDxIssued);
            this.address = address;
            this.drivLicNum = drivLicNum;
            this.firstName = firstName;
            this.lastName = lastName;
    }

    public void createPolicyHolder(CustomerAccount repoAccount, Policy policy){
        this.accountId = policy.getAccountId();
        System.out.println("\n==Please Provide details for Policy Holder Record creation==");
        System.out.println("Is a policy for you or other person? ");
        System.out.println("    [1] POLICY FOR THE ACCOUNT OWNER");
        System.out.println("    [2] POLICY FOR OTHER PERSON");

        String inputChoice = null;
        String determiner = null;
        while(inputChoice == null || ( inputChoice.equals("1") && inputChoice.equals("2") )){
            System.out.print("  Input your choice [1 or 2] : ");
            inputChoice= sc.nextLine();

            switch (inputChoice) {
                case "1":
                    determiner = " YOUR (ACCOUNT OWNER) ";
                    this.firstName = repoAccount.getFirstName();
                    this.lastName = repoAccount.getLastName();
                    this.address = repoAccount.getAddress();
                    System.out.println("\n*Account owners first name, last name and address was saved on the record*\n");
                    break;

                case "2":

                    System.out.print("Enter POLICY HOLDER's FIRST NAME: ");
                    this.firstName = sc.nextLine();
                    System.out.print("Enter POLICY HOLDER's LAST NAME: ");
                    this.lastName = sc.nextLine();
                    System.out.print("Enter POLICY HOLDER's ADDRESS: ");
                    this.address = sc.nextLine();
                    determiner = " " + firstName +"'s";
                    break;    
            
                default:
                    System.out.println("\n Invalid: Out of Choices! Please input [1] or [2] only..\n");
                    break;
                
            }
        }

        boolean inputLoop = true;
        while(inputLoop){
            System.out.print("Enter" + determiner +" BIRTH YEAR (YYYY) : ");
            String birthYear = sc.next();
            System.out.print("Enter" + determiner + " BIRTH MONTH (1-12): ");
            String birthMonth = sc.next();
            System.out.print("Enter" + determiner + " BIRTH DAY (1-31): ");
            String birthDay = sc.next();

            if (dateValid(birthYear, birthMonth, birthDay)){
                birthDay = String.format("%02d", Integer.parseInt(birthDay));
                birthMonth = String.format("%02d", Integer.parseInt(birthMonth));
                String dobStr = birthYear + "-" + birthMonth + "-" + birthDay;
                this.dob = LocalDate.parse(dobStr);
                inputLoop = false;
            }
            else{
                System.out.println("\nInvalid:Please input a valid Date!\n");
            }
        }

        System.out.print("Enter your DRIVER's LICENSE NUMBER: ");
        this.drivLicNum = sc.next();

        inputLoop = true;
        while(inputLoop){
            System.out.print("Enter the YEAR when "+ determiner + "LICENSE was 1ST ISSUED (YYYY) : ");
            String yearDxIssued = sc.next();
            System.out.print("Enter the MONTH when "+ determiner + "LICENSE was 1ST ISSUED (1-12) : ");
            String monthDxIssued = sc.next();
            System.out.print("Enter the DAY when "+ determiner + "LICENSE was 1ST ISSUED (1-31) : ");
            String dayDxIssued = sc.next();
            
            if (dateValid(yearDxIssued, monthDxIssued, dayDxIssued)){
                monthDxIssued = String.format("%02d", Integer.parseInt(monthDxIssued));
                dayDxIssued = String.format("%02d", Integer.parseInt(dayDxIssued));
                String dobStr = yearDxIssued + "-" + monthDxIssued + "-" + dayDxIssued;
                this.dateDxIssued = LocalDate.parse(dobStr);
                inputLoop = false;
            }
            else{
                System.out.println("\nInvalid:Please input a valid Date!\n");
            }
        }

        policy.setDlx(getDlx());
        
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
        Calendar calendar = Calendar.getInstance();
        if(isNumeric(inputMonthStr) && isNumeric(inputYearStr) && isNumeric(inputDayStr)){
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int inputYear = Integer.parseInt(inputYearStr);
            int inputMonth = Integer.parseInt(inputMonthStr);
            int inputDay = Integer.parseInt(inputDayStr);
          
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

    public int getDlx(){
        LocalDate currentDate = LocalDate.now();
        int dlx = Period.between(this.dateDxIssued, currentDate).getYears();
        return dlx;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public int getPolicyHolderId() {
        return policyHolderId;
    }

    public LocalDate getDateDxIssued() {
        return dateDxIssued;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getDrivLicNum() {
        return drivLicNum;
    }

    public String getAddress() {
        return address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPolicyHolderId(int policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }
    
}
