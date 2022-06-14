import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PASRepository {


    String sqlCommand;
    String details;
    boolean exist;

    CustomerAccount repoAccount;
    Policy repoPolicy;
    PolicyHolder repoPolicyHolder;
    Vehicle[] repoVehicleArray;
    Claim repoClaim;
    

    public void directCommandToDb(String sqlCommand){
        try (
            // a database connection "conn"
            Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/pas_system","root", "JR@norima2022"); 
            
            Statement stmt = conn.createStatement();                          //statement object inside the connection
           ){        
   
            System.out.println("The SQL statement is: " + sqlCommand + "\n");  // Echo For debugging
            stmt.executeUpdate(sqlCommand);
            

            // ResultSet rset = stmt.executeQuery(sqlCommand);                    // rset - ResultSet object where query result was get/set or return

            // System.out.println("The records selected are:");  
            // int rowCount = 0;
            // // table header
            // System.out.printf("%-5s %10s %18s %18s %n","Policy Number", "User ID", "Date Registered", "Policy Type ID");
            // System.out.println("---------------------------------------------------------------");
   
            // while(rset.next()) {   // Repeatedly process each row
               
            //    String policy_no = rset.getString("policy_no");          // retrieve a policy no in the row
            //    int user_id = rset.getInt("user_id");                    // retrieve a user ID in the row
            //    Date date_registered = rset.getDate("date_registered");  // retrieve a date registered in the row
            //    String policy_type_id = rset.getString("policy_type_id"); // retrieve a policy type id in the row
            //    System.out.printf("%-5s %15d %15s %13s %n", policy_no, user_id, date_registered, policy_type_id); //display format
            //    ++rowCount;
            // }
            // System.out.println("Total number of records = " + rowCount); // records count
        } catch(SQLException ex) {
            ex.printStackTrace();   
        }
    }

    public boolean testExistOnDb(String sqlCommand,String table, String column, String dataToFind){
        exist = false;
        try (
            // a database connection "conn"
            Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/pas_system","root", "JR@norima2022"); 
            
            Statement stmt = conn.createStatement();                          //statement object inside the connection
           ){        
            
            if(table.equals("account")){
                sqlCommand = sqlCommand + " " + table +" where (" + column + ") = ('" + dataToFind + "');";
            }
            else if(table.equals("policy")){
            //    Select * from  policy where (policyID,accountID) = ((Select max(policyID) from policy), dataToFind);
                sqlCommand = sqlCommand + " " + table + " where (" +"policyID," + column + ") = ((Select max(policyID) from policy), " + dataToFind + ");";
            }
            else if(table.equals("policyholder")){
                sqlCommand = sqlCommand + " " + table + " where (" +"policyHolderID," + column + ") = ((Select max(policyHolderID) from policyholder), " + dataToFind + ");";
            }


            System.out.println(sqlCommand);  // Echo For debugging            

            ResultSet rset = stmt.executeQuery(sqlCommand);                    // rset - ResultSet object where query result was get/set or return
            int rowCount = 0;
            while(rset.next()) {   // Repeatedly process each row
                if (table.equals("account")){
                    try {
                        int intDataToFind = Integer.parseInt(dataToFind);
                        int accountId = rset.getInt("accountID");
                        String firstName = rset.getString("firstName");
                        String lastName = rset.getString("lastName");
                        String address = rset.getString("address");
                        int accountID = rset.getInt("accountID");

                        if(intDataToFind == accountId){
                            repoAccount = new CustomerAccount(accountID, firstName, lastName, address); 
                            exist = true;
                            return exist;
                        }
                        
                    } catch (NumberFormatException exception) {
                        String firstName = rset.getString("firstName");
                        String lastName = rset.getString("lastName");
                        String address = rset.getString("address");
                        int accountID = rset.getInt("accountID");
                        String datafName = dataToFind.substring(0,dataToFind.indexOf('\''));
                        String datalName = dataToFind.substring(dataToFind.indexOf(',') + 2,dataToFind.length());

                        if(datafName.equalsIgnoreCase( firstName ) && datalName.equalsIgnoreCase(  lastName )){
                            repoAccount = new CustomerAccount(accountID, firstName, lastName, address); // 
                            exist = true;
                            return exist;
                        }
                    }
                }
                else if(table.equals("policy")){   //////////////////////////////////////////////////////////////////
                    int intDataToFind = Integer.parseInt(dataToFind);
                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID");
                    String effectDate = rset.getString("effectiveDate");
                    String expireDate = rset.getString("expirationDate");
                    double policyPremium = rset.getDouble("policyPremium");
                    int vehicleNum = rset.getInt("vehicleNum");
                    

                    if(intDataToFind == accountId){
                        repoPolicy = new Policy(policyId, accountId, effectDate, expireDate, policyPremium, vehicleNum); 
                        exist = true;
                        return exist;
                    }
                }
                else if(table.equals("policyholder")){          ////////////////////////////////////////
                    int intDataToFind = Integer.parseInt(dataToFind);
                    int policyHolderId = rset.getInt("policyHolderID");
                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID"); 
                    String firstName = rset.getString("firstName");
                    String lastName = rset.getString("lastName");
                    String address = rset.getString("address");
                    String dob = rset.getString("dob");
                    String drivLicNum = rset.getString("drivLicNum");
                    String drivLic1stIssDate = rset.getString("drivLic1stIssDate");
                    

                    if(intDataToFind == accountId){
                        repoPolicyHolder = new PolicyHolder(policyHolderId, policyId, accountId, firstName, lastName, address, dob, drivLicNum,drivLic1stIssDate); 
                        exist = true;
                        return exist;
                    }
                }

                else if(table.equals("claim")){

                }
               
               ++rowCount;
            }
            System.out.println("Total number of records = " + rowCount); // records count
            
        } catch(SQLException ex) {
            ex.printStackTrace();   
        }
        return exist;
    }

    

    public void saveeToDb(CustomerAccount account){
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String address = account.getAddress();

        String details = "('"+firstName + "','" + lastName + "','" + address+"')";
        directCommandToDb("INSERT INTO account(firstName, lastName, address)"
                        + " VALUES "+details + ";" );
        System.out.println("\n  \n ACCOUNT CREATION SUCCESSFUL! \n \n");
    }

    public void saveeToDb(Policy policy){
        LocalDate effectDate = policy.getEffectDate();
        LocalDate expireDate = policy.getExpireDate();
        int accountId = policy.getAccountId();
        double policyPremium = policy.getPolicyPremium();
        int vehicleNum = policy.getVehicleNum();

        String details = "('"+ accountId + "','" + effectDate + "','" + expireDate + "','" + policyPremium + "','" + vehicleNum + "')";
        directCommandToDb("INSERT INTO policy(accountID, effectiveDate, expirationDate, policyPremium, vehicleNum)"
                        + " VALUES "+details + ";" );

                     System.out.println("INSERT INTO policy(accountID, effectiveDate, expirationDate, policyPremium, vehicleNum)"  //echo for debugging only
                                        + " VALUES "+details + ";");   

        System.out.println("\n  \n POlICY (6-MONTHS FROM DATE OF EFFECTIVITY) TERM SAVED! \n \n");
    }

    public void saveeToDb(PolicyHolder policyHolder){
        String firstName = policyHolder.getFirstName();
        String lastName = policyHolder.getLastName();
        int accountId = policyHolder.getAccountId();
        int policyId = policyHolder.getPolicyId();
        LocalDate dob = policyHolder.getDob();
        String address = policyHolder.getAddress();
        String drivLicNum = policyHolder.getDrivLicNum();
        LocalDate drivLic1stIssDate = policyHolder.getDateDxIssued();

        String details = "('" + policyId + "','" + accountId + "','" + firstName + "','" + lastName + "','" + address + "','" + dob + "','" + drivLicNum + "','" + drivLic1stIssDate + "')";
        directCommandToDb("INSERT INTO policyholder( policyID, accountID, firstName, lastName, address, dob, drivLicNum, drivLic1stIssDate)"
                        + " VALUES " + details + ";" );

                    System.out.println("INSERT INTO policyholder(firstName, lastName, accountID, policyID, dob, address, drivLicNum, drivLic1stIssDate)"
                        + "VALUES" + details + ";" );

    }

    public void saveeToDb(Vehicle vehicle){
        int accountId = vehicle.getAccountId();
        int policyId = vehicle.getPolicyId();
        String make = vehicle.getMake();
        String model = vehicle.getModel();
        int year = vehicle.getYear();
        String type = vehicle.getType();
        String fuelType = vehicle.getFuelType();
        double purchasePrice = vehicle.getPurchasePrice();
        String color = vehicle.getColor();
        double eachVehiclePremium = vehicle.getEachVehiclePremium();


        String details = "('" + accountId + "','" + policyId + "','" + make + "','" + model + "','" + year + "','" + type + "','" + fuelType + "','" + purchasePrice + "','" + color + "','" + eachVehiclePremium + "')";
        directCommandToDb("INSERT INTO vehicles(accountID, policyID, make, model, year, type, fuelType, purchasePrice, color, premium )"
                        + " VALUES " + details + ";" );

                    System.out.println("INSERT INTO vehicles(accountID, policyID, make, model, year, type, fuelType, purchasePrice, color, premium )"
                    + " VALUES " + details + ";" );

    }

    public void saveeToDb(Claim claim){
        int policyId = claim.getPolicyId();
        int accountId = claim.getAccountId();
        LocalDate dateOfAcc = claim.getDateOfAcc();
        String addAccHappen = claim.getAddAccHappen();
        String descOfAcc = claim.getDescOfAcc();
        String descOfDamage = claim.getDescOfDamage();
        Double estimatedCostOfRep = claim.getEstimatedCostOfRep();

        


        String details = "('" + policyId + "','" + accountId + "','" + dateOfAcc + "','" + addAccHappen + "','" + descOfAcc + "','" + descOfDamage + "','" + estimatedCostOfRep + "')";
        directCommandToDb("INSERT INTO claim(policyID, accountID, dateOfAcc, addAccHappen, descOfAcc, descOfDamage, estimatedCostOfRep)"
                        + " VALUES " + details + ";" );

                    System.out.println("INSERT INTO claim(policyID, accountID, dateOfAcc, addAccHappen, descOfAcc, descOfDamage, estimatedCostOfRep)"
                    + " VALUES " + details + ";" );

    }


    public boolean selectDisplayTable(String table, String commExtension){
        exist = false;
        try (
            // a database connection "conn"
            Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/pas_system","root", "JR@norima2022"); 
            
            Statement stmt = conn.createStatement();                          //statement object inside the connection
           ){        

                sqlCommand = "Select * from " + table + commExtension;
            
            System.out.println(sqlCommand);  // Echo For debugging            

            ResultSet rset = stmt.executeQuery(sqlCommand);                    // rset - ResultSet object where query result was get/set or return
            int rowCount = 0;
            while(rset.next()) {   // Repeatedly process each row
                if (table.equals("account")){
                    if(rowCount == 0){
                        System.out.println("ACCOUNT DETAILS:");
                        System.out.println("===============================================================================================================================================");
                        System.out.printf("%-10s %20s %20s %20s \n", "AccountID", "First Name", "Last Name" , "Address");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
                    }

                        int accountId = rset.getInt("accountID");
                        String firstName = rset.getString("firstName");
                        String lastName = rset.getString("lastName");
                        String address = rset.getString("address");
                        String accountNumber = String.format("%04d", accountId);
                        
                        System.out.printf("%-10s %20s %20s %20s \n", accountNumber, firstName, lastName, address);
        
                    repoAccount = new CustomerAccount(accountId, firstName, lastName, address); 
                }
                    
                else if(table.equals("policy")){   //////////////////////////////////////////////////////////////////
                    if(rowCount == 0){
                        System.out.println("POLICY DETAILS:");
                        System.out.println("===============================================================================================================================================");
                        System.out.printf("%-10s %20s %20s %20s %20s %29s\n", "PolicyID", "AccountID", "Effectivity Date", "Expiration Date" , "Policy Premium", "No. of Vehicle Enrolled");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
                    }

                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID");
                    String effectDate = rset.getString("effectiveDate");
                    String expireDate = rset.getString("expirationDate");
                    double policyPremium = rset.getDouble("policyPremium");
                    int vehicleNum = rset.getInt("vehicleNum");

                    repoPolicy = new Policy(policyId, accountId, effectDate, expireDate, policyPremium, vehicleNum); 

                    String policyNumber = repoPolicy.toPolicyNumConfig(policyId);
                    String accountNumber = String.format("%04d", accountId); 
                    String premInDoll =   "$" + String.format("%.2f", policyPremium);
                    System.out.printf("%-10s %20s %20s %20s %20s %29d\n", policyNumber, accountNumber, effectDate, expireDate, premInDoll, vehicleNum);
        
                }
                
                else if(table.equals("policyholder")){          ////////////////////////////////////////
                    if(rowCount == 0){
                        System.out.println("POLICY HOLDER DETAILS:");
                        System.out.println("===============================================================================================================================================");
                        System.out.printf("%-15s %10s %15s %15s %12s %15s %20s %20s \n",  "PolicyID", "AccountID", "FirstName", "Last Name", "Address" , "DOB", "Driv.Lic Number", "Dx 1st Issued Date");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
                    }
                    int policyHolderId = rset.getInt("policyHolderID");
                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID"); 
                    String firstName = rset.getString("firstName");
                    String lastName = rset.getString("lastName");
                    String address = rset.getString("address");
                    String dob = rset.getString("dob");
                    String drivLicNum = rset.getString("drivLicNum");
                    String drivLic1stIssDate = rset.getString("drivLic1stIssDate");
                    
                    String accountNumber = String.format("%04d", accountId);
                    String policyNumber = String.format("%06d", policyId);
                    System.out.printf("%-15s %10s %15s %15s %12s %15s %20s %20s \n", policyNumber, accountNumber, firstName, lastName, address , dob, drivLicNum, drivLic1stIssDate);
                    
                    repoPolicyHolder = new PolicyHolder(policyHolderId, policyId, accountId, firstName, lastName, address, dob, drivLicNum,drivLic1stIssDate); 
                }

                else if(table.equals("vehicles")){
                    repoVehicleArray = new Vehicle[repoPolicy.getVehicleNum()];
                    if(rowCount == 0){
                        System.out.println("VEHICLE DETAILS:");
                        System.out.println("===============================================================================================================================================");
                        System.out.printf("%-12s %10s %10s %10s %7s %13s %15s %15s %12s %13s\n", "Policy ID", "Account ID", "Make", "Model" , "Year", "Type", "Fuel Type", "Purchase Price", "Color", "Premium");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");                   
                    }
                    int vehicleId = rset.getInt("vehicleID");
                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID");
                    String make = rset.getString("make");
                    String model = rset.getString("model");
                    int year = rset.getInt("year");
                    String type = rset.getString("type");
                    String fuelType = rset.getString("fuelType");
                    double purchasePrice = rset.getDouble("purchasePrice");
                    String color = rset.getString("color");
                    double premium = rset.getDouble("premium");

                    String accountNumber = String.format("%04d", accountId);
                    String policyNumber = String.format("%06d", policyId);
                    String purchPriceInDoll = "$" + String.format("%.2f", purchasePrice);
                    String premiumInDoll = "$" + String.format("%.2f", premium);
                    
                    System.out.printf("%-12s %10s %10s %10s %7d %13s %15s %15s %12s %13s\n", policyNumber, accountNumber, make, model , year, type, fuelType, purchPriceInDoll, color, premiumInDoll);
                    
                    repoVehicleArray[rowCount] = new Vehicle(vehicleId, accountId, policyId, year, make, model, type, fuelType, color, purchasePrice, premium);
                }

                else if(table.equals("claim")){ //////////////////////////////////////////////////////////
                    if(rowCount == 0){
                        System.out.println("CLAIM DETAILS: ");
                        System.out.println("===============================================================================================================================================");
                        System.out.printf("%-10s %10s %12s %15s %16s %18s %20s %20s \n",  "ClaimID", "PolicyID", "AccountID", "Accident Date", "Acc. Address" , "Acc. Description", "Damage Description", "Repair Est.Cost");
                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
                    }
                    int claimId = rset.getInt("claimID");
                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID");
                    String dateOfAccStr = rset.getString("dateOfAcc");
                    String addAccHappen = rset.getString("addAccHappen");
                    String descOfAcc = rset.getString("descOfAcc");
                    String descOfDamage = rset.getString("descOfDamage");
                    Double estimatedCostOfRep = rset.getDouble("estimatedCostOfRep");

                    repoClaim = new Claim(claimId, policyId, accountId, dateOfAccStr, addAccHappen, descOfAcc, descOfDamage, estimatedCostOfRep);
                    
                    String claimNumber = repoClaim.toClaimNumberConfig(claimId);
                    String policyNumber = String.format("%06d", policyId);
                    String accountNumber = String.format("%04d", accountId);
                    String estimatedCostOfRepInDol = String.format("$%.2f", estimatedCostOfRep);

                    System.out.printf("%-10s %10s %12s %15s %16s %18s %20s %20s \n",  claimNumber, policyNumber, accountNumber, dateOfAccStr, addAccHappen, descOfAcc, descOfDamage, estimatedCostOfRepInDol);

                }      
               ++rowCount;
            }
            if (rowCount == 0){
                exist = false;
            }
            else{
                System.out.println("===============================================================================================================================================");
                System.out.println("Records count = " + rowCount); // records count
                exist =  true;
            }
            
        } catch(SQLException ex) {
            ex.printStackTrace();   
        }
        return exist;

    }

    public void updateTable(String table, String columnChanges, String condition){
        String sqlCommand = "UPDATE " + table + " SET " + columnChanges + " WHERE " + condition;
        directCommandToDb(sqlCommand);

    }
    
}