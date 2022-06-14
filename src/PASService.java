public class PASService extends PASRepository{
       
    CustomerAccount account;
    Policy policy;
    PolicyHolder policyHolder;
    Vehicle vehicle;
    Vehicle[] vehicleArray;
    Claim claim;
    RatingEngine ratingEngine;
    
    public void createAccount(){
        account = new CustomerAccount();
        account.createAccount();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        
        if(checkSelectAccExistByFnLn(firstName, lastName) == false){
            saveeToDb(account);
        }
        else{
            String accountNumber = account.toAccountNumberConfig(super.repoAccount.getAccountId());
            System.out.println("\n Account Already Exist with Account Number: "+ accountNumber + "\n");  
        }

        account.getFirstName();
        repoAccount.getFirstName();
        
    }

    public void getPolicy(){

        account = new CustomerAccount();
        String accountNumber = account.askAccNum();
        if(account.isNumeric(accountNumber) && accountNumber.length() == 4){        // if input is a 4 - digit number
            
            if(checkSelectExistByAccId("account", "accountID", accountNumber)){                             // if account number is existing on DB
                System.out.println("\nHi " + super.repoAccount.getFirstName() +"!" );
                
                policy = new Policy();                                          //begin policy creation
                policy.createPolicy(repoAccount); 

                policyHolder = new PolicyHolder();                              //begin policy holder creation
                policyHolder.createPolicyHolder( repoAccount, policy);
                
                vehicle = new Vehicle();
                int vehicleNum = vehicle.askForVehicleNum();
                policy.setVehicleNum(vehicleNum);
                vehicleArray = new Vehicle[vehicleNum];
                for(int counter = 0; counter < vehicleNum; counter++ ){
                    vehicleArray[counter] = new Vehicle();
                    vehicleArray[counter].createVehicle(repoAccount, policy, counter, ratingEngine);
                }
                policy.setPolicyPremium(RatingEngine.getPolicyPremium());
                vehicle.dispPremiumBreakDown(vehicleNum, vehicleArray);

                boolean acceptConfirm = policy.askForPolicyAccept();
                if (acceptConfirm){                                         /// If policy was accepted save the record on database

                    saveeToDb(policy);
                    if(checkSelectExistByAccId("policy", "accountID", accountNumber)){
                        System.out.println("***Status: Policy record saved successfully!");
                    }
                    else{
                        System.out.println("!!!Status: Record for POLICY was not save on DATABASE!");
                    }

                    policyHolder.setPolicyId(repoPolicy.getPolicyId());
                    saveeToDb(policyHolder);
                    if(checkSelectExistByAccId("policyholder", "accountID", accountNumber)){
                        System.out.println("***Status: Policy Holder's record saved successfully!");
                    }
                    else{
                        System.out.println("!!!Status: Record for POLICYHOLDER was not save on DATABASE!");
                    }

                    for(int counter = 0; counter < vehicleNum; counter++ ){
                        vehicleArray[counter].setPolicyId(repoPolicy.getPolicyId());
                        saveeToDb(vehicleArray[counter]);
                    }
                    
               
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
            System.out.println("\n Invalid: Please input 4-DIGIT NUMBER ONLY! \n");
        }
        
        RatingEngine.setPolicyPremium(0);
    }

    public void cancelPolicy(){
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();
        boolean exist = checkSelectPolicyExistById(policyId);
        if (exist){
            String updatedDate = repoPolicy.askForUpdatedDate();
            updateTable("policy", "expirationDate = '"+ updatedDate + "'", "policyID = " + policyId + ";");
            System.out.println("Changes on Expiration of Policy to " + repoPolicy.getExpireDate() + " SUCCESSFUL!");
            System.out.println("\nUpdated Data: ");
            checkSelectPolicyExistById(policyId);
        }
        else{   
            System.out.println("\n Invalid: Policy Not Exist!\n");
        }
    }

    public void searchCustumerAcc(){         
        account = new CustomerAccount();
        String fnLn = account.askForFnLn();
        String firstName = fnLn.substring(0, fnLn.indexOf(","));
        String lastName = fnLn.substring(fnLn.indexOf(",") + 1, fnLn.length());
        if (checkSelectAccExistByFnLn(firstName, lastName)){
            System.out.println("\nACCOUNT'S DETAILS:");
            selectDisplayTable("account", " where accountID = '" + repoAccount.getAccountId() + "';");
            System.out.println("\nACCOUNT'S POLICY DETAILS:");
            selectDisplayTable("policy", " where accountID = '" + repoAccount.getAccountId() + "';");
            System.out.println("\nACCOUNT'S POLICY HOLDER DETAILS:");
            selectDisplayTable("policyholder", " where accountID = '" + repoAccount.getAccountId() + "';");
            System.out.println();
        }
        else{
            System.out.println("\nInvalid: Account not Exist!\n");
        }

    }

    public void searchDispPolicy(){               ////////////////////////////////////////////////////////////////
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();
        boolean exist = checkSelectPolicyExistById(policyId);
        if (exist){
            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("policy", " where policyID = '" + policyId + "';");

            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("policyholder", " where policyID = '" + policyId + "';");

            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("vehicles", " where policyID = '" + policyId + "';");
        }
        else{   
            System.out.println("\n Invalid: Policy Not Exist!\n");
        }
    }

    public void fileClaim(){
        claim = new Claim();
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();
        boolean exist = checkSelectPolicyExistById(policyId);
        if(exist){
            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("policy", " where policyID = '" + policyId + "';");
            System.out.println("\nPOLICY + " + policyId);
            System.out.println("\nPOLICY + " + String.format("06d", policyId));
            selectDisplayTable("vehicles"," where policyID = '" + policyId + "';");
            claim.createClaim(repoPolicy);
            saveeToDb(claim);
        }
        else{
            System.out.println("\n Invalid: Policy Not Exist!\n");
        }
    }

    public void searchDispClaim(){
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
 
    public boolean checkSelectExistByAccId(String tableToSelect, String columnToSelect, String accountNumber){
        String sqlCommand = "Select * from ";
        String table = tableToSelect;
        String column = columnToSelect;
        String dataToFind = accountNumber;

        boolean exist = testExistOnDb(sqlCommand, table, column, dataToFind);
        return exist;
        
    }

    public boolean checkSelectAccExistByFnLn(String firstName, String lastName){    //Firstname and Lastname
        String sqlCommand = "Select * from ";
        String table = "account";
        String column = "firstName,lastName";
        String dataToFind = firstName + "','" + lastName ;

        boolean exist = testExistOnDb(sqlCommand, table, column, dataToFind);
        return exist;
    }

    public boolean checkSelectPolicyExistById(int policyId){  
        String table = "policy";
        String commExtension = " where policyID = " + policyId + ";";

        boolean exist = selectDisplayTable(table, commExtension);
        return exist;
    }

    public void display(){
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
