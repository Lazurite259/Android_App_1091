import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server 
{
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
    public static void main(String[] args) throws IOException 
    {
        ServerSocket ss = new ServerSocket(7070); /*Port*/
        while (true) 
        {
            Socket s = ss.accept();            
            System.out.println(s.toString()+" connect success!");
            socketList.add(s);
            new Thread(new ServerThread(s)).start();
        }
    }
}
class ServerThread implements Runnable 
{

    private Socket mSocket = null;
    private BufferedReader mBufferedReader = null;
    
    public ServerThread(Socket socket) throws UnsupportedEncodingException, IOException 
    {
        mSocket = socket;
        mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
    }

    @Override
    public void run() 
    {
        try 
        {
            String content = null;
            while ((content = mBufferedReader.readLine()) != null) 
            {
                System.out.println(content);
                for (Iterator<Socket> it = Server.socketList.iterator(); it.hasNext();) 
                {
                    Socket s = it.next();
                    try 
                    {
                        OutputStream os = s.getOutputStream();
                        os.write((content + "\n").getBytes("UTF-8"));
                    } 
                    catch (SocketException e) 
                    {
                        e.printStackTrace();
                        //System.out.println(it.toString()+" disconnect!");
                        //it.remove();
                    }
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            System.out.println(mSocket.toString()+" disconnect!");
            Server.socketList.remove(mSocket);
        }
    }
}