package gui;

import client.*;

import entity.*;
import gui.MainGUI.SceneType;
import common.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.JOptionPane;//library for popup messages

public class MainGUIController implements ControllerListener {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private AnchorPane MapSearchWindow;
	@FXML
	private TableView<Map> SearchResultsTable;
	@FXML
	private TableColumn<Map, String> col_cityName;
	@FXML
	private TableColumn<Map, String> col_description;
	@FXML
	private TableColumn<Map, String> col_mapsNumber;
	@FXML
	private TableColumn<Map, String> col_sitesNumber;
	@FXML
	private TableColumn<Map, String> col_routesNumber;
//	@FXML
//	private TableColumn<Map, String> col_sitesNumber;
//	@FXML
//	private TableColumn<Map, String> col_version;
    @FXML
    private Button btnEditMaps;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnManage;
	@FXML
	private Button btnMyProfile;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnBuy;
	@FXML
	private TextField tfUser;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private TextField tfCitySearch;
	@FXML
	private TextField tfDesSearch;
	@FXML
	private TextField tfSiteSearch;
	@FXML
	private Text txtMapsCatalog;
	@FXML
    private Button btnInbox;
	@FXML
	private Label lblWelcome;


	/**
	 * @param event making the data to send to the server
	 */
	@FXML
	void Search(ActionEvent event) {
		try {
			Message myMessage;
			String cityName, siteName, mapDescription;
			ArrayList<Object> data = new ArrayList<Object>();
			cityName = tfCitySearch.getText();
			siteName = tfSiteSearch.getText();
			mapDescription = tfDesSearch.getText();
			if (!cityName.isEmpty())
				data.add(cityName);
			else
			   data.add(null);
			
			if (!siteName.isEmpty()) 
				data.add(siteName);
			else
			   data.add(null);
			
			if (!mapDescription.isEmpty())
				data.add(mapDescription);
			else
			   data.add(null);
			myMessage = new Message(Action.SEARCH, data);
			MainGUI.GUIclient.sendToServer(myMessage);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not send message to server. Terminating client.",
					"Error", JOptionPane.WARNING_MESSAGE);
			MainGUI.GUIclient.quit();
		}
	}
	
	

	@FXML
	void Login(ActionEvent event) {
		// handle the event here
//		try {
//			Message myMessage;
			String userName = "", password;
			ArrayList<Object> data = new ArrayList<Object>();
			userName = tfUser.getText();
			password = pfPassword.getText();
			if ((userName != null) && (password != null)) {
				data.add(userName);
				data.add(password);
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect username or password, please try again.", "",
						JOptionPane.INFORMATION_MESSAGE);
				tfUser.setText("");
				pfPassword.setText("");
			}
			GUIClient.sendActionToServer(Action.LOGIN,data);
