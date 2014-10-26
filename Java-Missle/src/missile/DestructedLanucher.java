package missile;

import java.util.List;
import java.util.logging.FileHandler;
import launcher.Destructor;
import launcher.Launcher;
import war.War;
import war.controller.WarEventListener;
import war.db.WarDBConnection;

public class DestructedLanucher extends AbstractMissile {

  private static final long 		serialVersionUID = 1L;
  private Launcher 					target;
  private Destructor 				destructor;
  private List<WarEventListener> 	allListeners;
  private int delayFromStart;
  /**
   * Constructor
 * @param delayFromStart 
 * @param launcher
   * @param destructTime
   */
  public DestructedLanucher(int delayFromStart, Launcher target, int destructTime, Destructor destructor,
      FileHandler fileHandler, List<WarEventListener> allListeners) {
    super(destructTime, fileHandler);
    this.delayFromStart = delayFromStart;
    this.target = target;
    this.destructor = destructor;
    this.allListeners = allListeners;    
  }

  /**
   * Run object
   */
  public void run() {
    Object arr[] = {this, target};
    try {
      // wait untill destroy after war launch
      sleep(delayFromStart * War.TIME_INTERVAL);
      synchronized (destructor) {
        try {
          for (WarEventListener l : allListeners) {
            l.addedLauncherToDestroy(destructor.getDestructorId(), target.getLauncherId(),
                super.getDelayBeforeLaunch());
          }
          for (int time = 0; time <= super.getDelayBeforeLaunch(); time++) {
            Thread.sleep(War.TIME_INTERVAL);
            for (WarEventListener l : allListeners) {
              l.UpdatedMissileProgressToModelEvent(time, target.getLauncherId(),
                  "launcherDestroyer",
                  (destructor.getType() + " #" + destructor.getDestructorId()), 0,
                  super.getDelayBeforeLaunch());
            }
          }
          for (WarEventListener l : allListeners) {
            l.RemoveCurrentElement(target.getLauncherId());
          }
        } catch (InterruptedException e) {
        }

        // synchronized (destructor) {
        logDestroyingLauncher();
        destroyTarget(); // try to destroy launcher
      }
    } catch (Exception e) {
    }
  }

  public Launcher getTarget() {
	return target;
}

public void setTarget(Launcher target) {
	this.target = target;
}

public Destructor getDestructor() {
	return destructor;
}

public void setDestructor(Destructor destructor) {
	this.destructor = destructor;
}

public void logDestroyingLauncher() {	
}

/**
   * method to destroy a selected launcher
   * 
   * @throws Exception if fails with message of fail reason
   */
  @Override
  public void destroyTarget() throws Exception {
    Object arr[] = {this, target};
    double rate = 3;// Math.random(); // generate random success

    if (target.isRunning()) {
      if (!target.isHidden()) {
        // if rate bigger than success rate it will destroy
        if (rate > War.SUCCESS_RATE) {
          for (WarEventListener l : allListeners) {
            l.RemovedLauncherFromUI(target.getLauncherId());
          }
          for (WarEventListener l : allListeners) {
            l.AddMessageToGui("Destruction of launcher " + target.getLauncherId()
                + " was sucess!");
          }
          target.stopLauncher();
          logTargetDestroyed();                    
          WarDBConnection.updateLauncherStatus(target.getLauncherId().toString(), "Destroy");
        } else {
          for (WarEventListener l : allListeners) {
            l.AddMessageToGui("Destruction of launcher " + target.getLauncherId() + " was failed");
          }
          throw new Exception("Destruction of launcher " + target.getLauncherId() + " was failed");

        }
      } else {

        for (WarEventListener l : allListeners) {
          l.AddMessageToGui("Destruction of launcher " + target.getLauncherId()
              + " was failed - Launcher is hidden!");
        }
        throw new Exception("Destruction of launcher " + target.getLauncherId()
            + " was failed - Launcher is hidden!");
      }
    } else {
      for (WarEventListener l : allListeners) {
        l.AddMessageToGui("Destruction of launcher " + target.getLauncherId()
            + " was failed - Launcher is not running!");
      }
      throw new Exception("Destruction of launcher " + target.getLauncherId()
          + " was failed - Launcher is not running!");
    }
  }

public void logTargetDestroyed() {	// aspect log
}

}
