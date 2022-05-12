package client;

import competition.TrialDTO;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class CompetitionTestClient {

    private static final String URL = "http://localhost:8080/competition/trials";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable)  {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public TrialDTO[] getAll(){
        return execute(()->restTemplate.getForObject(URL,TrialDTO[].class));
    }

    public TrialDTO getOne(Long id){
        return execute(()->restTemplate.getForObject(URL+"/"+id.toString(),TrialDTO.class));
    }

    public TrialDTO create(TrialDTO trial){
        return execute(()->restTemplate.postForObject(URL,trial,TrialDTO.class));

    }

    public void update(TrialDTO trial){
        execute(()-> {
            restTemplate.put(URL+"/"+trial.getId().toString(),trial);
            return null;
        });
    }
    public void delete(Long id){
        execute(()-> {
            restTemplate.delete(URL+"/"+id.toString());
            return null;
        });
    }

}
