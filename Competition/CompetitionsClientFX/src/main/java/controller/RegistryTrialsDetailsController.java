package controller;


import competition.*;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.ResourceBundle;


public class RegistryTrialsDetailsController extends UnicastRemoteObject implements ICompetitionObserver, Initializable {
    TrialDTO trial;

    ICompetitionServices competitionServices; // e proxy
    ObservableList<Participant> modelParticipant = FXCollections.observableArrayList();
    Stage dashboardRegistryStage;
    Stage registryDetailsStage;

    @FXML
    TableView<Participant> trialDetailsParticipantsTv;
    @FXML
    TableColumn<ObservableList<Participation>, Long> trialDetailsParticipantsIdCl;
    @FXML
    TableColumn<ObservableList<Participation>, String> trialDetailsParticipantsUsernameCl;
    @FXML
    TableColumn<ObservableList<Participation>, String> trialDetailsParticipantsFirstNameCl;
    @FXML
    TableColumn<ObservableList<Participation>, String> trialDetailsParticipantsLastNameCl;
    @FXML
    Label trialDetailsIdLb;
    @FXML
    Label trialDetailsTypeLb;
    @FXML
    Label trialDetailsCategoryLb;
    @FXML
    Label trialDetailsMaxLb;
    @FXML
    Label trialDetailsStartsLb;
    @FXML
    Label trialDetailsEndsLb;
    @FXML
    Label trialDetailsRegisteredLb;

    public RegistryTrialsDetailsController() throws RemoteException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trialDetailsParticipantsIdCl.setCellValueFactory(new PropertyValueFactory<>("id"));
        trialDetailsParticipantsUsernameCl.setCellValueFactory(new PropertyValueFactory<>("username"));
        trialDetailsParticipantsFirstNameCl.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        trialDetailsParticipantsLastNameCl.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        trialDetailsParticipantsTv.setItems(modelParticipant);
    }


    public void setAtrributes(ICompetitionServices competitionServices, TrialDTO trial,Stage registryDetailsStage,Stage dashboardRegistryStage) {
        this.competitionServices = competitionServices;
        this.trial = trial;
        this.registryDetailsStage = registryDetailsStage;
        this.dashboardRegistryStage = dashboardRegistryStage;
        initModel();
        initDescription();
    }

    private void initDescription() {
        trialDetailsIdLb.setText(trial.getId().toString());
        trialDetailsTypeLb.setText(trial.getTrialType().toString());
        trialDetailsCategoryLb.setText(trial.getAgeCategory().toString());
        trialDetailsMaxLb.setText(String.valueOf(trial.getMaxNumberOfParticipants()));
        trialDetailsStartsLb.setText(trial.getStartDate().toString());
        trialDetailsEndsLb.setText(trial.getEndDate().toString());
    }

    private void initModel() {
        Iterable<Participant> list = null;
        try {
            list = competitionServices.getAllParticipantsOfTrial(trial.getId());
            if (list instanceof Collection) {
                trialDetailsRegisteredLb.setText(String.valueOf(((Collection<Participant>) list).size()));
            }
        } catch (CompetitionException e) {
            e.printStackTrace();
        }
        modelParticipant.setAll((Collection<? extends Participant>) list);
    }

    @Override
    public void updateTrialsDto() throws RemoteException {
        Platform.runLater( ()->{
            Iterable<Participant> list = null;
            try {
                list = competitionServices.getAllParticipantsOfTrial(trial.getId());
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
            modelParticipant.setAll((Collection<? extends Participant>) list);
        });
    }


}
