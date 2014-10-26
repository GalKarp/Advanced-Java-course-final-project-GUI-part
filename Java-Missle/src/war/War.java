package war;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import launcher.Destructor;
import launcher.Launcher;
import logger.LogFormatter;
import missile.AbstractMissile;
import missile.DestructedLanucher;
import missile.DestructedMissile;
import missile.Missile;

import org.xml.sax.SAXException;

import war.controller.WarController;
import war.controller.WarEventListener;
import war.db.WarDBConnection;

/**
 * A war class that holds all the war objects
 * 
 * @author first part: Avishay and Dvir
 * @author 2nd and 3rd parts: Andrey & Gal
 * 
 */

public class War {
	/**
     * 
     */

	public static final int TIME_INTERVAL = 1000; // sleep time for threads
	public static final double SUCCESS_RATE = 0.2; // success rate for
													// destructors
	public static final String LAUNCHER = "launcher";
	public static final String MISSILE = "missile";
	public static final int TAKES_TIME_MIN = 1;
	public static final int TAKES_TIME_MAX = 10;

	private static Logger logger;

	private Vector<Launcher> missileLaunchers = new Vector<>();
	private Vector<Destructor> missileDestructors = new Vector<>();
	private Vector<Destructor> missileLauncherDestructors = new Vector<>();
	private Vector<WarEventListener> listeners;
	private Client client;
	private Server server;

