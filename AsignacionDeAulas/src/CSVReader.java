import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
	
	File archivo;
	CSVParser parser;
	
	public CSVReader(String nombreDelArchivo, CSVParser parser) {
        String fileNameDefined = nombreDelArchivo;
        archivo = new File(fileNameDefined);
        this.parser = parser;
	}
	
	
	public void readTXTFile() throws IOException {
		boolean leyoPrimera = false;
		
		try {
		   BufferedReader reader = new BufferedReader(new FileReader(archivo));  
	       for (String line = reader.readLine(); line != null; line = reader.readLine()){
                if(leyoPrimera)
                	parser.parse(line);
                else
                	leyoPrimera = true;
            }
	        reader.close();
	        
	    } catch (IOException e) {
            e.printStackTrace();
        }
	}

}