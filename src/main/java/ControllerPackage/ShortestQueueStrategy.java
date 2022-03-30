package ControllerPackage;

import ModelPackage.Server;
import ModelPackage.Task;

import java.util.LinkedList;

public class ShortestQueueStrategy implements Strategy{


    @Override
    public void addTask(LinkedList<Server> servers, Task task) {

        Server server = null;
        int minim = Integer.MAX_VALUE;

        for (Server s: servers) {
            int queueSize = s.getTasks().size();

            if (minim > queueSize) {
                minim = queueSize;
                server = s;
            }
        }

        assert server != null;
        server.addTask(task);

    }
}
