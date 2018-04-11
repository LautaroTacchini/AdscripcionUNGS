package aulas

import exactas.ClaseExactas
import jxl.*
import jxl.write.*

//class Pair extends groovy.util.MapEntry { 
//	Pair(Object key, Object value) { super(key, value) }
//}
	
class Solucion {
	
	Map asignadas // Clase -> Aula
	Set noAsignadas

	Solucion(asignadas, noAsignadas) {
		this.asignadas = asignadas
		this.noAsignadas = noAsignadas
	}
		
	def escribirSolucion(String filename) {
		def tab = "\t", endl = "\n"
		def out = new File(filename)
		out.delete()
		out << "# IdClase" + tab + "IdAula" + endl
		out.withWriterAppend { w ->
			asignadas.sort().each {
				w << it.key + tab + it.value + endl 
			}
		}
	}
	
	def escribirSolucionExcel(String filename, Problema pb) {
		WorkbookSettings ws = new WorkbookSettings();
//		ws.setLocale(new Locale("en", "EN"));
		ws.setLocale(new Locale("es", "AR"));
//		workbook = Workbook.createWorkbook(new File(filename), ws);
		WritableWorkbook workbook = Workbook.createWorkbook(new File(filename), ws);
//		WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
		
		WritableSheet sheet = workbook.createSheet("Resultado", 1);
//		// Add a print area to the "Labels" sheet
//		sheet.getSettings().setPrintArea(4,4,15,35);

		sheet.setColumnView(0, 50);
  
		// Labels
		addCells(sheet, 0, ["Asignatura", "Concepto", "Turno", "Día", 
				"Pab. preferido", "Aula preasignada",  
				"Pab. asignado", "Aula asignada", "Capacidad", "Inscriptos", "Comentarios"])
		
		pb.clases.sort { it.id as Integer }.eachWithIndex { ClaseExactas c, i ->
			PreAsignacion pre = pb.preasignadas.find {
				it.asignacion == c.id as Integer
			}
			// armar una funcioncita que trimee el primer char de un string
			def aulaPreasignada = (pre? pre.aula[1..pre.aula.size()-1]: "")
			def pabAsignado = ""
			def aulaAsignada = ""
			def capacidadAula = ""
			def comentario = ""
			
			def asignacion = asignadas.get(c.id)
			if (asignacion != null) {
				pabAsignado = asignacion[0]
				aulaAsignada = asignacion[1..asignacion.size() - 1]
				if (!(c.pabellonPreferido in [0, pabAsignado as Integer]))
					comentario = "Cambio de pabellón."
				Aula aula = pb.aulas.find { it.id == asignacion }
				capacidadAula = aula.capacidad 
				
			} else {
				comentario = "Sin asignar."
			}
			// estaría bueno agregar un comentario cuando el cupo del aula es 
			// menor a la cantidad de inscriptos y se toma en cuenta el excedente.
			
			// también sería bueno agregar un comentario cuando la cantidad de 
			// pizarrones pedida no se respete.
			
			addCells(sheet, i+1, [c.asignatura, c.concepto, c.turno, 
					c.diaSemana.toString().toLowerCase(),
					c.pabellonPreferido as String, aulaPreasignada,
					pabAsignado, aulaAsignada, capacidadAula, c.cantidadInscriptos, comentario
					])
		}
		workbook.write();
		workbook.close();
	}
	
	def addCells(WritableSheet sheet, int row, List<String> labels) {
		labels.eachWithIndex { it, i -> 
			// usar case
			if (it.class == Integer.class || it.isInteger())
				sheet.addCell(new Number(i, row, it as Integer))
			else
				sheet.addCell(new Label(i, row, it.toString()))
		}
	}
	
}
