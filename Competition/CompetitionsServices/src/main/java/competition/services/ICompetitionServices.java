package competition.services;

import competition.SystemUser;

public interface ICompetitionServices {

//    Iterable<Participant> findAllParticipantsByTestLegthAndAge(int testLength, int minAge, int maxAge) throws AthleticsException;
//
//    Iterable<ParticipantDTO> getAllParticipantsDto() throws AthleticsException;
//
//    Iterable<AthleticTestDto> getAllAthleticTestDto() throws AthleticsException;
//
//    Participant addParticipantWithReturning(Participant participant) throws AthleticsException;
//
//    AthleticTest findAthleticTestByTestLengthAndAge(int testLength, int minAge, int maxAge) throws AthleticsException;
//
//    void addEntry(Entry entry) throws AthleticsException;
//
    void login(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;

    void logout(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException;
}

