
public class Main {
	
	public static void main(String args[]){
		Histograma histograma = new Histograma();
		
		Solicitud s1 = new Solicitud("a", "lunes", 8, 10);
		Solicitud s2 = new Solicitud("b", "lunes", 9, 11);
		Solicitud s3 = new Solicitud("c", "lunes", 8, 12);
		
		histograma.agregarSolicitud(s1);
		histograma.agregarSolicitud(s2);
		histograma.agregarSolicitud(s3);
		
		System.out.println(histograma.imprimir("lunes"));
	}

}
