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
    Vehicle repoVehicle;
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

                        if(datafName.equals( firstName ) && datalName.equals(  lastName )){
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

                        int accountId = rset.getInt("accountID");
                        String firstName = rset.getString("firstName");
                        String lastName = rset.getString("lastName");
                        String address = rset.getString("address");
                        int accountID = rset.getInt("accountID");
        
                    repoAccount = new CustomerAccount(accountID, firstName, lastName, address); 
                }
                    
                else if(table.equals("policy")){   //////////////////////////////////////////////////////////////////
                    if(rowCount == 0){
                        System.out.printf("%-10s %20s %20s %20s %20s %29s\n", "Policy ID", "Account ID", "Effectivity Date", "Expiration Date" , "Policy Premium", "No. of Vehicle Enrolled");
                        System.out.println("===================================================================================================================================");
                    }

                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID");
                    String effectDate = rset.getString("effectiveDate");
                    String expireDate = rset.getString("expirationDate");
                    double policyPremium = rset.getDouble("policyPremium");
                    int vehicleNum = rset.getInt("vehicleNum");

                    repoPolicy = new Policy(policyId, accountId, effectDate, expireDate, policyPremium, vehicleNum); 

                    String policyNumber = repoPolicy.toPolicyNumConfig(policyId);   
                    System.out.printf("%-20s %5s %04d %20s %20s %20.2f %29d\n", policyNumber, "", accountId, effectDate, expireDate, policyPremium, vehicleNum);
        
                }
                
                else if(table.equals("policyholder")){          ////////////////////////////////////////
                    int policyHolderId = rset.getInt("policyHolderID");
                    int policyId = rset.getInt("policyID");
                    int accountId = rset.getInt("accountID"); 
                    String firstName = rset.getString("firstName");
                    String lastName = rset.getString("lastName");
                    String address = rset.getString("address");
                    String dob = rset.getString("dob");
                    String drivLicNum = rset.getString("drivLicNum");
                    String drivLic1stIssDate = rset.getString("drivLic1stIssDate");
                    
                    repoPolicyHolder = new PolicyHolder(policyHolderId, policyId, accountId, firstName, lastName, address, dob, drivLicNum,drivLic1stIssDate); 
                }

                else if(table.equals("claim")){

                }
               
               ++rowCount;
            }
            System.out.println("===================================================================================================================================");
            if(commExtension.equals(null)){
                System.out.println("Total number of records = " + rowCount); // records count
            }

            if (rowCount == 0){
                exist = false;
            }
            else{
                exist =  true;
            }
            
        } catch(SQLException ex) {
            ex.printStackTrace();   
        }
        return exist;

    }





    
    
}