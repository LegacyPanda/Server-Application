package legecy.panda;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;
import java.net.ServerSocket;

/**
 * Server to respond to client
 */
public class Server
{
  private Socket socket;
  private ServerSocket server;
  private boolean running;
  private int attempts;

  /**
   * Processes in the server
   */
  public Server()
  {
    try
    {
      this.server = new ServerSocket(7777);
      this.socket = server.accept();

      BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

      out.println("HOWZIT");
      out.flush();

      String response = "";

      while((response = in.readLine()) != null)
      {
        if(response.equals("SUPER"))
        {
          running = true;
          attempts = 0;
          String question = "ANSWER the Question:/’How do you say Happy Birthday in South Africa?/’ or CHEERS";
          out.println(question);
          out.flush();
        }else if(response.startsWith("ANS") && running)
        {
          attempts++;
          String clientAnswer = response.substring(4).trim(); // remove "ANS "
          String reply = attempts + "# " + response(clientAnswer);
          out.println(reply);
          out.flush();

          if(attempts == 4)
          {
            String bye = "HAMBA KAHLE - you had 4 chances";
            out.println(bye);
            out.flush();
          }
        }else if(response.equals("CHEERS"))
        {
          String bye = "0# OK BYE - " + attempts + " answers provided";
          out.println(bye);
          out.flush();
          break;
        }else
        {
          out.println("Invalid option");
          out.flush();
        }
      }
    }catch(IOException ex)
    {
      ex.getStackTrace();
    }finally
    {
      try
      {
        if(server != null) server.close();
        if(socket != null) socket.close();
      }catch(IOException ex)
      {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Appropriate response to client
   * @param answer Client response
   * @return
   */
  private String response(String answer)
  {
    answer= answer.toLowerCase();
    String ans = "";
    if(answer.equals("gelukkige") || answer.equals("verjaarsdag"))
    {
      ans = "Baie dankie";
    }else if(answer.startsWith("happy"))
    {
      String[] responses = {"Thanks", "Hooray", "To You!"};
      ans = responses[new Random().nextInt(responses.length)];
    }else if(answer.contains("usuku olumnandi") || answer.contains("lokuzalwa"))
    {
      ans = "Yebo - kuhle";
    }else
    {
      String[] responses = {"Escusez-moi?", "Oh ok!", "Nein"};
      ans = responses[new Random().nextInt(responses.length)];
    }

    return ans;
  }
}
