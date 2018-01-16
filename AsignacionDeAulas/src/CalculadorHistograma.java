import java.util.ArrayList;
import java.util.List;

public class CalculadorHistograma {
	private Histograma histograma;
	
	public CalculadorHistograma(Histograma hist) {
		histograma = hist;
	}
	
	// Dado un dia de la semana, devuelve todos los horarios
	// con la cantidad de solicitudes por hora.
	public List<Integer> cantSolicitudesPorHora(String dia){
		List<Integer> cantMateriasPorHora = new ArrayList<Integer>();
		for(int i=0; i<=23; i++){
			cantMateriasPorHora.add(histograma.cantSolicitudes(dia,i));
		}		
		return cantMateriasPorHora;
	}
	
	// Devuelve un String con todos los horarios de un dia.
	public String mostrarSolicitudesPorHora(String dia){
		String ret = dia;
		List<Integer> cantMaterias = cantSolicitudesPorHora(dia);
		for(int indice = 0; indice < cantMaterias.size();indice++){
			ret = ret + " " +cantMaterias.get(indice);
		}
		return ret;
	}
	
	// Devuelve la cantidad maxima de solicitudes en un dia.
	private int maxHrSolicitudes(String dia){
		int horaMasSolicitada = horaMasSolicitada(dia);
		int cantSolicitudes = histograma.cantSolicitudes(dia, horaMasSolicitada);
		return cantSolicitudes;
	}
	
	// Dado un dia devuelve la hora que tiene mas 
	// solicitudes en ese dia.
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
	
	// Muestra la hora mas solicitada con su respectiva cantidad,
	// de solicitudes.
	public String mostrarHoraMasSolicitada(String dia){
		String ret = dia + " " +Integer.toString(horaMasSolicitada(dia)) + "Hrs"
						 + "  Cant. de Solicitudes: " + Integer.toString(maxHrSolicitudes(dia));
		return ret;
	}
	
	public List<String> horasSemanalesMasSolicitadas() {
		List<String> ret = new ArrayList<>();
		String[] arrayDias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
		for (String s : arrayDias) {
			ret.add(mostrarHoraMasSolicitada(s)+"\n");
		}
		return ret;
	}
	
	public String mostrarHorasSemanalesMasSolicitada() {
		String ret = "";
		List<String> masSolicitadasSemana = horasSemanalesMasSolicitadas();
		for(String s : masSolicitadasSemana) {
			ret = ret + s ;		}
		return ret;
	}
	
}
