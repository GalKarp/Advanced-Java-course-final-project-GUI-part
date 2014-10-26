package war.controller;

import java.io.IOException;
import java.util.Vector;

import war.War;

public class WarController implements WarUIEventsListener, WarEventListener{
    private War warModel;
    private AbstractWarView warView;

    public WarController(War model, AbstractWarView view) {
	warModel = model;
	warView = view;

	warModel.registerListener(this);
	warView.registerListener(this);
//	warModel.start();
    }

    public War getWarModel() {
	return warModel;
    }
    
    public void setWarModel(War warModel) {
	this.warModel = warModel;
    }

    public AbstractWarView getWarView() {
	return warView;
    }

    public Vector<WarEventListener> getWarListeners() {
	return warModel.getListeners();
    }

    public void setWarView(AbstractWarView warView) {
	this.warView = warView;
    }

    @Override
    public void addDestructorToModel(String id, String type) {

    }

    @Override
    public void addLauncherToModel(String id) {
	// TODO Auto-generated method stub

    }

    @Override
    public void addLauncherToUI(String id) {
	try {
	    warModel.addLauncher(id);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addDestructorToUI(String id, String type) {
	try {
	    warModel.addDestructor(id, type);
	} catch (SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public void addedLauncherToModelEvent(String launcherId) {
	warView.addLauncherToUI(launcherId);
    }

    @Override
    public void addedLuncherDestructorToModelEvent(String destructorId,
	    String type) {
	warView.addLauncherDestructorToUI(destructorId, type);
    }

    @Override
    public void addedMissileDestructorToModelEvent(String destructorId,
	    String type) {
	warView.addMissileDestructorToModelEvenet(destructorId, type);

    }

    @Override
    public void addedMissileToModelEvent(String missileId, String destination,
	    int damage, int flyTime) {
	warView.addMissileToUI(missileId, destination, damage, flyTime);
//and i see you already did that so why you need listeners in missiles/launchers in the first place? for the "l"?
    }

    @Override
    public void addMissileToUI(String missileId, String destination,
	    String damage, String flyTime, String launcherId) {
	warModel.addMissile(missileId, destination, damage, flyTime, launcherId);
    }

    @Override
    public void addMissileToProgress(String name, String dest, String damage,
	    String flyTime, String id) {

    }

    @Override
    public void destroyLauncherInUI(int destructAfterLaunch,String destroyerId,
	    String launcherIdToDestroy) {
	warModel.destroyLauncher(destructAfterLaunch,destroyerId, launcherIdToDestroy);

    }

    @Override
    public void addedLauncherToDestroy(String destructor_id, String target_id,
	    int destruct_time) {
	// here you need to pass the information to the progress panel to add
	// the destructor bar and when
	// destruct time is over need to check with model if the destructor was
	// hidden or not
	// or you can do same as with missile to add listeners to destructor and
	// check every second
	// to fill the progress bar...
	warView.addDestroyerProgress(destructor_id, target_id, destruct_time);

    }

    @Override
    public void DestroyMissileProgressBar(String missileId, String type) {
	warView.destroyMissileProgress(missileId, type);

    }

    @Override
    public void RemovedLauncherFromUI(String launcherId) {
	warView.removeLauncherFromView(launcherId);
    }

    @Override
    public void UpdatedMissileProgressToModelEvent(int time, String missileId,
	    String type, String destination, int damage, int flyTime) {
	warView.updateMissileProgress(time, missileId, type, destination,
		damage, flyTime);

    }

    @Override
    public void addInterceptionToUI(int destructAfterLaunch,String missileId, String ironDome) {
	warModel.startMissileInterception(destructAfterLaunch,missileId, ironDome);

    }

    @Override
    public void addedMissileToDestroy(String ironDome, String missileId,
	    int destruct_time) {
	warView.addDestroyerProgress(ironDome, missileId, destruct_time);

    }

    @Override
    public void RemoveCurrentElement(String destructorId) {
	warView.RemoveCurrentElement(destructorId);
    }

    @Override
    public void AddMessageToGui(String string) {
	warView.addMessageToGui(string);

    }

    @Override
    public void addMissileThroughClient(String id, String dest, String damage,
	    String flyTime, String launcherName) {
	warModel.addMissileToClient(id, dest, damage, flyTime, launcherName);

    }

    @Override
    public void addLauncherThroughClient(String id) {
	warModel.addLauncherToClient(id);

    }

    @Override
    public void connectToServer() {
	warModel.connectToServer();

    }

	@Override
	public int[] getStatistics() {		
		return warModel.displayStatistics();
	}

	@Override
	public void finishProgram() {
		warModel.finishProgram();
		
	}


}
