package competition.network.rpcprotocol;

import competition.Participant;
import competition.Registry;
import competition.SystemUser;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;

public class CompetitionClientRpcReflectionWorker implements Runnable, ICompetitionObserver {

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();
    private ICompetitionServices server;    // care e serviciul nostru implementat
    private Socket connection;
    private ObjectInputStream input;        // pentru a citi (read) de pe socket
    private ObjectOutputStream output;      // pentru a scrie pe socket
    // variabila e folosita de mai multe threaduri
    private volatile boolean connected;     // pentru a lua informatia din memorie nu din memoria cache

    public CompetitionClientRpcReflectionWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (connected) {
            try {
                Object request = input.readObject(); //citeste un obiect de pe socket
                Response response = handleRequest((Request) request);//trateaza cererea corespunzatoare
                if (response != null) {
                    sendResponse(response);//trimite un raspuns
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error : " + e);
        }


    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response" + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }

    }

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName : " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request worker...");
        SystemUser systemUser = (SystemUser) request.data();
        System.out.println("systemUser in handle login" + systemUser);
        try {
            server.loginParticipant(systemUser, this);

            return okResponse;
        } catch (CompetitionException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handlePARTICIPANTS_DTO(Request request) {
        System.out.println("getAllTrialsDto worker...");
        try {
            return new Response.Builder().type(ResponseType.GET_TRIALS_DTO).data(server.getAllTrialsDTO()).build();
        } catch (CompetitionException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT_REGISTRY(Request request) {
        System.out.println("Logout registry worker...");
        Registry registry = (Registry) request.data();
        try {
            server.logoutRegistry(registry, this);
            connected = false;
            return okResponse;

        } catch (CompetitionException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT_PARTICIPANT(Request request) {
        System.out.println("Logout participant worker...");
        Participant participant = (Participant) request.data();
        try {
            server.logoutParticipant(participant, this);
            connected = false;
            return okResponse;

        } catch (CompetitionException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    @Override
    public void updateTrialsDto() throws CompetitionException, RemoteException {
        Response response = new Response.Builder().type(ResponseType.GET_UPDATE_TRIALS_DTO).build();
        System.out.println("update interfaces worker ...");
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

