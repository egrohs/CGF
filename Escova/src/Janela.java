import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.event.*;

public class Janela extends JInternalFrame
{
	JDesktopPane desktop;
	String nomes[]={"PC","Estivador"};//
	/*Pc pc=new Pc();
	Jogador pl=new Jogador("Estivador");*/

	public Janela(JDesktopPane desktop/*, Pc pc, Jogador pl*/)
	{
		super("Jogo de Estivador");
		this.desktop=desktop;
		/*this.pc=pc;
		this.pl=pl;*/
		Jogo j = new Jogo(2,nomes/*,jogo*/);//
		Meupanel jogo=new Meupanel(j/*j.getPc(),j.getJog()*/);
		getContentPane().add(jogo,BorderLayout.CENTER);
    setSize(800,550);
    //setLocation(0,5);
    adicionaChat();
    desktop.add(this);
    setVisible(true);
    
    //j.start();
  }
  
	public void adicionaChat()
	{
		JButton send=new JButton("enviar mensagem");
		send.setMnemonic('s');
    send.setToolTipText("Clique aqui para enviar sua mensagem!");
		JTextField msg=new JTextField(30);
		msg.setToolTipText("Digite aqui sua mensagem!");
		JPanel jp =new JPanel();
		jp.setBackground(Color.red);
		JLabel nomej = new JLabel("Aham");
    jp.add(nomej);
		jp.add(msg);
		jp.add(send);
		jp.setLayout(new FlowLayout());
		getContentPane().add(jp,BorderLayout.SOUTH);
	}
}