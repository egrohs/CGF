import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgf.Constantes.Valores;
import cgf.estado.EstadoJogo;
import cgf.estado.Zona;

public abstract class Jogo {
public static void spawnObject(Object params) {
    }// TODO talvez nao precisem ser static

    public static Object getObjectFromGUID(String guid) {
        // TODO procurar em todos os objetos
        return null; // Placeholder for actual object retrieval logic
    }

    public static boolean destroyObject(Object obj) {
        return true;
    }// TODO talvez nao precisem ser static

    // getPlayers();//TODO aqui ou no TTSPlayer?
    // public static broadcastToAll(String msg, color);
    // public static broadcastToPlayer(String msg, playerColor);

    // Global.setVar(String name, value);
    // Global.getVar(String name);
    // public static saveData();
    // loadData();

    // public static Wait.time(func, delay);
    // math.random();
    // string.sub();

    // carregar o state e criar e preparar os objetos
    protected void onLoad() {

    }

    // TODO objetos preparados podem ter funções para definir seu comportamento como
    // clicks

    // onUpdate();
    // onload(saved_state);
    // onPlayerChangeColor(color);
    // onPlayerTurn(Player player);
    // onSave();

    // TODO usar reflexao pra ler os todos os metodos da classe de jogo que herda de
    // Base e exportar pra json
    // ObjectMapper mapper = new ObjectMapper();
    // converter pra codigo lua e,
    // String json = mapper.writeValueAsString(metodo.toString());

//============================================================================================

    //TODO public static ?
    public static String nomePlayer;

    // TODO usar mouselistener ou mouse ?
    protected List<EventListener> myListenersList;

    protected List<EstadoJogo> mementos;

    protected boolean moveu = false;

    public abstract Zona defineDeck();

    public abstract List<Zona> defineZonas();

    public Jogo(EstadoJogo estado) {
        System.out.println("Base constructor called");
        onLoad();// TODO script_state, decode da string json pra object state
        inicializaMementos();
        addMemento(estado);
        new Controle(this);
    }

    /**
     * TODO Pode ser sobrescrito pra definir outro estado de jogo que extends
     * EstadoJogo. Essa eh a melhor maneira?
     */
    private EstadoJogo criaEstadoJogo() {
        return new EstadoJogo();
    }

    /**
     * "Host" configura o estado do jogo ao clicar play. Define os nomes dos
     * jogadores as zonas e notifica-os.
     * 
     * @param playerNames
     * 
     */
    public final EstadoJogo configuraEstado(String[] playerNames) {
        // Se atingiu numero de jogadores.
        EstadoJogo estado = getUltimoMemento();
        estado.setPlayerVez(nomePlayer);
        estado.setPlayerNames(playerNames);
        // setNumPlayers(numPlayers);
        estado.setValores(defineValores());
        estado.setDeck(defineDeck());
        for (Zona zona : defineZonas()) {
            estado.add(zona);
        }
        return estado;
    }

    /**
     * Valida a a��o de mover retornando msg de erro quando invalida. Pode ser
     * sobreescrito invocando o super para adicionar novas msgs.
     * 
     * @return Msg de erro mostrada ao user.
     */
    public String validaMove(List<Zona> origens, Zona destino) {
        for (Zona origem : origens) {
            if (origem == null) {
                return "Zona origem nula";
            }
            if (origem == null) {
                return "Zona destino nula";
            }
            if (origem.equals(destino)) {
                return "Zona origem igual � destino: " + origem.getName();
            }
            if (!origem.possuida(nomePlayer)) {// donos.contains(Player.getInstancia().getNome()))
                return "Zona origem n�o pertence ao player: " + origem.getName();
            }
            if (!destino.possuida(nomePlayer)) {
                return "Zona destino n�o pertence ao player: " + destino.getName();
            }
            if (!getUltimoMemento().playerVez.equals(nomePlayer)) {
                return "N�o � sua vez";
            }
        }
        return null;
    }

    // public static final void mover(Set<Zona> origem, Zona destino,
    // ComportamentoMover comportamento) {
    // for (Zona zona : origem) {
    // mover(zona, destino, comportamento);
    // }
    // }

    /**
     * @param origens
     *            Zonas a serem movida.
     * @param destino
     *            Zona � qual a zona origem sera adicionada.
     * @param validar
     *            Se deve validar a jogada. Caso a valida��o falhar n�o move.
     * @return Se moveu com sucesso.
     */
    public final String mover(List<Zona> origens, Zona destino, boolean validar) {
        String msg = null;
        if (validar) {
            msg = validaMove(origens, destino);
            if (msg != null) {
                return msg;
            }
        }
        // EstadoJogo antesMoves = getUltimoMemento();
        for (Zona origem : origens) {
            destino.add(origem);
            if (origem.getParent() instanceof Zona) {
                ((Zona) origem.getParent()).reorganiza();
            }
        }
        moveu = true;
        // Cria o memento apos o move
        addMemento((EstadoJogo) destino.getEstado());
        // estado.firePropertyChange("moveu", antesMoves, estado);
        return msg;
    }

    public void passaVez() {
        EstadoJogo estado = getUltimoMemento();
        estado.setPlayerVez(nextPlayer());
        moveu = false;
    }

    /**
     * Invocado pelo clique do bot�o, define o pr�ximo jogador a jogar. Pode ser
     * sobrescrito para definir uma ordem diferente de acordo com as regras do
     * jogo.
     * 
     * @return
     */
    public String nextPlayer() {
        EstadoJogo estado = getUltimoMemento();
        String vez = null;
        // Circular
        for (int i = 0; i < estado.playerNames.length; i++) {
            if (estado.playerVez.equals(estado.playerNames[i])) {
                if (i == estado.playerNames.length - 1) {
                    vez = estado.playerNames[0];
                } else {
                    vez = estado.playerNames[i + 1];
                }
            }
        }
        return vez;
    }

    public boolean isMoveu() {
        return moveu;
    }

    //	public void setNomePlayer(String nome) {
    //		this.nomePlayer = nome;
    //	}
    //
    //	public String getNomePlayer() {
    //		return nomePlayer;
    //	}

    public final Map<Valores, Integer> defineValores() {
        Map<Valores, Integer> valores = new HashMap<Valores, Integer>();
        for (int i = 0; i < Valores.values().length; i++) {
            valores.put(Valores.values()[i], i);
        }
        aposDefineValores(valores);
        return valores;
    }

    public void aposDefineValores(Map<Valores, Integer> valores) {

    }

    public void aposReceberEstado() {
        // TODO Auto-generated method stub

    }

    /** Retorna o ultimo estado modificado */
    public EstadoJogo getUltimoMemento() {
        return getMemento(mementos.size() - 1);
    }

    public final EstadoJogo getMemento(int index) {
        return mementos.get(index);
    }

    public final void addMemento(EstadoJogo estado) {
        /*try {
            mementos.add((EstadoJogo) estado.clone());
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        mementos.add(estado);
    }

    public void inicializaMementos() {
        if (mementos == null) {
            mementos = new ArrayList<EstadoJogo>();
        }
        mementos.clear();
    }

    public final void preparaEstado(EstadoJogo estado, boolean compress) {
        inicializaMementos();
        //estado.preparaEstado(myListenersList, compress);
        estado.preparaEstadoRecursivo(myListenersList, estado, compress);
    }

    public void setListeners(EventListener[] listeners) {
        myListenersList = Arrays.asList(listeners);
    }

    @Override
    public String toString() {
        return nomePlayer;
    }
}