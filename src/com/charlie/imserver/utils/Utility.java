package com.charlie.imserver.utils;

/**
 * Utility:
 * deal with and accord developer request to get users input in console
 */

import java.util.Scanner;

public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * read menu button selection
     *
     * @return
     */
    public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false);
            c = str.charAt(0);  //string to char
            if (c != '1' && c != '2' && c != '3' && c != '4' && c != '5') {
                System.out.print("wrong selection, please try again: ");
            } else break;
        }
        return c;
    }

    /**
     * function: read a char from keyboard input
     *
     * @return a char
     */
    public static char readChar() {
        String str = readKeyBoard(1, false);    //just one char
        return str.charAt(0);
    }

    /**
     * function: read a char from keyboard input,
     * if press enter key directly, return default value
     *
     * @param defaultValue
     * @return input value or default value
     */
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true);
        return (str.length() == 0) ? defaultValue : str.charAt(0);
    }

    /**
     * function: read int number form keyboard input
     * length smaller than 2
     *
     * @return integer number
     */
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(4, false);    //integer number
            try {
                n = Integer.parseInt(str);  //convert string to integer
                break;
            } catch (NumberFormatException e) {
                System.out.print("wrong type input, please try again: ");
                e.printStackTrace();
            }
        }
        return n;
    }

    /**
     * function: read integer or default number from keyboard
     * if press enter key directly, return default value
     * else return input integer number
     *
     * @param defaultValue
     * @return input integer number or default value
     */
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(10, true);
            if (str.equals("")) {
                return defaultValue;
            }
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("wrong type input, please try again: ");
                e.printStackTrace();
            }
        }
        return n;
    }

    /**
     * function: read specified length of string from keyboard input
     *
     * @param limit length of string
     * @return string
     */
    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    /**
     * function: read specified length string or default string
     * if press enter key directly return default string
     * else return input string
     *
     * @param limit        length of string
     * @param defaultValue default string
     * @return default string or input string
     */
    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("") ? defaultValue : str;
    }

    /**
     * function: read yes or no options
     *
     * @return Y or N
     */
    public static char readConfirmSelection() {
        System.out.println("please enter your selection(Y/N): ");
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("wrong option input, please try again: ");
            }
        }
        return c;
    }

    /**
     * @param limit
     * @param blankReturn
     * @return
     */
    private static String readKeyBoard(int limit, boolean blankReturn) {
        //define string
        String line = "";

        //scanner.hasNextLine()
        while (scanner.hasNextLine()) {
            line = scanner.nextLine(); //read line

            //if line.length() == 0, input is empty, press enter directly;
            if (line.length() == 0) {
                if (blankReturn) return line; //if blankReturn==ture, return empty string
                else continue;  //if blankReturn==false, must input string
            }

            //if input string length greater than limit, retry
            //if input string length >0 && <=limit, its ok
            if (line.length() < 1 || line.length() > limit) {
                System.out.print("input length wrong (length must smaller than " + limit + "), try again: ");
                continue;
            }
            break;
        }
        return line;
    }
}
