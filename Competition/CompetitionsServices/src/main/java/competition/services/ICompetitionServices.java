package competition.services;

import competition.SystemUser;
import competition.TrialDTO;

public interface ICompetitionServices {

    Iterable<TrialDTO> getAllTrialsDTO() throws CompetitionException;

    void login(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;

    void logout(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;
}

