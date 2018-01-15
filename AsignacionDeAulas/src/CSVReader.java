import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVReader {
	
	File archivo;
	CSVParser parser;
	
	public CSVReader(String nombreDelArchivo, CSVParser parser) {
        String fileNameDefined = nombreDelArchivo;
        archivo = new File(fileNameDefined);
        this.parser = parser;
	}
	
	public void read() {
		boolean leyoPrimera = false;
		try{
            Scanner inputStream = new Scanner(archivo);
            while(inputStream.hasNext()){
                String data = inputStream.next();
                if(leyoPrimera)
                	parser.parse(data);
                else
                	leyoPrimera = true;
            }
            inputStream.close();
        }
		catch (FileNotFoundException e){
			e.printStackTrace();
        }
	}

}