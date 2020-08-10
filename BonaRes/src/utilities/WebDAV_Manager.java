package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

@SuppressWarnings("unused")
public class WebDAV_Manager {
	String URL;
	String Benutzername;
	String Password;
	String fs;

	public WebDAV_Manager(String propertyDateiPfad) throws IOException {
		this.fs = Utilities.fs;
		Properties_Manager prop = new Properties_Manager(propertyDateiPfad);
		this.URL = prop.readStringFromProperty("URL");
		this.Benutzername = prop.readStringFromProperty("Benutzername");
		this.Password = prop.readStringFromProperty("Password");
	}

	/*
	 * Was nötig war:
	 * URL im Browser eingeben.
	 * Zertifikat anschauen -> Details
	 * In Datei kopieren... weiter...
	 * Der -codiert-binär X.509 (.CER)...
	 * Und unter C:\Del als bonares abspeichern.
	 * cmd mit Adminrechten ausführen...
	 * C:\Windows\system32>keytool -importcert -trustcacerts -alias bonares -file C:\Del\bonares.cer -keystore "%java_home%jre/lib/security/cacerts" -storepass changeit
	 * eingeben und Zertifikat vertrauen.
	 */
	public void anzeige() throws IOException {

		try {
			Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
			//System.out.println(this.URL);
			List<DavResource> resources = sardine.list(this.URL);
			for (DavResource res : resources) {
				System.out.println(res);
			}
		} catch(Exception e) {
			System.out.println("Fehler aufgetreten:");
			System.out.println(e);
		}

		System.out.println("Anzeige Ende.");
	}

}
