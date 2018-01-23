package negocio;
import java.util.ArrayList;
import java.util.List;

public class CalculadorHistograma {
	private Histograma histograma;
	
	public CalculadorHistograma(Histograma hist) {
		histograma = hist;
	}
		
	// Devuelve la cantidad maxima de solicitudes en un dia.
	private int maxSolicitudes(String dia){
//		int cantSolicitudes = histograma.cantSolicitudesPorHora(dia, horaMasSolicitada(dia));
		int cantSolicitudes = histograma.cantSolicitudes(dia, histograma.obtenerHorario(dia,horaMasSolicitada(dia)));
		return cantSolicitudes;
	}
	
	// Dado un dia devuelve la hora que tiene mas 
	// solicitudes en ese dia.
	private int horaMasSolicitada(String dia){
		List<Integer> cantMateriasPorHora = histograma.cantSolicitudesPorHora(dia);
		int max = 0; 
		for (int i=0; i < cantMateriasPorHora.size();i++){
			if(max < cantMateriasPorHora.get(i)){
				max = cantMateriasPorHora.get(i);
			}
		}
		return max;
	}
	
	// Muestra la hora mas solicitada con su respectiva cantidad,
	// de solicitudes.
	public String mostrarHrMasSolicitada(String dia){
		String ret = dia + " " + histograma.obtenerHorario(dia,maxSolicitudes(dia)) + "Hrs"
						 + "  Cant. de Solicitudes: " + Integer.toString(maxSolicitudes(dia));
		return ret;
	}
	
	// Devuelve una lista con las solicitudes por hora de todos los
	// dias de la semana.
	public List<String> solicitudesSemanales() {
		List<String> ret = new ArrayList<>();
		String[] arrayDias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
		for (String s : arrayDias) {
			ret.add(histograma.mostrarSolicitudesPorHorario(s)+"\n");
		}
		return ret;
	}
	
	// IMPRIME LO ANTERIOR.
	public String imprimirSolicitudesSemanales() {
		String ret = "";
		List<String> masSolicitadasSemana = solicitudesSemanales();
		for(String s : masSolicitadasSemana) {
			ret = ret + s ;	
		}
		return ret;
	}
	
	// Muestra la hora mas solicitada de cada dia de la semana,
	// y muestra cuantas solicitudes existen en cada uno de los
	// horarios mas solicitados de cada dia.
	public List<String> hrMasSolicitadaPorDia() {
		List<String> ret = new ArrayList<>();
		String[] arrayDias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
		for (String s : arrayDias) {
			ret.add(mostrarHrMasSolicitada(s)+"\n");
		}
		return ret;
	}
	
	// IMPRIME LO ANTERIOR.
	public String imprimirHrMasSolicitadaPorDia() {
		String ret = "";
		List<String> masSolicitadasSemana = hrMasSolicitadaPorDia();
		for(String s : masSolicitadasSemana) {
			ret = ret + s ;	
		}
		return ret;
	}	
}
