/**
 * Norima Java Developer COURSE CAPSTONE
 * 
 * The Automobile Insurance Policy and Claims Administration System (PAS) Specification
 * 
 * @author Jerald Regidor
 * 2022 - June
 */

import java.util.Scanner;

public class PASApp {
    /*
        * Database: pas_system 
          - 5 tables (account, policy, policyholder, claim, vehicles)

        * On this project I used the AUTO_INCREMENT of databse to generate the ID's [ex. policyID(database) = 1 , UI output policyId = 000001]
         - in order to skip ID comparing and generate a unique ID faster (common to: account, policy, policyholder, claim, and vehicle)
         - ID identification: policyNumber - [UI output (String)]    vs.    [policyId - code ID or database ID (Integer)]
        
         * Project contains 2 helper class
         - PASRepository - handling database connection   (inherited by PASService class)
         - PASUIException - handling user input errors for number and date
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PASService service = new PASService();

        int numChoice = 0;

        do{
            service.display();     // display of choices and prompt for choice

            if(sc.hasNextInt()){
                numChoice = sc.nextInt();

                switch (numChoice) {
                    case 1:
                        service.createAccount();
                        break;
                    case 2:
                        service.getPolicy();
                        break;
                    case 3:
                        service.cancelPolicy();
                        break;
                    case 4:
                        service.fileClaim();
                        break;      
                    case 5:
                        service.searchCustumerAcc();
                        break; 
                    case 6:
                        service.searchDispPolicy();
                        break;
                    case 7:
                        service.searchDispClaim();
                        break;
                    case 8:                                             //Program will exit
                        System.out.println("\n =Program ENDED=\n");
                        System.exit(0);
                        break;               

                    default:
                        System.out.println("\n Invalid: Input number was OUT OF CHOICES!\n");;
                }
            }
            else{
                System.out.println("\n Invallid: Please input a NUMBER ONLY! \n");
                sc = new Scanner(System.in);
            }

            System.out.print("\n Press Enter to Continue...");     //Require user to press enter before proceeding to other process
            System.in.read();
            System.out.println("\n\n");
         
        } while(numChoice != 8);  // loop process till input was not 8

        sc.close();
    }
}
