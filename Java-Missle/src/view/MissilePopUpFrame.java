package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import war.controller.WarUIEventsListener;

public class MissilePopUpFrame extends JFrame   {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private JTextField txtId, txtDamage, txtDest, txtFlyTime;
	private String launcherId;
	private JButton addButton;
	private List<WarUIEventsListener> allListeners;
	private LaunchersPanel launchersPanel;
	private JLabel idLable;
	private JLabel destinationLable;
	private JLabel damageLable;
	private JLabel lblNewLabel_3;
	private JLabel blankLable;

	public MissilePopUpFrame(List<WarUIEventsListener> allListeners,
			String launcherId, LaunchersPanel launchersPanel)
			throws HeadlessException {
		this.launchersPanel = launchersPanel;
		this.allListeners = allListeners;
		this.launcherId = launcherId;
		getContentPane().setLayout(new FlowLayout(10));

		txtId = new JTextField(15);
		txtId.setText("Enter missile id");
		mouseListener(txtId);
		focusListener(txtId);
		txtDest = new JTextField(15);
		txtDest.setText("Enter missile destination");
		mouseListener(txtDest);
		focusListener(txtDest);
		txtDamage = new JTextField(15);
		txtDamage.setText("Enter missile damage");
		mouseListener(txtDamage);
		focusListener(txtDamage);
		txtFlyTime = new JTextField(15);
		txtFlyTime.setText("Enter missile fly time");
		mouseListener(txtFlyTime);
		focusListener(txtFlyTime);

		addButton = new JButton("FIRE");
		addButton.setForeground(Color.RED);
		
		idLable = new JLabel("Id                  ");
		getContentPane().add(idLable);
		getContentPane().add(txtId);
		
		destinationLable = new JLabel("Destination");
		getContentPane().add(destinationLable);
		getContentPane().add(txtDest);
		
		damageLable = new JLabel("Damage      ");
		getContentPane().add(damageLable);
		getContentPane().add(txtDamage);
		
		lblNewLabel_3 = new JLabel("Fly time       ");
		getContentPane().add(lblNewLabel_3);
		getContentPane().add(txtFlyTime);
		
		blankLable = new JLabel("                           ");
		getContentPane().add(blankLable);
		getContentPane().add(addButton);
		
		setSize(270, 200);
		setLocationRelativeTo(null);
		setVisible(true);
		getRootPane().setDefaultButton(addButton);
		requestFocus();
		
		// WINDOW LISTENER to reset border and boolean values
		// when user press the X button to close the window
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				closeFrame();
			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireAddNewMissilePressed();

			}

		});
	}

	public void fireAddNewMissilePressed() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// while (true) {

				String id = txtId.getText();
				String dest = txtDest.getText();
				String damage = txtDamage.getText();
				String flyTime = txtFlyTime.getText();
				if (id.isEmpty() || dest.isEmpty() || damage.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"You must fill all details!");
					return;
				}
				launchersPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
				for (WarUIEventsListener l : allListeners) {
					// sending missile details to the relevant GUI launcher
					l.addMissileToUI(id, dest, damage, flyTime, launcherId);

				}

				closeFrame();
				// }
			}
		}).start();
		setVisible(false);
	}

	public void mouseListener(final JTextField txtBox) {
		txtBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtBox.setText("");
			}
		});
	}

	public void focusListener(final JTextField txtBox) {
		txtBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				txtBox.setText("");

			}
		});
	}

	public void closeFrame() {
		launchersPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		launchersPanel.setFireMissileButtonPressed(false);
		dispose();

	}
}
