package cgf.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;

import cgf.Controle;
import cgf.Jogo;
import cgf.estado.EstadoJogo;



/**
 * Classe com metodos uteis de manipula��o do estado do jogo. Usa padr�o
 * Observer para conversar com os Players cadastrados por RMI.
 * 
 * @author Zidane
 * 
 */
public class Player extends UnicastRemoteObject implements IPlayer {
// admin		If the player is promoted or the host of the game. Read only.
// blindfolded		If the player is blindfolded.
// color		The player's Player Color. Read only.
// host		If the player is the host. Read only.
// lift_height		The lift height for the player. This is how far an object is raised when held in a player's hand. Value is ranged 0 to 1.
// promoted		If the current player is promoted.
// seated		If a player is currently seated at this color. Read only.
// steam_id		The Steam ID of the player. This is unique to each player's Steam account. Read only.
// steam_name		The Steam name of the player. Read only.
// team		The team of the player.
// Options: "None", "Clubs", "Diamonds", "Hearts", "Spades", "Jokers".

    private int numPlayers;

    //private boolean changed = false;

    private Map<String, IPlayer> observers;

    private Controle controle;

    /**
     * TODO estodo informado no construtor para dizer ao framework qual o estado
     * do modelo. Pensar solu��o melhor, talvez usando factory.
     */
    public Player(Controle controle) throws RemoteException {
        this.controle = controle;
        observers = new LinkedHashMap<String, IPlayer>();
    }

    public synchronized void addObserver(String nomePlayer, IPlayer player) {
        if (player == null)
            throw new NullPointerException();
        if (!observers.containsValue(player)) {
            observers.put(nomePlayer, player);
        }
        //setChanged(true);
        notifyObservers(observers);
    }

    public synchronized void deleteObserver(IPlayer o) {
        observers.remove(o);
        //setChanged(true);
        notifyObservers(observers);
    }

    public void notifyObservers(Object arg) {
        if (arg instanceof EstadoJogo) {
            System.out.println("Vai mandar o estado");
        }
//        synchronized (this) {
//            if (!changed)
//                return;
//            setChanged(false);
//        }
        for (IPlayer player : observers.values()) {
            try {
                System.out.println("Notify " + player + " = " + arg);
                player.update(Jogo.nomePlayer, arg);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    public synchronized void deleteObservers() {
        observers.clear();
        //setChanged(true);
        notifyObservers(observers);
    }

//    synchronized void setChanged(boolean changed) {
//        this.changed = changed;
//    }
//
//    public synchronized boolean getChanged() {
//        return changed;
//    }

    public void addObservers(Map<String, IPlayer> obs) {
        observers.putAll((Map<String, IPlayer>) obs);
    }

    /*
     * public synchronized Set<IPlayer> getObservers() { return observers; }
     */

    public void update(String sender, Object valor) {
        System.out.println("Update from " + sender + " = " + valor);
        if (valor instanceof IPlayer) {
            addObserver(sender, (IPlayer) valor);
            if (observers.size() == numPlayers) {
                // Ao atingir o nro de Players
                //Jogo.getInstancia().configuraEstado();
                controle.update(sender, observers.keySet().toArray(new String[] {}));
            }
        } else if (valor instanceof Map) {
            addObservers((Map<String, IPlayer>) valor);
        } else if (valor instanceof EstadoJogo) {
            controle.update(sender, valor);
        }
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
}