package legecy.panda;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Client that sends the responses
 */
public class Client implements Runnable
{
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private Display display;

  /**
   * Set up the socket and display
   * @param socket Socket to the server
   * @param display Display of the gui
   */
  public Client(Socket socket, Display display)
  {
    this.socket = socket;
    this.display = display;
  }
  
  /**
   * Interaction with the server
   */
  @Override
  public void run()
  {
    try
    {
      out = new PrintWriter(this.socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

      out.println("SUPER");
      out.flush();
      String server = "";

      while((server = in.readLine()) != null)
      {
        display.addMessage(server);
      }
    }catch(IOException ex)
    {
      ex.printStackTrace();
    }finally
    {
      disconnect();
    }
  }

  /**
   * User sent messages
   * @param msg Users message
   */
  public void sendMessage(String msg)
  {
    if(out != null)
    {
      out.println(msg);
      out.flush();
    }
  }

  /**
   * Server response if there exists one
   * @param response Byte response of server
   * @return String from server 
   */
  public String getResponse(BufferedReader response)
  {
    try
    {
      if(in != null)
      {
        String line = in.readLine();
        return line;
      }
    }catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return "";
  }

  /**
   * Disconnet to the server
   */
  public void disconnect() 
  {
    try 
    {
        if(out != null) out.close();
        if(in != null) in.close();
        if(socket != null && !socket.isClosed()) socket.close();
    }catch(IOException ex) 
    {
        ex.printStackTrace();
    }
}

}
