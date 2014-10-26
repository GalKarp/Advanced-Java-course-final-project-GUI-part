package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import war.controller.WarUIEventsListener;

public class IronDomesPanel extends JPanel   {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
	public static final String LAUNCHER_IMAGE_PATH = "/drawable/dome60x89.png";
	
    private List<WarUIEventsListener> 	allListeners;
    private Queue<JButton> 				domeQueue;
    private WarGui 						warGui;
    private String 						missileId;
    private boolean 					fireMissileButtonPressed;
    
    /** Constructor */
	public IronDomesPanel(List<WarUIEventsListener> allListeners, WarGui warGui) {
		domeQueue = new LinkedList<JButton>();
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new GridLayout(2, 3,3,3));
		this.fireMissileButtonPressed = false;
		this.allListeners=allListeners;
		this.warGui=warGui;

		}
	
	public void addIronDomeToPanel(final String id){
		
		JButton dome = new JButton(id,new ImageIcon(LaunchersPanel.class.getResource(LAUNCHER_IMAGE_PATH)));
		dome.setVerticalTextPosition(SwingConstants.BOTTOM);
		dome.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(dome);
		domeQueue.add(dome);
		dome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(fireMissileButtonPressed){
					String IronDomeId = ((JButton) e.getSource()).getText(); // id of the launcher to destroy
					setBorder(new LineBorder(new Color(0, 0, 0)));
					validate();
					for (WarUIEventsListener l : allListeners) {
						l.addInterceptionToUI(0,missileId, id);
					}
					fireMissileButtonPressed = false;
					
				}
				setBorder(new LineBorder(new Color(0, 0, 0)));
				validate();
			}

		});
		repaint();
		validate();
		
	}
	protected void getIronDome(String ironDomeId) {
	    warGui.getIronDome(ironDomeId);
	    
	}
	public void addMissileDestructorToUI(String destructorId, String type) {
		// type could be used for future functions if there are other Missile Destructors
		addIronDomeToPanel(destructorId);
	}
	public void selectLauncherTofireFrom(String missileId) {
		this.setBorder(new LineBorder(new Color(255, 0, 0), 2));
		fireMissileButtonPressed = true;
		this.missileId = missileId;
	    
	}
}
