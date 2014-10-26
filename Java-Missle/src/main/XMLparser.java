package main;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import launcher.Destructor;
import launcher.Launcher;
import missile.AbstractMissile;
import missile.DestructedLanucher;
import missile.DestructedMissile;
import missile.Missile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import war.War;
import war.WarUtility;
import war.controller.WarController;

public class XMLparser {
	private War war;
	private WarController 		controller;
	
	public XMLparser(WarController controller,War war) throws ParserConfigurationException, SAXException,
			IOException {
		this.controller = controller;
		this.war = war;
		readXML();
	}

	public War readXML() throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Get the DOM Builder
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		InputStream xml_file = ClassLoader
				.getSystemResourceAsStream("war.xml");
		//check if we have xml file
		if (xml_file == null) {
			return war;
		}
		Document document = builder.parse(xml_file);
		// Load and Parse the XML document
		// document contains the complete XML as a Tree.
		// Iterating through the nodes and extracting the data.
		NodeList rootNodeList = document.getDocumentElement().getChildNodes();
		// first we loop on 3 main array lists
		for (int i = 0; i < rootNodeList.getLength(); i++) {
			Node rootNode = rootNodeList.item(i);
			NodeList nodeList = rootNode.getChildNodes();// list of launchers

			// loop on each list to add launchers or destructors
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node childNodeList = nodeList.item(j);
				this.addLauncherToArray(childNodeList, rootNode);

				// on each launcher or destructor we add a missile to it
				NodeList leafNode = childNodeList.getChildNodes();
				for (int k = 0; k < leafNode.getLength(); k++) {
					Node missile = leafNode.item(k);
					this.addMissileToArray(missile, j);
				}
			}
		}
		return war;
	}

	/**
	 * add a launcher or destructor to array
	 * @param launcher - a launcher or destructor we want to add to list
	 * @param rootNode - one of the 3 root array lists
	 * @throws SecurityException
	 * @throws IOException
	 */
	private void addLauncherToArray(Node launcher, Node rootNode)
			throws SecurityException, IOException {
		if (launcher instanceof Element) {
			String id = launcher.getAttributes().getNamedItem("id")
					.getNodeValue();
			if (launcher.getNodeName().equals("launcher")) {
				boolean isHidden = Boolean.parseBoolean(launcher
						.getAttributes().getNamedItem("isHidden")
						.getNodeValue());
				war.addLauncher(new Launcher(id, isHidden,controller.getWarListeners() ));
			} else {
				String type = launcher.getAttributes().getNamedItem("type")
						.getNodeValue();
				if ((rootNode.getNodeName().equals("missileDestructors"))) {
				    war.addDestructor(id, type);

				} else {

					war.addDestructor(id, type);
				}
			}
		}
	}

	/**
	 * This method add a missile to the launcher's or destructor's array
	 * 
	 * @param missile - the missile to add
	 * @param index - the index inside war's arrays
	 */
	private void addMissileToArray(Node missile, int index) {
		if (missile instanceof Element) {
			String id = missile.getAttributes().getNamedItem("id")
					.getNodeValue();
			switch (missile.getNodeName()) {
			case "missile":
				// case one it is a missile so need to add it to
				// missile list
				String destination = missile.getAttributes()
						.getNamedItem("destination").getNodeValue();
				String launchtime = missile.getAttributes()
						.getNamedItem("launchTime").getNodeValue();
				int lt = Integer.parseInt(launchtime);
				String flytime =missile.getAttributes()
						.getNamedItem("flyTime").getNodeValue();
				int ft = Integer.parseInt(flytime);
				String damage = missile.getAttributes()
						.getNamedItem("damage").getNodeValue();
				int dm = Integer.parseInt(damage);

				// get the launcher and add missile to it
				Launcher launcher = war.getMissileLaunchers().get(index / 2);				
				launcher.addMissile(id, destination , lt , ft ,dm );
				break;
			case "destructdMissile":
				// case 2 it is a missle to destruct missles need
				// to add to destructors list
				int destructAfterLaunch = Integer.parseInt(missile
						.getAttributes().getNamedItem("destructAfterLaunch")
						.getNodeValue());
				// get the destructor and then add missile destructor to it
				
				Destructor destructor_m = war.getMissileDestructors().get(index / 2);
				Missile target_m = WarUtility.getMissileById(id, war);
				int destruct_time = 10;
//				(int) (War.TAKES_TIME_MIN + (Math.random() * (War.TAKES_TIME_MAX
//						- War.TAKES_TIME_MIN + 1)));
				DestructedMissile destructedM = new DestructedMissile(destructAfterLaunch,target_m,destruct_time, destructor_m, destructor_m.getFileHandler(),controller.getWarListeners());
				destructor_m.addDestructMissile(destructedM);
				war.startMissileInterception(destructAfterLaunch, target_m.getMissileId(), destructor_m.getDestructorId());

			
				break;
			case "destructedLanucher":
				// case 3 it is a missle to destruct launchers need
				// to add to destructors list
				destructAfterLaunch = Integer.parseInt(missile.getAttributes()
						.getNamedItem("destructTime").getNodeValue());
				// get the destructor and then add missile launcher destructor
				// to it
				Destructor destructor_l = war.getMissileLauncherDestructors().get(index / 2);
				Launcher target_l = WarUtility.getLauncherById(id, war);
				destruct_time = 10;
//				(int) (War.TAKES_TIME_MIN + (Math.random() * (War.TAKES_TIME_MAX
//						- War.TAKES_TIME_MIN + 1)));
				DestructedLanucher destructedL = new DestructedLanucher(destructAfterLaunch,target_l, 
						destruct_time, destructor_l, destructor_l.getFileHandler(),controller.getWarListeners());
				destructor_l.addDestructMissile(destructedL);
				break;
			}
		}
	}

}
