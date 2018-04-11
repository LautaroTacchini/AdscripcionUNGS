package aulas

class Intervalo {

	// see java.text.SimpleDateFormat
	static String timeFormat = "H:mm" // xej: 8:00, 12:00

	DiaSemana dia
	Date horaInicio
	Date horaFin
	String txt

	Intervalo (DiaSemana dia, Date horaInicio, Date horaFin) {
		this.dia = dia
		this.horaInicio = horaInicio
		this.horaFin = horaFin
		
		txt = String.format("[%s, %s, %s]", dia, horaInicio.format(timeFormat), 
			horaFin.format(timeFormat))
	}
	
	String toString() { return txt }
	
	@Override
	boolean equals(Object that) { 
		this.class == that.class && this.txt == that.txt
	}
	
	@Override
	public int hashCode() { txt.hashCode() }
	
}