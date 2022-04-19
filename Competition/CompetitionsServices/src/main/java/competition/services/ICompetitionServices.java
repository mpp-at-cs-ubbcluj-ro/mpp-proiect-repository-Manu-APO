package competition.services;

import competition.Participant;
import competition.Registry;
import competition.SystemUser;
import competition.TrialDTO;

public interface ICompetitionServices {

    Iterable<TrialDTO> getAllTrialsDTO() throws CompetitionException;

    void login(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;

    void logoutParticipant(Participant participant, ICompetitionObserver client) throws CompetitionException;

    void logoutRegistry(Registry registry, ICompetitionObserver client) throws CompetitionException;
}

