package repository;

import competition.Participant;
import competition.Trial;

public interface ParticipantRepository extends RepositoryCrud<String, Participant> {
    int getNumberOfParticipantsForTrial(Trial trial);
}


