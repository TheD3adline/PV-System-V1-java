import java.text.DecimalFormat;

/*
    Author: Michael Fessler
    Date: 2022/12/15
    Version: 0.1
    Description:
            App to calculate the break even point of a photovoltaic array.
 */
public class Main {

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
    static final int[] modulesConst = {1, 80};
    static final int[] subsidyConst = {0, 100};
    static final int[] bearingConst = {1, 5};
    static final int[] rateConst = {1, 100};
    static final int[] utilization = {100, 95, 90, 95, 90};
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
    static DecimalFormat df2 = new DecimalFormat();
    static DecimalFormat df3 = new DecimalFormat();

    public static void main(String[] args) {

        df2.setMaximumFractionDigits(2);
        df3.setMaximumFractionDigits(3);

        modules = UserInput.getInput(modulesText, modulesConst[0], modulesConst[1]);
        subsidy = UserInput.getInput(subsidyText, subsidyConst[0], subsidyConst[1]);
        bearing = UserInput.getInput(bearingText, bearingConst[0], bearingConst[1]);
        rate = UserInput.getInput(rateText, rateConst[0], rateConst[1]);

        if(modules == 1)
            System.out.println("One module facing " + direction[bearing - 1]);
        else
            System.out.println(modules + " modules facing " + direction[bearing - 1]);
        achievedPower = peakPower * modules * ((float) utilization[bearing] / 100);
        System.out.println("Achievable total power: " + df3.format(peakPower * modules) + "kWp.");
        if(bearing != 1)
            System.out.println("However, due to the suboptimal direction, only " + df3.format(achievedPower) + " kWp are achieved.");

        Y = y_Spec * achievedPower;
        investment = modules * cost;
        earnings = Y * rate;

        System.out.println("Investment of €" + df2.format(investment) + " leads to earnings of €" + df2.format(earnings / 100) + " each year.");
    }
}