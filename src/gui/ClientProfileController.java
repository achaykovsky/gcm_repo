package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import common.Action;
import common.Message;
import common.Permission;
import entity.Client;
import entity.User;
import gui.MainGUI.SceneType;
import entity.Map;
import entity.Purchase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;


	
public class ClientProfileController implements ControllerListener {

GUIClient client;

    @FXML 
    private ResourceBundle resources;
    @FXML 
    private URL location;
    @FXML 
    private AnchorPane ClientProfileWindow;
    @FXML 
    private TabPane TabPaneMyProfile;
    @FXML
    private Button btnMain;
    @FXML 
    private Button btnSave;
    @FXML
    private Button btnSavePayment;
    @FXML
    private Text lblCardNumber;
    @FXML
    private Text lblEmail;
    @FXML
    private Text lblExpiryDate;
    @FXML
    private Text lblFirstName;
    @FXML
    private Text lblIDnumber;
    @FXML
    private Text lblLastName;
    @FXML
    private Label lblMyProfile;
    @FXML
    private Text lblPassword;
    @FXML
    private Text lblTelephone;
    @FXML
    private Text lblUserName;
    @FXML
    private Label lblWelcome;
    @FXML
    private Tab tabPaymentDetails;
    @FXML
    private Tab tabPersonalInfo;
    @FXML
    private Tab tabPurchases;
    @FXML
    private TextField tfCreditCard1;
    @FXML
    private TextField tfCreditCard2;
    @FXML
    private TextField tfCreditCard3;
    @FXML
    private TextField tfCreditCard4;
    @FXML
    private TextField tfCurrLastNumbers;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfExpiryDate;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfIDNumber;
    @FXML
    private TextField tfLastName;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfphone;
    @FXML
    private Button btnWatchMap;
    @FXML
    private TableColumn<Purchase, String> col_cityName;
    @FXML
    private TableColumn<Purchase, String>col_expiryDate;
    @FXML
    private TableColumn<Purchase, String> col_price;
    @FXML
    private TableColumn<Purchase, String> col_purchaseDate;
    @FXML
    private TableColumn<Purchase, String> col_purchaseType;
    @FXML
    private TableView<Purchase> purchasesTable;
    @FXML
    private RadioButton rbChangeCreditNumber;
    @FXML
    private RadioButton rbtnPreviousCreditCard;

