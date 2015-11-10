/**
 *
 * @author: Chris Benda
 */
import java.io.*;
import java.net.*;

public class Server
{
    public static void main(String[] args) throws IOException 
    {
        ServerSocket tcpServerSocket = null;
        boolean listening = true;
        
        Resource burgerBox = new Resource(5);
        
        Producer burgerMaker1 = new Producer(burgerBox);
        Producer burgerMaker2 = new Producer(burgerBox);
	
        
        try 
        {
            tcpServerSocket = new ServerSocket(9090);
        }
        catch (IOException e) 
        {
            System.err.println("Could not listen on port: 9090");
            System.exit(1);
        }
        
        System.out.println("Cheezburgerz available: " + burgerBox.getLevel());
        burgerMaker1.start();
        burgerMaker2.start();
        
        while(listening) 
        {
            System.out.println("The server is listening on port: 9090");
            new ConsumerHandler(tcpServerSocket.accept(), burgerBox).start();
        }   
  }//end of main         
}//end of class
  