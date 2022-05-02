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


public class DashboardRegistryAndAdminController extends UnicastRemoteObject implements ICompetitionObserver, Initializable {
    Registry registry;

    ICompetitionServices competitionServices; // e proxy
    ObservableList<TrialDTO> modelTrialDTO = FXCollections.observableArrayList();

    Stage dashboardRegistryAndAdminStage;
    Stage loginStage;

    @FXML
    TableView<TrialDTO> dashboardRegistryVisualizeTrialsTv;
    @FXML
    TableColumn<ObservableList<TrialDTO>, Long> dashboardRegistryVisualizeTrialsIdCl;
    @FXML
    TableColumn<ObservableList<TrialDTO>, TRIAL_TYPE> dashboardRegistryVisualizeTrialsTypeCl;
    @FXML
    TableColumn<ObservableList<TrialDTO>, AGE_CATEGORY> dashboardRegistryVisualizeTrialsCategoryCl;
    @FXML
    TableColumn<ObservableList<TrialDTO>, Integer> dashboardRegistryVisualizeTrialsAvailableCl;
    @FXML
    TableColumn<ObservableList<TrialDTO>, Date> dashboardRegistryVisualizeTrialsStartsCl;
    @FXML
    TableColumn<ObservableList<TrialDTO>, Date> dashboardRegistryVisualizeTrialsEndsCl;

    public DashboardRegistryAndAdminController() throws RemoteException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardRegistryVisualizeTrialsIdCl.setCellValueFactory(new PropertyValueFactory<>("id"));
        dashboardRegistryVisualizeTrialsTypeCl.setCellValueFactory(new PropertyValueFactory<>("trialType"));
        dashboardRegistryVisualizeTrialsCategoryCl.setCellValueFactory(new PropertyValueFactory<>("ageCategory"));
        dashboardRegistryVisualizeTrialsAvailableCl.setCellValueFactory(new PropertyValueFactory<>("maxNumberOfParticipants"));
        dashboardRegistryVisualizeTrialsStartsCl.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        dashboardRegistryVisualizeTrialsEndsCl.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        dashboardRegistryVisualizeTrialsTv.setItems(modelTrialDTO);
    }


    public void setAtrributes(ICompetitionServices competitionServices, Registry registry, Stage dashboardRegistryAndAdminStage, Stage loginStage) {
        this.competitionServices = competitionServices;
        this.registry = registry;
        this.dashboardRegistryAndAdminStage = dashboardRegistryAndAdminStage;
        this.loginStage = loginStage;
        initModel();
    }

    private void initModel() {
        Iterable<TrialDTO> list = null;
        try {
            list = competitionServices.getAllTrialsDTO();
        } catch (CompetitionException e) {
            e.printStackTrace();
        }
        modelTrialDTO.setAll((Collection<? extends TrialDTO>) list);
        // initModelParticipantDTO();
    }

    @Override
    public void updateTrialsDto() throws CompetitionException, RemoteException {
        Platform.runLater( ()->{
            Iterable<TrialDTO> list = null;
            try {
                list = competitionServices.getAllTrialsDTO();
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
            modelTrialDTO.setAll((Collection<? extends TrialDTO>) list);
        });
    }

    public void logOut() {
        try{
            competitionServices.logoutRegistry((Registry) registry,this);
        } catch (Exception e) {
            System.err.println("Logout error "+e);
        }
        //principalStage.hide();
    }
}
