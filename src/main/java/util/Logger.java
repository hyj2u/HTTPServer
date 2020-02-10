package util;

import java.util.ArrayList;

/**
 * This class save request and response message as List to print at console.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class Logger {
    /**
     * Arraylist that has request and response message.
     */
    private ArrayList<String> logs;

    /**
     * This Constructor initializes arraylist logs.
     *
     * @since 1.0
     */
    public Logger() {
        logs = new ArrayList<>();
    }

    /**
     * Adds message to list.
     *
     * @param log
     * @since 1.0
     */
    public void addLog(String log) {
        logs.add(log);
    }

    /**
     * Returns contents of list.
     *
     * @return message
     * @since 1.0
     */
    public String getLogsBody() {
        StringBuilder message = new StringBuilder();
        for (String log : logs) {
            message.append(log).append("\n");
        }

        return new String(message);
    }


}
