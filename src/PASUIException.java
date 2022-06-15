/**  USER INPUT EXCEPTION CLASS (helper)
 * This class handles exception from user input for number and date
 */

import java.time.LocalDate;
import java.time.Period;

public class PASUIException { 
    PASUIException(){


    }

    public boolean isNumeric(String inputString, int min, int max){   // test if input is a number and also test if its within the valid range (min and max)
        boolean valid = false;                                        // only return TRUE if input is number and within valid range [ex.: Month (1-12 only)]
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

    public boolean dateValid(String dateToTestStr, String minDateStr, String maxDateStr){       // test if input is a valid date and also test if its within the valid range of date (min and max)
        boolean valid = false;                                                                  // only return TRUE if input is valid date and within valid range 
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
