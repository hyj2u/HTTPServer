package server;

import java.util.concurrent.Executor;

public class ExecutorWithNoThreads implements Executor {
    private int amountExecutedWasCalled;

    public ExecutorWithNoThreads() {
        this.amountExecutedWasCalled = 0;
    }

    @Override
    public void execute(Runnable runnable) {
        amountExecutedWasCalled++;
        runnable.run();
    }

    public int getAmountExecutedWasCalled() {
        return amountExecutedWasCalled;
    }
}
