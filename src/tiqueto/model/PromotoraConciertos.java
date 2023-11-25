package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {
		//MIENTRAS LA VENTA ESTE ABIERTA Y LAS ENTRADAS REPUESTAS SEA MENOR A LA SUMA DEL MÁXIMO DE ENTRADAS QUE PUEDE COMPRAR CADA FAN
		while (EjemploTicketMaster.VENTA_ABIERTA && EjemploTicketMaster.ENTRADAS_REPUESTAS < (EjemploTicketMaster.MAX_ENTRADAS_POR_FAN * EjemploTicketMaster.NUM_FANS)) {
			try {
				mensajePromotor("HAY PARA REPONER?");
				// PRIMERO EL PROMOTOR COMPRUEBA SI SE HAN AGOTADO LAS ENTRADAS QUE HABIA EN LA WEB POR SI SE HAN GASTADO Y HAY
				// 0 ENTRADAS, REPONERLAS
				int entradasRestantes = webCompra.entradasRestantes();
				if (entradasRestantes != 0) {
					//SI TODAVIA QUEDAN ENTRADAS, EL PROMOTOR NO REPONER ENTRADAS
					mensajePromotor("Todavia QUEDAN entradas");
				} else {
					if (EjemploTicketMaster.ENTRADAS_REPUESTAS == EjemploTicketMaster.TOTAL_ENTRADAS) {
						// SI SE HAN REPUESTO TODAS LAS ENTRADAS QUE SE ESPERABAN VENDER, EL PROMOTOR CIERRA LA VENTA
						mensajePromotor("NO QUEDAN mas entradas, cierro venta");
						webCompra.cerrarVenta();
					} else {
						// SI NO QUEDAN ENTRADAS Y NO SE HAN VENDIDO TODAS LAS ESPERADAS, EL PROMOTOR REPONE MAS ENTRADAS
						mensajePromotor("REPONIENDO...");
						webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);
						Thread.sleep((long) ((Math.random() * 8000) + 3000));
					}
				}
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
		// ESTE ES UN IF POR SI LA CONDICIÓN POR LA QUE SE HA SALIDO ANTES EL BUCLE WHILE ES PORQUE LOS FANS TIENEN TODOS EL MÁXIMO DE ENTRADAS Y NO PORQUE SE HAYA CERRADO LA VENTA
//		if (EjemploTicketMaster.VENTA_ABIERTA) {
//			webCompra.cerrarVenta();
//		}
	}
	
	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);
		
	}
}
