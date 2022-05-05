import competition.services.ICompetitionServices;
import controller.DashboardParticipantController;
import controller.DashboardRegistryAndAdminController;
import controller.LoginController;
import controller.RegistryTrialsDetailsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartRpcClientFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start StartRpcClientFX");

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        ICompetitionServices services = (ICompetitionServices) factory.getBean("competitionServices"); //aici iau toate serviciile de la server pentru client
        try {
            FXMLLoader loginLoader = new FXMLLoader();
            loginLoader.setLocation(getClass().getResource("/views/loginWindow.fxml"));
            AnchorPane loginRoot = loginLoader.load();
            LoginController loginController = loginLoader.getController();
            loginController.setService(services, primaryStage);
            Scene scene = new Scene(loginRoot);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Competition app");
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });


            FXMLLoader dashboardRegistryLoader = new FXMLLoader();
            dashboardRegistryLoader.setLocation(getClass().getResource("views/dashboardRegistryAndAdmin.fxml"));
            AnchorPane dashboardRegistryRoot = dashboardRegistryLoader.load();
            DashboardRegistryAndAdminController dashboardRegistryController = dashboardRegistryLoader.getController();
            loginController.setRegistryController(dashboardRegistryController);
            loginController.setRegistryParent(dashboardRegistryRoot);

            FXMLLoader dashboardParticipantLoader = new FXMLLoader();
            dashboardParticipantLoader.setLocation(getClass().getResource("views/dashboardParticipant.fxml"));
            AnchorPane dashboardParticipantRoot = dashboardParticipantLoader.load();
            DashboardParticipantController dashboardParticipantController = dashboardParticipantLoader.getController();
            loginController.setParticipantController(dashboardParticipantController);
            loginController.setParticipantParent(dashboardParticipantRoot);

            FXMLLoader registryTrialsDetailsLoader = new FXMLLoader();
            registryTrialsDetailsLoader.setLocation(getClass().getResource("views/registryTrialDetails.fxml"));
            AnchorPane registryTrialsDetailsRoot = registryTrialsDetailsLoader.load();
            RegistryTrialsDetailsController registryTrialsDetailsController = registryTrialsDetailsLoader.getController();
            dashboardRegistryController.setDetailsController(registryTrialsDetailsController);
            dashboardRegistryController.setDetailsParent(registryTrialsDetailsRoot);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
