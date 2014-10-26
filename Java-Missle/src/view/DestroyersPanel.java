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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import war.controller.WarUIEventsListener;

public class DestroyersPanel extends JPanel  {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
	public static final String F16_IMAGE_PATH = "/drawable/f16-100x80.png";
	public static final String SHIP_IMAGE_PATH = "/drawable/ship.png";	
	
    private Queue<JButton> 				domeQueue;
    private boolean 					destroyLauncherButtonPressed;
    private String 						launcherIdToDestroy;
    private List<WarUIEventsListener> 	allListeners;
    
    
	public DestroyersPanel(List<WarUIEventsListener> allListeners) {
		this.allListeners = allListeners;
		domeQueue = new LinkedList<JButton>();
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new GridLayout(2, 3,3,3));
		this.destroyLauncherButtonPressed = false;
		launcherIdToDestroy = null;
		}
	
	public void addf16ToPanel(String id){	
		
		
		JButton f16 = new JButton(id,new ImageIcon(LaunchersPanel.class.getResource(F16_IMAGE_PATH)));
		f16.setVerticalTextPosition(SwingConstants.BOTTOM);
		f16.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(f16);
		domeQueue.add(f16);
		repaint();
		validate();
		
		f16.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(destroyLauncherButtonPressed){
					String destroyerId = ((JButton) e.getSource()).getText();

					addNewLauncherToDestroy(destroyerId,launcherIdToDestroy);
					setBorder(new LineBorder(new Color(0, 0, 0)));
					destroyLauncherButtonPressed(false);
				}
			}
		});
		
	}
	public void destroyLauncherButtonPressed(boolean isPressed) {
		this.destroyLauncherButtonPressed = isPressed;	
	}

	public void addshipToPanel(String id){		
		JButton ship = new JButton(id,new ImageIcon(LaunchersPanel.class.getResource(SHIP_IMAGE_PATH)));
		ship.setVerticalTextPosition(SwingConstants.BOTTOM);
		ship.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(ship);
		domeQueue.add(ship);
		repaint();
		validate();
		
		ship.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(destroyLauncherButtonPressed){
					String destroyerId = ((JButton) e.getSource()).getText();
					addNewLauncherToDestroy(destroyerId,launcherIdToDestroy);
					setBorder(new LineBorder(new Color(0, 0, 0)));
					destroyLauncherButtonPressed(false);
				}
			}
		});
		
	}

	public void addNewLauncherToDestroy(String destroyerId,
			String launcherIdToDestroy) {
		for (WarUIEventsListener l : allListeners) {
			l.destroyLauncherInUI(0,destroyerId,launcherIdToDestroy);

		}
		
	}

	public void addLauncherDestructorToUI(String destructorId, String type) {
		if(type.equalsIgnoreCase("ship"))
			addshipToPanel(destructorId);
		if(type.equalsIgnoreCase("plane"))
			addf16ToPanel(destructorId);
			
	}

	public void getLauncherDestroyer(String launcherId) {
		JOptionPane.showMessageDialog(null, "Select Destroyer");
		this.setBorder(new LineBorder(new Color(255, 0, 0), 2));
		destroyLauncherButtonPressed = true;
		launcherIdToDestroy = launcherId;
		
	}
}
