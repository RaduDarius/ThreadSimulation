package ControllerPackage;

import ModelPackage.Server;
import ModelPackage.Task;

import java.util.LinkedList;

public class TimeStrategy implements Strategy{

    @Override
    public void addTask(LinkedList<Server> servers, Task task) {
        Server server = null;
        int minim = Integer.MAX_VALUE;

        for (Server s: servers) {
            int waitingPeriod = s.getWaitingPeriod().get();

            if (minim > waitingPeriod) {
                minim = waitingPeriod;
                server = s;
            }
        }

        assert server != null;
        server.addTask(task);

    }
}
