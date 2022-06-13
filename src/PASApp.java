import java.util.Scanner;

public class PASApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PASService service = new PASService();

        int numChoice = 0;

        do{
            service.display();

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
                        
                        break;      
                    case 5:
                        
                        break; 
                    case 6:
                        
                        break;
                    case 7:
                        
                        break;
                    case 8: //Program will exit
                        break;               

                    default:
                        System.out.println("\n Invalid: Input number was OUT OF CHOICES!\n");;
                }


            }
            else{
                System.out.println("\n Invallid: Please input a NUMBER ONLY! \n");
                sc = new Scanner(System.in);
            }

            System.out.print("\n Press Enter to Continue...");
            System.in.read();
            System.out.println("\n\n");
         



        } while(numChoice != 8);

        System.out.println("\n =Program ENDED=\n");
        

        sc.close();
    }
}
