package repository;

import competition.Participant;

public interface ParticipantRepository extends RepositoryCrud<Long, Participant> {
    Participant getParticipantByCredentials(String username, String password);
}


