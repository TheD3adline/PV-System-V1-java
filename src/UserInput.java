/*
    Author: Michael Fessler
    Date: 2022/12/15
    Version: 0.1
    Description:
            Class to process the user inputs.
 */
import java.util.Scanner;
public class UserInput {

    private static final Scanner sc = new Scanner(System.in);

    public static int getInput(String[] messages, int cLow, int cHigh) {
        int num;
        do {
            if(messages.length >= 2)
                for(int i = 2; i < messages.length; i++) {
                    System.out.println(messages[i]);
                }
            System.out.print(messages[0]);
            while(!sc.hasNextInt()) {
                System.out.println(messages[1]);
                sc.next();
            }
            num = sc.nextInt();
            if(num < cLow || num > cHigh)
                System.out.println(messages[1]);
        } while(num < cLow || num > cHigh);
        return num;
    }
}
