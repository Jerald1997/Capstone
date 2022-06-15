/** THE SERVICE CLASS
 * Handles the main function/method for each choice and main display
 * Prepares data before proceeding to the Repository or Database Connection class (parent class of this class)
 */
public class PASService extends PASRepository{
       
    CustomerAccount account;
    Policy policy;
    PolicyHolder policyHolder;
    Vehicle vehicle;
    Vehicle[] vehicleArray;
    Claim claim;
    RatingEngine ratingEngine;
    PASUIException except = new PASUIException();
    
    public void createAccount(){                        // choice number 1 - method responsible for creating account object and filling account table
        account = new CustomerAccount();
        account.createAccount();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        
        // test for existence of firstName and lastName input
        boolean exist = selectDisplayTable("account", " where (firstName, lastName) = ('" + firstName + "','" + lastName + "');");
        if((exist) == false){    
            saveeToDb(account);
            selectDisplayTable("account", " where (firstName, lastName) = ('" + firstName + "','" + lastName + "');"); //display information after creation
        }
        else{
            System.out.println("\n Account Already Exist with DETAILS DISPLAY ABOVE!");  
        }   
    }

    public void getPolicy(){                            // choice number 2 - method responsible for object creation and table data filling of policy, policyholder and vehicle 
        account = new CustomerAccount();
        String accountNumber = account.askAccNum();     //ask the account number first
        if(except.isNumeric(accountNumber, 1, 9999)){        // if input is a 4 - digit number
            boolean exist = selectDisplayTable("account", " where accountID = " + accountNumber + ";");
            if(exist){                             // if account number is existing on DB
                System.out.println("\nHi " + super.repoAccount.getFirstName() +"!" );

                policy = new Policy();                                          //begin policy object creation
                policy.createPolicy(repoAccount); 

                policyHolder = new PolicyHolder();                              //begin policy holder object creation
                policyHolder.createPolicyHolder( repoAccount, policy);
                
                vehicle = new Vehicle();                                        //begin vehicle object creation
                int vehicleNum = vehicle.askForVehicleNum();
                policy.setVehicleNum(vehicleNum);
                vehicleArray = new Vehicle[vehicleNum];                         //use ARRAY of objects incase of multiple vehicle on the policy

                for(int counter = 0; counter < vehicleNum; counter++ ){
                    vehicleArray[counter] = new Vehicle();
                    vehicleArray[counter].createVehicle(repoAccount, policy, counter, ratingEngine);
                }
                policy.setPolicyPremium(RatingEngine.getPolicyPremium());
                vehicle.dispPremiumBreakDown(vehicleNum, vehicleArray);

                boolean acceptConfirm = policy.askForPolicyAccept();        //ask for confimation before saving the objects record to database
                if (acceptConfirm){                                         // If policy was accepted save the record on database

                    saveeToDb(policy);                                      // use inherit (saveToDb()) method to get the object data
                    exist = selectDisplayTable("policy", " where accountID = " + accountNumber + ";");
                    if(exist){
                        System.out.println("***Status: Policy record saved successfully!");
                    }
                    else{
                        System.out.println("!!!Status: Record for POLICY was not save on DATABASE!");
                    }

                    policyHolder.setPolicyId(repoPolicy.getPolicyId());
                    saveeToDb(policyHolder);                                // use inherit (saveToD()) method to get the object data
                    exist = selectDisplayTable("policyholder", " where accountID = " + accountNumber + ";");
                    if(exist){
                        System.out.println("***Status: Policy Holder's record saved successfully!");
                    }
                    else{
                        System.out.println("!!!Status: Record for POLICYHOLDER was not save on DATABASE!");
                    }

                    for(int counter = 0; counter < vehicleNum; counter++ ){         // multiple saving if there is multiple vehicle
                        vehicleArray[counter].setPolicyId(repoPolicy.getPolicyId());
                        saveeToDb(vehicleArray[counter]);                   // use inherit (saveToD()) method to get the object data
                    }
                    
                    System.out.println("\n***Status: You successfully bought a policy!\n");
                }

                else{
                    System.out.println("\n====== YOU DECLINE THE POLICY PREMIUM OFFER :( ======\n");
                }

            }
            else{                                                                  // else if account number is NOT existing on DB
            System.out.println("\n Invalid: Account Number not Exist! \n");
            }
        }
        else{                                                                       //if its not a 4 digit number
            System.out.println("\n Try again.. \n");
        }
        
        RatingEngine.setPolicyPremium(0);
    }

