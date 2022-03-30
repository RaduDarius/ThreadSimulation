package ModelPackage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    // the Q, who contains the clients

    private final LinkedBlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;

    public Server() {
        waitingPeriod = new AtomicInteger(0);
        tasks = new LinkedBlockingQueue<>();
    }

    public synchronized void addTask(Task newTask) {

        try {
            this.tasks.put(newTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public String toString() {
        return "Server{" +
                "tasks=" + tasks +
                ", waitingPeriod=" + waitingPeriod +
                '}';
    }

    //SETTERS AND GETTERS

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public void run() {
        while (waitingPeriod.get() > 0) {
            try {
                Task currTask = this.tasks.take();

                int serviceTime = currTask.getServiceTime();

                Thread.sleep(serviceTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
