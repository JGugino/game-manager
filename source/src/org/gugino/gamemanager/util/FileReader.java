package org.gugino.gamemanager.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FileReader {

	public static Document readXMLFile(File _xmlFile) {
		try {
			DocumentBuilderFactory _docBuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder _docBuilder = _docBuildFactory.newDocumentBuilder();
			Document _doc = _docBuilder.parse(_xmlFile);
			_doc.getDocumentElement().normalize();
			return _doc;
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static File getStreamAsFile(InputStream _inputStream) {
		try {
			File _newFile = File.createTempFile("temp_template", ".xml");
			byte[] _inputBytes = _inputStream.readAllBytes();
			OutputStream _output = new FileOutputStream(_newFile);
			for (int i = 0; i < _inputBytes.length; i++) {
				_output.write(_inputBytes[i]);}
			_output.close();
			return _newFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
