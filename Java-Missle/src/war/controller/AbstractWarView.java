package war.controller;

import launcher.Destructor;
import launcher.Launcher;

public interface AbstractWarView {
	void registerListener(WarUIEventsListener listener);

	void addLauncherToUI(String id);

	void addDestructorToUI(Destructor destructor);

	void addLauncherDestructorToUI(String destructorId, String type);

	void addMissileDestructorToModelEvenet(String destructorId, String type);

	void addMissileToUI(String missileId, String destination, int damage,
			int flyTime);

	void destroyMissileProgress(String missileId, String type);

	void addDestroyerProgress(String destructor_id, String target_id,
			int destruct_time);

	void removeLauncherFromView(String launcherId);

	void updateMissileProgress(int time, String missileId, String type,
			String destination, int damage, int flyTime);

	void RemoveCurrentElement(String destructorId);

	void addMessageToGui(String string);

}
