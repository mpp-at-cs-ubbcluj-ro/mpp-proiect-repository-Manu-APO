package competition.network.utils;

import competition.services.ICompetitionServices;

import java.net.Socket;

public class CompetitionRpcConcurrentServer extends AbstractConcurrentServer {

    private ICompetitionServices competitionServices; /// asta o sa fie un CompetitionService

    public CompetitionRpcConcurrentServer(int port ,ICompetitionServices competitionServices) {
        super(port);
        this.competitionServices = competitionServices;
        System.out.println("Create Competition Rpc Concurrent Server");
    }

    @Override
    protected Thread createWorker(Socket client) {

        CompetitionClientRpcReflectionWorker worker  = new CompetitionClientRpcReflectionWorker(competitionServices, client);
        //wrokerul e un thread(client) care e pe server
        Thread thread = new Thread(worker);
        return  thread;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services...");
    }
}

