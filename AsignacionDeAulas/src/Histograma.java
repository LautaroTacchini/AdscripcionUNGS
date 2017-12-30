import java.util.ArrayList;
import java.util.List;

public class Histograma {
	List<Solicitud> solicitudes;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(){
		solicitudes = new ArrayList<Solicitud>();		
	}
	
	// Agrega una solicitud al histograma.
	public void agregarSolicitud(String materia, String dia, int hrIni, int hrFin){
		solicitudes.add(new Solicitud(materia,dia,hrIni,hrFin));
	}
	
	// Dado un dia, con un horario de inicio y de fin,
	// devuelve todas las materia que solicitaron ese horario.	
	public int cantMateriasSolicitantes(String dia, int hrIni){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia)){
				if(s.getHrIni() <= hrIni && s.getHrFin() >= hrIni+1){
					cantSolucitudes++;
				}
			}
		}
		return cantSolucitudes;
	}	
	
	// Comentario
}
