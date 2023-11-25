package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;


public class WebCompraConciertos implements IOperacionesWeb{


	public WebCompraConciertos() {
		super();
	}

	@Override
	public synchronized boolean comprarEntrada() {
		if (hayEntradas()) {
			// SI HAY ENTRADAS SE RESTA A LAS ENTRADAS DISPONIBLES Y DEVUELVE TRUE
			EjemploTicketMaster.ENTRADAS_DISPONIBLES--;
			notifyAll();
			return true;
		} else {
			// SI NO HAY ENTRADAS ESPERA Y DEVUELVE FALSE
			try {
				wait();
			} catch (Exception e) {
				System.out.println("ERROR");
			}
			return false;
		}
	}

	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {

		if ((EjemploTicketMaster.ENTRADAS_REPUESTAS + numeroEntradas) > EjemploTicketMaster.TOTAL_ENTRADAS) {
			// Repone solo la cantidad específica para que no se pase del total de entradas que se pueden vender
			numeroEntradas = EjemploTicketMaster.TOTAL_ENTRADAS - EjemploTicketMaster.ENTRADAS_REPUESTAS;
		}
		// SE SUMAN LAS ENTRADAS DISPONIBLES PARA COMPRAR
		EjemploTicketMaster.ENTRADAS_DISPONIBLES += numeroEntradas;
		// SE SUMAN LA CANTIDAD DE ENTRADAS QUE SE HAN REPUESTO EN TOTAL
		EjemploTicketMaster.ENTRADAS_REPUESTAS += numeroEntradas;

		notifyAll();
		return EjemploTicketMaster.ENTRADAS_DISPONIBLES;
	}

	@Override
	public synchronized void cerrarVenta() {
		// Cierra la venta
		EjemploTicketMaster.VENTA_ABIERTA = false;
	}

	@Override
	public synchronized boolean hayEntradas() {
		//SI HAY ENTRADAS DEVUELVE TRUE Y SINO DEVUELVE FALSE
		if (EjemploTicketMaster.ENTRADAS_DISPONIBLES > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public synchronized int entradasRestantes() {
		// DEVUELVE LAS ENTRADAS QUE QUEDAN DISPONIBLES
		return EjemploTicketMaster.ENTRADAS_DISPONIBLES;
	}

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
	}

}
