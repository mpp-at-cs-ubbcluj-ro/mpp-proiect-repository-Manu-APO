package competition.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICompetitionObserver extends Remote {
    void updateTrialsDto() throws CompetitionException, RemoteException;
}
