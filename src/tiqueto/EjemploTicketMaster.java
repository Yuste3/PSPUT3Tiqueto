package tiqueto;

import java.util.ArrayList;
import java.util.List;

import tiqueto.model.FanGrupo;
import tiqueto.model.PromotoraConciertos;
import tiqueto.model.WebCompraConciertos;

public class EjemploTicketMaster {

	// Entradas totales que se han repuesto
	public static int ENTRADAS_REPUESTAS = 0;

	// Variable para saber si la venta de entradas sigue disponible o si se ha cerrado ya
	public static boolean VENTA_ABIERTA = true;

	// Entradas disponibles
	public static int ENTRADAS_DISPONIBLES = 0;

	// Total de entradas que se vender�n
	public static int TOTAL_ENTRADAS = 10;

	// El número de entradas que reponerá cada vez el promotor
	public static int REPOSICION_ENTRADAS = 3;

	// El número máximo de entradas por fan
	public static int MAX_ENTRADAS_POR_FAN = 10;

	// El número total de fans
	public static int NUM_FANS = 5;


	public static void main(String[] args) throws InterruptedException {

		String mensajeInicial = "[ Empieza la venta de tickets. Se esperan %d fans, y un total de %d entradas ]";
		System.out.println(String.format(mensajeInicial, NUM_FANS, TOTAL_ENTRADAS));
		WebCompraConciertos webCompra = new WebCompraConciertos();
		PromotoraConciertos liveNacion = new PromotoraConciertos(webCompra);
		List<FanGrupo> fans = new ArrayList<>();

		// Creamos todos los fans
		for (int numFan = 1; numFan <= NUM_FANS; numFan++) {
			FanGrupo fan = new FanGrupo(webCompra, numFan);
			fans.add(fan);
			fan.start();
		}

		//Lanzamos al promotor para que empiece a reponer entradas
		liveNacion.start();

		//Esperamos a que el promotor termine, para preguntar a los fans cu�ntas entradas tienen compradas
		liveNacion.join();

		System.out.println("\n [ Terminada la fase de venta - Sondeamos a pie de calle a los compradores ] \n");
		System.out.println("Total entradas ofertadas: " + TOTAL_ENTRADAS);
		System.out.println("Total entradas disponibles en la web: " + webCompra.entradasRestantes());

		// Les preguntamos a cada uno
		for (FanGrupo fan : fans) {
			fan.dimeEntradasCompradas();
		}
		List<Class> listaClases = new ArrayList<>();
		listaClases.add(FanGrupo.class);
		listaClases.add(PromotoraConciertos.class);

		//Código cortesía de BARD para comprobar que no quedan hilos en ejecución
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		int activeCount = threadGroup.activeCount();
		// Comprueba si hay algún hilo ejecutandose
		if (activeCount > 0) {
			// Hay algún hilo ejecutandose
			Thread thread = Thread.currentThread();
			System.out.println("El hilo que está ejecutandose es: " + thread.getName() + " que pertenece al grupo de hilos " + threadGroup.getName());
		}
		System.exit(0);
	}

}
