package client;

public class ServiceException extends RuntimeException{

    public ServiceException(Exception e){
        super(e);
    }
    public ServiceException(String e){
        super(e);
    }
}
