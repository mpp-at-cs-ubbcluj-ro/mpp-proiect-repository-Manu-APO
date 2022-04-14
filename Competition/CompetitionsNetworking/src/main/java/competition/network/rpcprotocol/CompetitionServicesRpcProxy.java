package competition.network.rpcprotocol;

import competition.SystemUser;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/////sunt pe client

public class CompetitionServicesRpcProxy implements ICompetitionServices {


    private String host;
    private int port;

    private ICompetitionObserver client; //asta o sa fie un controller

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;

    private BlockingQueue<Response> qResponses;
    private volatile boolean finished;

    public CompetitionServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.qResponses = new LinkedBlockingDeque<Response>();
    }

    @Override
    public void login(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException {
        initializeConnection();

        Request request = new Request.Builder().type(RequestType.LOGIN).data(systemUser).build(); //creez o cerere

        sendRequest(request); //trimit cererea de la client la server (eu acuma sunt pe client)
        System.out.println("cererea de login s-a trimis cu succes in client");

        Response response = readResponse(); ///citest raspunsul
        System.out.println("raspunsul a fost citit cu succes in client");

        if(response.type() == ResponseType.OK){
            this.client = client; // cand e ok raspunsul aici tinem minte clientul mai exact controllerul lui (care defapt e un client)
            return;
        }
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            closeConnection();
            throw new CompetitionException(err);
        }

    }

    @Override
    public void logout(SystemUser organizer, ICompetitionObserver client) throws CompetitionException {
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(organizer).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new CompetitionException(err);
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Response readResponse() {
        Response response=null;
        try {
            response=qResponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendRequest(Request request) throws CompetitionException {

        try {
            System.out.println("request-ul din send request"+request);
            System.out.println(output);
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new CompetitionException("EROOR sending object "+e);
        }
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host,port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();//creem un reader Thread
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startReader() {
        Thread thread = new Thread(new ReaderThread());
        thread.start();
    }

    private void handleUpdate(Response response) {
        try {
            client.updateParticipantsDtoAthleticTestsDto(); // clientul e controllerul
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isUpdate(Response response) {

        return response.type() == ResponseType.GET_UPDATE_PARTICIPANTS_DTO_ATHLETIC_TESTS_DTO;
    }



    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println(input);
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qResponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

//    @Override
//    public Iterable<Participant> findAllParticipantsByTestLegthAndAge(int testLength, int minAge, int maxAge) throws AthleticsException {
//        AthleticTest athleticTest = new AthleticTest(minAge,maxAge,testLength);
//        Request request = new Request.Builder().type(RequestType.FIND_ALL_PARTICIPANTS_TEST_LEGTH_AND_AGE).data(athleticTest).build();
//
//        sendRequest(request);
//        System.out.println("cererea de findAllParticipantsByTestLegthAndAge s a trimis cu succes in client");
//        Response response = readResponse();
//        System.out.println("raspunsul din findAllParticipantsByTestLegthAndAge s a citit cu succes in client");
//
//        if(response.type() == ResponseType.GET_FIND_ALL_PARTICIPANTS_TEST_LEGTH_AND_AGE){
//            return (Iterable<Participant>) response.data();
//        }
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            throw new AthleticsException(err);
//        }
//        return null;
//
//
//    }
//
//
//
//
//
//
//
//    @Override
//    public Iterable<ParticipantDTO> getAllParticipantsDto() throws AthleticsException {
//
//        Request request = new Request.Builder().type(RequestType.PARTICIPANTS_DTO).build();//creez o cerere
//
//
//        sendRequest(request);//trimit cererea de la client la server (eu acuma sunt pe client)
//        System.out.println("cererea din getAllParticipantsDto() s a trimis cu succes in client");
//
//        Response response = readResponse(); ///citest raspunsul
//        System.out.println("raspunsul din getAllParticipantsDto() a fost citita cu succes in client");
//
//        if(response.type() == ResponseType.GET_PARTICIPANTS_DTO){
//
//            return (Iterable<ParticipantDTO>) response.data();
//        }
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            throw new AthleticsException(err);
//        }
//
//        return null;
//    }
//
//
//
//    @Override
//    public Iterable<AthleticTestDto> getAllAthleticTestDto() throws AthleticsException {
//        Request request = new Request.Builder().type(RequestType.ATHLETIC_TEST_DTO).build();//creez o cerere
//
//
//        sendRequest(request);//trimit cererea de la client la server (eu acuma sunt pe client)
//        System.out.println("cererea din getAllParticipantsDto() s a trimis cu succes in client");
//
//        Response response = readResponse(); ///citest raspunsul
//        System.out.println("raspunsul din getAllParticipantsDto() a fost citita cu succes in client");
//
//        if(response.type() == ResponseType.GET_ATHLETIC_TEST_DTO){
//
//            return (Iterable<AthleticTestDto>) response.data();
//        }
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            throw new AthleticsException(err);
//        }
//
//        return null;
//    }
//
//    @Override
//    public Participant addParticipantWithReturning(Participant participant) throws AthleticsException {
//        Request request = new Request.Builder().type(RequestType.ADD_PARTICIPANT_WITH_RETURNING).data(participant).build();
//        sendRequest(request);
//        System.out.println("cererea din addParticipantWithReturning s a trimis cu succes in client");
//
//        Response response=readResponse();
//        System.out.println("raspunsul din addParticipantWithReturning s a citit cu succes in client");
//
//        if(response.type() == ResponseType.GET_ADD_PARTICIPANT_WITH_RETURNING){
//            return (Participant) response.data();
//        }
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            throw new AthleticsException(err);
//        }
//        return null;
//    }
//
//    @Override
//    public AthleticTest findAthleticTestByTestLengthAndAge(int testLength, int minAge, int maxAge) throws AthleticsException {
//        AthleticTest athleticTest = new AthleticTest(minAge,maxAge,testLength);
//        Request request = new Request.Builder().type(RequestType.FIND_ATHLETIC_TEST_BY_TEST_LENGTH_AND_AGE).data(athleticTest).build();
//        sendRequest(request);
//        System.out.println("cererea din findAthleticTestByTestLengthAndAge s-a trimis cu succes in client");
//        Response response = readResponse();
//        System.out.println("raspunsul din findAthleticTestByTestLengthAndAge s-a citit cu succes in client");
//        if(response.type() == ResponseType.GET_FIND_ATHLETIC_TEST_BY_TEST_LENGTH_AND_AGE){
//            return (AthleticTest) response.data();
//        }
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            throw new AthleticsException(err);
//        }
//        return null;
//    }
//
//    @Override
//    public void addEntry(Entry entry) throws AthleticsException {
//        Request request = new Request.Builder().type(RequestType.ADD_ENTRY).data(entry).build();
//        sendRequest(request);
//        System.out.println("cererea din addEntry s-a trimis cu succes in client");
//        Response response = readResponse();
//        System.out.println("raspunsul din addEntry s-a citit cu succes in client");
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            throw new AthleticsException(err);
//        }
//
//    }
//
//



}

