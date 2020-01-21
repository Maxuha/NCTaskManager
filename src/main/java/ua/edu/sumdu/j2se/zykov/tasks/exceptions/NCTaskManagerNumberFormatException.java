package ua.edu.sumdu.j2se.zykov.tasks.exceptions;

public class NCTaskManagerNumberFormatException extends Exception {

    private int number;

    public int getNumber() {
        return number;
    }

    public NCTaskManagerNumberFormatException(String message, int number) {
        super(message);
        this.number = number;
    }
}
