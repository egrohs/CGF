import java.util.*;

class Player
{
	//protected String nome;
	protected int ouro,primeira,carta,escova=0,pontos=0;
	protected boolean belo,ult=false;
	protected Vector mao=new Vector();
	//protected Vector jogadas=new Vector();
	protected Vector monte=new Vector();
	
	/*public Player(String nome)
	{
		this.nome=nome;
	}
	
	public abstract Vector fazJogada(Vector jogada, Vector mesa)
	{
		return mesa;
	}*/
	public void setUlt(boolean b){ult=b;}
	public boolean getUlt(){return ult;}
	public void setPontos(int p){pontos+=p;}
	public int getPontos(Carta c){return pontos;}
	public void setMao(Carta c){mao.addElement(c);}
	public Vector getMao(){return mao;}
	public Vector getMonte(){return monte;}
	public void setMonte(Carta m){monte.addElement(m);}
	//public void setJogada(Vector v){jogadas.addElement(v);}
	//public Vector getJogadas(){return jogadas;}
	//public void setJogadas(Vector v){jogadas=v;/*jogadas.addElement(v);*/}
	//public Vector getJogadas(){return jogadas;}
	public boolean getBelo(){return belo;}
	public int getOuro(){return ouro;}
	public int getPrimeira(){return primeira;}
	public int getCarta(){return carta;}
	public void setEscova(){escova++;}
	public int getEscova(){return escova;}
}