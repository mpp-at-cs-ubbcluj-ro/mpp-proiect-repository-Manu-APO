package competition.network.utils;

import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer {

    public AbstractConcurrentServer(int port) {
        super(port);
        System.out.println("Create a Concurrent Abstract Server");
    }

    @Override
    protected void processRequest(Socket client) {
        Thread tw = createWorker(client);
        tw.start();//aici se porneste executia unui thread
    }

    protected abstract Thread createWorker(Socket client);
}

