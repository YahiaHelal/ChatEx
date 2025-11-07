package com.yahia.anotherchatapplicatoin;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
    private static final Logger logger = Logger.getLogger("Another-Chat-App");
    static {
        try {
            Locale.setDefault(Locale.US); // date and time in english no matter what system language is

            FileHandler fh = new FileHandler("chat.log", true);
            fh.setFormatter(new SimpleFormatter());

            logger.addHandler(fh);
            logger.setLevel(Level.INFO); // globally

        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
