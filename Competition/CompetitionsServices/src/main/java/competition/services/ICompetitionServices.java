package competition.services;

import competition.*;

public interface ICompetitionServices {

    Iterable<Participation> getAllUserParticipation() throws CompetitionException;

    Iterable<TrialDTO> getAllTrialsDTO() throws CompetitionException;

    Registry loginRegistry(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;

    Participant loginParticipant(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;

    void logoutParticipant(Participant participant, ICompetitionObserver client) throws CompetitionException;

    void logoutRegistry(Registry registry, ICompetitionObserver client) throws CompetitionException;
}

