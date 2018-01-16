import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String args[]){
		List<Solicitud> solicitudes = new ArrayList<>();
		
		Histograma histograma = new Histograma(solicitudes);
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
		ch.leerEscribir();
		
		System.out.println(ch.mostrarSolicitudesSemanales());
		System.out.println(ch.mostrarHrMasSolicitadaPorDia());	
	}

}
