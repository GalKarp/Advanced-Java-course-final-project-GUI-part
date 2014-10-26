package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import war.controller.WarUIEventsListener;


public class ControlPanel extends JPanel  {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	public static final String EXIT_IMAGE_PATH = "/drawable/exitButton.png";
	public static final String START_IMAGE_PATH = "/drawable/startButton.png";
	public static final String FIRE_MISSILE_IMAGE_PATH = "/drawable/fireMissile.png";
	public static final String ADD_LAUNCHER_IMAGE_PATH = "/drawable/addNewLauncher.png";
	public static final String INTERCEPT_MISSILE_IMAGE_PATH ="/drawable/interceptMissile.png";
	public static final String ADD_DESTRUCTOR_IMAGE_PATH ="/drawable/addNewDestructor.png";
	public static final String WAR_STATISTICS_IMAGE_PATH ="/drawable/warStatistics.png";	
	public static final String BACKGROUND_IMAGE_PATH = "/drawable/desert.png";
	public static final String CLIENT_IMAGE_PATH = "/drawable/client.jpg";
	
	private JButton exitButton,fireMissileButton;
	private JButton addNewLauncherButton,destoryLauncherButton;
	private JButton openClient,addNewDestructorButton,statisticsButton;	
	private List<WarUIEventsListener> allListeners;
	private WarGui warGui;
	
	
	
