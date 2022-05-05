package controller;

import competition.Participant;
import competition.Registry;
import competition.SystemUser;
import competition.services.CompetitionException;
import competition.services.ICompetitionServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;


public class LoginController extends UnicastRemoteObject implements Serializable, Initializable {

    private ICompetitionServices competitionServices; //asta e un proxy

    Stage loginStage;

    Stage dashboardRegistryAndAdminStage;
    Stage dashboardParticipantStage;


    DashboardRegistryAndAdminController dashboardRegistryAndAdminController;
    DashboardParticipantController dashboardParticipantController;
    Parent rootRegistryWindow;
    Parent rootParticipantWindow;

    @FXML
    PasswordField loginPasswordTf;
    @FXML
    TextField loginUsernameTf;
    @FXML
    Button loginSubmitBt;

    public LoginController() throws RemoteException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardRegistryAndAdminStage = new Stage();
        dashboardRegistryAndAdminStage.setTitle("Dashboard");
        dashboardRegistryAndAdminStage.setOnCloseRequest(event -> {
            dashboardRegistryAndAdminController.logOut();
            System.exit(0);
        });

        dashboardParticipantStage = new Stage();
        dashboardParticipantStage.setTitle("Dashboard");
        dashboardParticipantStage.setOnCloseRequest(event -> {
            dashboardParticipantController.logOut();
            System.exit(0);
        });
    }

    public void setService(ICompetitionServices services, Stage primaryStage) {
        this.competitionServices = services; // aici primesc CompetitionServicesRpcProxy
        this.loginStage = primaryStage;
    }

    public void loginUser(MouseEvent mouseEvent) throws CompetitionException {

        String username = loginUsernameTf.getText();
        String password = loginPasswordTf.getText();

        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);

        try {
            Registry registry = competitionServices.loginRegistry(systemUser, dashboardRegistryAndAdminController); //proxy.login()
            if(registry != null) {
                openRegistryWindow(registry);
            }
            else{
                Participant participant = competitionServices.loginParticipant(systemUser, dashboardParticipantController);
                openParticipantWindow(participant);
            }
        } catch (CompetitionException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wrong username or password");
            alert.show();
        }


    }

    private void openRegistryWindow(Registry registry) {
        dashboardRegistryAndAdminController.setAtrributes(competitionServices, registry, dashboardRegistryAndAdminStage, loginStage);
        dashboardRegistryAndAdminStage.show();
        loginStage.hide();
        loginUsernameTf.clear();
        loginPasswordTf.clear();
    }

    private void openParticipantWindow(Participant participant) {
        dashboardParticipantController.setAtrributes(competitionServices, participant, dashboardParticipantStage, loginStage);
        dashboardParticipantStage.show();
        loginStage.hide();
        loginUsernameTf.clear();
        loginPasswordTf.clear();
    }

    public void setRegistryController(DashboardRegistryAndAdminController ctrl) {
        dashboardRegistryAndAdminController = ctrl;
    }

    public void setParticipantController(DashboardParticipantController ctrl) {
        dashboardParticipantController = ctrl;
    }

    public void setRegistryParent(AnchorPane root) {
        rootRegistryWindow = root;
        Scene scene = new Scene(rootRegistryWindow);
        dashboardRegistryAndAdminStage.setScene(scene);
    }

    public void setParticipantParent(AnchorPane root) {
        rootParticipantWindow = root;
        Scene scene = new Scene(rootParticipantWindow);
        dashboardParticipantStage.setScene(scene);
    }
}

