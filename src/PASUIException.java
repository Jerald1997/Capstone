import java.time.LocalDate;
import java.time.Period;

public class PASUIException {
    PASUIException(){

    }

    public boolean isNumeric(String inputString, int min, int max){
        boolean valid = false;
        if (!inputString.equals(null)) {
            try {
                Double numberDouble = Double.parseDouble(inputString);
                Double minDouble = Double.valueOf(min);
                Double maxDouble = Double.valueOf(max);
                if(numberDouble <= maxDouble && numberDouble >= minDouble){
                    valid =  true;
                }
                else{
                    System.out.println(" \n Invalid: Invalid Number Input! \n");
                    valid = false;
                }
              } catch (NumberFormatException exception) {
                System.out.println(" \n Invalid: Enter number only! \n");
                valid = false;
              }
        }
        return valid;
    }

    public boolean dateValid(String dateToTestStr, String minDateStr, String maxDateStr){
        boolean valid = false;
        try {
            LocalDate testDate = LocalDate.parse(dateToTestStr);
            LocalDate minDate, maxDate;
            if(minDateStr.equals("now")){
                minDate = LocalDate.now();
            }
            else{
                minDate = LocalDate.parse(minDateStr);
            }

            if(maxDateStr.equals("now")){
                maxDate = LocalDate.now();
            }
            else{
                maxDate = LocalDate.parse(maxDateStr);
            }

            int daysMinDateToTestDate = Period.between(minDate, testDate).getDays();
            int daysTestDateToMaxDate = Period.between(testDate, maxDate).getDays();

            if(daysMinDateToTestDate >= 0 && daysTestDateToMaxDate >= 0){
                valid = true;
            }
            else{
                System.out.println("\nInvalid: Date was not applicable!\n");
                valid = false;
            }

            return valid;


        } catch (Exception e) {
            System.out.println("\nInvalid: Please input date base on format!\n");
            return false;
        }
    }
}
