package com.cogent.admin.utils;

import org.slf4j.Logger;

/**
 * @author Sauravi Thapa 1/7/20
 */
public class LogLevel {

    public static enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }


    public static void log(Logger logger, Level level, String txt) {
        if (logger != null && level != null) {
            switch (level) {
                case TRACE:
                    logger.trace(txt);
                    break;
                case DEBUG:
                    logger.debug(txt);
                    break;
                case INFO:
                    logger.info(txt);
                    break;
                case WARN:
                    logger.warn(txt);
                    break;
                case ERROR:
                    logger.error(txt);
                    break;
            }
        }
    }


}
