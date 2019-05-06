package org.aaroncoplan.waterfall;

public class ErrorHandler {

    public static void exit() {
        System.exit(-1);
    }

    public static void exit(String errorMessageFormat, Object... args) {
        final String errorMessage = String.format(errorMessageFormat, args);
        System.out.format("[ERROR] %s", errorMessage).println();
        System.exit(-1);
    }

}

