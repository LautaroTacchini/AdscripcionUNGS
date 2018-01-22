package negocio;
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
			cantMateriasPorHora.add(histograma.cantSolicitudesPorHora(dia,i));
		}	
		return cantMateriasPorHora;
	}
	
	// Devuelve un String con todos los horarios de un dia.
	public String mostrarSolicitudesPorHora(String dia){
		String ret = dia;
		for(int i : cantSolicitudesPorHora(dia)) {
			ret += " " + i;
		}
		return ret;
	}
	// TODO HACER MOSTRARSOLICITUDESPORHORARIO INCLUIR MINUTOS
	
	// Devuelve la cantidad maxima de solicitudes en un dia.
	private int maxSolicitudes(String dia){
		int horarioMasSolicitado = horaMasSolicitada(dia);
		int cantSolicitudes = histograma.cantSolicitudesPorHora(dia, horarioMasSolicitado);
		return cantSolicitudes;
	}
	
	// Dado un dia devuelve la hora que tiene mas 
	// solicitudes en ese dia.
	private int horaMasSolicitada(String dia){
		List<Integer> cantMateriasPorHora = cantSolicitudesPorHora(dia);
		int max = 0; 
		int ret = 0;
		for (int i=0; i < cantMateriasPorHora.size();i++){
			if(max < cantMateriasPorHora.get(i)){
				max = cantMateriasPorHora.get(i);
				ret = i;
			}
		}
		return ret;
	}
	
	// Muestra la hora mas solicitada con su respectiva cantidad,
	// de solicitudes.
	public String mostrarHrMasSolicitada(String dia){
		String ret = dia + " " +Integer.valueOf(horaMasSolicitada(dia)) + "Hrs"
						 + "  Cant. de Solicitudes: " + Integer.toString(maxSolicitudes(dia));
		return ret;
	}
	
	// Devuelve una lista con las solicitudes por hora de todos los
	// dias de la semana.
	public List<String> solicitudesSemanales() {
		List<String> ret = new ArrayList<>();
		String[] arrayDias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
		for (String s : arrayDias) {
			ret.add(mostrarSolicitudesPorHora(s)+"\n");
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
