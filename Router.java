import java.util.*;
import java.io.FileReader;
/**
 * Write a description of class router here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Router
{
     // instance variables - replace the example below with your own
    private int port;
    private String ip;
    private ArrayList<peerInfo> connectedRouters;
    /**
     * Constructor for objects of class Node
     */
    public Router(String fileName)
    {
        // initialise instance variables
        connectedRouters = new ArrayList<peerInfo>();
        readFile(fileName);
        SendUpdate sendUpdate = new SendUpdate();
        sendUpdate.run();
    }

    public void readFile(String fileName)
    {
        ArrayList<String> messageArray = new ArrayList<String>();
        Scanner sc=null;
        try{
            sc = new Scanner(new FileReader(fileName));
        }catch(Exception e)
        {System.out.println("Could not open file " + e);}
        
        String temp = sc.nextLine();
        String[] tokens = temp.split(" ");
        ip = tokens[0];
        port = Integer.parseInt(tokens[1]);
        
        while(sc.hasNextLine())
        {
            temp = sc.nextLine();
            tokens = temp.split(" ");
            peerInfo info = new peerInfo();
            info.ip = tokens[0];
            info.port = Integer.parseInt(tokens[1]);
            info.weight = Integer.parseInt(tokens[2]);
            connectedRouters.add(info);
        }
    }
    
    public class SendUpdate implements Runnable
    {
        public void run()
        { 
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("TimerTask executing counter is: ");
                }
            };
    
            Timer timer = new Timer("MyTimer");//create a new Timer
    
            timer.scheduleAtFixedRate(timerTask, 30, 3000);//this line starts the timer at the same time its executed
        }
    }
    
    public class recieveUpdate implements Runnable
    {
        
        public void run()
        {
            
        }
    }
    
    public class readCommands implements Runnable
    {
        
        public void run()
        {
            
        }
    }
}

class peerInfo
{
    public int port;
    public int weight;
    public String ip;
}
