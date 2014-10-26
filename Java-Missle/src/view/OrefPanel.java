package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OrefPanel extends JPanel   {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	public static final String OREF_IMAGE_PATH = "/drawable/orefImage225x70.png";
	public static final String OREF_ALERT_IMAGE_PATH = "/drawable/orefImage225x70Alert.png";
	public static final int ALERT_DISPLAY_TIME = 8;
	private JTextField topAlert, middleAlert, buttomAlert;
	private JLabel orefImageLable;
	private Font font = new Font("SansSerif", Font.BOLD, 20);

	public OrefPanel() {
		setBackground(Color.DARK_GRAY);
		this.setBorder(null);
		setLayout(new GridLayout(4, 1));

		orefImageLable = new JLabel();
		orefImageLable.setIcon(new ImageIcon(OrefPanel.class
				.getResource(OREF_IMAGE_PATH)));
		add(orefImageLable);

		topAlert = createNewAlert();
		middleAlert = createNewAlert();
		buttomAlert = createNewAlert();

		add(topAlert);
		add(middleAlert);
		add(buttomAlert);

	}

	public JTextField createNewAlert() {
		JTextField textBox = new JTextField("");
		textBox.setBackground(new Color(255, 127, 80));
		textBox.setColumns(10);
		textBox.setFont(font);
		textBox.setForeground(Color.WHITE);
		textBox.setHorizontalAlignment(JTextField.CENTER);
		textBox.setEditable(false);
		return textBox;
	}

	public void addMissileToOrefPanel(String destination, int time, int flyTime) {
		// exclude messages with # - to prevent showing interception alerts
		if(!(destination.contains ("#"))){
		// time % 2 - to make the alert blink every second
		if (time < ALERT_DISPLAY_TIME && time % 2 == 0) {
			topAlert.setText("Alert in " + destination);
			validate();
			setOrefImage(OREF_ALERT_IMAGE_PATH);
		} else if (time < ALERT_DISPLAY_TIME) {
			setOrefImage(OREF_ALERT_IMAGE_PATH);
			topAlert.setText("");

		} else {
			// the alert is finished, free the topAlert Label
			setOrefImage(OREF_IMAGE_PATH);
			topAlert.setText("");

		}
		if (time == flyTime) {
			// if missile flyTime is less then ALERT_DISPLAY_TIME
			topAlert.setText("");
			setOrefImage(OREF_IMAGE_PATH);

		}
	}
	}

	private void setOrefImage(String orefImagePath) {
		orefImageLable.setIcon(new ImageIcon(OrefPanel.class
				.getResource(orefImagePath)));

	}

}
