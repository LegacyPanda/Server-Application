import legecy.panda.Server;
import legecy.panda.Display;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Setting up the client
 */
public class Main extends Application
{
  /**
   * JavaDoc shut up
   */
   public Main(){}

   /**
    * Client Gui
    * @param args cmd inputs
    */
  public static void main(String[] args)
  {
    Application.launch(args);
  }

  /**
   * Begin the gui
   * @param stage Stage to the gui
   * @throws Exception Errors 
   */
  @Override
  public void start(Stage stage) throws Exception
  {
    Display display = new Display();
    Scene scene = new Scene(display, 700, 500);
    stage.setScene(scene);
    stage.setTitle("Server");
    stage.show();

    // Run the server in its own thread as to not freeze the gui
    new Thread(() -> new Server()).start();

  }
}