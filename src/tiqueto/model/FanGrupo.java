package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}

	@Override
	public void run() {
		//Mientras la venta este abierta y el fan no haya comprado su máximo de entradas
		while (EjemploTicketMaster.VENTA_ABIERTA && this.entradasCompradas != EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
			mensajeFan("Intento comprar una entrada");
			//LLAMA AL METODO COMPRAR ENTRADA QUE DEVUELVE TRUE SI LA HA COMPRADO (Dentro del método se comprueba si quedan entradas)
			boolean compraEntrada = webCompra.comprarEntrada();
			//SE HACE LA COMPROBACIÓN DE SI HA COMPRADO O NO LA ENTRADA
			if (compraEntrada) {
				//SI LA HA COMPRADO SE INCREMENTA LA VARIABLE DE LAS ENTRADAS COMPRADAS Y SE DUERME AL HILO
				mensajeFan("SI COMPRO entrada");
				this.entradasCompradas++;
				try {
					Thread.sleep((long) ((Math.random() * 3000) + 1000));
				} catch (InterruptedException e) {
					System.out.println("ERROR");
				}
			} else {
				//SI NO SE HA COMPRADO SE MANDA MENSAJE DE QUE NO HA COMPRADO Y LO VUELVE A INTENTAR
				mensajeFan("NO COMPRO entrada");
			}
		}
	}
	public void dimeEntradasCompradas() {
		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}
	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
		System.out.println(System.currentTimeMillis() + "|" + tabuladores +" Fan "+this.numeroFan +": " + mensaje);
	}
}
