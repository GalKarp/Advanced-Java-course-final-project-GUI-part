package missile;
import java.io.Serializable;
import java.util.List;
import java.util.logging.FileHandler;

import launcher.Destructor;
import missile.Missile.Status;
import war.War;
import war.controller.WarEventListener;
import war.db.WarDBConnection;

public class DestructedMissile extends AbstractMissile implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */	
	private Missile 				target;
	private Destructor 				destructor;
	private  List<WarEventListener> allListeners;
	private int delayFromStart;
	/**
	 * Constructor 
	 * @param delayFromStart 
	 * @param target
	 * @param destructAfterLaunch
	 */
	public DestructedMissile(int delayFromStart, Missile target, int destructTime, 
			Destructor destructor, FileHandler fileHandler , List<WarEventListener> allListeners) {
		super (destructTime, fileHandler);
		this.delayFromStart = delayFromStart;
		this.target = target;
		this.destructor = destructor;
		this.allListeners =allListeners;
	}

	public Missile getTarget() {
		return target;
	}

	public void setTarget(Missile target) {
		this.target = target;
	}

	/**
	 * Run object
	 */
	public void run() {
		while(true){
				try {
					sleep(delayFromStart * War.TIME_INTERVAL); 
			synchronized (destructor) {
			    if (target.getStatus() == Missile.Status.Launched){
				for (WarEventListener l : allListeners) {
				    l.addedMissileToDestroy(destructor.getDestructorId(),target.getMissileId(),super.getDelayBeforeLaunch());
				}
					for(int time = 0 ; time <= super.getDelayBeforeLaunch() ; time++){
					    Thread.sleep(War.TIME_INTERVAL);
					    for (WarEventListener l : allListeners) {
						l.UpdatedMissileProgressToModelEvent(time, target.getMissileId() , "IronDome",(destructor.getType()+" #" + destructor.getDestructorId()),0,super.getDelayBeforeLaunch());
					    }
					}
					    for (WarEventListener l : allListeners) {
						l.RemoveCurrentElement(target.getMissileId());
					    }
					    logDestroyingMissile();
				destroyTarget();		// try to destroy missile
			}
			}
				
		} catch (Exception e) {
		}
		}
	}
	
	public void logDestroyingMissile() {} // aspect log

	/**
	 * method to destroy a selected mussile
	 * @throws Exception if fails with message of fail reason
	 */
	@Override
	public void destroyTarget() throws Exception {
		Object arr[] = { this, target };
		double rate = 3;
//		Math.random();		// generate random success
			
		if (//(target.getStatus() == Missile.Status.Launched) &&
				(this.getDelayBeforeLaunch() < target.getFlyTime() + target.getLaunchTime())) {
			// if rate bigger than success rate it will destroy
			if (rate > War.SUCCESS_RATE) {
				synchronized (target) {
					target.interrupt();
				}
				for (WarEventListener l : allListeners) {
				    l.DestroyMissileProgressBar(target.getMissileId() , "missile");
				}
                for (WarEventListener l : allListeners) {
                l.AddMessageToGui("Destruction of missile "
                    + target.getMissileId() 
                    + " was sucess");
                }
			
				target.setStatus(Status.Destroyed);				
				WarDBConnection.updateMissileStatusAndDestructor(target.getMissileId().toString(), destructor.getDestructorId() + "");
				WarDBConnection.updateMissileStatus(target.getMissileId().toString(), target.getStatus().toString());
			} else {
			    for (WarEventListener l : allListeners) {
				l.AddMessageToGui("Destruction of missile "
					+ target.getMissileId() 
					+ " was failed");
			    }
				throw new Exception("Destruction of missile "
						+ target.getMissileId() 
						+ " was failed");
			}
		} else {
		    for (WarEventListener l : allListeners) {
			l.AddMessageToGui("Destruction of missile "
				+ target.getMissileId() 
				+ " was failed - Missile is not running");
		    }
			throw new Exception("Destruction of missile "
					+ target.getMissileId() 
					+ " was failed - Missile is not running");
		}
	}

}
