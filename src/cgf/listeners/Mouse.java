package cgf.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import cgf.Controle;
import cgf.estado.Zona;

public class Mouse extends MouseInputAdapter implements Serializable {
    private Controle controle;

    private Point lastPressedPoint;

    // private Zona dragZona;

    private Zona mouseOverZona;

    private boolean isDragging;

    public static List<Zona> selecionadas = new ArrayList<Zona>();

    public Mouse(Controle controle) {
        this.controle = controle;
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
        Zona zona = (Zona) e.getSource();
        if (e.isControlDown()) {
            if (zona.isSelecionada()) {
                selecionadas.remove(zona);
            } else {
                selecionadas.add(zona);
            }
            zona.setSelecionada(!zona.isSelecionada());
        } else if (e.isShiftDown()) {

        } else {
            deselecionaTudo();
            selecionadas.add(zona);
            zona.setSelecionada(true);
        }
        zona.repaint();
    }

    public void mouseEntered(MouseEvent e) {
        mouseOverZona = (Zona) e.getSource();
        Zona.criaBorda(mouseOverZona, true);
    }

    public void mouseExited(MouseEvent e) {
        Zona.criaBorda(mouseOverZona, false);
        mouseOverZona = null;
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
        lastPressedPoint = e.getPoint();
        // Joga o foco pra visao senao ela nao escuta keyListener 
        controle.getVisao().requestFocusInWindow();
        // Zona pai = (Zona) dragZona.getParent();
        // pai.setComponentZOrder(dragZona, pai.getComponents().length - 1);
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
        if (isDragging) {
            //Controle.setNotificaPlayers(false);
            String msg = controle.getJogo().mover(selecionadas, mouseOverZona, true);
            //Controle.setNotificaPlayers(true);
            deselecionaTudo();
            if (msg != null) {
                JOptionPane.showMessageDialog(controle.getVisao(), msg);
            }
            Zona.criaBorda(mouseOverZona, false);
        }
        isDragging = false;
        //mouseOverZona.getEstado().printEstado();
    }

    public void mouseDragged(MouseEvent e) {
        Zona label = (Zona) e.getSource();
        // System.out.println("Dragging = "+label);
        label.setLocation(label.getX() + e.getX() - lastPressedPoint.x, label.getY() + e.getY() - lastPressedPoint.y);
        isDragging = true;
    }

    // public void mouseMoved(MouseEvent e) {
    // // TODO Auto-generated method stub
    //
    // }

    public static void deselecionaTudo() {
        for (Zona zona : selecionadas) {
            zona.setSelecionada(false);
            zona.repaint();
        }
        selecionadas.removeAll(selecionadas);
    }
}
