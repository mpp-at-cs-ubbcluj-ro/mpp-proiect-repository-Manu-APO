package service;

import competition.*;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;
import repository.ParticipantRepository;
import repository.ParticipationRepository;
import repository.RegistryRepository;
import repository.TrialRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CompetitionService implements ICompetitionServices {

    private ParticipantRepository participantRepository;
    private TrialRepository trialRepository;
    private RegistryRepository registryRepository;
    private ParticipationRepository participationRepository;
    private Map<String, ICompetitionObserver> loggedRegistries;
    private Map<String, ICompetitionObserver> loggedParticipants;


    public CompetitionService(ParticipantRepository participantRepository, TrialRepository trialRepository, RegistryRepository registryRepository, ParticipationRepository participationRepository) {
        this.participantRepository = participantRepository;
        this.trialRepository = trialRepository;
        this.registryRepository = registryRepository;
        this.participationRepository = participationRepository;
        loggedRegistries = new ConcurrentHashMap<>();
        loggedParticipants = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Iterable<Participation> getAllUserParticipation() throws CompetitionException {
        List<Participation> participation = new ArrayList<>();
        var participationDto = participationRepository.findAll();
        for(ParticipationDTO part: participationDto){
            var participant = participantRepository.findOne(part.getParticipantId());
            var trial = trialRepository.findOne(part.getTrialId());
            var registry = registryRepository.findOne(part.getRegistryId());
            Trial mock_trial = new Trial(new Competition(new Date(1000),new Date(1000)),trial.getMaxNumberOfParticipants(),trial.getTrialType(),trial.getAgeCategory(),trial.getStartDate(),trial.getEndDate());
            var finalPart = new Participation(participant,mock_trial,part.getDateOfSubmission(),registry);
            finalPart.setId(part.getId());
            participation.add(finalPart);
        }
        return participation;
    }

    @Override
    public synchronized Iterable<TrialDTO> getAllTrialsDTO() throws CompetitionException {
        return trialRepository.findAll();
    }

    @Override
    public synchronized Registry loginRegistry(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException {
        Registry registerReceived = registryRepository.getRegistryByCredentials(systemUser.getUsername(), systemUser.getPassword());

        if (registerReceived != null) {
            if (loggedRegistries.get(registerReceived.getUsername()) != null) {
                throw new CompetitionException("Registry already logged in.");
            }
            loggedRegistries.put(registerReceived.getUsername(), client);
            return registerReceived;

        }
        return registerReceived;
    }

    @Override
    public synchronized Participant loginParticipant(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException {
        Participant participantReceived = participantRepository.getParticipantByCredentials(systemUser.getUsername(), systemUser.getPassword());

        if (participantReceived != null) {
            if (loggedParticipants.get(participantReceived.getUsername()) != null) {
                throw new CompetitionException("Participant already logged in.");
            }
            loggedParticipants.put(participantReceived.getUsername(), client);
            return participantReceived;
        } else {
            throw new CompetitionException("Authentication failed.");
        }
    }

    @Override
    public void logoutRegistry(Registry registry, ICompetitionObserver client) throws CompetitionException {
        ICompetitionObserver registries = loggedRegistries.remove(registry.getUsername());

        if(registries == null){
            throw new CompetitionException("Registry "+ registry.getUsername()+" is not logged in");
        }
    }

    @Override
    public void logoutParticipant(Participant participant, ICompetitionObserver client) throws CompetitionException {
        ICompetitionObserver participants = loggedParticipants.remove(participant.getUsername());

        if(participants == null){
            throw new CompetitionException("Participant "+ participant.getUsername()+" is not logged in");
        }
    }

    public synchronized Iterable<Participant> getAllParticipantsOfTrial(Long trialId){
        return participationRepository.getAllParticipantsOfTrial(trialId);
    }

    public synchronized void addParticipantForTrial(TrialDTO trial,Participant participant){
        participant = participantRepository.add(participant);
        participationRepository.add(new ParticipationDTO(participant.getId(), trial.getId(), new Date(),1L));

    }
    public synchronized void removeParticipantFromTrial(TrialDTO trial,Participant participant){

    }
    public synchronized void modifyParticipantFromTrial(TrialDTO trial,Participant participant){

    }
}
