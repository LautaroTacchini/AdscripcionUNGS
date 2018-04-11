import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aulas.Solver;
import exactas.Exactas;

public class FileManager {

	File[] archivos;
	File directorio;
	
	public FileManager(String nombreDirectorio) {
		directorio = new File(nombreDirectorio);
	}
	
	// Esta es la función principal, recorre todos los archivos que se encuentren en
	// el directorio que se reciba por parámetro. 
	public void recorrerArchivos() {
		archivos = directorio.listFiles(); 
			
		for (int i=0; i<archivos.length; i++){
			// TODO estos sysout quedan medio mal aca, ver si se puede reemplazar o sacar.
			copyFile(archivos[i],"pedidosPrueba.xls");
			
			Date horaIni = new Date();
			
			System.out.println(archivos[i]);
			exactas.ClaseParser.setGlobalId(2);
			exactas.PreAsignacionParser.setGlobalId(2);
			Solver.solve(Exactas.setup);	

			Date horaFin = new Date();
			
			System.out.println("Guardando resultados...");
			copyFile(find("/","asignacion.xls"),"asignacion_" + archivos[i].getName());
			System.out.println("Resutados guardados con exito!");
			
			imprimirDuracion(horaIni,horaFin);
			//ClaseParse.groove
		}		
	}

	// A partir de un directorio y el nombre de un archivo, retorna el 
	// archivo especificado correspondiente al directorio.
	public File find(String path, String nombre) {
		File archivo = new File(path);
		if (nombre.equalsIgnoreCase(archivo.getName())) 
			return archivo;
		if (archivo.isDirectory()) {
			for (String s : archivo.list()) {
				File archivoEncontrado = find(path + File.separator + s, nombre);
				if (archivoEncontrado != null) 
					return archivoEncontrado;
			}
		}
		return null;
	}
	
	public File copyFile(File origen, String nombreDestino) {
		File outFile = new File(nombreDestino);

		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {
			in = new FileInputStream(origen);
			out = new FileOutputStream(outFile);
			
			int c;
			while( (c = in.read() ) != -1)
				out.write(c);

			in.close();
			out.close();
		} catch(IOException e) {
			System.err.println("Hubo un error de entrada/salida!!!");
		}
		return outFile;
	}

	private void imprimirDuracion(Date hrIni, Date hrFin) {
		long millis = hrFin.getTime() - hrIni.getTime(); 
		
		String ret = String.format("%d min, %d sec", 
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
		);
		
		System.out.println("Tiempo de ejecución: "+ ret);
	}
	
	public static void main(String args[]) {
		FileManager fileManager = new FileManager("instancias");
		fileManager.recorrerArchivos();
	}

}

