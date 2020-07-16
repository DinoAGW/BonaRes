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
//		this.URL = "https://owncloud.bonares.de/index.php/apps/files?dir=/&fileid=101358";
		this.Benutzername = prop.readStringFromProperty("Benutzername");
		this.Password = prop.readStringFromProperty("Password");
	}

	public void anzeige() throws IOException {
//		String keyStoreFilename = System.getProperty("user.home") + fs + "OwnCloud.cer";
//		File keystoreFile = new File(keyStoreFilename);
//		FileInputStream fis = new FileInputStream(keystoreFile);		
//		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // JKS
//		keyStore.load(fis, null);		
//		final SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore);				
//			
//		Sardine sardine = new SardineImpl() {
//			@Override
//			protected SSLSocketFactory createDefaultSecureSocketFactory() {
//				return socketFactory;
//			}			
//		};
		
		Sardine sardine = SardineFactory.begin(this.Benutzername, this.Password);
		System.out.println(this.URL);
		List<DavResource> resources = sardine.list(this.URL);
		for (DavResource res : resources)
		{
		     System.out.println(res);
		}
		
		System.out.println("Anzeige Ende.");
	}

}
