import java.util.Scanner;

public class CustomerAccount{
    private String firstName;
    private String lastName;
    private String address;
    private int accountId;
    private String accountNumber;

    public CustomerAccount(){

    }

    public CustomerAccount(int accountID, String firstName, String lastName, String address){
        this.accountId = accountID;
        this. firstName = firstName;
        this. lastName = lastName;
        this.address = address;
    }

    Scanner sc = new Scanner(System.in);

    public void createAccount(){
        System.out.println("==Please Provide details for ACCOUNT CREATION==");
        System.out.print("Enter your First Name: ");
        this.firstName = sc.nextLine();
        System.out.print("Enter your Last Name: ");
        this.lastName = sc.nextLine();
        System.out.print("Enter your Address: ");
        this.address = sc.nextLine();
    }

    public String askForFnLn(){
        System.out.print("Enter your First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter your Last Name: ");
        String lastName = sc.nextLine();
        String fnLn = firstName + "," + lastName;
        return fnLn;
    }
    

    public int getAccountId(){
        return this.accountId;
    }

    public String getFirstName()
     {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String toAccountNumberConfig(int accountId) {
        accountNumber = String.format("%04d", accountId); 
        return accountNumber;
    }

    public int toAccountIdConfig(String accountNumber) {
        accountId = Integer.parseInt(accountNumber);
        return accountId;
    }

    public String askAccNum(){
        System.out.print("Enter your (4-DIGIT) ACCOUNT NUMBER: ");
        String inputAccountNumber = sc.next();
        return inputAccountNumber;

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



}
