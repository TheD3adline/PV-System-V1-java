/*
    Author: Michael Fessler
    Date: 2022/12/15
    Version: 0.1
    Description:
            App to calculate the break even point of a photovoltaic array.
 */
import java.text.DecimalFormat;
public class Main {

    //String arrays with console messages for user input
    static final String[] modulesText = {"Number of solar modules: ", "Invalid input! The number of solar modules " +
                                        "must be an integer greater than or equal to 1 and less than or equal to 80."};
    static final String[] subsidyText = {"Subsidy in % of the investment: ", "Invalid input! The subsidy " +
                                        "must be an integer greater than or equal to 0 and less than or equal to 100"};
    static final String[] bearingText = {"Please enter the direction: ", "Invalid input! The direction " +
                                        "must be an integer greater than or equal to 1 and less than or equal to 5.",
                                        "Direction of the photovoltaic system:", "1: SOUTH", "2: SOUTH-EAST",
                                        "3: EAST", "4: SOUTH-WEST", "5: WEST"};
    static final String[] rateText = {"Electricity rate in cents/kWh: ", "Invalid input! The electricity rate " +
                                     "must be an integer which is at least 1 and must not exceed 100 cents/kWh."};
    static final String[] direction = {"SOUTH", "SOUTH-EAST", "EAST", "SOUTH-WEST", "WEST"};

    //integer arrays to carry relevant data, such as input constraints and values required for calculations
    static final int[] modulesConst = {1, 80};
    static final int[] subsidyConst = {0, 100};
    static final int[] bearingConst = {1, 5};
    static final int[] rateConst = {1, 100};
    static final int[] utilization = {100, 95, 90, 95, 90};

    //variables
    static int modules;
    static int subsidy;
    static int bearing;
    static int rate;
    static final float peakPower = 0.375F;
    static final float cost = 259.99F;
    static float achievedPower = 0.375F;
    static float y_Spec = 905;
    static float Y;
    static float earnings;
    static float investment;
    static float remCosts;
    static float yearlySubsidy;
    static int years;

    //to properly format floats according to assignment
    static DecimalFormat df2 = new DecimalFormat();

    public static void main(String[] args) {

        //set decimal format objects to given task, to display either 2 or 3 digits after floating point
        df2.setMaximumFractionDigits(2);

        //getInput() method calls to assign values to variables for calculation
        modules = UserInput.getInput(modulesText, modulesConst[0], modulesConst[1]);
        subsidy = UserInput.getInput(subsidyText, subsidyConst[0], subsidyConst[1]);
        bearing = UserInput.getInput(bearingText, bearingConst[0], bearingConst[1]);
        rate = UserInput.getInput(rateText, rateConst[0], rateConst[1]);

        //prints to console amount of solar panels and the direction they are facing
        if(modules == 1)
            System.out.println("One module facing " + direction[bearing - 1]);
        else
            System.out.println(modules + " modules facing " + direction[bearing - 1]);

        //calculates power output based on number of panels and the direction they are facing, then prints to console
        achievedPower = peakPower * modules * ((float) utilization[bearing - 1] / 100);
        System.out.println("Achievable total power: " + df2.format(peakPower * modules) + "kWp.");
        if(bearing != 1)
            System.out.println("However, due to the suboptimal direction, only " + df2.format(achievedPower) +
                                " kWp are achieved.");

        //calculations for yearly power output, investment in € for the panels,
        //yearly earnings, yearly amount of government subsidy in €
        Y = y_Spec * achievedPower;
        investment = modules * cost;
        earnings = Y * rate;
        remCosts = investment;
        yearlySubsidy = (investment / 100) * subsidy;

        System.out.println("Investment of €" + df2.format(investment) + " leads to earnings of €" +
                            df2.format(earnings / 100) + " each year.");

        //loop to perform yearly calculations
        while(remCosts > 0) {
            years++;
            if(subsidy > 0) {
                remCosts = remCosts - (earnings / 100) - yearlySubsidy;
                if(remCosts < 0) {
                    if(years <= 1)
                        System.out.println("PV system amortized within a year.");
                    else
                        System.out.println("PV system amortized within " + years + " years.");
                    break;
                }
                System.out.println("After year " + years + ": €" + df2.format(remCosts) + " still to compensate (€" +
                                    df2.format(yearlySubsidy) + " paid by the government).");
            } else {
                remCosts = remCosts - (earnings / 100);
                if(remCosts < 0) {
                    if(years <= 1)
                        System.out.println("PV system amortized within a year.");
                    else
                        System.out.println("PV system amortized within " + years + " years.");
                    break;
                }
                System.out.println("After year " + years + ": €" + df2.format(remCosts) + " still to compensate.");
            }
        }
    }
}