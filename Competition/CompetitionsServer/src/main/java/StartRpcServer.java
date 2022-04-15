import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CompetitionService;

public class StartRpcServer {
    private static int defaultPort = 55555;


    public static void main(String[] args) {
//        Properties serverProps = new Properties();
//        try{
//            serverProps.load(StartRpcServer.class.getResourceAsStream("/athleticsserver.properties"));
//            System.out.println("Server properties set.");
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        IAthleticsServices athleticsServer = getServices();
//
//        int serverPort = defaultPort;
//        try{
//            serverPort = Integer.parseInt((serverProps.getProperty("athletics.server.port")));
//        } catch (NumberFormatException e) {
//            System.err.println("Wrong Port Number"+e.getMessage());
//            System.err.println("Using default port"+defaultPort);
//        }
//        System.out.println("Starting server on port: "+serverPort);
//
//        AbstractServer server = new AthleticsRpcConcurrentServer(serverPort,athleticsServer);
//
//        try{
//            server.start();
//        } catch (ServerException e) {
//           System.err.println("Error starting server"+e.getMessage());
//        }finally {
//            try{
//                server.stop();
//            } catch (ServerException e) {
//                System.err.println("Error stopping server"+e.getMessage());
//            }
//        }
//

//        Organizer organizer = new Organizer() ;
//        organizer.setUsername("test");
//        organizer.setPassword("test");
//        OrganizerDBRepositoryORM organizerDBRepositoryORM = new OrganizerDBRepositoryORM();
//        organizerDBRepositoryORM.add(organizer);
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        System.out.println("waiting for clients ....");
    }

    static CompetitionService getServices()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("CompetitionConfig.xml");
        CompetitionService services = context.getBean(CompetitionService.class);
        return services;
    }
}
