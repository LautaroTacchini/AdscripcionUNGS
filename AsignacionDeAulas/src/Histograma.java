import java.util.List;

public class Histograma {
	
	private List<Solicitud> solicitudes;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(List<Solicitud> solicitudes){
		this.solicitudes = solicitudes;		
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
	
	public List<Solicitud> getSolicitudes(){
		return solicitudes;
	}
}
