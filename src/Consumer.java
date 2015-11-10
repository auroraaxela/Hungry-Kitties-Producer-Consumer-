/**
 *
 * @author: Chris Benda
 */

import java.io.*;
import java.net.*;

public class Consumer extends Thread{
	
	public static void main(String [] args)
        {
            Consumer consumer = new Consumer();
            consumer.run();
	}
        
        boolean again = true;
	Socket tcpSocket = null;
	PrintWriter outMessagePrintWriter = null;
	BufferedReader inMessageBufferedReader = null,
		systemIn = new BufferedReader(new InputStreamReader(System.in));
	String messageFromServer, messageToServer,message = null;
	
	public void run()
        {
            try
            {
                tcpSocket = new Socket("localhost", 9090);
		outMessagePrintWriter = new PrintWriter(tcpSocket.getOutputStream(), true);
		inMessageBufferedReader = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
		while(again)
                {
                    System.out.println("Is the kitty hungry?  Yes/No/Quit:");
                    messageToServer = systemIn.readLine().toLowerCase();
                    if(messageToServer.equals("quit"))
                    {
                        again = false;
                        outMessagePrintWriter.println(messageToServer);
                    }
                    else if(messageToServer.equalsIgnoreCase("yes")||messageToServer.equalsIgnoreCase("no"))
                    {
                        outMessagePrintWriter.println(messageToServer);
                    }
                    else{ 
                        outMessagePrintWriter.println(messageToServer);
                    }
                
                ///This section reads the feed coming back from the server
                    while((messageFromServer = inMessageBufferedReader.readLine()) != null) 
                    {
                        if(message == null) 
                        {
                            message = messageFromServer;
                        }
                        else 
                        {
                            message = message + " " + messageFromServer;
                        }
                        if( !messageFromServer.equals(""))
                        {
                            break;
                        }
                    }//end of while loop
                    System.out.println(message);
                    message = null;
                    messageToServer = null;
                    messageFromServer = null;
                }//end of while
                
                outMessagePrintWriter.close();
                inMessageBufferedReader.close();
                tcpSocket.close();
                systemIn.close();
            }//end of try
            catch(UnknownHostException e) 
            {
                System.err.println("Don't know about the host: localHost" );
            }
            catch ( IOException e ) 
            {
                System.err.println("Couldn't get I/O for the connection to : localHost" );
		System.exit(1);
            }

            System.exit(1);
	}//end of run
} //end of class