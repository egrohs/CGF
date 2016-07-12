import java.util.*;

class Pc extends Player
{
	//private String nome;
	//private int ouro,primeira,carta,escova,pontos=0;
	//private boolean belo;
	//private Vector mao=new Vector();
	private Vector jogadas=new Vector();
	//private Vector monte=new Vector();
	
	public Pc(/*String nome*/)
	{
		//this.nome=nome;
	}
	
	public void criaJogadas(/*Pc pc,*/Vector mesa)//versao binaria
	{
		Integer zero=new Integer(0);
		Integer um=new Integer(1);
		//Vector temp=pc.getMao();
		Carta c;
		Vector bin=new Vector();
		Vector jogada=new Vector();
		//Vector jogadas=new Vector();
		int soma=0;
		
		//bin.addElement(1);
		for(int i=0;i<mao.size();i++)
		{
			for(int n=0;n<mesa.size()/*-1*/;n++)//zera o vector bin
			{
				bin.addElement(zero);
			}
			c=(Carta)mao.elementAt(i);
			for(int j=0;j<Math.pow(2,mesa.size())-1;j++)
			{
				int ult;
				ult=mesa.size()-1;
				jogada.addElement(new Integer(i));

				while(true)
				{
					if(zero.equals(bin.elementAt(ult)))
	  			{
			  		bin.setElementAt(um,ult);
      			break;
    			}
   				//else
	   			//{
    			bin.setElementAt(zero,ult);
   				ult--;
	  		//}
				}
				for(int atual=0;atual<mesa.size();atual++)
				{
					if(um.equals(bin.elementAt(atual)))
					{
						//jogada.addElement((Carta)mesa.elementAt(atual));
						jogada.addElement(new Integer(atual));
						soma+=((Carta)mesa.elementAt(atual)).getValor();
					}
				}
				if(15-c.getValor()==soma)
				{
					Vector t=new Vector(jogada);
					jogadas.addElement(t);
					
					/*System.out.println("Jog");
					for(int l=0;l<jogada.size();l++)
					{
						System.out.println(" "+jogada.elementAt(l));
					}*/
					
				}
				jogada.removeAllElements();
				soma=0;
			}
			bin.removeAllElements();
		}
		//System.out.println("nro de jog= "+jogadas.size());	
		//setJogadas(jogadas);
	}
	
	public Vector melhorJogada(Jogador pl, Vector mesa)
	{
		boolean desempate=false;
		int tmao=mao.size();
		//int smesa=somaVector(mesa);
		int jog1=-1;//variavel com 2 funcoes; guarda a posicao da melhor jogada ou o peso de uma jogada
		int jog2=-1;//variavel com 2 funcoes; guarda a posicao da melhor jogada ou o peso de uma jogada
		int jog3=-1;//variavel com 2 funcoes; guarda a posicao da melhor jogada ou o peso de uma jogada
		int opcoes=0;
		Carta c1,c2,c3;
	
		if(jogadas.size()>0)
		{
			Vector jog;
			for(int i=0;i<jogadas.size();i++)
			{
				jog=(Vector)jogadas.elementAt(i);
				jog2=pesoJogada(jog,mesa);
				if(jog2<jog1)
				{
					jogadas.removeElementAt(i);
					i--;
				}
				if(jog2==jog1)desempate=true;
				if(jog2>jog1)
				{
					desempate=false;
					jog1=jog2;//guarda peso da jogada
					jog3=0;//pois a melhor jogada agora eh a primeira
					for(int j=i-1;j>-1;j--)jogadas.removeElementAt(j);//remove todos anteriores
					i=0;
				}
			}
			if(desempate==true)
			{
				jog=(Vector)jogadas.elementAt(0);
				jog2=jog.size();
				for(int i=1;i<jogadas.size();i++)
				{
					jog=(Vector)jogadas.elementAt(i);
					if(jog.size()>jog2)
					{
						jog2=jog.size();
						jog3=i;
					}
				}
			}
			mesa=fazJogada(pl,(Vector)jogadas.elementAt(jog3),mesa);
		}
//---------------------Vai ter que por na mesa------------------------
		else
		{
			if(tmao==1)//se só tiver uma carta na mao
			{
				mesa.addElement(mao.elementAt(0));
				mao.removeElementAt(0);
				return mesa;
			}
			c1=(Carta)mao.elementAt(0);
			c2=(Carta)mao.elementAt(1);
			//calculo para a 1 carta
			jog1=pesoCarta(c1,mesa);
			//calculo para a 2 carta							
			jog2=pesoCarta(c2,mesa);
			//calculo para a 3 carta			
			if(tmao==3)
			{
				c3=(Carta)mao.elementAt(2);
				jog3=pesoCarta(c3,mesa);		
				//poe uma das 3 cartas
				if(jog1<jog2)
				{
					if(jog1<jog3)
				  {
						mesa.addElement(mao.elementAt(0));
						mao.removeElementAt(0);
						return mesa;
				  }
					else
					{
						mesa.addElement(mao.elementAt(2));
						mao.removeElementAt(2);
						return mesa;
					}
				}
				else if(jog2<jog3)
						 {
						 	 mesa.addElement(mao.elementAt(1));
							 mao.removeElementAt(1);
							 return mesa;
						 }
						 else
						 {
						 	 mesa.addElement(mao.elementAt(2));
							 mao.removeElementAt(2);
							 return mesa;
						 }
			}//if do tmao==3
				
			//poe uma das 2 cartas
			if(jog1<jog2)
			{
				mesa.addElement(mao.elementAt(0));
				mao.removeElementAt(0);
				return mesa;
			}
			else
			{
				mesa.addElement(mao.elementAt(1));
				mao.removeElementAt(1);
				return mesa;
			}
		}//else do poe na mesa
		return mesa;
	}//funcao
	
