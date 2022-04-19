package controller;


import competition.SystemUser;
import competition.TrialDTO;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.ResourceBundle;


public class DashboardRegistryAndAdminController extends UnicastRemoteObject implements ICompetitionObserver, Initializable {
    SystemUser systemUser;
    ICompetitionServices competitionServices; // e proxy
    ObservableList<TrialDTO> modelTrialDTO = FXCollections.observableArrayList();

    Stage dashboardRegistryAndAdminStage;
    Stage loginStage;

    @FXML
    TableView<TrialDTO> dashboardRegistryVisualizeTrialsTv;

    public DashboardRegistryAndAdminController() throws RemoteException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //listViewTrialsDTO.setItems(modelTrialDTO);
    }


    public void setAtrributes(ICompetitionServices competitionServices, SystemUser systemUser, Stage dashboardRegistryAndAdminStage, Stage loginStage) {
        this.competitionServices = competitionServices;
        this.systemUser = systemUser;
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
}
