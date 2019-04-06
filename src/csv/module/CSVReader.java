package csv.module;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	
	File archivo;
	CSVParser parser;
	
	public CSVReader(String nombreDelArchivo, CSVParser parser) {
        String fileNameDefined = nombreDelArchivo;
        archivo = new File(fileNameDefined);
        this.parser = parser;
	}
	
	public void read() throws IOException {
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