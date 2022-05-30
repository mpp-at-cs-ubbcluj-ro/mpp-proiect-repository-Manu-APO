package controller;


import competition.Participant;
import competition.Participation;
import competition.TrialDTO;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.ResourceBundle;


public class AddUpdateParticipantController extends UnicastRemoteObject implements ICompetitionObserver, Initializable {
    Participant participant;
    TrialDTO trial;

    ICompetitionServices competitionServices; // e proxy

    @FXML
    TextField usernameTF;
    @FXML
    PasswordField passwordTF;
    @FXML
    TextField firstNameTF;
    @FXML
    TextField lastNameTF;

    public AddUpdateParticipantController() throws RemoteException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setAtrributes(ICompetitionServices competitionServices, Participant participant,TrialDTO trial) {
        this.competitionServices = competitionServices;
        this.participant = participant;
        this.trial = trial;
        initDescription();
    }

    private void initDescription() {
        usernameTF.setText(participant.getUsername());
        passwordTF.setText(participant.getPassword());
        firstNameTF.setText(participant.getFirstName());
        lastNameTF.setText(participant.getLastName());
    }

    @Override
    public void updateTrialsDto() throws RemoteException {

    }

    public void addParticipant(){

        participant = new Participant(usernameTF.getText(),passwordTF.getText(),firstNameTF.getText(),lastNameTF.getText());

        try {
            competitionServices.addParticipantForTrial(trial, participant);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Participant added!");
            alert.show();
        } catch (CompetitionException e) {
            e.printStackTrace();
        }
    }

}