	public Vector fazJogada(Jogador pl, Vector jogada, Vector mesa)
	{
		/*for(int i=0;i<jogada.size();i++)
		{
			Integer itg=(Integer)jogada.elementAt(i);
			int pos=Integer.parseInt(itg.toString());
		}*/
		ult=true;pl.setUlt(false);
		Integer itg=(Integer)jogada.elementAt(0);
		int pos=Integer.parseInt(itg.toString());
		//poe a carta da mao no monte
		Carta c=(Carta)mao.elementAt(pos);
		//System.out.println("Minha Carta ["+c.getValor()+" "+c.getNaipe()+"]");
		monte.addElement(c);
		mao.removeElementAt(pos);
		jogada.removeElementAt(0);
		//poe as cartas da mesa no monte
		do
		{
			int maior=-1;
			for(int i=0;i<jogada.size();i++)
			{
				itg=(Integer)jogada.elementAt(i);
				pos=Integer.parseInt(itg.toString());
				if(pos>maior)
					maior=pos;
			}
			monte.addElement((Carta)mesa.elementAt(maior));
			mesa.removeElementAt(maior);
			for(int i=0;i<jogada.size();i++)
			{
				itg=(Integer)jogada.elementAt(i);
				pos=Integer.parseInt(itg.toString());
				if(maior==pos)
					jogada.removeElementAt(i);
			}
		}while(jogada.size()>0);
		jogadas.removeAllElements();
		return mesa;
	}
		
	public int pesoCarta(Carta c, Vector mesa)
	{
		int jog=-1;
		int smesa=somaVector(mesa);
		boolean pares=true;
		
		if(!c.getNaipe().equals("Ouro"))jog=0;	
		for(int i=0;i<mesa.size();i++)//se a mesa so tem carta par...
			if(((Carta)mesa.elementAt(i)).getValor()%2!=0)pares=false;
		if(pares==true)
			if(c.getValor()%2==0)jog+=2;
		if(!(c.getValor()==7))
			if(smesa<4)
				if(c.getValor()<=4-smesa)
					if(c.getValor()==4-smesa)jog+=7;
					else jog+=5;
				else if(c.getValor()+smesa>15)jog+=3;
		return jog;
	}
	
	public int pesoJogada(Vector jogada, Vector mesa)
	{
		int peso=-1;
		Vector v=new Vector(jogada);//isso pq o filha da puta muda forever o conteudo do vetor jogada se eu removo algum elemento dele!
		
		Integer itg=(Integer)v.elementAt(0);
		int pos=Integer.parseInt(itg.toString());
		Carta c=(Carta)mao.elementAt(pos);
		v.removeElementAt(0);
		if(mesa.size()==v.size()-1)
		{
			if(c.getValor()==7)
				if(c.getNaipe().equals("Ouro"))peso=13;
				else peso=11;
			//entao eh escova
			for(int i=0;i<v.size();i++)
			{
				itg=(Integer)v.elementAt(i);
				pos=Integer.parseInt(itg.toString());
				Carta m=(Carta)mesa.elementAt(pos);
				if(m.getValor()==7)
					if(m.getNaipe().equals("Ouro"))
						if(peso==11)peso=14;
						else peso=13;
					else
					{
						if(peso==-1)peso=11;
						else if(peso==11)peso=12;
						if(peso==13)peso=14;
					}
				else if(peso<11 && c.getNaipe().equals("Ouro"))peso=10;
			}
			return peso;
		}
		//entao nao eh escova
		if(c.getValor()==7)
			if(c.getNaipe().equals("Ouro"))peso=7;
			else peso=2;
		for(int i=0;i<v.size();i++)
		{
			itg=(Integer)v.elementAt(i);
			pos=Integer.parseInt(itg.toString());
			Carta m=(Carta)mesa.elementAt(pos);
			if(m.getValor()==7)
				if(m.getNaipe().equals("Ouro"))
					if(peso==2)peso=9;
					else peso=7;
				else
				{
					if(peso==-1)peso=3;
					if(peso==2)peso=4;
					if(peso==7)peso=8;
				}
			else if(peso==-1)
						 if(c.getNaipe().equals("Ouro") || m.getNaipe().equals("Ouro"))
							 peso=1;
						 else peso=0;
		}
		if(peso<4)//tenta deixar 4 ou menos na mesa
		{
			int acu=somaVector(mesa);
			for(int i=0;i<v.size();i++)
			{
				itg=(Integer)v.elementAt(i);
				pos=Integer.parseInt(itg.toString());
				Carta m=(Carta)mesa.elementAt(pos);
				acu-=m.getValor();
			}
			if(acu<=4)
				peso=4;
		}
		//System.out.println("Peso= "+peso);
		return peso;
	}
	
	public int somaVector(Vector v)
	{
		int acu=0;
		for(int i=0;i<v.size();i++)
		{
			Carta c=(Carta)v.elementAt(i);
			acu+=c.getValor();
		}
		return acu;
	}
	
	public int somaJog(Vector v, Vector mao, Vector mesa)
	{
		if(v.size()>0)
		{
			
			Integer itg=(Integer)v.elementAt(0);
			int pos=Integer.parseInt(itg.toString());
			Carta c=(Carta)mao.elementAt(pos);
			int acu=c.getValor();
			for(int i=1;i<v.size();i++)
			{
				itg=(Integer)v.elementAt(i);
				pos=Integer.parseInt(itg.toString());
				c=(Carta)mesa.elementAt(pos);
				acu+=c.getValor();
			}
			return acu;
		}
		return 0;
	}
	
	//public void setJogadas(Vector v){jogadas=v;/*jogadas.addElement(v);*/}
	public Vector getJogadas(){return jogadas;}
}