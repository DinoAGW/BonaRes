package dateiDownload;

import java.io.IOException;
//import java.util.List;
//
//import com.github.sardine.DavResource;
//import com.github.sardine.Sardine;
//import com.github.sardine.SardineFactory;

import utilities.Utilities;
import utilities.WebDAV_Manager;

public class DateiDownload {
	String fs = Utilities.fs;
	WebDAV_Manager aWebDAV_Manager;
	
	public DateiDownload() throws IOException {
		this.aWebDAV_Manager = new WebDAV_Manager(System.getProperty("user.home") + fs + "BonaRes_Properties.txt");
	}
}
