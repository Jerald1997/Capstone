import java.time.LocalDate;
import java.util.Scanner;

public class Vehicle{
    private int vehicleId, accountId, policyId, year, carYear;
    private String make, model, type, fuelType, color;
    private double purchasePrice, eachVehiclePremium;
    PASUIException except = new PASUIException();

    public Vehicle(){
    }
    
    // a constructor that accepts data according to columns of vehicle table of database
    public Vehicle(int vehicleId, int accountId, int policyId, int year, String make, String model, String type,
            String fuelType, String color, double purchasePrice, double eachVehiclePremium) {
        this.vehicleId = vehicleId;
        this.accountId = accountId;
        this.policyId = policyId;
        this.year = year;         
        this.make = make;
        this.model = model;
        this.type = type;
        this.fuelType = fuelType;
        this.color = color;
        this.purchasePrice = purchasePrice;
        this.eachVehiclePremium = eachVehiclePremium;
    }

    Scanner sc = new Scanner(System.in);

    // filling data for Vehicle object by user input and data from CustomerAccount and Policy, and Rating Engine object parameter
    public void createVehicle(CustomerAccount repoAccount, Policy policy, int counter, RatingEngine ratingEngine){
        System.out.println("\n==Please Provide details for Vehicle/s Record creation==");

            int vehicleDeterminer = counter + 1 ;
            System.out.println("[ Record for VEHICLE NUMBER " + vehicleDeterminer + " ]");
            System.out.print("    Enter MAKE for Vehicle " + vehicleDeterminer + " : ");
            this.make = sc.nextLine();

            System.out.print("    Enter MODEL for Vehicle " + vehicleDeterminer + " : ");
            this.model = sc.nextLine();

            boolean inputLoop = true;
            while (inputLoop){
                System.out.print("    Enter YEAR for Vehicle " + vehicleDeterminer + " [YYYY]: ");
                String yearStr = sc.nextLine();
                if(except.isNumeric(yearStr,1000,9999)){
                    this.year = Integer.parseInt(yearStr);
                    inputLoop = false;
                }
                else{
                    inputLoop = true;
                }
            }

            System.out.print("    Enter TYPE for Vehicle " + vehicleDeterminer + " : ");
            this.type = sc.nextLine();

            System.out.print("    Enter FUEL TYPE for Vehicle " + vehicleDeterminer + " : ");
            this.fuelType = sc.nextLine();

            inputLoop = true;
            while (inputLoop){
                System.out.print("    Enter PURCHASE PRICE for Vehicle " + vehicleDeterminer + " : ");
                String purchasePriceStr = sc.nextLine();
                if(except.isNumeric(purchasePriceStr, 1, 999999999)){
                    this.purchasePrice = Double.parseDouble(purchasePriceStr);
                    inputLoop = false;
                }
                else{
                    inputLoop = true;
                }
            }

            System.out.print("    Enter COLOR for Vehicle " + vehicleDeterminer + " : ");
            this.color = sc.nextLine();


            LocalDate currentDate = LocalDate.now();

            this.carYear = currentDate.getYear() - year;

            policy.setCarYear(this.carYear);

            policy.setPurchasePrice(this.purchasePrice);

            ratingEngine = new RatingEngine(policy);

            this.eachVehiclePremium = ratingEngine.getEachVehiclePremium();
            this.accountId = repoAccount.getAccountId();

            System.out.print("      =- Vehicle "+ vehicleDeterminer + " Premium : $ ");
            System.out.printf("%.2f %s \n\n",  this.eachVehiclePremium,  " -=");
    }
    
    public int askForVehicleNum(){
        System.out.print("How many vehicles you want to add on the policy? ");
        String vehicleNumStr = sc.nextLine();
        int vehicleNum = 0;

        boolean inputLoop = true;
        while (inputLoop){
            if(except.isNumeric(vehicleNumStr,1, 999999)){
                vehicleNum = Integer.parseInt(vehicleNumStr);
                return vehicleNum;
            }
            else{
                inputLoop = true;
            }
        }
    
        return vehicleNum;
    }

    public void dispPremiumBreakDown(int vehicleNum, Vehicle[] vehicleArray){  //responsible for displaying the breakdown from array of vehicle objects

        System.out.printf("%-10s %10s %10s %10s %10s %15s", "Vehicle", "Make", "Model", "Year" , "Color", "Unit Premium");
        System.out.println("\n----------------------------------------------------------------------------------------");
        for(int counter = 0; counter < vehicleNum; counter++){
            int vehicleDeterminer = counter + 1;
            System.out.printf("%-10d %10s %10s %10d %10s %7s %.2f \n", vehicleDeterminer, vehicleArray[counter].make, vehicleArray[counter].model, vehicleArray[counter].year, vehicleArray[counter].color, "$", vehicleArray[counter].eachVehiclePremium);
        }
        System.out.println("----------------------------------------------------------------------------------------\n");
        System.out.printf("%65s","TOTAL POLICY PREMIUM = $");
        System.out.printf("%.2f\n\n",  RatingEngine.getPolicyPremium());
        System.out.print("Press Enter to proceed to Confirmation Choices..");
        sc.nextLine();
    }

    public int getVehicleId() {
        return this.vehicleId;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public int getPolicyId() {
        return this.policyId;
    }

    public int getYear() {
        return this.year;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public String getType() {
        return this.type;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public String getColor() {
        return this.color;
    }

    public double getEachVehiclePremium() {
        return this.eachVehiclePremium;
    }

    public int getCarYear() {
        return carYear;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

}

