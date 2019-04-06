package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.CalculadorHistograma;
import csv.module.CSVParser;
import csv.module.CSVReader;
import domain.Solicitud;
import negocio.Histograma;

public class Main {
	
	public static void main(String args[]){
		List<Solicitud> solicitudes = new ArrayList<>();
		
		Histograma histograma = new Histograma(solicitudes,15,30);
		CalculadorHistograma ch = new CalculadorHistograma(histograma);
	
		CSVParser parser = new CSVParser(solicitudes);
		CSVReader reader = new CSVReader("instancias/pedidos2017-1.csv",parser);
		try {
			reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(ch.imprimirSolicitudesSemanales());
		System.out.println(ch.imprimirHrMasSolicitadaPorDia());	
	}

}
