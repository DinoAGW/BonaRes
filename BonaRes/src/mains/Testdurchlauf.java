package mains;

import java.io.IOException;

import dateiDownload.DateiDownload;
//import utilities.Properties_Manager;
import utilities.Utilities;
import utilities.WebDAV_Manager;

public class Testdurchlauf {
	static String fs = Utilities.fs;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		DateiDownload dateiDownloader = new DateiDownload();
		WebDAV_Manager aWebDAV_Manager = new WebDAV_Manager(System.getProperty("user.home") + fs + "BonaRes_Properties.txt");

		aWebDAV_Manager.anzeige();

		System.out.println("Testdurchlauf Ende.");
	}

}
