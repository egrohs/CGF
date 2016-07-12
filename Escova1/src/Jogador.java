import java.util.*;

class Jogador extends Player
{
	private String nome;
	//private int ouro,primeira,carta,escova,pontos=0;
	//private boolean belo;
	//private Vector mao=new Vector();
	//private Vector jogadas=new Vector();
	//private Vector monte=new Vector();
	
	public Jogador(String nome)
	{
		this.nome=nome;
	}
	
	public Vector fazJogada(Pc pc, Vector jogada, Vector mesa)
	{
		if(jogada.size()>1)//entao vai pegar
		{
			/*for(int i=0;i<jogada.size();i++)
			{
				Integer itg=(Integer)jogada.elementAt(i);
				int pos=Integer.parseInt(itg.toString());
			}*/
			ult=true;pc.setUlt(false);
			Integer itg=(Integer)jogada.elementAt(0);
			int pos=Integer.parseInt(itg.toString());
			//poe a carta da mao no monte
			monte.addElement((Carta)mao.elementAt(pos));
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
		}
		else//senao joga uma na mesa
		{
			Integer itg=(Integer)jogada.elementAt(0);
			int pos=Integer.parseInt(itg.toString());
			mesa.addElement((Carta)mao.elementAt(pos));
			mao.removeElementAt(pos);
		}
		return mesa;
	}
}