import java.util.ArrayList;
import java.util.List;

public class Histograma {
	List<Solicitud> solicitudes;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(){
		solicitudes = new ArrayList<Solicitud>();		
	}
	
	// Agrega una solicitud al histograma.
	public void agregarSolicitud(Solicitud solicitud){
		solicitudes.add(solicitud);
	}
	
	// Dado un dia, con un horario de inicio,
	// devuelve la cantidad de materias que solicitaron ese horario.	
	public int cantSolicitudes(String dia, int hora){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia) && s.getHrIni() <= hora && s.getHrFin() >= hora+1){
					cantSolucitudes++;
				}
		}		
		return cantSolucitudes;
	}
	
	// Dado un dia de la semana, devuelve todos los horarios
	// con la cantidad de solicitudes por hora.
	public List<Integer> cantSolicitudesPorHora(String dia){
		List<Integer> cantMateriasPorHora = new ArrayList<Integer>();
		
		for(int i=0; i<=23; i++){
			cantMateriasPorHora.add(cantSolicitudes(dia,i));
		}
		
		return cantMateriasPorHora;
	}
	
		
	// Devuelve un String con todos los horarios de un dia.
	public String mostrarSolicitudes(String dia){
		String ret = dia;
		List<Integer> cantMaterias = cantSolicitudesPorHora(dia);
		for(int indice = 0; indice < cantMaterias.size();indice++){
			ret = ret + " " +cantMaterias.get(indice);
		}
		return ret;
	}
	
	private int maxHrSolicitudes(String dia){
		List<Integer> cantMateriasPorHora = cantSolicitudesPorHora(dia);
		int max = 0; 
		for (int i=0; i< cantMateriasPorHora.size();i++){
			if(max<cantMateriasPorHora.get(i))
				max = cantMateriasPorHora.get(i);
		}
		return max;
	}
	
	private int horaMasSolicitada(String dia){
		List<Integer> cantMateriasPorHora = cantSolicitudesPorHora(dia);
		int max = 0; 
		int indice = 0;
		for (int i=0; i < cantMateriasPorHora.size();i++){
			if(max < cantMateriasPorHora.get(i)){
				max = cantMateriasPorHora.get(i);
				indice = i;
			}
		}
		return indice;
	}
	
	public String mostrarHoraMasSolicitada(String dia){
		String ret = dia + " " +Integer.toString(horaMasSolicitada(dia)) + "Hrs"
						 + "  Cant. de Solicitudes: " + Integer.toString(maxHrSolicitudes(dia));
		return ret;
	}
}
