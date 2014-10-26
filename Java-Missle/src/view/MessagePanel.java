package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import war.controller.WarUIEventsListener;

public class MessagePanel extends JPanel   {
    
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private JLabel label;
	private boolean fireMissileButtonPressed;
	private JPanel panel;
	
	public MessagePanel(List<WarUIEventsListener> allListeners, WarGui warGui) {
		setLayout(new BorderLayout(0, 0));
		

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(305, 100));
		add(scrollPane, BorderLayout.CENTER);
	}

	public boolean isFireMissileButtonPressed() {
		return fireMissileButtonPressed;
	}

	public void setFireMissileButtonPressed(boolean fireMissileButtonPressed) {
		this.fireMissileButtonPressed = fireMissileButtonPressed;
	}

	public void addMessageToPanel(String id) {
	    	label = new JLabel();
		label.setText(id);
		label.setToolTipText(id);
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 30));
		panel.add(label);
		repaint();
		validate();


	}

}
