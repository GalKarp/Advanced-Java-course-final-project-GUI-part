package view;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StatisticsPopUpFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField launchedMissiles;
	private JTextField destroyedMissiles;
	private JTextField hitMissiles;
	private JTextField destroyedLaunchers;
	private JTextField totalDamage;
	
	public StatisticsPopUpFrame(int launched_m, int destroyed_m, int hit_m, int destroyed_l, int total_d) {
		
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel launched_missiles = new JLabel("Total Launched Missiles");
		springLayout.putConstraint(SpringLayout.NORTH, launched_missiles, 7, SpringLayout.NORTH, getContentPane());
		getContentPane().add(launched_missiles);
		
		launchedMissiles = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, launchedMissiles, -3, SpringLayout.NORTH, launched_missiles);
		springLayout.putConstraint(SpringLayout.EAST, launchedMissiles, -74, SpringLayout.EAST, getContentPane());
		getContentPane().add(launchedMissiles);
		launchedMissiles.setColumns(10);
		
		JLabel destroyed_missiles = new JLabel("Total Destroyed Missiles");
		springLayout.putConstraint(SpringLayout.NORTH, destroyed_missiles, 31, SpringLayout.SOUTH, launched_missiles);
		springLayout.putConstraint(SpringLayout.WEST, launched_missiles, 0, SpringLayout.WEST, destroyed_missiles);
		getContentPane().add(destroyed_missiles);
		
		destroyedMissiles = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, destroyed_missiles, -78, SpringLayout.WEST, destroyedMissiles);
		springLayout.putConstraint(SpringLayout.NORTH, destroyedMissiles, -3, SpringLayout.NORTH, destroyed_missiles);
		springLayout.putConstraint(SpringLayout.EAST, destroyedMissiles, 0, SpringLayout.EAST, launchedMissiles);
		getContentPane().add(destroyedMissiles);
		destroyedMissiles.setColumns(10);
		
		JLabel missiles_hit = new JLabel("Total Missiles Hit");
		springLayout.putConstraint(SpringLayout.WEST, missiles_hit, 0, SpringLayout.WEST, launched_missiles);
		getContentPane().add(missiles_hit);
		
		hitMissiles = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, hitMissiles, -3, SpringLayout.NORTH, missiles_hit);
		springLayout.putConstraint(SpringLayout.EAST, hitMissiles, 0, SpringLayout.EAST, launchedMissiles);
		getContentPane().add(hitMissiles);
		hitMissiles.setColumns(10);
		
		JLabel destroyed_launchers = new JLabel("Total Destroyed Launchers");
		springLayout.putConstraint(SpringLayout.NORTH, destroyed_launchers, 162, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, missiles_hit, -40, SpringLayout.NORTH, destroyed_launchers);
		springLayout.putConstraint(SpringLayout.WEST, destroyed_launchers, 0, SpringLayout.WEST, launched_missiles);
		getContentPane().add(destroyed_launchers);
		
		destroyedLaunchers = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, destroyedLaunchers, -3, SpringLayout.NORTH, destroyed_launchers);
		springLayout.putConstraint(SpringLayout.EAST, destroyedLaunchers, 0, SpringLayout.EAST, launchedMissiles);
		getContentPane().add(destroyedLaunchers);
		destroyedLaunchers.setColumns(10);
		
		JLabel total_damage = new JLabel("Total Damage");
		springLayout.putConstraint(SpringLayout.NORTH, total_damage, 42, SpringLayout.SOUTH, destroyed_launchers);
		springLayout.putConstraint(SpringLayout.WEST, total_damage, 0, SpringLayout.WEST, launched_missiles);
		getContentPane().add(total_damage);
		
		totalDamage = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, totalDamage, -3, SpringLayout.NORTH, total_damage);
		springLayout.putConstraint(SpringLayout.EAST, totalDamage, 0, SpringLayout.EAST, launchedMissiles);
		getContentPane().add(totalDamage);
		totalDamage.setColumns(10);
		
		launchedMissiles.setHorizontalAlignment(JTextField.CENTER);
		destroyedMissiles.setHorizontalAlignment(JTextField.CENTER);
		hitMissiles.setHorizontalAlignment(JTextField.CENTER);
		destroyedLaunchers.setHorizontalAlignment(JTextField.CENTER);
		totalDamage.setHorizontalAlignment(JTextField.CENTER);
		
		launchedMissiles.setText(Integer.toString(launched_m));
		destroyedMissiles.setText(Integer.toString(destroyed_m));
		hitMissiles.setText(Integer.toString(hit_m));
		destroyedLaunchers.setText(Integer.toString(destroyed_l));
		totalDamage.setText(Integer.toString(total_d));
		
		setResizable(false);
		setSize(450, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
	}
}
