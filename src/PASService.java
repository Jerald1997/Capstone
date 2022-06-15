public class PASService extends PASRepository{
       
    CustomerAccount account;
    Policy policy;
    PolicyHolder policyHolder;
    Vehicle vehicle;
    Vehicle[] vehicleArray;
    Claim claim;
    RatingEngine ratingEngine;
    PASUIException except = new PASUIException();
    
    public void createAccount(){
        account = new CustomerAccount();
        account.createAccount();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        
        boolean exist = selectDisplayTable("account", " where (firstName, lastName) = ('" + firstName + "','" + lastName + "');");
        if((exist) == false){
            saveeToDb(account);
            selectDisplayTable("account", " where (firstName, lastName) = ('" + firstName + "','" + lastName + "');");
        }
        else{
            System.out.println("\n Account Already Exist with DETAILS DISPLAY ABOVE!");  
        }   
    }

    public void getPolicy(){
        account = new CustomerAccount();
        String accountNumber = account.askAccNum();
        if(except.isNumeric(accountNumber, 1, 9999)){        // if input is a 4 - digit number
            
            boolean exist = selectDisplayTable("account", " where accountID = " + accountNumber + ";");
            if(exist){                             // if account number is existing on DB
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
                    exist = selectDisplayTable("policy", " where accountID = " + accountNumber + ";");
                    if(exist){
                        System.out.println("***Status: Policy record saved successfully!");
                    }
                    else{
                        System.out.println("!!!Status: Record for POLICY was not save on DATABASE!");
                    }

                    policyHolder.setPolicyId(repoPolicy.getPolicyId());
                    saveeToDb(policyHolder);
                    exist = selectDisplayTable("policyholder", " where accountID = " + accountNumber + ";");
                    if(exist){
                        System.out.println("***Status: Policy Holder's record saved successfully!");
                    }
                    else{
                        System.out.println("!!!Status: Record for POLICYHOLDER was not save on DATABASE!");
                    }

                    for(int counter = 0; counter < vehicleNum; counter++ ){
                        vehicleArray[counter].setPolicyId(repoPolicy.getPolicyId());
                        saveeToDb(vehicleArray[counter]);
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

    public void cancelPolicy(){
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();
        boolean exist = selectDisplayTable("policy", " where policyID = '" + policyId + "';");
        if (exist){
            String updatedDate = repoPolicy.askForUpdatedDate();
            updateTable("policy", "expirationDate = '"+ updatedDate + "'", "policyID = " + policyId + ";");
            System.out.println("Changes on Expiration of Policy to " + repoPolicy.getExpireDate() + " SUCCESSFUL!");
            System.out.println("\nUpdated Data: ");
            selectDisplayTable("policy", " where policyID = '" + policyId + "';");
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
        boolean exist = selectDisplayTable("account", " where (firstName, lastName) = ('" + firstName + "','" + lastName + "');");

        if (exist){
            System.out.println("\nACCOUNT " + String.format("%04d", repoAccount.getAccountId()));
            selectDisplayTable("policy", " where accountID = '" + repoAccount.getAccountId() + "';");
            System.out.println("\nACCOUNT " + String.format("%04d", repoAccount.getAccountId()));
            selectDisplayTable("policyholder", " where accountID = '" + repoAccount.getAccountId() + "';");
            System.out.println();
        }
        else{
            System.out.println("\nInvalid: Account not Exist!\n");
        }

    }

    public void searchDispPolicy(){          
        policy = new Policy();
        int policyId = policy.askForPolicyNumber();
        boolean exist = selectDisplayTable("policy", " where policyID = '" + policyId + "';");
        if (exist){
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