	/**
	 * Constructor for the war which take from XML the stats to begin
	 * 
	 * @param missileLaunchers2
	 * @param missileLauncherDestructors2
	 * @param missileDestructors2
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public War(Vector<Destructor> missileDestructors,
			Vector<Destructor> missileLauncherDestructors,
			Vector<Launcher> missileLaunchers)
			throws ParserConfigurationException, SAXException, IOException {

		FileHandler fileHandler = new FileHandler("war_log.txt");
		fileHandler.setFormatter(new LogFormatter());
		logger = Logger.getLogger("warLogger");
		logger.addHandler(fileHandler);
		logger.setUseParentHandlers(false);
		listeners = new Vector<WarEventListener>();
		this.missileLaunchers = missileLaunchers;
		this.missileDestructors = missileDestructors;
		this.missileLauncherDestructors = missileLauncherDestructors;
	}

	public War() throws SecurityException, IOException {
		FileHandler fileHandler = new FileHandler("war_log.txt");
		fileHandler.setFormatter(new LogFormatter());
		logger = Logger.getLogger("warLogger");
		logger.addHandler(fileHandler);
		logger.setUseParentHandlers(false);
		listeners = new Vector<WarEventListener>();
	}

	public Vector<Launcher> getMissileLaunchers() {
		return missileLaunchers;
	}

	public Vector<Destructor> getMissileDestructors() {
		return missileDestructors;
	}

	public Vector<Destructor> getMissileLauncherDestructors() {
		return missileLauncherDestructors;
	}

	public void addLauncherDestructor(Destructor desctructor) {
		missileLauncherDestructors.add(desctructor);
		fireAddLauncherDestructorEvent(desctructor);
		desctructor.start();
	}

	public void fireAddLauncherDestructorEvent(Destructor desctructor) {
		for (WarEventListener l : listeners) {
			l.addedLuncherDestructorToModelEvent(desctructor.getDestructorId(),
					desctructor.getType());
		}

	}

	public void addMissileDestructor(Destructor desctructor) {
		missileDestructors.add(desctructor);
		fireAddMissileDestructorEvent(desctructor);
		desctructor.start();
	}

	public void fireAddMissileDestructorEvent(Destructor desctructor) {
		for (WarEventListener l : listeners) {
			l.addedMissileDestructorToModelEvent(desctructor.getDestructorId(),
					desctructor.getType());
		}

	}

	public Vector<WarEventListener> getListeners() {
		return listeners;
	}

	public void addLauncher(String id) throws Exception {
		if ((id.isEmpty()) || (WarUtility.getLauncherById(id, this) != null)) {
			throw new Exception("This Id is empty or already exist");
		}
		boolean is_hidden = (Math.round(Math.random()) == 1) ? true : false;
		addLauncher(new Launcher(id, is_hidden, listeners));
		int stat = (is_hidden == true) ? 1 : 0;
		WarDBConnection.addNewLauncher(id, stat);
	}
	
	public void addLauncher(Launcher launcher) {
		this.missileLaunchers.add(launcher);
		int stat = (launcher.isHidden() == true) ? 1 : 0;	
		WarDBConnection.addNewLauncher(launcher.getLauncherId(), stat);
		fireAddLauncherEvent(launcher);
		launcher.start();
		
	}

	public void fireAddLauncherEvent(Launcher launcher) {
		for (WarEventListener l : listeners) {
			l.addedLauncherToModelEvent(launcher.getLauncherId());
		}

	}

	public void registerListener(WarController warController) {
		listeners.add(warController);

	}

	public void addDestructor(String id, String type) throws SecurityException,
			IOException {

		if (WarUtility.getDestructorById(id, this, type) != null) {
			// tell controller the id already exists
		}

		if (type.equalsIgnoreCase("Plane") || type.equalsIgnoreCase("Ship")) {
			Destructor desctructor = new Destructor(id, type,
					new Vector<AbstractMissile>());
			addLauncherDestructor(desctructor);
		} else if (type.equals("Iron Dome")) {
			Destructor desctructor = new Destructor(id, type,
					new Vector<AbstractMissile>());
			addMissileDestructor(desctructor);
		}
		WarDBConnection.addNewDestructor(id, type);
	}

	public void addMissile(String name, String dest, String damage,
			String flyTime, String launcherId) {
		int damageInt = Integer.parseInt(damage);
		int flyTimeInt = Integer.parseInt(flyTime);
		for (Launcher launcher : missileLaunchers) {
			if (launcherId.equalsIgnoreCase(launcher.getLauncherId())) {
				launcher.addMissile(name, dest, 0, flyTimeInt, damageInt);
				WarDBConnection.addNewMissile(name, dest, damageInt,
						flyTimeInt, "wait");
			}
		}
	}

	public void destroyLauncher(int delayFromStart,String destructor_id, String target_id) {

		Destructor selected_destructor = WarUtility.getDestructorById(
				destructor_id, this, LAUNCHER);
		if (selected_destructor == null) {
		}
		Launcher target = WarUtility.getLauncherById(target_id, this);
		if (target == null) {
			// throw new Exception("Target does not exist");
		}
		int destruct_time = 10;
//		(int) (TAKES_TIME_MIN + (Math.random() * (TAKES_TIME_MAX
//				- TAKES_TIME_MIN + 1)));
		// assign destructor to destruct the launcher

		DestructedLanucher assigned_destructor = new DestructedLanucher(delayFromStart,target,
				destruct_time, selected_destructor,
				selected_destructor.getFileHandler(), listeners);
		selected_destructor.addDestructMissile(assigned_destructor);
	}

	public void startMissileInterception(int delayFromStart,String missileId, String ironDome) {
		Destructor selected_destructor = WarUtility.getDestructorById(ironDome,
				this, MISSILE);
		int destruct_time = 10 ;
//		(int) (TAKES_TIME_MIN + (Math.random() * (TAKES_TIME_MAX
//				- TAKES_TIME_MIN + 1)));
		Missile target = WarUtility.getMissileById(missileId, this);
		DestructedMissile assigned_destructor = new DestructedMissile(delayFromStart,target,
				destruct_time, selected_destructor,
				selected_destructor.getFileHandler(), this.getListeners());
		selected_destructor.addDestructMissile(assigned_destructor);

	}

	public int[] displayStatistics() {

		int statistics[] = new int[5];
		int total_launched_missiles = 0;
		int total_destroyed_missiles = 0;
		int total_missiles_hit = 0;
		int total_destroyed_launchers = 0;
		int total_damage = 0;
		int size_launcher = getMissileLaunchers().size();
		int size_missiles;

		Launcher l;
		Missile m;

		for (int i = 0; i < size_launcher; i++) {
			l = getMissileLaunchers().get(i);
			if (l.isRunning() == false) {
				total_destroyed_launchers++;
			}
			size_missiles = l.getMissiles().size();
			for (int j = 0; j < size_missiles; j++) {
				m = l.getMissiles().get(j);
				if (m.getStatus() != Missile.Status.Waiting) {
					total_launched_missiles++;
					if (m.getStatus() == Missile.Status.Hit) {
						total_missiles_hit++;
						total_damage += m.getDamage();
					} else if (m.getStatus() == Missile.Status.Destroyed) {
						total_destroyed_missiles++;
					}
				}
			}
		}
		statistics[0] = total_launched_missiles;
		statistics[1] = total_destroyed_missiles;
		statistics[2] = total_missiles_hit;
		statistics[3] = total_destroyed_launchers;
		statistics[4] = total_damage;

		return statistics;
	}

	public void addLauncherToClient(String id) {
		if ((id.isEmpty()) || (WarUtility.getLauncherById(id, this) != null)) {
			try {
				throw new Exception("This Id is empty or already exist");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean is_hidden = (Math.round(Math.random()) == 1) ? true : false;
		try {
			Launcher launcher = new Launcher(id, is_hidden, listeners);
			client.sendObjectToServer(launcher);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addMissileToClient(String id, String dest, String damage,
			String flyTime, String launcherName) {
		// TODO Auto-generated method stub
		for (Launcher launchers : missileLaunchers) {
			if (launcherName.equalsIgnoreCase(launchers.getLauncherId())) {
				Missile missile = launchers.CreatWithoutAddMissile(id, dest, 0,
						flyTime, damage, listeners);
				// Client client = new Client();
				try {
					client.sendObjectToServer(launchers, missile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public void connectToServer() {
		try {
			server = new Server();
			server.start();
			server.setWar(this);
			client = new Client();
			client.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// client.connectToServer();

	}

	public void openServer() {
		try {
			server = new Server();
			server.setWar(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Method to stop all threads and exit */
	public void finishProgram() {
		for (int i = 0; i < missileLaunchers.size(); i++) {
			missileLaunchers.elementAt(i).stop();
		}

		for (int i = 0; i < missileDestructors.size(); i++) {
			missileDestructors.elementAt(i).stop();
		}

		for (int i = 0; i < missileLauncherDestructors.size(); i++) {
			missileLauncherDestructors.elementAt(i).stop();
		}
		WarDBConnection.clearWarDataBase();
		System.exit(0);

	}

}
