package com.androsov.server.logging;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

class LogFormatter extends Formatter
{
    // ANSI escape code
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // format is called for every console log message
    @Override
    public String format(LogRecord record)
    {
        StringBuilder builder = new StringBuilder();

        if (record.getLevel().equals(Level.INFO)) {
            builder.append(ANSI_YELLOW);
        } else if (record.getLevel().equals(Level.WARNING)) {
            builder.append(ANSI_RED);
        }

        builder.append("[");
        builder.append(calcDate(record.getMillis()));
        builder.append("]");

        builder.append(" [");
        builder.append(record.getSourceClassName());
        builder.append("]");

        builder.append(" [");
        builder.append(record.getSourceMethodName());
        builder.append("]");

        builder.append(" [");
        builder.append(record.getLevel().getName());
        builder.append("]");

        builder.append(ANSI_WHITE);
        builder.append("\n");
        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null)
        {
            builder.append("\t");
            for (int i = 0; i < params.length; i++)
            {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        builder.append(ANSI_RESET);
        builder.append("\n");
        return builder.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}

public class LogToFile  {
    /**
     * Sets {@link java.util.Formatter} and {@link FileHandler} to {@link Logger} named "LOGGER".
     * @param logFilePath full path to log file place.
     * @throws IOException If some I/O exception occurred.
     */
    public static void configureLogger(String logFilePath) throws IOException {
        final Logger LOGGER = Logger.getLogger(("LOGGER"));
        LOGGER.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();

        Formatter formatter = new LogFormatter();
        handler.setFormatter(formatter);
        LOGGER.addHandler(handler);

        final FileHandler fh = new FileHandler(logFilePath);
        LOGGER.addHandler(fh);
    }
}