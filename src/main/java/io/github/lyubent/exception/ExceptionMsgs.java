package io.github.lyubent.exception;

public class ExceptionMsgs {

    public static String tooFewArgs() {
        return "Too few arguments. Expected 'session' or 'thread' without quotation marks, " +
               "followed by list of contact points separated by a space.\nE.g.: session 10.0.0.1 10.0.0.2";
    }

    public static String invalidArg1(String arg1) {
        return "Invalid arg: " + arg1 + ". Expected 'session' or 'thread' without quotation marks, " +
                "followed by list of contact points separated by a space.\nE.g.: session 10.0.0.1 10.0.0.2";
    }
}
