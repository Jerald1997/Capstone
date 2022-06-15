import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;


public class PolicyHolder {

    private int policyHolderId, policyId, accountId;
    private LocalDate dob, dateDxIssued;
    private String address, drivLicNum, firstName, lastName;
    PASUIException except = new PASUIException();    

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
        String inputChoice = null;
        String determiner = null;
        boolean inputLoop = true;

        while(inputLoop){
            System.out.println("\n==Please Provide details for Policy Holder Record creation==");
            System.out.println("Is a policy for you or other person? ");
            System.out.println("    [1] POLICY FOR THE ACCOUNT OWNER");
            System.out.println("    [2] POLICY FOR OTHER PERSON");
            System.out.print("  Input your choice [1 or 2] : ");
            inputChoice= sc.nextLine();
            if(except.isNumeric(inputChoice, 1, 2)){
                inputLoop = false;
            }
            else{
                inputLoop = true;
            }
        }

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

        inputLoop = true;
        while(inputLoop){
            System.out.print("Enter" + determiner +" BIRTH YEAR (YYYY) : ");
            String birthYear = sc.next();
            System.out.print("Enter" + determiner + " BIRTH MONTH (1-12): ");
            String birthMonth = sc.next();
            System.out.print("Enter" + determiner + " BIRTH DAY (1-31): ");
            String birthDay = sc.next();

            if(except.isNumeric(birthMonth, 1, 12) && except.isNumeric(birthDay, 1, 31)){
                birthDay = String.format("%02d", Integer.parseInt(birthDay));
                birthMonth = String.format("%02d", Integer.parseInt(birthMonth));
                String dobStr = birthYear + "-" + birthMonth + "-" + birthDay;
                if (except.dateValid(dobStr, "1000-01-01", "now")){
                    this.dob = LocalDate.parse(dobStr);
                    inputLoop = false;
                }
                else{
                    inputLoop = true;
                }
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

            if(except.isNumeric(monthDxIssued, 1, 12) && except.isNumeric(dayDxIssued, 1, 31)){
                monthDxIssued = String.format("%02d", Integer.parseInt(monthDxIssued));
                dayDxIssued = String.format("%02d", Integer.parseInt(dayDxIssued));
                String dateIssued = yearDxIssued + "-" + monthDxIssued + "-" + dayDxIssued;
                if (except.dateValid(dateIssued, "1000-01-01", "now")){
                    this.dateDxIssued = LocalDate.parse(dateIssued);
                    inputLoop = false;
                }
                else{
                    inputLoop = true;
                }
            }
            else{
                inputLoop = true;
            }
        }

        policy.setDlx(getDlx());
        
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