    public void cancelPolicy(){                 // choice number 3 - method responsible for changing expiration date of existing policy                         
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();                     // ask for Policy Number
        boolean exist = selectDisplayTable("policy", " where policyID = '" + policyId + "';");    // test existence of policy number
        if (exist){
            String updatedDate = repoPolicy.askForUpdatedDate();
            updateTable("policy", "expirationDate = '"+ updatedDate + "'", "policyID = " + policyId + ";");    //update table command
            System.out.println("Changes on Expiration of Policy to " + repoPolicy.getExpireDate() + " SUCCESSFUL!");
            System.out.println("\nUPDATED DATA: ");
            selectDisplayTable("policy", " where policyID = '" + policyId + "';");  //display updated data
        }
        else{   
            System.out.println("\n Invalid: Policy Not Exist!\n");
        }
    }

    public void fileClaim(){        // choice number 4 - method responsible for changing expiration date of existing policy 
        claim = new Claim();
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();
        boolean exist = selectDisplayTable("policy", " where policyID = '" + policyId + "';");
        if(exist){
            System.out.println("\nPOLICY + " + String.format("%06d", policyId));
            selectDisplayTable("vehicles"," where policyID = '" + policyId + "';");
            claim.createClaim(repoPolicy);
            saveeToDb(claim);
        }
        else{
            System.out.println("\n Invalid: Policy Not Exist!\n");
        }
    }

    public void searchCustumerAcc(){           // choice number 5 - method responsible for searching/displaying custumer account by firstName and lastName     
        account = new CustomerAccount();        
        String fnLn = account.askForFnLn();         // ask user for first name and last name
        String firstName = fnLn.substring(0, fnLn.indexOf(","));    // get first name and last name
        String lastName = fnLn.substring(fnLn.indexOf(",") + 1, fnLn.length());
       
        //test existence of data and also display account table if exist
        boolean exist = selectDisplayTable("account", " where (firstName, lastName) = ('" + firstName + "','" + lastName + "');");
        if (exist){
            System.out.println("\nACCOUNT " + String.format("%04d", repoAccount.getAccountId()));
            selectDisplayTable("policy", " where accountID = '" + repoAccount.getAccountId() + "';"); //display policy table1
            selectDisplayTable("policyholder", " where accountID = '" + repoAccount.getAccountId() + "';"); //display policyHolder table
            System.out.println();
        }
        else{
            System.out.println("\nInvalid: Account not Exist!\n"); 
        }

    }

    public void searchDispPolicy(){          // choice number 6 - method responsible for displaying policy table details
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();        // ask to input policy number and test if exist
        boolean exist = selectDisplayTable("policy", " where policyID = '" + policyId + "';");  
        if (exist){
            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("policyholder", " where policyID = '" + policyId + "';"); //display policy holder table

            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("vehicles", " where policyID = '" + policyId + "';");   // display vehicle table
        }
        else{   
            System.out.println("\n Invalid: Policy Not Exist!\n");
        }
    }

    public void searchDispClaim(){      // choice number 6 - method responsible for displaying claim table details
        policy = new Policy();
        claim = new Claim();
        String claimNumber = claim.askForClaimNumber();
        int claimId = claim.toClaimIdConfig(claimNumber);
        boolean exist = selectDisplayTable("claim", " where claimID = '" + claimId + "';");
        if (exist){
            System.out.println("\n = CLAIM DETAILS END = \n");
        }
        else{   
            System.out.println("\n Invalid: Claim Number Not Exist!\n");
        }
    }

    public void display(){                                                      // method responsible for displaying choices and prompt user for choice
        System.out.println("===============================================");
        System.out.println("1. Create a new Customer Account");
        System.out.println("2. Get a policy quote and buy the policy");
        System.out.println("3. Cancel a specific policy ");
        System.out.println("4. File an accident claim against a policy");
        System.out.println("5. Search for a Customer account ");
        System.out.println("6. Search for and display a specific policy");
        System.out.println("7. Search for and display a specific claim");
        System.out.println("8. Exit the PAS System");
        System.out.println("===============================================");
        System.out.print("    What to do? \n");
        System.out.print("    Enter a number (1-8): ");
    }
}
