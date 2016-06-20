package cgf.visao;

import java.awt.FlowLayout;

import cgf.rmi.IPlayer;



public class CircularStrategy implements ILayoutStrategy {
	public void reorganiza(IPlayer player) {
		// modelo.getEstado().setLayout(null);
		// for (Zona zona : modelo.getEstado().getZonas()) {
		//
		// }
		player.getEstado().setLayout(new FlowLayout());
		player.getEstado().repaint();
	}
}
