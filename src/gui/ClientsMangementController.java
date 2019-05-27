package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import common.Action;
import common.Message;
import entity.Client;
import entity.Map;
import gui.MainGUI.SceneType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ClientsMangementController implements ControllerListener {
	
GUIClient client;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane ClientsMangementWindow;
    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> col_Email;
    @FXML
    private TableColumn<Client, String> col_FirstName;
    @FXML
    private TableColumn<Client, String> col_LastName;
    @FXML
    private TableColumn<Client, String> col_Purchases;
    @FXML
    private TableColumn<Client, String> col_Telephone;
    @FXML
    private TableColumn<Client, String> col_UserName;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnMain;
    @FXML
    private Button btnShowDetails;
    @FXML
    private Label lblWelcome;


    @FXML
    void LogOut(ActionEvent event) {
		ArrayList<Object> data = new ArrayList<Object>();
		String userName = MainGUI.currClient.getUserName();
		data.add(userName);
		Message myMessage = new Message(Action.LOGOUT,data);
		try {
			MainGUI.GUIclient.sendToServer(myMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainGUI.openScene(SceneType.MAIN_GUI);
    }

    @FXML
    void backToMainGUI(ActionEvent event) {
		MainGUI.openScene(SceneType.MAIN_GUI);
    }

    @FXML
    void ShowDetails(ActionEvent event) {
		MainGUI.openScene(MainGUI.SceneType.ClientProfile);
    }

    @FXML
    void initialize() {
    	//setTableViewForClients(clients);
		lblWelcome.setText("Welcome " + MainGUI.currClient.getUserName() + "!");
    }

	@Override
	public void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		Message currMsg = (Message) msg;
		switch (currMsg.getAction()) {
		case EDIT_USER_DETAILS:
			if ((Integer) currMsg.getData().get(0) == 0) {
				JOptionPane.showMessageDialog(null, "All the changes have been saved succesfully", "",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(null, (currMsg.getData().get(1)).toString(), "",
						JOptionPane.WARNING_MESSAGE);
			}
		case LOGOUT:
			if ((Integer) currMsg.getData().get(0) == 0) {
				JOptionPane.showMessageDialog(null, "You are now logged out of the system.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(null, (currMsg.getData().get(1)).toString(), "",
						JOptionPane.WARNING_MESSAGE);
			}
		
	}
		
		
	}
     
	public void setTableViewForClients(ArrayList<Client> clients) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				ObservableList<Client> clientsList = FXCollections.observableArrayList();
				col_UserName.setCellValueFactory(new PropertyValueFactory<Client, String>("userName"));
				col_FirstName.setCellValueFactory(new PropertyValueFactory<Client, String>("FirstName"));
				col_LastName.setCellValueFactory(new PropertyValueFactory<Client, String>("LastName"));
				col_Telephone.setCellValueFactory(new PropertyValueFactory<Client, String>("Telephone"));
				col_Email.setCellValueFactory(new PropertyValueFactory<Client, String>("Email"));
				col_Purchases.setCellValueFactory(new PropertyValueFactory<Client, String>("Purchases"));

				clientsTable.getColumns().addAll(col_UserName, col_FirstName, col_LastName,col_Telephone,col_Email,col_Purchases);
				clientsTable.setItems(clientsList);
			}
		});
	}

}

