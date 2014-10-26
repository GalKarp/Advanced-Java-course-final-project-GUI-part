package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import war.controller.WarUIEventsListener;

public class ProgressPanel extends JPanel {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	public static final String Intercepting = "/drawable/stop-sign-round-sticker2.png";
	private WarGui warGui;
	private JLabel label;
	private JButton button;
	private JProgressBar progressBar;
	private String ironDomeId;
    private	HashMap<String, JProgressBar> map = new HashMap<String, JProgressBar>();
	private HashMap<String, JProgressBar> destructors = new HashMap<String, JProgressBar>();
	private Font myFont = new Font("Tahoma", Font.BOLD, 14);

	public ProgressPanel(List<WarUIEventsListener> allListeners, WarGui warGui) {
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setVisible(true);
		this.warGui = warGui;

	}

	public void addMissileToProgressBar(final String missileId,
			String destination, int damage, int flyTime) {
		progressBar = new JProgressBar(0, flyTime - 1);
		progressBar.setLayout(new BorderLayout(5, 5));
		progressBar.setPreferredSize(new Dimension(600, 50));
		label = new JLabel();
		button = new JButton();

		try {
			Image img = ImageIO.read(getClass().getResource(Intercepting));
			button.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		button.setPreferredSize(new Dimension(50, 40));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Interception(missileId);
			}

		});

		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setBorder(new EmptyBorder(15, 15, 15, 15));
		label.setFont(myFont);
		progressBar.add(label, BorderLayout.CENTER);
		progressBar.add(button, BorderLayout.EAST);

		add(progressBar);
		map.put(missileId, progressBar);
		validate();
	}

	public void Interception(String missileId) {

		Iterator<String> keySetIterator = map.keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			if (key.equalsIgnoreCase(missileId)) {

				// sending missile details to the relevant GUI launcher
				JOptionPane.showMessageDialog(null,
						"Select Iron dome to shoot from");
				selectIronDomeToFireFrom(missileId);

			}
		}

	}

	private void selectIronDomeToFireFrom(String missileId) {
		warGui.selectIronDomeToFireFrom(missileId);

	}

	public void destroyProgress(String missileId, String type) {

		Iterator<String> keySetIterator = map.keySet().iterator();
		Iterator<String> destructorsSetIterator = destructors.keySet()
				.iterator();
		if (type == "missile") {
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				progressBar = map.get(key);
				if (key.equalsIgnoreCase(missileId)) {
					progressBar.setVisible(false);
				}
			}
		} else if (type == "destructor") {
			while (destructorsSetIterator.hasNext()) {
				String key = destructorsSetIterator.next();
				progressBar = map.get(key);
				if (key.equalsIgnoreCase(missileId)) {
					progressBar.setVisible(false);
				}
			}
		} else if (type == "IronDome") {
			while (destructorsSetIterator.hasNext()) {
				String key = destructorsSetIterator.next();
				progressBar = map.get(key);
				if (key.equalsIgnoreCase(missileId)) {
					progressBar.setVisible(false);

				}
			}
		}

	}

	public void addDestryoerToProgressBar(String destructor_id,
			String target_id, int destruct_time) {
		progressBar = new JProgressBar(0, destruct_time - 1);
		progressBar.setLayout(new BorderLayout(5, 5));
		progressBar.setPreferredSize(new Dimension(600, 50));
		label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setBorder(new EmptyBorder(15, 15, 15, 15));
		label.setFont(myFont);
		progressBar.add(label, BorderLayout.CENTER);
		add(progressBar);
		destructors.put(target_id, progressBar);
		validate();
	}

	public void updateMissileTime(int time, String missileId, String type,
			String destination, int damage, int flyTime) {
		Iterator<String> keySetIterator = map.keySet().iterator();
		Iterator<String> destuctorsSetIterator = destructors.keySet()
				.iterator();
		if (type == "missile") {
			while (keySetIterator.hasNext()) { // for missiles
				String key = keySetIterator.next();
				if (key.equalsIgnoreCase(missileId)) {
					progressBar = map.get(key);
					progressBar.setForeground(Color.LIGHT_GRAY);
					progressBar.setValue(time);
					JLabel temp = (JLabel) progressBar.getComponent(0);
					temp.setText("Missile id#" + missileId + " Will Hit "
							+ destination + " for " + damage + " damage in "
							+ (flyTime - time) + "s");

				}

			}
		} else if (type == "launcherDestroyer") {
			while (destuctorsSetIterator.hasNext()) { // for desturctors
				String key = destuctorsSetIterator.next();
				if (key.equalsIgnoreCase(missileId)) {
					progressBar = destructors.get(key);
					progressBar.setForeground(Color.CYAN);
					progressBar.setValue(time);
					JLabel temp = (JLabel) progressBar.getComponent(0);
					temp.setText(destination + " Will Hit Launcher #"
							+ missileId + " in " + (flyTime - time) + "s");

				}

			}
		} else if (type == "IronDome") {
			while (destuctorsSetIterator.hasNext()) { // for desturctors
				String key = destuctorsSetIterator.next();
				if (key.equalsIgnoreCase(missileId)) {
					progressBar = destructors.get(key);
					progressBar.setForeground(Color.CYAN);
					progressBar.setValue(time);
					JLabel temp = (JLabel) progressBar.getComponent(0);
					temp.setText(destination + " Will Hit Missile #"
							+ missileId + " in " + (flyTime - time) + "s");

				}

			}
		}

	}

	public void getIronDome(String ironDomeId) {
		this.ironDomeId = ironDomeId;

	}

	public void RemoveCurrentElement(String destructorId) {
		Iterator<String> destructorsSetIterator = destructors.keySet()
				.iterator();
		while (destructorsSetIterator.hasNext()) {
			String key = destructorsSetIterator.next();
			if (key.equalsIgnoreCase(destructorId)) {
				progressBar.setVisible(false);
			}
		}

	}

}
