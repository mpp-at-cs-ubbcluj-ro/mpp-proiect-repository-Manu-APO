package competition.network.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;

    public AbstractServer(int port){
        this.port = port;
    }

    public void start() throws ServerException {
        try{
            server = new ServerSocket(port); //creaza un server socket cu portul respectiv
            //ServerSocket e clasa corespunzatoare serverului care asteapta conexiuni TCP

            while(true){
                System.out.println("Waiting for clients ...");
                Socket client = server.accept(); // astepta pentru un client ,se blocheaza pana un client se conecteaza
                System.out.println("Client connected");
                processRequest(client); //cerem un proces
            }
        } catch (IOException e) {
            throw new ServerException("Starting server error ",e);
        } finally {
            stop();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {

        try{
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error",e);
        }

    }

}
