package war.controller;

import java.io.Serializable;


public interface WarUIEventsListener extends Serializable{
	void addLauncherToUI(String id);

	void addDestructorToUI(String id, String type);

	void addMissileToUI(String id, String dest, String damage, String flyTime,
			String launcherId);

	void addMissileToProgress(String name, String dest, String damage,
			String flyTime, String launcherId);

	void addLauncherThroughClient(String id);

	void connectToServer();

	void addMissileThroughClient(String id, String dest, String damageT,
		String flyTime, String launcherId);

	int[] getStatistics();

	void finishProgram();

	void addInterceptionToUI(int destructAfterLaunch, String missileId,
			String ironDome);

	void destroyLauncherInUI(int destructAfterLaunch, String destroyerId,
			String launcherIdToDestroy);

}
