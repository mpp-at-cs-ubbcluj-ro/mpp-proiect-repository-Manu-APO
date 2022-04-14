package competition.network.rpcprotocol;

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

public class CompetitionClientRpcReflectionWorker implements Runnable , ICompetitionObserver {

    private ICompetitionServices server;    // care e serviciul nostru implementat
    private Socket connection;
    private ObjectInputStream input;        // pentru a citi (read) de pe socket
    private ObjectOutputStream output;      // pentru a scrie pe socket
    private volatile boolean connected;     // pentru a lua informatia din memorie nu din memoria cache
    // variabila e folosita de mai multe threaduri


    public CompetitionClientRpcReflectionWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
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

        while(connected){
            try{
                Object request = input.readObject(); //citeste un obiect de pe socket
                Response response = handleRequest((Request)request);//trateaza cererea corespunzatoare
                if(response != null){
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
            System.out.println("Error : "+e);
        }


    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response"+response);
        synchronized (output){
            output.writeObject(response);
            output.flush();
        }

    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle"+(request).type();
        System.out.println("HandlerName : "+handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName,Request.class);
            response = (Response)method.invoke(this,request);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request worker...");
        SystemUser systemUser = (SystemUser)request.data();
        System.out.println("systemUser in handle login" + systemUser);
        try{
            server.login(systemUser,this);

            return okResponse;
        } catch (CompetitionException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


//    private Response handlePARTICIPANTS_DTO(Request request){
//        System.out.println("getAllParticipantsDto worker...");
//        try{
//            return new Response.Builder().type(ResponseType.GET_PARTICIPANTS_DTO).data(server.getAllParticipantsDto()).build();
//        } catch (CompetitionException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//
//    private Response handleATHLETIC_TEST_DTO(Request request){
//        System.out.println("getAllAthletiTestDto worker..");
//        try {
//            return new Response.Builder().type(ResponseType.GET_ATHLETIC_TEST_DTO).data(server.getAllAthleticTestDto()).build();
//        } catch (CompetitionException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout worker...");
        SystemUser systemUser = (SystemUser) request.data();
        try {
            server.logout(systemUser,this);
            connected = false;
            return okResponse;

        } catch (CompetitionException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }



//    private Response handleFIND_ALL_PARTICIPANTS_TEST_LEGTH_AND_AGE(Request request){
//        System.out.println("FindAllParticipantsByTestLengthAndAge worker ...");
//        AthleticTest athleticTest = (AthleticTest) request.data();
//        try {
//            Iterable<Participant> participants = server.findAllParticipantsByTestLegthAndAge(athleticTest.getTestLength(),athleticTest.getMinAge(),athleticTest.getMaxAge());
//            return new Response.Builder().type(ResponseType.GET_FIND_ALL_PARTICIPANTS_TEST_LEGTH_AND_AGE).data(participants).build();
//        } catch (AthleticsException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//
//    }
//
//    private Response handleADD_PARTICIPANT_WITH_RETURNING(Request request){
//        System.out.println("Add participant with returning working ...");
//        Participant participant = (Participant) request.data();
//        try{
//            Participant participantAdded = server.addParticipantWithReturning(participant);
//            return new Response.Builder().type(ResponseType.GET_ADD_PARTICIPANT_WITH_RETURNING).data(participantAdded).build();
//
//        } catch (CompetitionException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//
//    private Response handleFIND_ATHLETIC_TEST_BY_TEST_LENGTH_AND_AGE(Request request){
//        System.out.println("find athletic test bu test length and age wroker ...");
//        AthleticTest athleticTest = (AthleticTest) request.data();
//
//        try {
//            AthleticTest athleticTestReturned = server.findAthleticTestByTestLengthAndAge(athleticTest.getTestLength(),athleticTest.getMinAge(),athleticTest.getMaxAge());
//            return new Response.Builder().type(ResponseType.GET_FIND_ATHLETIC_TEST_BY_TEST_LENGTH_AND_AGE).data(athleticTestReturned).build();
//        } catch (CompetitionException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//
//    }
//
//    private Response handleADD_ENTRY(Request request){
//        System.out.println("add entry worker ...");
//        Entry entry = (Entry) request.data();
//
//        try {
//            server.addEntry(entry);
//            return okResponse;
//        } catch (CompetitionException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//
    @Override
    public void updateParticipantsDtoAthleticTestsDto() throws CompetitionException {
        Response response = new Response.Builder().type(ResponseType.GET_UPDATE_PARTICIPANTS_DTO_ATHLETIC_TESTS_DTO).build();
        System.out.println("update interfaces worker ...");
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

