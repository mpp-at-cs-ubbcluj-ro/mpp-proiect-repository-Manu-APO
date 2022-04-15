package service;

import athletics.services.AthleticsException;
import athletics.services.IAthleticsObserver;
import athletics.services.IAthleticsServices;
import athletic.*;
import repository.AthleticTestRepository;
import repository.EntryRepository;
import repository.OrganizerRepository;
import repository.ParticipantRepository;


import java.rmi.RemoteException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CompetitionService implements IAthleticsServices {

    private ParticipantRepository participantRepository;
    private AthleticTestRepository athleticTestRepository;
    private OrganizerRepository organizerRepository;
    private EntryRepository entryRepository;
    private Map<String,IAthleticsObserver> loggedOrganizers;


    public CompetitionService(ParticipantRepository participantRepository, AthleticTestRepository athleticTestRepository, OrganizerRepository organizerRepository, EntryRepository entryRepository) {
        this.participantRepository = participantRepository;
        this.athleticTestRepository = athleticTestRepository;
        this.organizerRepository = organizerRepository;
        this.entryRepository = entryRepository;
        loggedOrganizers = new ConcurrentHashMap<>();
    }

    public synchronized Iterable<Participant> findAllParticipantsByTestLegthAndAge(int testLength, int minAge, int maxAge){
        AthleticTest athleticTest =  athleticTestRepository.findAthleticTestByTestLengthAndAge(testLength,minAge,maxAge);
        if(athleticTest == null)
        {
            return null;
        }else
        return  participantRepository.findAllParticipantsByTestLengthAndAge(athleticTest);
    }



    public synchronized Iterable<ParticipantDTO> getAllParticipantsDto(){
        Iterable<Participant> participants = participantRepository.findAll();
        List<ParticipantDTO> participantDTOS = new ArrayList<>();
        participants.forEach(x->{
            int nrOfAthleticTest = athleticTestRepository.getNumberOfAthleticTestForParticipant(x);
            participantDTOS.add(new ParticipantDTO(x.getFirstName(),x.getLastName(),x.getAge(),nrOfAthleticTest));
        });

        return participantDTOS;

    }


    public synchronized Iterable<AthleticTestDto> getAllAthleticTestDto(){
        Iterable<AthleticTest>athleticTests = athleticTestRepository.findAll();
        List<AthleticTestDto> athleticTestDtos = new ArrayList<>();
        athleticTests.forEach(a->{
            int nrOfParticipants = participantRepository.getNumberOfParticipantsForAthleticTest(a);;
            athleticTestDtos.add(new AthleticTestDto(a.getMinAge(),a.getMaxAge(),a.getTestLength(),nrOfParticipants));
        });
        return athleticTestDtos;

    }

    public synchronized Participant addParticipantWithReturning(Participant participant){
        return  participantRepository.addWithReturning(participant);
    }

    public synchronized AthleticTest findAthleticTestByTestLengthAndAge(int testLength,int minAge, int maxAge){
        return athleticTestRepository.findAthleticTestByTestLengthAndAge(testLength,minAge,maxAge);
    }
    public synchronized void addEntry(Entry entry){
        entryRepository.add(entry);
        notifyAllOrganizers();
    }

    private final int defaultThread = 5;
    private void notifyAllOrganizers() {
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThread);
        loggedOrganizers.forEach( (id,client)->{
            if(client != null){
                System.out.println("Notified client:" + id);
                try {
                    client.updateParticipantsDtoAthleticTestsDto();
                } catch (AthleticsException | RemoteException e) {
                    e.printStackTrace();
                    System.out.println("error add updating");
                }
            }
        });

        executorService.shutdown();
    }

    @Override
    public synchronized void login(Organizer organizer, IAthleticsObserver client) throws AthleticsException {

        Organizer organizerReceived = organizerRepository.findOneByUsernameAndPassword(organizer.getUsername(),organizer.getPassword());

        if(organizerReceived != null){
            if(loggedOrganizers.get(organizerReceived.getUsername()) != null) {
                throw new AthleticsException("Organizer already logged in.");
            }
            loggedOrganizers.put(organizerReceived.getUsername(),client);

        }else {

            throw new AthleticsException("Authentication failed.");
        }
    }

    @Override
    public synchronized void logout(Organizer organizer, IAthleticsObserver client) throws AthleticsException {
        IAthleticsObserver organizers = loggedOrganizers.remove(organizer.getUsername());

        if(organizers==null){
            throw new AthleticsException("Organizer "+organizer.getUsername()+"is not logged in");
        }
    }
}
