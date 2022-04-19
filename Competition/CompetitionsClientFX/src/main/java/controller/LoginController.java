package controller;

import competition.SystemUser;
import competition.services.CompetitionException;
import competition.services.ICompetitionServices;
import javafx.fxml.FXML;
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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class LoginController extends UnicastRemoteObject implements Serializable {

    Stage loginStage;
    DashboardRegistryAndAdminController dashboardRegistryAndAdminController;
    Parent rootPrincipalWindow;

    @FXML
    PasswordField loginPasswordTf;
    @FXML
    TextField loginUsernameTf;
    @FXML
    Button loginSubmitBt;

    private ICompetitionServices competitionServices; //asta e un proxy

    public LoginController() throws RemoteException {
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
            competitionServices.login(systemUser, dashboardRegistryAndAdminController); //proxy.login()
            openPrincipalWindow(systemUser);
        } catch (CompetitionException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wrong username or password");
            alert.show();
        }


    }

    private void openPrincipalWindow(SystemUser systemUser) {

        Stage dashboardRegistryAndAdminStage = new Stage();
        Scene scene = new Scene(rootPrincipalWindow);
        dashboardRegistryAndAdminStage.setScene(scene);

        dashboardRegistryAndAdminStage.setTitle("@" + systemUser.getUsername());
        dashboardRegistryAndAdminController.setAtrributes(competitionServices, systemUser, dashboardRegistryAndAdminStage, loginStage);
        dashboardRegistryAndAdminStage.show();
        loginStage.hide();
        dashboardRegistryAndAdminStage.setOnCloseRequest(event -> {
//            dashboardRegistryAndAdminController.lo();
            System.exit(0);
        });
        loginUsernameTf.clear();
        loginPasswordTf.clear();


    }

    public void setCompetitionController(DashboardRegistryAndAdminController ctrl1) {
        dashboardRegistryAndAdminController = ctrl1;
    }

    public void setParent(AnchorPane root1) {
        rootPrincipalWindow = root1;
    }
}

