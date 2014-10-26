package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import war.controller.WarUIEventsListener;

public class DestroyerPopUpFrame extends JFrame   {
	/**
     * 
     */
    private static final long 			serialVersionUID = 1L;
	private List<WarUIEventsListener> 	allListeners;
	private JRadioButton 				ironDomeRadioButton;
	private JRadioButton 				shipRadioButton;
	private JRadioButton 				planeDomeRadioButton;
	private JLabel 						top;
	private JLabel 						center;
	private JTextField 					txtId;
	private JButton 					addButton;
	private String 						destroyerType;

	/** constructor */
	public DestroyerPopUpFrame(List<WarUIEventsListener> allListeners) {
		this.allListeners = allListeners;
		center = new JLabel();
		center.setLayout(new FlowLayout());
		addButton = new JButton("add");
		txtId = new JTextField(8);

		ironDomeRadioButton = new JRadioButton("Iron Dome");
		shipRadioButton = new JRadioButton("Ship");
		planeDomeRadioButton = new JRadioButton("Plane");

		setLayout(new GridLayout(2, 1));
		top = new JLabel();
		top.setLayout(new FlowLayout());

		ButtonGroup bg = new ButtonGroup();

		bg.add(ironDomeRadioButton);
		bg.add(shipRadioButton);
		bg.add(planeDomeRadioButton);

		top.add(ironDomeRadioButton);
		top.add(shipRadioButton);
		top.add(planeDomeRadioButton);

		getContentPane().add(top);

		//Iron Dome Radio Button Action Listener
		ironDomeRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showIronDomeLable();

			}
		});
		
		//Ship Radio Button Action Listener
		shipRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showShipLable();

			}
		});
		
		//Plane Radio Button Action Listener
		planeDomeRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showPlaneLable();

			}
		});

		// Add selected Destructor button Action Listener
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireAddnewDestroyerPressed();

			}
		});

		setSize(500, 250);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** Message to controller that Destroyer is added in UI */
	public void fireAddnewDestroyerPressed() {
		destroyerType = null;
		String id = txtId.getText();
		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must fill a name first!");
			return;
		}
		if (ironDomeRadioButton.isSelected()) {
			destroyerType = ironDomeRadioButton.getText();
		} else if (shipRadioButton.isSelected()) {
			destroyerType = shipRadioButton.getText();
		} else if (planeDomeRadioButton.isSelected()) {
			destroyerType = planeDomeRadioButton.getText();
		}
		
		for (WarUIEventsListener l : allListeners) {
			l.addDestructorToUI(id,destroyerType);
		}

		dispose();
		

	}

	public void showPlaneLable() {
		center.removeAll();
		center.add(new JLabel("Enter Plane Id"));
		center.add(txtId);
		center.add(addButton);
		center.setVisible(true);
		add(center);
		repaint();
		validate();
	}

	public void showShipLable() {
		center.removeAll();
		center.add(new JLabel("Enter ship Id"));
		center.add(txtId);
		center.add(addButton);
		center.setVisible(true);
		add(center);
		repaint();
		validate();
	}

	public void showIronDomeLable() {
		center.removeAll();
		center.add(new JLabel("Enter Iron Dome id"));
		center.add(txtId);
		center.add(addButton);
		center.setVisible(true);
		add(center);
		repaint();
		validate();
	}

}
