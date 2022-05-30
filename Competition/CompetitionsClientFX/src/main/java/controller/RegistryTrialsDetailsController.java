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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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

    Stage addUpdateParticipantStage;
    Parent rootAddUpdateParticipantWindow;
    AddUpdateParticipantController addUpdateParticipantController;

    @FXML
    Button trialDetailsParticipantsAddBt;
    @FXML
    Button trialDetailsParticipantsModifyBt;
    @FXML
    Button trialDetailsParticipantsRemoveBt;


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
        addUpdateParticipantStage=new Stage();
        addUpdateParticipantStage.setTitle("Participant crud");

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

    public void setAddUpdateParticipantParent(AnchorPane root) {
        rootAddUpdateParticipantWindow = root;
        Scene scene = new Scene(rootAddUpdateParticipantWindow);
        addUpdateParticipantStage.setScene(scene);
    }
    public void setAddUpdateParticipantController(AddUpdateParticipantController ctrl) {
        addUpdateParticipantController = ctrl;
    }

    public void addParticipant(){
        addUpdateParticipantController.setAtrributes(competitionServices, new Participant("","","",""), trial);
        addUpdateParticipantStage.show();
    }

    public void removeParticipant(){
        Participant participant = trialDetailsParticipantsTv.getSelectionModel().getSelectedItem();
        if(participant != null){
            try {
                competitionServices.removeParticipantFromTrial(trial, participant);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Participant removed!");
                alert.show();
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select a participant");
            alert.show();
        }
    }

    public void modifyParticipant(){
        Participant participant = trialDetailsParticipantsTv.getSelectionModel().getSelectedItem();
        if(participant != null){
            try {
                competitionServices.modifyParticipantFromTrial(trial, participant);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Participant modified!");
                alert.show();
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select a participant");
            alert.show();
        }
    }


}
