package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.List;

import javax.annotation.Resources;
import javax.net.ssl.SSLSocketFactory;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

@SuppressWarnings("unused")
/*
 * Was nötig war: URL im Browser eingeben. Zertifikat anschauen -> Details In
 * Datei kopieren... weiter... Der -codiert-binär X.509 (.CER)... Und unter
 * C:\Del als bonares abspeichern. cmd mit Adminrechten ausführen...
 * C:\Windows\system32>keytool -importcert -trustcacerts -alias bonares -file
 * C:\Del\bonares.cer -keystore "%java_home%jre/lib/security/cacerts" -storepass
 * changeit eingeben und Zertifikat vertrauen.
 * 
 * gute Infos:
 * https://github.com/lookfirst/sardine/wiki/UsageGuide
 */
public class WebDAV_Manager {
	String URL;
	String Benutzername;
	String Password;
	String fs;
	String Pfad;
	int overlap;

	public WebDAV_Manager(String propertyDateiPfad) throws IOException {
		this.fs = Utilities.fs;
		Properties_Manager prop = new Properties_Manager(propertyDateiPfad);
		this.URL = prop.readStringFromProperty("URL") + prop.readStringFromProperty("URL2");
		this.overlap = prop.readStringFromProperty("URL2").length();
		this.Benutzername = prop.readStringFromProperty("Benutzername");
		this.Password = prop.readStringFromProperty("Password");
		this.Pfad = "/";
	}

	public void anzeige() throws IOException {
		List<DavResource> resources;
		Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
		resources = sardine.list(this.URL + this.Pfad);
		System.out.println("Inhalt von: " + this.Pfad);
		for (DavResource res : resources) {
			String ausgabe = res.toString();
			ausgabe = ausgabe.substring(this.Pfad.length() + this.overlap);
			if (ausgabe.length() > 0)
				System.out.println("*" + ausgabe);
		}
	}

	public void cd(String folder) throws IOException {
		List<DavResource> resources;
		Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
		if (folder == "..") {
			int stelle = this.Pfad.indexOf("/", 1);
			if (stelle > 0) {
				this.Pfad = this.Pfad.substring(stelle);
			} else {
				System.out.println("kann bei Pfad " + this.Pfad + " nicht weiter hochgehen.");
			}
		} else {
			if (!folder.endsWith("/")) {
				folder += "/";
			}
			if (sardine.exists(this.URL + this.Pfad + folder)) {
				this.Pfad = this.Pfad + folder;
			} else {
				System.out.println("Dieser Ordner existiert nicht.");
			}
		}
		resources = sardine.list(this.URL + this.Pfad);
		System.out.println("Inhalt von: " + this.Pfad);
		for (DavResource res : resources) {
			String ausgabe = res.toString();
			ausgabe = ausgabe.substring(this.Pfad.length() + this.overlap);
			if (ausgabe.length() > 0)
				System.out.println("*" + ausgabe);
		}
	}

	/*
	 * ob wohl auch ein anderer Pfad klappt als unten? Anderer Dateiname wird nicht
	 * supported.
	 */
	public void download(String resource, String zielordner) throws IOException {
		String dateiname = resource.substring(resource.lastIndexOf("/") + 1);
		Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
		InputStream is = sardine.get(this.URL + this.Pfad + resource);
		File file = new File(zielordner + dateiname);
		Utilities.copyInputStreamToFile(is, file);
	}

	public void download(String resource) throws IOException {
		String zielordner = System.getProperty("user.home") + this.fs + "Downloads/";
		download(resource, zielordner);
	}

	public void upload(String quelldatei) throws IOException {
		String dateiname = quelldatei.substring(quelldatei.lastIndexOf(this.fs) + 1);
		Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
		InputStream fis = new FileInputStream(new File(quelldatei));
		sardine.put(this.URL + this.Pfad + dateiname, fis);
	}

	public void delete(String datei) throws IOException {
		Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
		sardine.delete(this.URL + this.Pfad + datei);
	}
}
