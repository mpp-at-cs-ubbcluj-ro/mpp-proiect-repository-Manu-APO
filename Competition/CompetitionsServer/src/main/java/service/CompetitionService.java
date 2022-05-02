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
    public Iterable<Participation> getAllUserParticipation() throws CompetitionException {
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
    public Iterable<TrialDTO> getAllTrialsDTO() throws CompetitionException {
        return trialRepository.findAll();
    }

    @Override
    public Registry loginRegistry(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException {
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
    public Participant loginParticipant(SystemUser systemUser, ICompetitionObserver client) throws CompetitionException {
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
        ICompetitionObserver participants = loggedRegistries.remove(participant.getUsername());

        if(participants == null){
            throw new CompetitionException("Participant "+ participant.getUsername()+" is not logged in");
        }
    }

//    public synchronized Iterable<Participant> findAllParticipantsByTestLegthAndAge(int testLength, int minAge, int maxAge){
//        AthleticTest athleticTest =  athleticTestRepository.findAthleticTestByTestLengthAndAge(testLength,minAge,maxAge);
//        if(athleticTest == null)
//        {
//            return null;
//        }else
//        return  participantRepository.findAllParticipantsByTestLengthAndAge(athleticTest);
//    }


//    public synchronized Iterable<ParticipantDTO> getAllParticipantsDto(){
//        Iterable<Participant> participants = participantRepository.findAll();
//        List<ParticipantDTO> participantDTOS = new ArrayList<>();
//        participants.forEach(x->{
//            int nrOfAthleticTest = athleticTestRepository.getNumberOfAthleticTestForParticipant(x);
//            participantDTOS.add(new ParticipantDTO(x.getFirstName(),x.getLastName(),x.getAge(),nrOfAthleticTest));
//        });
//
//        return participantDTOS;
//
//    }


//    public synchronized Iterable<AthleticTestDto> getAllAthleticTestDto(){
//        Iterable<AthleticTest>athleticTests = athleticTestRepository.findAll();
//        List<AthleticTestDto> athleticTestDtos = new ArrayList<>();
//        athleticTests.forEach(a->{
//            int nrOfParticipants = participantRepository.getNumberOfParticipantsForAthleticTest(a);;
//            athleticTestDtos.add(new AthleticTestDto(a.getMinAge(),a.getMaxAge(),a.getTestLength(),nrOfParticipants));
//        });
//        return athleticTestDtos;
//
//    }
//
//    public synchronized Participant addParticipantWithReturning(Participant participant){
//        return  participantRepository.addWithReturning(participant);
//    }
//
//    public synchronized AthleticTest findAthleticTestByTestLengthAndAge(int testLength,int minAge, int maxAge){
//        return athleticTestRepository.findAthleticTestByTestLengthAndAge(testLength,minAge,maxAge);
//    }
//    public synchronized void addEntry(Entry entry){
//        entryRepository.add(entry);
//        notifyAllOrganizers();
//    }
//
//    private final int defaultThread = 5;
//    private void notifyAllOrganizers() {
//        ExecutorService executorService = Executors.newFixedThreadPool(defaultThread);
//        loggedOrganizers.forEach( (id,client)->{
//            if(client != null){
//                System.out.println("Notified client:" + id);
//                try {
//                    client.updateParticipantsDtoAthleticTestsDto();
//                } catch (AthleticsException | RemoteException e) {
//                    e.printStackTrace();
//                    System.out.println("error add updating");
//                }
//            }
//        });
//
//        executorService.shutdown();
//    }

//    @Override
//    public synchronized void login(Organizer organizer, IAthleticsObserver client) throws AthleticsException {
//
//        Organizer organizerReceived = organizerRepository.findOneByUsernameAndPassword(organizer.getUsername(),organizer.getPassword());
//
//        if(organizerReceived != null){
//            if(loggedOrganizers.get(organizerReceived.getUsername()) != null) {
//                throw new AthleticsException("Organizer already logged in.");
//            }
//            loggedOrganizers.put(organizerReceived.getUsername(),client);
//
//        }else {
//
//            throw new AthleticsException("Authentication failed.");
//        }
//    }
//
//    @Override
//    public synchronized void logout(Organizer organizer, IAthleticsObserver client) throws AthleticsException {
//        IAthleticsObserver organizers = loggedOrganizers.remove(organizer.getUsername());
//
//        if(organizers==null){
//            throw new AthleticsException("Organizer "+organizer.getUsername()+"is not logged in");
//        }
//    }


}
