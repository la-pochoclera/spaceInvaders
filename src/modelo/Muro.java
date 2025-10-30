package modelo;

import java.util.ArrayList;
import java.util.List;

public class Muro {
	private int posX;
	private int posY;
	private List<SegmentoMuro> segmentos;
	private double saludTotal;//  (0.0 .. 1.0).
	
	public Muro(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.segmentos = new ArrayList<>();
		this.saludTotal = 1.0;
	}

	// UML: inicializar(): construye los segmentos
	public void inicializar() {
		segmentos.clear();

		// CONFIG: queremos exactamente 10 minisegmentos.
		int cols = 5;   // 5 columnas
		int rows = 2;   // 2 filas  -> 5 * 2 = 10 segmentos

		// Tamaño visual de cada minisegmento (coincide con lo que dibuja la vista)
		int segAncho = 10; // ancho en unidades lógicas (coincide con w en VistaJuego)
		int segAlto = 6;   // alto en unidades lógicas (coincide con h en VistaJuego)

		// Sin espacios entre segmentos: separación center-to-center = segAncho / segAlto
		int spacingX = segAncho;
		int spacingY = segAlto;

		// Calculamos la posición del borde izquierdo y superior para centrar el bloque en (posX,posY)
		int totalWidth = cols * segAncho;   // ancho total del bloque
		int totalHeight = rows * segAlto;   // alto total del bloque

		// left = coordenada del borde izquierdo; center de primer segmento = left + segAncho/2
		double left = posX - (totalWidth / 2.0);
		double top = posY - (totalHeight / 2.0);

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				int centerX = (int) Math.round(left + segAncho / 2.0 + c * spacingX);
				int centerY = (int) Math.round(top + segAlto / 2.0 + r * spacingY);
				segmentos.add(new SegmentoMuro(centerX, centerY, segAncho, segAlto));
			}
		}

		// asegurar que saludTotal y segmentos estén sin daños al inicializar
		this.saludTotal = 1.0;
		actualizarSegmentosSegunSalud();
	}

	public List<SegmentoMuro> getSegmentos() {
		return segmentos;
	}

	public void reiniciarMuro() {
		this.saludTotal = 1.0;
		actualizarSegmentosSegunSalud();
	}
	
	public void recibirImpacto(boolean esAliado) {
		double danio;
		if (esAliado) {
		    danio = 0.10; //10% 10 disparos aliados para destruir
		} else {
		    danio = 0.05; //5% 20 disparos enemigos para destruir
		}

		this.saludTotal =saludTotal- danio; 

		if (this.saludTotal < 0.0) {
		    this.saludTotal = 0.0;
		}

		actualizarSegmentosSegunSalud();
	}

	private void actualizarSegmentosSegunSalud() {
		if (segmentos == null || segmentos.isEmpty())
			return;
		int total = segmentos.size(); // debería ser 10
		// número de segmentos que deberían estar vivos (redondeo)
		int vivos = (int) Math.round(this.saludTotal * total);
		if (vivos < 0)
			vivos = 0;
		if (vivos > total)
			vivos = total;

		//mantenemos los primeros 'vivos' segmentos como activos
		for (int i = 0; i < total; i++) {
			SegmentoMuro s = segmentos.get(i);
			if (i < vivos) {
				s.setSalud(1.0);
			} else {
				s.setSalud(0.0);
			}
		}
	}

	// UML: segmentoEnPosicion(x,y)
	public SegmentoMuro segmentoEnPosicion(int x, int y) {
		for (SegmentoMuro s : segmentos) {
			if (!s.estaDestruido()) {
				int left = s.getPosX() - s.getAncho() / 2;
				int right = s.getPosX() + s.getAncho() / 2;
				int top = s.getPosY() - s.getAlto() / 2;
				int bottom = s.getPosY() + s.getAlto() / 2;
				if (x >= left && x <= right && y >= top && y <= bottom) {
					return s;
				}
			}
		}
		return null;
		
	}
	
	// Getters
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public double getSaludTotal() {
		return saludTotal;
	}
}