import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
class Servidor extends UnicastRemoteObject implements RMInterface
{
	public Servidor()throws RemoteException
	{
		super();
	}
	public void Seila(){;}
}