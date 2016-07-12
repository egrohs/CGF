import java.util.*;
import java.math.*;

class Jogo// extends Thread
{
	private String naipes[]={"ouros","paus","copas","espadas"};
	private Vector baralho=new Vector();
	private Vector mesa=new Vector();
	private Vector jogadores=new Vector();
	private Vector pcs=new Vector();
	//private Meupanel mp;
	//private int pos;
	
	public Jogo(int nplayers, String nomes[]/*, Meupanel mp*/)
	{
		//for(int i=0;i<nplayers;i++)
			//players.addElement(new Player(nomes[i]));//arrumar para pc e player
			//this.mp=mp;//
			pcs.addElement(new Pc());
			jogadores.addElement(new Jogador("Estivador"));//
		inicio();
	}
	
	public void inicio()
	//public void start()
	{
		for(int v=1;v<=10;v++)//cria baralho
		{
			//System.out.println("Oi");
			for(int n=0;n<4;n++)
			{
				Carta c=new Carta(v,naipes[n]);
				mesa.addElement(c);
				//System.out.println("Val= "+c.getValor()+"Naip= "+c.getNaipe());
			}
		}
		for(int i=40;i>0;i--)//embaralha
		{
			int pos=(int)(Math.random()*i);
			//System.out.println("Pos= "+pos);
			baralho.addElement((Carta)mesa.elementAt(pos));
			mesa.removeElementAt(pos);
		}
		for(int i=0;i<4;i++)//bota a mesa
		{
			mesa.addElement((Carta)baralho.elementAt(0));
			baralho.removeElementAt(0);
		}
		damaos();
	}
	
	public void joga(Vector jogada, Meupanel mp)
	{
		Pc pc=(Pc)pcs.elementAt(0);
		Jogador pl=(Jogador)jogadores.elementAt(0);
		
		Vector maopc=(Vector)pc.getMao();
		Vector maojog=(Vector)pl.getMao();
			
		mesa=pl.fazJogada(pc,jogada,mesa);
		if(mesa.size()==0)//entao fez escova
		{
			pl.setEscova();
			System.out.println("Voce fez Escova!");
			//mp.repaint();
		}
		
		pc.criaJogadas(mesa);
		mesa=pc.melhorJogada(pl,mesa);
		if(mesa.size()==0)//entao fez escova
		{
			pc.setEscova();
			System.out.println("O Pc fez Escova!");
			//mp.repaint();	
		}
		
		if(baralho.size()>0)
		{
			if(maopc.size()==0)
			{
				damaos();
				//mp.repaint();
			}
		}
		if(baralho.size()==0&&maopc.size()==0)//acabou jogo, pontuacao
		{
			pontuacao(pl,pc);
		}
		mp.repaint();
	}
	
	public void damaos()
	{
		//falta zerar o vetor de jogadas!!!
		for(int i=0;i<jogadores.size();i++)
		{
			Jogador p=(Jogador)jogadores.elementAt(i);
			for(int j=0;j<3;j++)//bota as maos de todos players
			{
				p.setMao((Carta)baralho.elementAt(0));
				baralho.removeElementAt(0);
			}
		}
		for(int i=0;i<jogadores.size();i++)
		{
			Pc pc=(Pc)pcs.elementAt(i);
			for(int j=0;j<3;j++)//bota as maos de todos pcs
			{
				pc.setMao((Carta)baralho.elementAt(0));
				baralho.removeElementAt(0);
			}
		}
	}
	
	public void pontuacao(Jogador pl, Pc pc)
	{
		int jogpont=0,pont=0,ouro=0,setes=0;
			
			while(mesa.size()>0)//resolve a ultima de mesa
			{
				Carta c=(Carta)mesa.elementAt(0);
				if(pl.getUlt()==true)
					pl.setMonte(c);
				else pc.setMonte(c);
				mesa.removeElementAt(0);
			}
			
			Vector mpc=(Vector)pc.getMonte();
			Vector mjog=(Vector)pl.getMonte();
			
			System.out.println("mpc= "+mpc.size());
			System.out.println("mjog= "+mjog.size());
			
			for(int l=0;l<mpc.size();l++)	//verifica 7ouro, ouros e setes do PC
			{	
				Carta h=(Carta)mpc.elementAt(l);
				if(h.getValor()==7 && h.getNaipe().equals("ouros"))
					pont++;
				if(h.getNaipe().equals("ouros"))
					ouro++;
				if(h.getValor()==7)
					setes++;
			}
			if(setes>=3)pont++;	//tem erro
			if(ouro>=6)pont++;
			
			ouro=0;setes=0;
		
			for(int l=0;l<mjog.size();l++)	//verifica 7ouro, ouros e setes do PC
			{	
				Carta h=(Carta)mjog.elementAt(l);
				if(h.getValor()==7 && h.getNaipe().equals("ouros"))
					jogpont++;
				if(h.getNaipe().equals("ouros"))
					ouro++;
				if(h.getValor()==7)
					setes++;
			}

			if(setes>=3)jogpont++;	//tem erro
			if(ouro>=6)jogpont++;
			
			if(mpc.size()>20)pont++;
			if(mjog.size()>20)jogpont++;
			pont+=pc.getEscova();	
			jogpont+=pl.getEscova();
			pc.setPontos(pont);
			pl.setPontos(jogpont);
			System.out.println("Pc Pontos= "+pont);
			System.out.println("Jog Pontos= "+jogpont);
	}
	
	public Vector ordenaVector(Vector v)//nao esta sendo usado
	{
		Vector t=new Vector();
		int men;
		while(v.size()>0)
		{
			men=0;
			for(int j=0;j<v.size();j++)
			{
				Carta c1=(Carta)v.elementAt(men);
				Carta c2=(Carta)v.elementAt(j);
				if(c2.getValor()<c1.getValor())
					men=j;
			}
			t.addElement((Carta)v.elementAt(men));
			v.removeElementAt(men);
		}
		return t;
	}
	public Vector getMesa(){return mesa;}
	public void setMesa(Vector m){mesa=m;}
	public Pc getPc(){return (Pc)pcs.elementAt(0);}
	public Jogador getJog(){return (Jogador)jogadores.elementAt(0);}
}