package war;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import launcher.Launcher;
import missile.Missile;

public class Client extends Thread {
    static Socket socket = null;
    static ObjectInputStream fromNetInputStream;
    static ObjectOutputStream toNetOutputStream;
    Queue<Object> vec = new LinkedList<>();;

    public Client() throws IOException {
    }
    public void sendObjectToServer(Launcher launchers, Missile missile)
	    throws IOException {
	vec.add(missile);
	vec.add(launchers);
	toNetOutputStream.writeObject(vec);
	System.out.println("cilent");
	vec.clear();
	toNetOutputStream.reset();
    }
    public void sendObjectToServer(Launcher launcher) throws IOException {
	vec.add(launcher);
	toNetOutputStream.writeObject(vec);
	toNetOutputStream.reset();
    }
    @Override
    public void run() {
	try {
	    socket = new Socket("localhost", 7000);
	} catch (UnknownHostException e1) {
	    e1.printStackTrace();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	System.out.println(new Date() + " --> Connected to server at "
		+ socket.getLocalAddress() + ":" + socket.getLocalPort());

	try {
	    toNetOutputStream = new ObjectOutputStream(socket.getOutputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    fromNetInputStream = new ObjectInputStream(socket.getInputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
