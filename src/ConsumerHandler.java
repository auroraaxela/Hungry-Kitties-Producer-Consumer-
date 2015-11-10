//ConsumerHandler is a thread spawned by the Server to open a TCP connection
//and communicate with an individual client.

/**
 *
 * @author: Chris Benda
 */
import java.io.*;
import java.net.*;

public class ConsumerHandler extends Thread
{
	private Socket clientSocket = null;
	String messageFromClient, messageToClient, message = null;
	PrintWriter outMessagePrintWriter;
	BufferedReader inMessageBufferedReader;
        Resource burgerBox;
        boolean again = true;
	
	public ConsumerHandler(Socket socket, Resource box) ///constructor takes
        {                                                   ///the resource box as
            super();                                        ///as an argument
            clientSocket = socket;
            burgerBox = box;
	}
	
	public void run() 
        {
            try 
            {
                while(again)   ///Input from the client makes the loop run again or close
                {                       
                    outMessagePrintWriter = new PrintWriter(
                                            clientSocket.getOutputStream(), true);
                    inMessageBufferedReader = new BufferedReader(new InputStreamReader(
                                                clientSocket.getInputStream()));
				
                    while((messageFromClient = inMessageBufferedReader.readLine()) != null) 
                    {
                        if(message == null)                 ///This section processes the message
                        {                                   ///from the client
                            message = messageFromClient;
                        }
                        else 
                        {
                            message = message + " " + messageFromClient;
                        }
                        
                        if(!messageFromClient.equals("")) 
                        {
                            break;
                        }                        }
                        
                        if(message.equalsIgnoreCase( "yes"))  ///Request from client to consume a resource
                        {                                     ///Consumes an item, and messages the client.
                            burgerBox.takeOne();
                            System.out.println("Cheezburgers available: "+ burgerBox.getLevel());
                            messageToClient = "From the Server: Here is your cheezburger. Enjoy.";
                            outMessagePrintWriter.println(messageToClient);
                        }
                        else if(message.equalsIgnoreCase("no"))  ///Request from client to do nothing.
                        {                                        ///Handler replies, stands by.
                            messageToClient = "From the Server: Come back later for a cheezburger!";
                            outMessagePrintWriter.println(messageToClient);
                        }
                        else if(message.equalsIgnoreCase("quit"))  ///Request from client to quit.
                        {                                          ///Handler responds and closes connection.
                            again=false;
                            messageToClient = "Bye bye kitty!";
                            outMessagePrintWriter.println(messageToClient);
                            outMessagePrintWriter.close();
                            inMessageBufferedReader.close();
                            clientSocket.close();
                        }
                        else
                        {
                            messageToClient = "Ooops, try again, kitty!";
                            outMessagePrintWriter.println(messageToClient);   
                        }
						
			messageToClient = null;
			message = null;
			messageFromClient = null;
			                       
                    }///end of inner while
		}/// end of while(again)
		catch (IOException e) 
                {
                    e.printStackTrace();
		}
	} //end of run
} //end of class