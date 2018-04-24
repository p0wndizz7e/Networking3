import java.util.*;
import java.net.*;
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
    private ArrayList<peerInfo> adjacentRouters; //The list of routers that are directly connected
    private ArrayList<peerInfo> connectedRouters; //List of routers that have paths to
    private ArrayList<routes> paths;
    /**
     * Constructor for objects of class Node
     */
    public Router(String fileName)
    {
        // initialise instance variables
        adjacentRouters = new ArrayList<peerInfo>();
        connectedRouters = new ArrayList<peerInfo>();
        
        paths = new ArrayList<routes>();
        readFile(fileName);
        SendUpdate sendUpdate = new SendUpdate();
        sendUpdate.run();
    }

    
    public byte[] convertToByteArray(ArrayList<peerInfo> connectedRouters)
    {
        byte[] data = new byte[2048];
        int index = 0;
        for(peerInfo r : connectedRouters)
        {
            String temp = r.ip + "," + r.port + "," + r.weight + ":";
            byte[] byteConversion = temp.getBytes();
        }
        return data;
    }
    
    public ArrayList<routes> convertToArrayList(byte[] byteArray)
    {
        return paths;
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
            adjacentRouters.add(info);
        }
    }
    
    public class SendUpdate implements Runnable
    {
        public void run()
        { 
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("TimerTask executing");
                    try{
                        for(peerInfo a : adjacentRouters)
                        {
                            DatagramSocket clientSocket = new DatagramSocket();
                            InetAddress IPAddress = InetAddress.getByName("hostname");
                            byte[] sendData = new byte[1024];
                            //sendData = paths.getBytes();
                        }
                    }catch(Exception e )
                    {
                        System.out.println("Error sending datagrams");
                    }
                }
            };
            Timer timer = new Timer("MyTimer");//create a new Timer
    
            timer.scheduleAtFixedRate(timerTask, 0, 3000);//this line starts the timer at the same time its executed
        }
    }
    
    public class recieveUpdate implements Runnable
    {
        
        public void run()
        {
            
        }
    }
    
    public class ReadCommands implements Runnable
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

class routes
{
    public peerInfo toThrough[] = new peerInfo[2];
}
