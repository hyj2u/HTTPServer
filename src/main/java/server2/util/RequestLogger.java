package server2.util;

import java.util.ArrayList;

public class RequestLogger {
    private ArrayList<String> logs;

    public RequestLogger() {
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
        return new String(responseBody);
    }
}
