package server2.util;

import java.util.ArrayList;

public class Logger {
    private ArrayList<String> logs;

    public Logger() {
        logs = new ArrayList<>();
    }

    public void addLog(String log) {
        logs.add(log);
    }

    public String getLogsBody() {
        StringBuilder responseBody = new StringBuilder();
        for (String log : logs) {
            responseBody.append(log).append("\n");
        }
        logs.clear();
        return new String(responseBody);
    }
}
