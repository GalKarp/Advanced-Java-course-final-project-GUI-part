package aop;

import java.util.logging.Level;
import java.util.logging.Logger;

import missile.DestructedLanucher;
import missile.DestructedMissile;
import missile.Missile;

public aspect LoggerAspect {
	private Logger logger = Logger.getLogger("warLogger");

	/**
	 * Log Missile Hit Message
	 */
	pointcut missileHit() : execution (public void logMissileHit());

	before() : missileHit() {
		Missile missile = (Missile) thisJoinPoint.getTarget();

		String print_log = "Missle " + missile.getMissileId() + " hit "
				+ missile.getDestination() + " with " + missile.getDamage()
				+ " damage";

		logger.log(Level.INFO, print_log, missile);
	}

	/**
	 * Log Missile Launched message
	 */
	pointcut missileLaunched() : execution (public void logMissileLaunched());

	before() : missileLaunched() {
		Missile missile = (Missile) thisJoinPoint.getTarget();

		String print_log = "Missle " + missile.getMissileId()
				+ " was launched from launcher: " + missile.getLauncherId();

		logger.log(Level.INFO, print_log, missile);
	}

	/**
	 * Trying to destroy launcher message
	 */
	pointcut destroyingLauncher() : execution (public void logDestroyingLauncher());

	before() : destroyingLauncher() {

		DestructedLanucher destructedLauncher = (DestructedLanucher) thisJoinPoint
				.getTarget();
		Object arr[] = { destructedLauncher, destructedLauncher.getTarget() };

		String print_log = "Trying to destroy launcher:"
				+ destructedLauncher.getTarget().getLauncherId();
		logger.log(Level.INFO, print_log, arr);

	}
	
	/**
	 * Launcher destroyed message
	 */
	pointcut LauncherDestroyed() : execution (public void logTargetDestroyed());

	before() : LauncherDestroyed() {

		DestructedLanucher destructedLauncher = (DestructedLanucher) thisJoinPoint
				.getTarget();
		Object arr[] = { destructedLauncher, destructedLauncher.getTarget() };

		String print_log = "Launcher "
				+ destructedLauncher.getTarget().getLauncherId()
				+ " was destroyed";
		logger.log(Level.INFO, print_log, arr);

	}
	
	/**
	 * Trying to destroy Missile message
	 */

	pointcut destroyingMissile() : execution (public void logDestroyingMissile());

	before() : destroyingMissile() {

		DestructedMissile destructedMissile = (DestructedMissile) thisJoinPoint
				.getTarget();
		Object arr[] = { destructedMissile, destructedMissile.getTarget() };

		String print_log = 
				"Trying to destroy missile :" + destructedMissile.getTarget().getMissileId()
						+ " from Missile Destructor";
		logger.log(Level.INFO, print_log, arr);

	}

	/**
	 * Missile Destroyed message
	 */

	pointcut missileDestroyed() : execution (public void logMissileDestroyed());

	before() : missileDestroyed() {

		DestructedMissile destructedMissile = (DestructedMissile) thisJoinPoint
				.getTarget();
		Object arr[] = { destructedMissile, destructedMissile.getTarget() };

		String print_log = "Missle " + destructedMissile.getTarget().getMissileId()
				+ " was destroyed";
		
		logger.log(Level.INFO, print_log, arr);

	}

}
