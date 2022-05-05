package controller;


import competition.*;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;


public class DashboardParticipantController extends UnicastRemoteObject implements ICompetitionObserver, Initializable {
    Participant participant;

    ICompetitionServices competitionServices; // e proxy
    ObservableList<Participation> modelParticipation = FXCollections.observableArrayList();

    Stage dashboardParticipantStage;
    Stage loginStage;

    @FXML
    TableView<Participation> visualizeTrialsTV;
    @FXML
    TableColumn<ObservableList<Participation>, Long> idCL;
    @FXML
    TableColumn<ObservableList<Participation>, TRIAL_TYPE> typeCL;
    @FXML
    TableColumn<ObservableList<Participation>, Date> startsCL;
    @FXML
    TableColumn<ObservableList<Participation>, Date> endsCL;
    @FXML
    TableColumn<ObservableList<Participation>, Date> submissionCL;

    @FXML
    Button dashboardParticipantLogoutBt;
    @FXML
    Label dashboardParticipantUsernameLb;

    public DashboardParticipantController() throws RemoteException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCL.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCL.setCellValueFactory(new PropertyValueFactory<>("trialType"));
        startsCL.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endsCL.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        submissionCL.setCellValueFactory(new PropertyValueFactory<>("dateOfSubmission"));

        visualizeTrialsTV.setItems(modelParticipation);
    }


    public void setAtrributes(ICompetitionServices competitionServices, Participant participant, Stage dashboardParticipantStage, Stage loginStage) {
        this.competitionServices = competitionServices;
        this.participant = participant;
        this.dashboardParticipantStage = dashboardParticipantStage;
        this.loginStage = loginStage;
        initModel();
    }

    private void initModel() {
        dashboardParticipantUsernameLb.setText("@"+participant.getUsername());

        Iterable<Participation> list = null;
        try {
            list = competitionServices.getAllUserParticipation();
        } catch (CompetitionException e) {
            e.printStackTrace();
        }
        modelParticipation.setAll((Collection<? extends Participation>) list);
    }

    @Override
    public void updateTrialsDto() throws CompetitionException, RemoteException {
        Platform.runLater( ()->{
            Iterable<Participation> list = null;
            try {
                list = competitionServices.getAllUserParticipation();
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
            modelParticipation.setAll((Collection<? extends Participation>) list);
        });
    }

    public void logOutAndSwitchToLogin() {
        try{
            competitionServices.logoutParticipant(participant,this);
        } catch (Exception e) {
            System.err.println("Logout error "+e);
        }
        dashboardParticipantStage.hide();
        loginStage.show();
    }

    public void logOut(){
        try{
            competitionServices.logoutParticipant(participant,this);
        } catch (Exception e) {
            System.err.println("Logout error "+e);
        }
    }
}
