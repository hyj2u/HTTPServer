package server;

public class ServerStatusSpy extends ServerStatus{
    private int numberOfRequests;
    private int counter;

    public ServerStatusSpy(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
        counter=-1;
    }

    @Override
    public boolean isRunning() {
        counter++;
        return counter < numberOfRequests;
    }
}
