import java.awt.event.*;

public class Mou implements MouseListener
{
	public Mou(/*Classe escutada*/){}
	public void mouseClicked(MouseEvent e){System.out.println("Apertou");}
	public void mouseEntered(MouseEvent e){System.out.println("Entrou");}
	public void mouseExited(MouseEvent e){System.out.println("Saiu");}
	public void mouseReleased(MouseEvent e){System.out.println("Soltou");}
	public void mousePressed(MouseEvent e){System.out.println("Pressionou");}
	//e.getActionCommand();
}