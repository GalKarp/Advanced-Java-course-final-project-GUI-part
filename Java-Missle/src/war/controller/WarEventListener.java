package war.controller;

import java.io.Serializable;

public interface WarEventListener extends Serializable {

	void addDestructorToModel(String id, String type);

	void addLauncherToModel(String id);

	void addedLauncherToModelEvent(String launcherId);

	void addedLuncherDestructorToModelEvent(String destructorId, String type);

	void addedMissileDestructorToModelEvent(String destructorId, String type);

	void addedMissileToModelEvent(String missileId, String destination,
			int damage, int flyTime);

	void addedLauncherToDestroy(String destructor_id, String target_id,
			int destruct_time);

	void DestroyMissileProgressBar(String missileId, String string);

	void RemovedLauncherFromUI(String launcherId);

	void UpdatedMissileProgressToModelEvent(int time, String missileId,
			String string, String destination, int damage, int flyTime);

	void addedMissileToDestroy(String ironDome, String missileId,
		int destruct_time);

	void RemoveCurrentElement(String destructorId);

	void AddMessageToGui(String string);

}
