package repository;

import competition.Participant;
import competition.ParticipationDTO;

public interface ParticipationRepository extends RepositoryCrud<Long, ParticipationDTO> {
    public Iterable<Participant> getAllParticipantsOfTrial(Long trialId);
}