	public ControlPanel(List<WarUIEventsListener> allListeners, WarGui warGui) {
		setBackground(Color.DARK_GRAY);
		this.allListeners = allListeners;
		this.warGui = warGui;
		
		exitButton = new JButton();
		fireMissileButton = new JButton();
		addNewLauncherButton = new JButton();
		openClient = new JButton();
		addNewDestructorButton = new JButton();
		statisticsButton = new JButton();
		destoryLauncherButton = new JButton();

		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		
		// Exit Button //
		springLayout.putConstraint(SpringLayout.SOUTH, exitButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, exitButton, -61, SpringLayout.EAST, this);
		exitButton.setIcon(new ImageIcon(ControlPanel.class.getResource(EXIT_IMAGE_PATH)));
		exitButton.setOpaque(false);
		exitButton.setFocusPainted(false);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		validate();
		add(exitButton);
		validate();
		
		
		// Fire Missile Button
		springLayout.putConstraint(SpringLayout.SOUTH, fireMissileButton, -27, SpringLayout.NORTH, destoryLauncherButton);		
		springLayout.putConstraint(SpringLayout.NORTH, fireMissileButton, 22, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, fireMissileButton, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, fireMissileButton, -14, SpringLayout.WEST, addNewLauncherButton);		
		fireMissileButton.setIcon(new ImageIcon(ControlPanel.class.getResource(FIRE_MISSILE_IMAGE_PATH)));
		fireMissileButton.setOpaque(false);
		fireMissileButton.setFocusPainted(false);
		fireMissileButton.setBorderPainted(false);
		fireMissileButton.setContentAreaFilled(false);
		validate();
		add(fireMissileButton);
		
		
		// Add new Launcher Button
		springLayout.putConstraint(SpringLayout.NORTH, addNewLauncherButton, 22, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, addNewLauncherButton, 118, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, addNewLauncherButton, 0, SpringLayout.SOUTH, fireMissileButton);
		springLayout.putConstraint(SpringLayout.EAST, addNewLauncherButton, 0, SpringLayout.EAST, addNewDestructorButton);		
		addNewLauncherButton.setIcon(new ImageIcon(ControlPanel.class.getResource(ADD_LAUNCHER_IMAGE_PATH)));
		addNewLauncherButton.setOpaque(false);
		addNewLauncherButton.setFocusPainted(false);
		addNewLauncherButton.setBorderPainted(false);
		addNewLauncherButton.setContentAreaFilled(false);
		validate();
		add(addNewLauncherButton);				
		
		// OpenClient Button //
		springLayout.putConstraint(SpringLayout.WEST, openClient, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, openClient, -23, SpringLayout.WEST, statisticsButton);
		springLayout.putConstraint(SpringLayout.SOUTH, openClient, -22, SpringLayout.NORTH, exitButton);
		springLayout.putConstraint(SpringLayout.NORTH, openClient, 20, SpringLayout.SOUTH, destoryLauncherButton);
		openClient.setIcon(new ImageIcon(ControlPanel.class.getResource(CLIENT_IMAGE_PATH)));
		openClient.setOpaque(false);
		openClient.setFocusPainted(false);
		openClient.setBorderPainted(false);
		openClient.setContentAreaFilled(false);
		validate();
		add(openClient);
		
		// Add New Destructor Button //
		springLayout.putConstraint(SpringLayout.WEST, addNewDestructorButton, 14, SpringLayout.EAST, destoryLauncherButton);
		springLayout.putConstraint(SpringLayout.SOUTH, addNewDestructorButton, 0, SpringLayout.SOUTH, destoryLauncherButton);	
		springLayout.putConstraint(SpringLayout.EAST, addNewDestructorButton, 0, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, addNewDestructorButton, 138, SpringLayout.NORTH, this);				
		addNewDestructorButton.setIcon(new ImageIcon(ControlPanel.class.getResource(ADD_DESTRUCTOR_IMAGE_PATH)));		
		addNewDestructorButton.setOpaque(false);
		addNewDestructorButton.setFocusPainted(false);
		addNewDestructorButton.setBorderPainted(false);
		addNewDestructorButton.setContentAreaFilled(false);
		validate();
		add(addNewDestructorButton);
		
		
		
		// Statistics Button // 
		springLayout.putConstraint(SpringLayout.WEST, statisticsButton, 127, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, statisticsButton, 0, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, statisticsButton, 248, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, statisticsButton, 0, SpringLayout.SOUTH, openClient);
		statisticsButton.setIcon(new ImageIcon(ControlPanel.class.getResource(WAR_STATISTICS_IMAGE_PATH)));
		statisticsButton.setOpaque(false);
		statisticsButton.setFocusPainted(false);
		statisticsButton.setBorderPainted(false);
		statisticsButton.setContentAreaFilled(false);
		validate();
		add(statisticsButton);
		///////////////////////
		
		
		// Destroy Launcher Button
		springLayout.putConstraint(SpringLayout.SOUTH, destoryLauncherButton, -188, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, destoryLauncherButton, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, destoryLauncherButton, -119, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, destoryLauncherButton, 138, SpringLayout.NORTH, this);
		destoryLauncherButton.setIcon(new ImageIcon(ControlPanel.class.getResource("/drawable/DestroyLauncher.png")));
		destoryLauncherButton.setOpaque(false);
		destoryLauncherButton.setFocusPainted(false);
		destoryLauncherButton.setContentAreaFilled(false);
		destoryLauncherButton.setBorderPainted(false);
		add(destoryLauncherButton);
		
		///////////////////////////////////////////		
		
		
				/** Action Listeners */
		
		
		// ADD NEW LAUNCHER BUTTON ACTION LISTENER
		addNewLauncherButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg) {
				addNewLauncherPopUpFrame();				
			}
		});
		
		// ADD NEW DESTRUCTOR BUTTON ACTION LISTENER
		addNewDestructorButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewDestroyerPopUpFrame();
			}
		});
		
		// FIRE MISSILE BUTTON ACTION LISTENER
		fireMissileButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Select Launcher to shoot from");
				selectLauncherToFireFrom();
			}
		});
		
		// DESTROY LAUNCHER BUTTON ACTION LISTENER
		destoryLauncherButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Select Launcher to Destroy");
				selectLauncherToDestroy();
			}
		});
		
		// STATISTICS BUTTON ACTION LISTENER
		statisticsButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ShowStatistics();
				
			}
		});
		// CLIENT BUTTON ACTION LISTENER		
		openClient.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Select Launcher to shoot from");
				selectLauncherToFireFromWithFX();
			}
		});
		
		
		// EXIT BUTTON ACTION LISTENER		
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				finishProgram();
				
			}
		});
	}
	/** close all open threads */
	public void finishProgram() {
		for (WarUIEventsListener l : allListeners) {
			l.finishProgram();
		}
	}

	public void selectLauncherToDestroy() {
		warGui.selectLauncherToDestroy();
		
	}

	public void selectLauncherToFireFrom() {
		warGui.selectLauncherToFireFrom();
		
	}
	public void selectLauncherToFireFromWithFX() {
		warGui.selectLauncherToFireFromWithFX();
		
	}

	public void addNewDestroyerPopUpFrame() {
			new DestroyerPopUpFrame(allListeners);
	}

	public void addNewLauncherPopUpFrame() {
		new LauncherPopUpFrame(allListeners);		
	}

	public void addMessageToGui(String string) {
	    JOptionPane.showMessageDialog(null, string);
	    
	}
	
	/** get statistics from warModel */
	public void ShowStatistics() {
		int[] statistics = new int[5];				
		for (WarUIEventsListener l : allListeners) {
			statistics = l.getStatistics();
		}		
	//[0] - Missiles launched
	//[1] - Missiles destroyed
	//[2] - Missiles hit
	//[3] - Launchers destroyed
	//[0] - Total Damage			
		new StatisticsPopUpFrame(statistics[0], statistics[1], statistics[2], statistics[3], statistics[4]);

	}
	
   

}


