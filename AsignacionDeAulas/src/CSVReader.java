import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVReader {
	File archivo;
	
	public CSVReader(String nombreDelArchivo) {
        String fileNameDefined = nombreDelArchivo;
        archivo = new File(fileNameDefined);
	}
	
	public String leerCSV() {
		String ret = "";
		try{
            Scanner inputStream = new Scanner(archivo);
            while(inputStream.hasNext()){
                String data = inputStream.next();
                ret = ret + "\n" + data  ;
            }
            inputStream.close();
        }
		catch (FileNotFoundException e){
			e.printStackTrace();
        }
		return ret; 
	}

}