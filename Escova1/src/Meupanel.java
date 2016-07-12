import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.event.*;

class Meupanel extends Canvas
{
	private Vector jogada=new Vector();
	//private Vector mesa=new Vector();
	/*private Vector mao=new Vector();
	private int mpc;
	private int mpl;
	private int maopc;*/
	/*private Pc pc;
	private Jogador pl;*/
	private Jogo j;
		
	public Meupanel(Jogo j/*Pc pc, Jogador pl*/)
	{
		super();
		/*this.pc=pc;
		this.pl=pl;*/
		this.j=j;
    setBackground(Color.green);
    //setVisible(true);
	}
	public boolean mouseDown(Event e,int x,int y)
	{
		int lxme=(800-75*j.getMesa().size())/2;
		int lxma=(800-75*j.getJog().getMao().size())/2;
		//System.out.println("Tam Jogada= "+jogada.size());
		
		if(j.getPc().somaJog(jogada,j.getJog().getMao(),j.getMesa())<15)//nao estourou
		{
			if(j.getMesa().size()<12)
			{
				if(jogada.size()>0&&y>200&&y<296&&x>lxme&&x<lxme+75*j.getMesa().size()-4)//entao eh a carta da mesa
				{
					for(int i=0;i<j.getMesa().size();i++)
					{
						if(x>75*i+lxme&&x<75*i+(lxme+71))
						{
							jogada.addElement(new Integer(i));
							if(j.getPc().somaJog(jogada,j.getJog().getMao(),j.getMesa())==15)//se fechou 15
							{
								j.joga(jogada,this);
								//j.setMesa(j.getJog().fazJogada(j.getPc(),jogada,j.getMesa()));
								jogada.removeAllElements();
							}
							break;
						}
					}
				}
				else if(jogada.size()==1&&(y<200||y>296||x<lxme||x>lxme+75*j.getMesa().size()-4))//entao vai largar na mesa
						 {
							 j.joga(jogada,this);
							 jogada.removeAllElements();
						 }
			}
			//else if(y>100&&y<196){}
			
		}
			
		if(jogada.size()==0&&y>350&&y<446)//entao eh carta da mao
		{
			for(int i=0;i<j.getJog().getMao().size();i++)
			{
				if(x>75*i+lxma&&x<75*i+(lxma+71))
				{
					jogada.addElement(new Integer(i));
					break;
				}
			}
		}
		return true;
	}
	//falta fazer o grafico das escovas
	public void paint(Graphics g)
	{
		for(int i=0;i<j.getJog().getMao().size();i++)//poe as cartas da mao
		{
			Carta c=(Carta)j.getJog().getMao().elementAt(i);
			int valor= c.getValor();
			String naipe=c.getNaipe();
			String num=Integer.toString(valor);
			String arq="d:\\\\Programacao\\\\Java\\\\Escova\\\\Cartas\\\\";
			arq=arq.concat(num);
			arq=arq.concat(naipe);
			if(naipe.equals("espadas")||naipe.equals("paus"))
				arq=arq.concat(".gif");
			else arq=arq.concat(".jpg");
			g.drawImage(Toolkit.getDefaultToolkit().getImage(arq),i*75+(800-75*j.getJog().getMao().size())/2,350,this);
		}
		for(int i=0;i<j.getJog().getMonte().size();i++)//poe a monte do pl
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("d:\\Programacao\\Java\\Escova\\Cartas\\versoh.jpg"),i+650,i+350,this);
		}
		
		for(int i=0;i<j.getMesa().size();i++)//poe a mesa
		{
			Carta c=(Carta)j.getMesa().elementAt(i);
			int valor= c.getValor();
			String naipe=c.getNaipe();
			String num=Integer.toString(valor);
			String arq="d:\\\\Programacao\\\\Java\\\\Escova\\\\Cartas\\\\";
			arq=arq.concat(num);
			arq=arq.concat(naipe);
			if(naipe.equals("espadas")||naipe.equals("paus"))
				arq=arq.concat(".gif");
			else arq=arq.concat(".jpg");
			
			if(j.getMesa().size()<12)
				g.drawImage(Toolkit.getDefaultToolkit().getImage(arq),i*75+(800-75*j.getMesa().size())/2,200,this);
			else if(i<11)g.drawImage(Toolkit.getDefaultToolkit().getImage(arq),i*75+(800-75*j.getMesa().size())/2,100,this);
					 else g.drawImage(Toolkit.getDefaultToolkit().getImage(arq),i*75+(800-75*j.getMesa().size())/2,200,this);
		}
		
		for(int i=0;i<j.getPc().getMao().size();i++)//poe a mao do pc
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("d:\\Programacao\\Java\\Escova\\Cartas\\verso.jpg"),i*75+(800-75*j.getPc().getMao().size())/2,30,this);
		}
		for(int i=0;i<j.getPc().getMonte().size();i++)//poe a monte do pc
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("d:\\Programacao\\Java\\Escova\\Cartas\\versoh.jpg"),i+30,i+30,this);
		}
	}
	/*public void setMaopc(int maopc){this.maopc=maopc;}
	public void setMpc(int mpc){this.mpc=mpc;}
	public void setMpl(int mpl){this.mpl=mpl;}
	public void setMao(Vector mao){this.mao=mao;}
	public void setMesa(Vector mesa){this.mesa=mesa;}*/
}
	