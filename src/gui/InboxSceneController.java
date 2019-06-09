package gui;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import common.Action;
import common.Message;
import common.Permission;
import common.Status;
import entity.*;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class InboxSceneController implements ControllerListener {
	@FXML
	private TableView<InboxMessage> tblInbox;
	@FXML
	private TableColumn<InboxMessage ,String> clmMessages;
	@FXML
	private TableColumn<InboxMessage, LocalDate> clmDate;
	
	@FXML
	private Pane paneApproveDecline;
	@FXML
	private Button btnApprove;
	@FXML
	private Button btnDecline;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnRefresh;
	
	InboxMessage m_selectedMessage;
	
	List<InboxMessage> m_inboxMessages = List.of(
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.NEW, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.DECLINED, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.APPROVED, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.DECLINED, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.DECLINED, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.APPROVED, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.NEW, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.DECLINED, LocalDate.now()),
			new InboxMessage(0, "reciever", Permission.MANAGING_EDITOR, "reciever", Permission.CEO,
					"Hello there you have a new request here to approve", Status.NEW, LocalDate.now())
			);
	
	private void sendApprovalOrDeclineMessage(boolean isApproved) {
		ArrayList<Object> arrList = new ArrayList<Object>();
		arrList.add(new Boolean(isApproved));
		try {
			MainGUI.GUIclient.sendToServer(new Message(Action.UPDATE_MESSAGE_APPROVAL, arrList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void Approve(ActionEvent event) {
		sendApprovalOrDeclineMessage(true);
	}
	
	@FXML
	public void Decline(ActionEvent event) {
		sendApprovalOrDeclineMessage(false);
	}

	@FXML
	public void Refresh(ActionEvent event) {
		try {
			MainGUI.GUIclient.sendToServer(new Message(Action.GET_INBOX_MESSAGES));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void Close(ActionEvent event) {
		MainGUI.openScene(MainGUI.SceneType.MAIN_GUI);
	}

	@Override
	public void handleMessageFromServer(Object message) {
		Message currMsg = (Message) message;
		switch (currMsg.getAction()) 
		{
			case GET_INBOX_MESSAGES:
				updateTable((List<InboxMessage>) currMsg.getData().get(0));
				break;
		}
		System.out.println("Error - got unsupported action - " + currMsg.getAction());
	}
	
	@FXML
	void initialize() {
		updateTable(m_inboxMessages);
	}
	
	private void updateTable(List<InboxMessage> messages) {
		ObservableList<InboxMessage> observableList = FXCollections.observableArrayList(messages);
		
		tblInbox.setRowFactory(tv -> {
	        TableRow<InboxMessage> row = new TableRow<InboxMessage>() {
		    @Override
		    public void updateItem(InboxMessage item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.getStatus() == Status.APPROVED) {
		            setStyle("-fx-background-color: lightgreen ;");
		        } else if (item.getStatus() == Status.DECLINED) {
		            setStyle("-fx-background-color: lightsalmon;");
		        } else {
		            setStyle("");
		        }
		    }};
        if (MainGUI.currUser.getPermission() != Permission.CLIENT) {
        	row.setOnMouseClicked(e -> {
                if (!row.isEmpty()) {
                	m_selectedMessage = row.getItem();
                    if (m_selectedMessage.getStatus() == Status.NEW) {
                		paneApproveDecline.setVisible(true);
                    } else {
                		paneApproveDecline.setVisible(false);
                    }
                }
            });
        }
        return row;
		});
		
		clmMessages.setCellValueFactory(new PropertyValueFactory<InboxMessage, String>("Content"));
		clmDate.setCellValueFactory(new PropertyValueFactory<InboxMessage, LocalDate>("ReceiveDate"));
		tblInbox.setItems(observableList);
	}
}