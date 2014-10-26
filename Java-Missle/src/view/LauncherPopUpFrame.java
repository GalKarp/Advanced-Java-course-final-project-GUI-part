package view;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import war.controller.WarUIEventsListener;

public class LauncherPopUpFrame extends JFrame  {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JButton addButton;
	private List<WarUIEventsListener> allListeners;

	public LauncherPopUpFrame(List<WarUIEventsListener> allListeners)
			throws HeadlessException {
		this.allListeners = allListeners;
		setLayout(new FlowLayout());
		txtId = new JTextField(10);
		addButton = new JButton("Add Launcher");
		add(txtId);
		add(addButton);
		setSize(300, 100);
		setLocationRelativeTo(null);
		setVisible(true);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireAddNewLauncherPressed();
			}

		});
	}

	public void fireAddNewLauncherPressed() {
		String id = txtId.getText();
		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must fill a name first!");
			return;
		}
		for (WarUIEventsListener l : allListeners) {
			l.addLauncherToUI(id);
		}

		dispose();

	}

}
