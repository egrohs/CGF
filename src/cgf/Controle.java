package cgf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.EventListener;

import cgf.estado.EstadoJogo;
import cgf.listeners.ListenersVisao;
import cgf.listeners.Mouse;
import cgf.rmi.IPlayer;
import cgf.rmi.Player;
import cgf.visao.Visao;

//TODO Singleton ?
public class Controle implements PropertyChangeListener, IPlayer {
    private static boolean notificaPlayers = true;

    //private static Controle controle;

    private Jogo jogo;

    private Player player;

    private Visao visao;

    Controle(Jogo jogo) {
        this.jogo = jogo;
        try {
            player = new Player(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        visao = new Visao();
        /*
         * TODO nao precisa dar new aki, podem ser metodos esticos pra adicionar
         * os listeners?!
         */
        new ListenersVisao(this);
    }

    //    public static Controle getInstancia() {
    //        if (controle == null) {
    //            controle = new Controle();
    //            new ListenersVisao(controle);
    //        }
    //        return controle;
    //    }

    /*
     * Escuta alterações no estado de jogo e manda executar as notificações
     * remotas.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (notificaPlayers) {
            if ("moveu".equals(evt.getPropertyName())) {
                EstadoJogo estado = (EstadoJogo) evt.getNewValue();
                jogo.preparaEstado(estado, true);
                broadcastEstado(estado);
            }
        }
    }

    public static void setNotificaPlayers(boolean notifica) {
        notificaPlayers = notifica;
    }

    public Visao getVisao() {
        return visao;
    }

    public void update(String sender, Object valor) {
        if (valor instanceof String[]) {
            setNotificaPlayers(false);
            EstadoJogo estado = jogo.configuraEstado((String[]) valor);
            addEstadoListeners(estado);
            // jogo.addMemento(estado);
            visao.setEstado(estado);
            setNotificaPlayers(true);
            broadcastEstado(estado);
        } else if (valor instanceof EstadoJogo) {
            EstadoJogo estado = (EstadoJogo) valor;
            if (!sender.equals(Jogo.nomePlayer)) {
                addEstadoListeners(estado);
                jogo.preparaEstado(estado, false);
                visao.setEstado(estado);
                jogo.inicializaMementos();
                jogo.addMemento((EstadoJogo) valor);
                jogo.aposReceberEstado();
            }
        }
    }

    private void addEstadoListeners(EstadoJogo estado) {
        //jogo.setListeners(new EventListener[] { new Mouse(this) });
        // TODO Usar mesmo property listeners???
        //estado.addPropertyChangeListener("playerVez", this);
        //estado.addPropertyChangeListener("moveu", this);
    }

    private void broadcastEstado(EstadoJogo estado) {
        // TODO setChanged(true) aki?
        //player.setChanged(true);
        for (PropertyChangeListener listener : estado.getPropertyChangeListeners()) {
            estado.removePropertyChangeListener(listener);
        }
        estado.preparaEstadoRecursivo(jogo.myListenersList, estado, true);
        player.notifyObservers(estado);
    }

    public Jogo getJogo() {
        return jogo;
    }

    public Player getPlayer() {
        return player;
    }
}