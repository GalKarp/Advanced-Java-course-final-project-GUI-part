package main;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import view.WarGui;
import war.War;
import war.controller.AbstractWarView;
import war.controller.WarController;
import war.db.WarDBConnection;

public class Program {
	/**
     * 
     */
	public static final String 			LAUNCHER = "launcher";
	public static final String 			MISSILE = "missile";
	public static final int 			TAKES_TIME_MIN = 4;
	public static final int 			TAKES_TIME_MAX = 6;

	public static void main(String[] args) {
		
		try {
			War warModel = new War();
			AbstractWarView viewGui = new WarGui();	
			WarController controller = new WarController(warModel,viewGui);
			
			
			new XMLparser(controller, warModel);			
		    new WarDBConnection();
		    
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	

}