//			myMessage = new Message(Action.LOGIN, data);
//			MainGUI.GUIclient.sendToServer(myMessage);
//		} catch (IOException e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null,
//					"Could not send message to server.  Terminating client.", "Error",
//					JOptionPane.WARNING_MESSAGE);
//			MainGUI.GUIclient.quit();
//		}
	}
	
	@FXML
	void OpenInbox(ActionEvent event) {
		MainGUI.openScene(MainGUI.SceneType.Inbox);
	}

	@FXML
	void Buy(ActionEvent event) {
		MainGUI.openScene(MainGUI.SceneType.BUY);
	}

	@FXML
	void Logout(ActionEvent event) {
		ArrayList<Object> data = new ArrayList<Object>();
		String userName = MainGUI.currUser.getUserName();
		data.add(userName);
		GUIClient.sendActionToServer(Action.LOGOUT,data);
//		Message myMessage = new Message(Action.LOGOUT, data);
//		try {
//			MainGUI.GUIclient.sendToServer(myMessage);
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e.toString() + "Couldn't send message", "Error",
//					JOptionPane.WARNING_MESSAGE);
//		}
	}

	@FXML
	void Register(ActionEvent event) {
		MainGUI.MainStage.setTitle("Global City Map - Registration");
		MainGUI.openScene(MainGUI.SceneType.REGISTER);
	}

	@FXML
	void MyProfile(ActionEvent event) {
		MainGUI.MainStage.setTitle("Global City Map - My Profile");
		MainGUI.openScene(SceneType.ClientProfile);
	}

	@FXML
	void initialize() {
		setSearchInfoBooleanBinding();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessageFromServer(Object msg) {
		try {
			Message currMsg = (Message) msg;
			switch (currMsg.getAction()) {
			case LOGIN:
				if ((Integer) currMsg.getData().get(0) == 0) {
						tfUser.setVisible(false);
						pfPassword.setVisible(false);
						btnLogin.setVisible(false);
						btnRegister.setVisible(false);
						btnLogout.setVisible(true);
						MainGUI.currUser = (User) currMsg.getData().get(1);
						Permission permission = ((User) currMsg.getData().get(1)).getPermission();
						switch (permission) {
						case CLIENT:
							MainGUI.currClient = (Client) currMsg.getData().get(1);
							Platform.runLater(() -> {
							btnMyProfile.setVisible(true);
							btnEditMaps.setText("Show Maps");
							btnEditMaps.setVisible(true);
							btnBuy.setVisible(true);
							btnInbox.setVisible(true);
							});
							break;
						case EDITOR:
							MainGUI.currEmployee = (Employee) currMsg.getData().get(1);
							Platform.runLater(() -> {
							btnEditMaps.setVisible(true);
							btnBuy.setVisible(false);
							btnManage.setVisible(true);
							btnEditMaps.setText("Edit Maps");
							btnEditMaps.setVisible(true);
							btnBuy.setVisible(false);
							btnInbox.setVisible(true);
							});
							break;
						case MANAGING_EDITOR:
							MainGUI.currEmployee = (Employee) currMsg.getData().get(1);
							Platform.runLater(() -> {
							btnEditMaps.setVisible(true);
							btnBuy.setVisible(false);
							btnManage.setVisible(true);
							btnEditMaps.setText("Edit Maps");
							btnEditMaps.setVisible(true);
							btnBuy.setVisible(false);
							btnInbox.setVisible(true);
							});
							break;
						case CEO:
							MainGUI.currEmployee = (Employee) currMsg.getData().get(1);
							Platform.runLater(() -> {
							btnManage.setVisible(true);
							btnEditMaps.setText("Edit Maps");
							btnEditMaps.setVisible(true);
							btnBuy.setVisible(false);
							btnInbox.setVisible(true);
						});
							break;
						default:
						}
						String name = ((User) currMsg.getData().get(1)).getUserName();
						Platform.runLater(() -> {
							lblWelcome.setText("Welcome " + name + "!");
						});
				} else {
					// System.out.println((currMsg.getData().get(1)).toString());
					JOptionPane.showMessageDialog(null, (currMsg.getData().get(1)).toString(), "",
					JOptionPane.INFORMATION_MESSAGE);
					Platform.runLater(() -> {
						tfUser.setText("");
						pfPassword.setText("");
					});
				}
				break;
			case LOGOUT:
				try {
					Platform.runLater(() -> {
						tfUser.setText("");
						pfPassword.setText("");
						lblWelcome.setText("Welcome");
					});
						tfUser.setVisible(true);
						pfPassword.setVisible(true);
						btnLogin.setVisible(true);
						btnRegister.setVisible(true);
						btnLogout.setVisible(false);
						btnMyProfile.setVisible(false);
						btnManage.setVisible(false);
						btnEditMaps.setVisible(false);
						btnBuy.setVisible(false);
						btnInbox.setVisible(false);
					JOptionPane.showMessageDialog(null, "Disconnected successfully", "Notification",
							JOptionPane.DEFAULT_OPTION);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString() + "The log out failed.", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
				break;
			case SEARCH:
			 if((Integer)currMsg.getData().get(0) == 0) 
			 {
				 HashMap<Integer, String> maps = new HashMap<>();
					maps = (HashMap<Integer, String>) currMsg.getData().get(1);
				 setTableViewForMapsSearchResult(maps);
			 }
				break;
     			default:
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void setTableViewForMapsSearchResult(HashMap<Integer, String> maps) 
	{
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				//Create Map object and insert the Value properties from the search HashMap
				Map<String, String> maps_string  = new HashMap<>();
				for  (Integer currmap  :  maps.keySet())  {
				maps_string.put(maps.get(currmap).split(",")[0], maps.get(currmap).split(",")[1]);
				}
				if (maps.isEmpty()) {
					setTableViewForEmptySearchResult();
				}
				// Create ObservableList of type Map
				ObservableList<Map>  keys  =  FXCollections.observableArrayList();
				// Insert every pair according to the names of columns
				if (maps.keySet().size()==2)
				{
					for  (Integer key  :  maps.keySet())  
					{
					Map<String, String>  m  =  new  HashMap<String, String>();
					m.put("mapDescription",  maps.get(key).split(",")[0]);
					m.put("sitesNumber", maps.get(key).split(",")[1]);
					keys.add(m);
					}
					SearchResultsTable.setVisible(true);
					// Create the columns necessary for the current search  of city
					col_cityName  = new  TableColumn<>("Map Description");
					col_cityName.setCellValueFactory(new MapValueFactory("mapDescription"));
					col_description  =  new  TableColumn<>("Sites Number");
					col_description.setCellValueFactory(new MapValueFactory("sitesNumber"));
				}
				else
				{
					for  (Integer key  :  maps.keySet())  
					{
					Map<String, String>  m  =  new  HashMap<String, String>();
					m.put("mapDescription",  maps.get(key).split(",")[0]);
					m.put("cityName", maps.get(key).split(",")[1]);
					keys.add(m);
					}
					SearchResultsTable.setVisible(true);
					// Create the columns necessary for the current search  of site (or site with other param)
					col_cityName  =  new  TableColumn<>("City Name");
					col_cityName.setCellValueFactory(new MapValueFactory("cityName"));
					col_description  = new  TableColumn<>("Map Description");
					col_description.setCellValueFactory(new MapValueFactory("mapDescription"));

				}

				// Set columns as children of the table view
				SearchResultsTable.getColumns().setAll(col_cityName, col_description/*,col_mapsNumber,col_sitesNumber,col_routesNumber*/);
				// Set the ObservableList<Map> as items
				SearchResultsTable.setItems(keys);
				}
			//}
		});
	}

	/**
	 * Called when there are no search results for maps.
	 * @param msg
	 */
	public void setTableViewForEmptySearchResult() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				SearchResultsTable.setVisible(true);
				SearchResultsTable.getItems().clear();
			}
		});
	}

	@FXML
	void Manage(ActionEvent event) {
		MainGUI.MainStage.setTitle("Global City Map - Users Management");
		MainGUI.openScene(SceneType.ClientsManagement);
	}

	@FXML
    void EditMaps(ActionEvent event) {
		Permission permission = (MainGUI.currUser.getPermission());
		switch(permission) 
		{
			case CLIENT:
			{
				MainGUI.MainStage.setTitle("Global City Map - View Maps");
				break;
			}
			default:
				MainGUI.MainStage.setTitle("Global City Map - Edit Maps");
		}
		MainGUI.openScene(SceneType.Edit);
    }
    
	void setSearchInfoBooleanBinding() {
		BooleanBinding booleanBind;
		booleanBind = (tfCitySearch.textProperty().isEmpty()).and(tfSiteSearch.textProperty().isEmpty()).and(tfDesSearch.textProperty().isEmpty());
		btnSearch.disableProperty().bind(booleanBind);
	}
}
