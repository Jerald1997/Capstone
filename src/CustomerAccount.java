import java.util.Scanner;

public class CustomerAccount{
    private String firstName;
    private String lastName;
    private String address;
    private int accountId;
    private String accountNumber;

    public CustomerAccount(){

    }

    // a constructor that accepts data according to columns of account table of database
    public CustomerAccount(int accountID, String firstName, String lastName, String address){  
        this.accountId = accountID;
        this. firstName = firstName;
        this. lastName = lastName;
        this.address = address;
    }

    Scanner sc = new Scanner(System.in);

    public void createAccount(){                                                    // filling of CustomerAccount object data
        System.out.println("==Please Provide details for ACCOUNT CREATION==");
        System.out.print("Enter your First Name: ");
        this.firstName = sc.nextLine();
        System.out.print("Enter your Last Name: ");
        this.lastName = sc.nextLine();
        System.out.print("Enter your Address: ");
        this.address = sc.nextLine();
    }

    public String askForFnLn(){                                     //asking user and returning firstName and fastName 
        System.out.print("Enter your First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter your Last Name: ");
        String lastName = sc.nextLine();
        String fnLn = firstName + "," + lastName;
        return fnLn;
    }

        public String toAccountNumberConfig(int accountId) {        // Converting from ID (int) to NUMBER (String) [ex.: accountId = 1 to accountNumber = 0001]
        accountNumber = String.format("%04d", accountId); 
        return accountNumber;
    }

    public int toAccountIdConfig(String accountNumber) {    // Converting from NUMBER (String) to ID (int) [ex.: accountNumber = 0001 to accountId = 1]
        accountId = Integer.parseInt(accountNumber);
        return accountId;
    }

    public String askAccNum(){                                          //asking user and returning String of number
        System.out.print("Enter your (4-DIGIT) ACCOUNT NUMBER: ");
        String inputAccountNumber = sc.next();
        return inputAccountNumber;
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

}
