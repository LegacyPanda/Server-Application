package legecy.panda;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

/**
 * Responsible for the gui set up and button actions
 */
public class Display extends VBox
{
  private Button btnConnect, btnDisconnect, btnCommunicate;
  private TextField txtInput;
  private TextArea txtOutput;
  private Label lblInput, lblOutput;
  private Client client;

  /**
   * Initialize setup and actions
   */
  public Display()
  {
    setUp();
    actions();
  }

  /**
   * Set up the gui
   */
  private void setUp()
  {
    lblInput = new Label("Enter response");
    lblOutput = new Label("Server Response");

    txtInput = new TextField();
    txtOutput = new TextArea();
    txtOutput.setEditable(false);

    btnCommunicate = new Button("Communicate");
    btnConnect = new Button("Connect");
    btnDisconnect = new Button("Disconnect");

    setSpacing(10);

    getChildren().addAll(btnConnect, lblInput, txtInput, btnCommunicate, lblOutput, txtOutput, btnDisconnect);
  }

  /**
   * Declare the actions
   */
  private void actions() 
  {
    btnConnect.setOnAction(e -> 
    {
      try 
      {
        Socket socket = new Socket("localhost", 7777);
        client = new Client(socket, this); // pass Display for GUI updates
        new Thread(client).start();
      }catch(IOException ex) 
      {
        txtOutput.setText("Failed to connect: " + ex.getMessage());
      }
    });

    /**
     * Button to disconnect to server
     */
    btnCommunicate.setOnAction(e -> 
    {
      if(client != null) 
      {
        String input = txtInput.getText();
        client.sendMessage("ANS "  + input);
        txtInput.clear();
      }
    });

    btnDisconnect.setOnAction(e -> 
    {
      if(client != null) 
      {
        client.disconnect();
        txtOutput.setText("Disconnected");
      }
    });
  }

  /**
   * Server response
   * @param message String from server
   */
  public void addMessage(String message) 
  {
    Platform.runLater(() -> txtOutput.setText(message));
  }
  
}