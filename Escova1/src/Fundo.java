import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.event.*;

public class Fundo extends JFrame
{
	JDesktopPane desktop;
	/*Pc pc;
	Jogador pl;*/
	
	public Fundo(/*Pc pc, Jogador pl*/)
	{
		super("Jogo de Escova - NET");
		/*this.pc=pc;
		this.pl=pl;*/
		desktop = new JDesktopPane();
    addWindowListener(new WindowAdapter()
    {
    	public void windowClosing(WindowEvent e){System.exit(0);}
    });
    
    Menu m =new Menu(this,desktop/*,pc,pl*/);
    setContentPane(desktop);
    //Arrastamento mais rápido
    desktop.putClientProperty("JDesktopPane.dragMode", "outline");
    
    setSize(800,600);
    //setLocation(20,20);
    setVisible(true);
  }
}