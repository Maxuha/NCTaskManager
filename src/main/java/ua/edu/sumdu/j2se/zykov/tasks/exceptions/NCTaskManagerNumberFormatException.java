package ua.edu.sumdu.j2se.zykov.tasks.exceptions;

public class NCTaskManagerNumberFormatException extends Exception {

    /**
     * number - user entered value
     */
    private int number;

    /**
     * @return last user entered value
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param message - error message
     * @param number - new value entered by user
     */
    public NCTaskManagerNumberFormatException(String message, int number) {
        super(message);
        this.number = number;
    }
}
