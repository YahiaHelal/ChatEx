package com.yahia.chatio.utils.logging;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
    private static final Logger logger = Logger.getLogger("Another-Chat-App");
    private static final Level RUN_LOG_LEVEL = Level.INFO;
    private static final Level DEBUG_LOG_LEVEL = Level.ALL;
    private static final Level LOG_LEVEL = DEBUG_LOG_LEVEL;
    static {
        try {
            Locale.setDefault(Locale.US); // date and time in english no matter what system language is

            FileHandler fh = new FileHandler("chat.log", false);
            fh.setFormatter(new SimpleFormatter());

            logger.addHandler(fh);
            logger.setLevel(LOG_LEVEL); // globally
            logger.setUseParentHandlers(false); // no console logging
            logger.log(Level.CONFIG, String.format("Logging Level Set to %s", LOG_LEVEL));

        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
