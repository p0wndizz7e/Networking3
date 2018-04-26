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
    private ArrayList<peerInfo> connectedRouters; //List of routers that have a path to
    private ArrayList<routes> forwardingTable;
    /**
     * Constructor for objects of class Node
     */
    public static void main(String[] args)
    {
        try
        {
            Router obj = new Router();
            obj.run(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run (String[] args)
    {
        // initialise instance variables
        adjacentRouters = new ArrayList<peerInfo>();
        connectedRouters = new ArrayList<peerInfo>();
        
        forwardingTable = new ArrayList<routes>();
        readFile(args[0]);
        
        SendUpdate sendUpdate = new SendUpdate();
        ReceiveUpdate receiveUpdate = new ReceiveUpdate();
        sendUpdate.run();
        receiveUpdate.run();
    }
    
    //convert the list calculated distance vectors to a byte array 
    //This is done, stop looking at it
    public byte[] convertToByteArray(ArrayList<peerInfo> connectedRouters)
    {
        byte[] data = new byte[2048];
        int index = 0;
        String temp = "";
        for(peerInfo r : connectedRouters)
        {
            temp += r.ip + "," + r.port + "," + r.weight + ":";
        }
        data = temp.getBytes();
        return data;
    }
    

	
    //Converts a recieved message, to an array list
    public ArrayList<peerInfo> convertToPeerInfoArray(byte[] byteArray)
    {
        ArrayList<peerInfo> receivedDistances = new ArrayList<peerInfo>();
        String temp = new String(byteArray);
        String[] tokens = temp.split(":");
        for(int x = 0; x < tokens.length - 1; x++)
        {
            System.out.print(tokens[x] + " ^ ");
            String[] parts = tokens[x].split(",");
            peerInfo link = new peerInfo();
            System.out.print(parts[0] + " ? ");
            System.out.print(parts[1] + " ? ");
            System.out.print(parts[2] + " ? ");
            
            link.ip = parts[0];
            link.port = Integer.parseInt(parts[1]);
            link.weight = Integer.parseInt(parts[2]);
            receivedDistances.add(link);
        }
        System.out.println("Cheese Credits");
        for(peerInfo p : receivedDistances)
        {
            System.out.println(p.toString());
        }
        return receivedDistances;
    }
    
    //5 before 12
    public ArrayList<peerInfo> calculateDistance(ArrayList<peerInfo> recievedDistances)
    {
        //Need to calculate new distances
        for(peerInfo p : recievedDistances)
		{
			
		}
        //Updated forwarding table
        return connectedRouters;
    }
    
    
    
    public class SendUpdate implements Runnable
    {
        @Override
        public void run()
        { 
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("TimerTask executing");
                    try{
                        for(peerInfo a : adjacentRouters)
                        {
                            DatagramSocket socket = new DatagramSocket();
                            //Neeeds to be changed when using actual IP's
                            InetAddress IPAddress = InetAddress.getByName("localhost"); 
                            byte[] sendData = convertToByteArray(connectedRouters);
                            DatagramPacket sendPacket = new DatagramPacket
                                                        (sendData, sendData.length, IPAddress, a.port);
                            socket.send(sendPacket);
                            
                        }
                    }catch(Exception e )
                    {
                        e.printStackTrace();
                        System.out.println("Error sending datagrams");
                    }
                }
            };
            Timer timer = new Timer("MyTimer");//create a new Timer
    
            timer.scheduleAtFixedRate(timerTask, 0, 3000);//this line starts the timer at the same time its executed
        }
    }
    
    public class ReceiveUpdate implements Runnable
    {
        @Override
        public void run()
        {
            try{
                DatagramSocket socket = new DatagramSocket(port);
                byte[] receiveData = new byte[2028];
                while(true)
                {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);
                    System.out.println(receivePacket);
                    ArrayList<peerInfo> nodes =  convertToPeerInfoArray(receivePacket.getData());
                    //socket.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public class ReadCommands implements Runnable
    {
        @Override
        public void run()
        {
            
        }
    }
    
    public ArrayList<peerInfo> sortPeerInfo(ArrayList<peerInfo> arrayToSort)
    {
        return arrayToSort;
    }
    
    //Sets up adjacent routers from the given text file
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
            connectedRouters.add(info);
        }
    }
}

class peerInfo
{
    public String ip;
    public int port;
    public int weight;
    
    public String toString()
    {
        return ip + ":" + port + " W>" + weight;
    }
}

class routes
{
    public peerInfo toThrough[] = new peerInfo[2];
}
