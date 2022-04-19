import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CompetitionService;

import static java.lang.Thread.sleep;

public class StartRpcServer {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        System.out.println("waiting for clients ....");

    }

    public static CompetitionService getServices() {
        ApplicationContext context = new ClassPathXmlApplicationContext("CompetitionConfig.xml");
        CompetitionService services = context.getBean(CompetitionService.class);
        return services;
    }
}