	/**
	 *
	 *	Sets listener to specific TextField - Enable write on textField only digits Input.
	 *
	 * @param textField - textField.
	 */
	@FXML
	void Save(ActionEvent event) 
	{
		try {
			Message myMessage;
			String userName = MainGUI.currClient.getUserName();
			String firstName = MainGUI.currClient.getFirstName();
			String lastName = MainGUI.currClient.getLastName();
			String email = MainGUI.currClient.getEmail();
			String password = MainGUI.currClient.getPassword();
			long telephone = MainGUI.currClient.getTelephone();
			byte[] salt = MainGUI.currClient.getSalt();
			String	permission = "Client";
			long cardNumber = MainGUI.currClient.getCardNumber();
			long id =MainGUI.currClient.getID(); 
			LocalDate expiryDate = MainGUI.currClient.getExpiryDate() ;
			String fullCardString;
			firstName = tfFirstName.getText();
			lastName = tfLastName.getText();
			userName = tfUserName.getText();
			password = tfPassword.getText();
			if (password == null)
				password = MainGUI.currClient.getPassword();
			email = tfEmail.getText();
			telephone = (tfphone.getText().length() <= 10) ?
					Long.parseLong(tfphone.getText()) : 0L;
			if (!isValid(email)) 
			{
				JOptionPane.showMessageDialog(null, "The email address is invalid", "Error",
				JOptionPane.INFORMATION_MESSAGE);
			}
			if(rbChangeCreditNumber.isSelected())
			{
				//setCreditCardBooleanBinding();
				setInputVerification();
				id = (tfIDNumber.getText().length() <= 9) ?
						Long.parseLong(tfIDNumber.getText()) : 0L;
				fullCardString = tfCreditCard1.getText() + tfCreditCard2.getText() + tfCreditCard3.getText()
				+ tfCreditCard4.getText();						
				cardNumber = (fullCardString.length() >= 17) 
						? 0L : Long.parseLong(fullCardString);	
			}
				//cardNumber =  Long.parseLong(fullCardString);
//				try 
//				{
//					expiryDate = tfExpiryDate.getText();
//					// Validate date is a valid string
//					LocalDate.parse(expiryDate);
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, "Date invalid - " + tfExpiryDate.getText(), "Update Information error",
//							JOptionPane.INFORMATION_MESSAGE);
//					return;
//			}
		
				myMessage = new Message(Action.EDIT_USER_DETAILS, firstName,lastName,userName,password,salt,email,permission,
						telephone,cardNumber,id,expiryDate,userName);
				client.sendToServer(myMessage);
			//}
		//}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					e.toString() + " Could not send message to server.  Terminating client.", "Error",
					JOptionPane.WARNING_MESSAGE);
			client.quit();
		}
	}
	/**
	 *
	 *method to disable the option to change the credit card details
	 *
	 */
    @FXML
    void samePaymentMethod(ActionEvent event) {
    	rbChangeCreditNumber.setSelected(false);
    	rbtnPreviousCreditCard.setSelected(true);
    	setCreditCardBooleanBinding();
    	tfIDNumber.setDisable(true);
    	tfExpiryDate.setDisable(true);
    	tfCreditCard1.setDisable(true);
    	tfCreditCard2.setDisable(true);
    	tfCreditCard3.setDisable(true);
    	tfCreditCard4.setDisable(true);
    }
	/**
	 *
	 *method to enable the option to change the credit card details
	 * 
	 */
    @FXML
    void changeCreditCard(ActionEvent event) {
    	rbtnPreviousCreditCard.setSelected(false);
    	rbChangeCreditNumber.setSelected(true);
    	setCreditCardBooleanBinding();
    	tfIDNumber.setDisable(false);
    	tfExpiryDate.setDisable(false);
    	tfCreditCard1.setDisable(false);
    	tfCreditCard2.setDisable(false);
    	tfCreditCard3.setDisable(false);
    	tfCreditCard4.setDisable(false);
    	//BooleanBinding booleanBind;
    	//booleanBind = (tfIDNumber.disabledProperty()).and(tfExpiryDate.disabledProperty()).and(tfCreditCard1.disabledProperty()).and(tfCreditCard2.disabledProperty()).and(tfCreditCard3.disabledProperty()).and(tfCreditCard4.disabledProperty());
    	//rbChangeCreditNumber.selectedProperty().bind(booleanBind);
    }
	/**
	 *
	 *method to check the validation of the email address
	 *
	 *
	 */

	static boolean isValid(String email) {
		   String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		   return email.matches(regex);
	}
	/**
	 *set the initial details of the user in the fields
	 *
	 *
	 *
	 */
	@FXML
	void initialize() {
		lblWelcome.setText("Welcome " + MainGUI.currUser.getUserName() + "!");
		//setRadioButtonGroup();
		setPersonalInfoBooleanBinding();
		sendRequestToServer(MainGUI.currClient.getUserName());
		String telephoneAsString = String.valueOf(MainGUI.currClient.getTelephone());
		long lastFourDigitsLong=Math.abs(MainGUI.currClient.getCardNumber())%10000;
		int lastFourDigits=Math.toIntExact(lastFourDigitsLong);
		String LastFourDigitsString=String.valueOf(lastFourDigits);
		Permission permission = (MainGUI.currClient.getPermission());
				switch(permission) 
				{
					case CLIENT:
					{
						tfUserName.setText(MainGUI.currClient.getUserName());
						tfFirstName.setText(MainGUI.currClient.getFirstName());
						tfLastName.setText(MainGUI.currClient.getLastName());
						tfEmail.setText(MainGUI.currClient.getEmail());
						tfphone.setText(telephoneAsString);
						tfCurrLastNumbers.setText(LastFourDigitsString);
						break;
					}
					case CEO:
					{
						//lblMyProfile.setText(MainGUI.currClient.getUserName() + "'s Profile");
						tfUserName.setEditable(false);
						tfFirstName.setEditable(false);
						tfLastName.setEditable(false);
						tfEmail.setEditable(false);
						tfphone.setEditable(false);
						btnSave.setVisible(false);
						break;
					}
					default:
				}
	}
	
	void sendRequestToServer(String userName) {
		Message myMessage = new Message(Action.GET_USER_PURCHASES,userName);
		try {
			MainGUI.GUIclient.sendToServer(myMessage);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString() + "Couldn't send message", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void setRadioButtonGroup() {
		final ToggleGroup radioButtonGroup = new ToggleGroup();
		rbtnPreviousCreditCard.setToggleGroup(radioButtonGroup);
		rbChangeCreditNumber.setToggleGroup(radioButtonGroup);
	}
	
//	rbChangeCreditNumber.setOnAction(new EventHandler<ActionEvent>() {
//		 @Override
//		 public void handle(ActionEvent actionEvent) {
//		     if(rbChangeCreditNumber.isSelected()){
//		    	 setCreditCardBooleanBinding();
//		     }else{
//		          //textField.setEnabled(false);
//		     }
//		   }
//		}
//	}}

	/**
	 *Loading the Main GUI
	 *	
	 *
	 * 
	 */
    @FXML
    void backToMainGUI(ActionEvent event) {
		MainGUI.MainStage.setTitle("Global City Map");
    	MainGUI.openScene(SceneType.MAIN_GUI);
    }
	/**
	 *handling the messages from the server
	 *	
	 *
	 *
	 */
	@Override
	public void handleMessageFromServer(Object msg) {
		Message currMsg = (Message) msg;
		switch (currMsg.getAction()) 
		{
		case EDIT_USER_DETAILS:
			if ((Integer) currMsg.getData().get(0) == 0) 
				JOptionPane.showMessageDialog(null, "All the changes have been saved succesfully", "Notification",
						JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(null, (currMsg.getData().get(1)).toString(), "Error",
						JOptionPane.WARNING_MESSAGE);
		break;
		case GET_USER_PURCHASES:
			if ((Integer) currMsg.getData().get(0) == 0) 
			{
		    	ArrayList<Purchase> purchases = (ArrayList<Purchase>) currMsg.getData().get(1);
				ObservableList<Purchase> currPurchasesList = FXCollections.observableArrayList(purchases);
				setTableViewForPurchases(currPurchasesList);
			}
			else 
				JOptionPane.showMessageDialog(null, (currMsg.getData().get(1)).toString(), "Error",
						JOptionPane.WARNING_MESSAGE);
		break;
		default:
		}
	}
	/**
	 *
	 *sets the table for maps that the user purchased
	 *
     */
	
	public void setTableViewForPurchases(ObservableList<Purchase> currPurchasesList) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				col_cityName.setCellValueFactory(new PropertyValueFactory<Purchase,String>("City"));
				col_purchaseType.setCellValueFactory(new PropertyValueFactory<Purchase,String>("Purchase Type"));
				col_purchaseDate.setCellValueFactory(new PropertyValueFactory<Purchase,String>("Purchase Date"));
				col_expiryDate.setCellValueFactory(new PropertyValueFactory<Purchase,String>("Expiry Date"));
				col_price.setCellValueFactory(new PropertyValueFactory<Purchase,String>("Price"));

				//purchasesTable.getColumns().addAll(col_cityName, col_purchaseType, col_purchaseDate,col_expiryDate,col_price);
				purchasesTable.setItems(currPurchasesList);
			}
		});
	}

    @FXML
    void Watch(ActionEvent event) {
    }
	

	/**
	 *
	 *If the user wants to change his details.
	 *
	 *Sets boolean binding on the New Credit Card details for the empty property
	 *
	 *
	 */
	void setCreditCardBooleanBinding() {
		BooleanBinding booleanBind;
		booleanBind = (tfIDNumber.textProperty().isEmpty()).or(tfExpiryDate.textProperty().isEmpty()).or(tfCreditCard1.textProperty().isEmpty()).or(tfCreditCard2.textProperty().isEmpty()).or(tfCreditCard3.textProperty().isEmpty()).or(tfCreditCard4.textProperty().isEmpty());
		btnSave.disableProperty().bind(booleanBind);
		if(rbtnPreviousCreditCard.isSelected())
			btnSave.disableProperty().unbind();
	}
	/**
	 *
	 *Sets boolean binding to the Personal Info of the Client.
	 *
	 * @param Button Save - Button.
	 */
	void setPersonalInfoBooleanBinding() {
		BooleanBinding booleanBind;
		booleanBind = (tfFirstName.textProperty().isEmpty()).or(tfLastName.textProperty().isEmpty()).or(tfEmail.textProperty().isEmpty()).or(tfphone.textProperty().isEmpty());
		btnSave.disableProperty().bind(booleanBind);
	}

	/**
	 *
	 *	Sets listener to specific TextField - Enable write on textField only digits Input.
	 *
	 * @param textField - textField.
	 */
	void listenerForOnlyDigitsInput(TextField textField) 
	{
		textField.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() 
		{
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				if (!newValue.matches("\\d*")) 
				{
					textField.setText(newValue.replaceAll("[^\\d]", ""));
					JOptionPane.showMessageDialog(null, "\"*Please enter only digits from 0 to 9.\"", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			};
		});
	}
	
	void setInputVerification() {
		//setPersonalInfoBooleanBinding();
		//setPaymentRadioButtonsGroup();
		listenerForOnlyDigitsInput(tfphone);
		listenerForOnlyDigitsInput(tfIDNumber);
		listenerForOnlyDigitsInput(tfCreditCard1);
		listenerForOnlyDigitsInput(tfCreditCard2);
		listenerForOnlyDigitsInput(tfCreditCard3);
		listenerForOnlyDigitsInput(tfCreditCard4);
	}
}


