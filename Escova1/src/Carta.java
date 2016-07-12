class Carta
{
	private int Valor;
	private String Naipe;
	
	public Carta(int v,String n)
	{
		Valor=v;
		Naipe=n;
	}
	public int getValor(){return Valor;}
	public String getNaipe(){return Naipe;}
	public void setValor(int v){Valor=v;}
	public void setNaipe(String n){Naipe=n;}
}