package gui;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import com.google.protobuf.TextFormat.ParseException;

import common.Action;
import common.Message;
import common.Permission;
import entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RegisterController implements ControllerListener {

	GUIClient client;
	


	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private AnchorPane RegisterWindow;
	@FXML
	private Text lblCardNumebr;
	@FXML
	private Text lblExpiryDate;
	@FXML
	private Text lblIDnumber;
	@FXML
	private Label lblRegistrationForm;
	@FXML
	private TextField tfCreditCard1;
	@FXML
	private TextField tfCreditCard2;
	@FXML
	private TextField tfCreditCard3;
	@FXML
	private TextField tfCreditCard4;
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

	void setGUIClient(GUIClient client) {
		this.client = client;
		client.addControllerListener(this);
	}

	@FXML
	void Register(ActionEvent event) {
		// handle the event here
		try {
			Message myMessage;
			String firstName = null, lastName = null, userName = null, password = null, email = null,
					permission = "CLEINT";
			long telephone = 0L, cardNumber = 0L, id = 0L;
			String expireDate, fullCardNum = tfCreditCard1.getText() + tfCreditCard2.getText()
					+ tfCreditCard3.getText() + tfCreditCard4.getText();

			ArrayList<Object> data = new ArrayList<Object>();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			firstName = tfFirstName.getText();
			lastName = tfLastName.getText();
			userName = tfUserName.getText();
			password = tfPassword.getText();
			email = tfEmail.getText();
			telephone = (tfphone.getText().isBlank()) ? 0L : Long.parseLong(tfphone.getText());
			cardNumber = (fullCardNum.equals("")) ? 0L : Long.parseLong(fullCardNum);
			id = (tfIDNumber.getText().equals("")) ? 0L : Long.parseLong(tfIDNumber.getText());
			
			try {
				expireDate = tfExpiryDate.getText();
				// Validate date is a valid string
				expireDate= dtf.parse(expireDate).toString();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Date invalid - ", tfExpiryDate.getText(),
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if ((!userName.isBlank()) && (!firstName.isBlank()) && (!lastName.isBlank())
					&& (!password.isBlank()) && (!email.isBlank())
					&& (telephone != 0L) && (cardNumber != 0L) && (!expireDate.isBlank()) && (id != 0L)) {
				data.add(firstName);
				data.add(lastName);
				data.add(userName);
				data.add(password);
				data.add(email);
				data.add(permission);
				data.add(telephone);
				data.add(cardNumber);
				data.add(id);
				data.add(expireDate);
				myMessage = new Message(Action.REGISTER, data);
				client.sendToServer(myMessage);
			} else {
				JOptionPane.showMessageDialog(null, "One or more of the fields are incorrect, please try again", "",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					e.toString() + " Could not send message to server.  Terminating client.", "Error",
					JOptionPane.WARNING_MESSAGE);
			// quit();
		}
	}

	@FXML
	void initialize() {
	}

	@Override
	public void handleMessageFromServer(Object msg) {
		Message currMsg = (Message) msg;
		if ((Integer) currMsg.getData().get(0) == 0) {
			JOptionPane.showMessageDialog(null, "Registration completed successfully", "",
					JOptionPane.INFORMATION_MESSAGE);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainGUIScene.fxml"));
			MainGUIController controller = loader.getController();
			controller.setGUIClient(client);
			Platform.exit();
		} 
		
		else {
			JOptionPane.showMessageDialog(null, (currMsg.getData().get(1)).toString(), "",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

}
