public class RatingEngine  {

    private double eachVehiclePremium;
    static private double policyPremium;   // Static total of each vehicle premium

    RatingEngine(Policy policy){                       // rating engine constructor that accepts Policy object only 
        double purchasePrice = policy.getPurchasePrice();
        
        int dlx = policy.getDlx();
        Double dlxDouble = Double.valueOf(dlx);

        int carYearAge = policy.getCarYear();

        float priceFactor;
        
        if(carYearAge <= 1){
            priceFactor = 1.0f; 
        }
        else if(carYearAge <= 3){
            priceFactor = 0.8f; 
        }
        else if(carYearAge <= 5){
            priceFactor = 0.7f; 
        }
        else if(carYearAge <= 10){
            priceFactor = 0.6f; 
        }
        else if(carYearAge <= 15){
            priceFactor = 0.4f; 
        }
        else if(carYearAge <= 20){
            priceFactor = 0.2f; 
        }
        else if(carYearAge <= 40){
            priceFactor = 0.1f; 
        }
        else{
            priceFactor = 0.1f;
        }

        this.eachVehiclePremium = ( purchasePrice * priceFactor) + ((purchasePrice/100)/dlxDouble);   // Premium Formula

        policyPremium += eachVehiclePremium;        // each vehicle premium addition for static total policyPremium
    }

    public double getEachVehiclePremium() {
        return this.eachVehiclePremium;
    }

    public static double getPolicyPremium() {
        return policyPremium;
    }

    public static void setPolicyPremium(double policyPremium) {   // for setting the static policy Premium to ZERO after creation of each policy
        RatingEngine.policyPremium = policyPremium;
    }

}
