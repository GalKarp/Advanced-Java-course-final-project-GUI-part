package missile;
import java.io.Serializable;
import java.util.List;
import java.util.logging.FileHandler;
import launcher.Launcher;
import war.War;
import war.controller.WarEventListener;
import war.db.WarDBConnection;

public class Missile extends AbstractMissile implements Serializable   {

    /**
     * 
     */
	
	public enum Status {Waiting, Launched, Destroyed, Hit};
	
    private static final long 					serialVersionUID = 1L;       
    private transient List<WarEventListener> 	allListeners;
    private String 						  	 	missileId;
    private String 								destination;
    private int 								flyTime;
    private int 								damage;
    private Launcher 							launcher;
    private Status								status;


    /**
     * Constructor 
     * @param id
     * @param destination
     * @param launchTime
     * @param flyTime
     * @param damage
     * @param fileHandler
     * @param launcher
     */
    public Missile(String id, String destination, int launchTime, int flyTime,
	    int damage, FileHandler fileHandler, Launcher launcher , List<WarEventListener> allListeners) {
	super(launchTime, fileHandler);
	this.missileId = id;
	this.destination = destination;
	this.flyTime = flyTime;
	this.damage = damage;
	this.launcher = launcher;
	this.setStatus(Status.Waiting);
	this.allListeners = allListeners;
    }


    /**
     * @return id of missile 
     */
    public String getMissileId() {
	return missileId;
    }


    public String getDestination() {
	return destination;
    }

    /**
     * @return launch time
     */
    public int getLaunchTime() {
	return super.getDelayBeforeLaunch();
    }

    /**
     * @return fly time
     */
    public int getFlyTime() {
	return flyTime;
    }

    /**
     * @return damage
     */
    public int getDamage() {
	return damage;
    }

    /**
     * @return missile status 
     */
    public Status getStatus() {
	return status;
    }


    /**
     * set status
     * @param status
     */
    public void setStatus(Status status) {
	this.status = status;
    }

    /** 
     * Run missile 
     */
    public void run() {
	boolean reveal_status = false;
	try {
		WarDBConnection.addNewMissile(missileId,destination,damage,flyTime,status.toString());
	    sleep(getLaunchTime() * War.TIME_INTERVAL);

	    synchronized (launcher) {

		if (launcher.isRunning()) {
		    this.setStatus(Status.Launched);
		    WarDBConnection.updateMissileStatus(missileId, status.toString());
		    // make launcher not hidden for X amount of time
		    if (launcher.isHidden()) {
			launcher.revealYourSelf(); 
			reveal_status = true;
		    }
		    	    	
	//	    java.sql.Date sqlDate = Utils.utilDateToSqlDate(birthdate);
		    for (WarEventListener l : allListeners) {
			l.addedMissileToModelEvent(missileId,destination,damage,flyTime);
		    }
		    //					notify();
		    
		    logMissileLaunched();
		    try {
			for(int time = 0 ; time <= flyTime ; time++){
			    Thread.sleep(War.TIME_INTERVAL);
			    for (WarEventListener l : allListeners) {
				l.UpdatedMissileProgressToModelEvent(time, missileId , "missile",destination,damage,flyTime);
			    }
			}
			for (WarEventListener l : allListeners) {
			    l.DestroyMissileProgressBar(missileId , "missile");
			}
		    } catch (InterruptedException e) {
		    }	
		    destroyTarget();
		    if (reveal_status == true) {
			launcher.hideYourSelf(); // make launcher hide again
		    }
		}
	    }	
	} catch (InterruptedException e) {
	    //missile is destroyed
	    this.setStatus(Status.Destroyed);
	    WarDBConnection.updateMissileStatus(missileId, status.toString());
	    
	    if (reveal_status == true) {
		launcher.hideYourSelf(); // make launcher hide again
	    }
	} catch (SecurityException e) {

	} catch (Exception e) {
	}
    }


    public void logMissileLaunched() {} // aspect log


	@Override
    public void destroyTarget() {
	logMissileHit();	
	this.setStatus(Status.Hit);
	WarDBConnection.updateMissileStatus(missileId, status.toString());
    }


	public void logMissileHit() {} // aspect log


	public String getLauncherId() {
		
		return launcher.getLauncherId();
	}

}
