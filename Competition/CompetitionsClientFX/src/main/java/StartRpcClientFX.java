import competition.services.ICompetitionServices;
import controller.DashboardRegistryAndAdminController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartRpcClientFX extends Application {

    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start in StartRpcClientFX");

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        ICompetitionServices services = (ICompetitionServices) factory.getBean("competitionServices"); //aici iau toate serviciile de la server pentru client
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/loginWindow.fxml"));

            AnchorPane root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setService(services, primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setTitle("Competition app");


            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(getClass().getResource("/views/loginWindow.fxml"));
            AnchorPane root1 = loader1.load();
            DashboardRegistryAndAdminController ctrl1 = loader1.getController();

            ctrl.setCompetitionController(ctrl1);
            ctrl.setParent(root1);


            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
