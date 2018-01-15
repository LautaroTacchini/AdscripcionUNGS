import java.util.List;

public class CalculadorHistograma {
	private Histograma histograma;
	
	public CalculadorHistograma(Histograma hist) {
		histograma = hist;
	}
	
	private int maxHrSolicitudes(String dia){
		int horaMasSolicitada = horaMasSolicitada(dia);
		int cantSolicitudes = histograma.cantSolicitudes(dia, horaMasSolicitada);
		return cantSolicitudes;
	}
	
	private int horaMasSolicitada(String dia){
		List<Integer> cantMateriasPorHora = histograma.cantSolicitudesPorHora(dia);
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
