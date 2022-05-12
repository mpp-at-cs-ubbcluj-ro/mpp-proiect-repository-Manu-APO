package start;


import competition.AGE_CATEGORY;
import client.CompetitionTestClient;
import client.ServiceException;
import competition.TRIAL_TYPE;
import competition.TrialDTO;

import java.util.Arrays;
import java.util.Date;

public class StartRestClient {

    private final static CompetitionTestClient competitionClient = new CompetitionTestClient();

    public static void main(String[] args) {

        TrialDTO trial = new TrialDTO(20, TRIAL_TYPE.ACTING, AGE_CATEGORY.KIDS,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()));
        TrialDTO trialResponse =  competitionClient.create(trial);

        show(()-> System.out.println(trialResponse));
        show(()-> System.out.println(Arrays.toString(competitionClient.getAll())));
        show(()-> System.out.println(competitionClient.getOne(trialResponse.getId())));
        trialResponse.setAgeCategory(AGE_CATEGORY.SMALL_KIDS);
        show(()-> competitionClient.update(trialResponse));
        show(()-> System.out.println(competitionClient.getOne(trialResponse.getId())));
        show(()-> competitionClient.delete(trialResponse.getId()));
        show(()-> System.out.println(Arrays.toString(competitionClient.getAll())));

    }

    private static void show(Runnable task){
        try{
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service Exception "+e);
        }
    }
}
