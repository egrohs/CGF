package cgf.estado;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import lombok.Data;

@Data
// TODO a classe deveria se chamar Componente?
public class Zona implements Cloneable {
    private String guid; // The 6 character unique Object identifier within Tabletop Simulator. It is										USAR
                  // assigned correctly once the spawning member variable becomes false.; //
    private String held_by_color; // The Color of the Player that is holding the object.; //										USAR
    private int held_flip_index; // 0-23 value. Changes when a Player hits flip or alt + rotate.; //										USAR
    private Vector held_position_offset; // Position offset from pointer.; //
    private Vector held_rotation_offset; // Rotation offset from pointer.; //
    private int held_spin_index; // 0-23 value. Changes when a Player rotates the Object.; //										USAR
    private boolean hide_when_face_down; // Hide the Object when face-down as if it were in a hand zone. The face is the										USAR
                                         // "top" of the Object, the direction of its positive Y coordinate. Cards/decks
                                         // default to true.; //
    private boolean ignore_fog_of_war; // Makes the object not be hidden by Fog of War.; //										USAR
    private boolean interactable; // If the object can be interacted with by Players. Other object will still be
                                  // able to interact with it.; //
    private boolean is_face_down; // If the Object is roughly face-down (like with cards). The face is the "top"										USAR
                                  // of the Object, the direction of its positive Y coordinate. Read only.; //
    private boolean locked; // If the object is frozen in place (preventing physics interactions).; //										USAR
    private String memo; // A string where you may persist user-data associated with the object. Tabletop										USAR
                  // Simulator saves this field, but otherwise does not use it. Store whatever
                  // information you see fit.; //
    private String name; // Internal resource name for this Object. Read only, and only useful for										USAR
                  // spawnObjectData(). Generally, you want getName().; //
    private String type; // This object's type. Read only.; //																	USAR
    private boolean use_hands; // If this object can be held in a hand zone.; //										USAR
    private int value; // A numeric value associated with the object, which when non-zero, will be										USAR
                       // displayed when hovering over the object.
    // In the case of stacks, the value shown in the UI will be multiplied by the
    // stack size i.e. you can use value to create custom stackable chips.
    // When multiple objects are selected, values will be summed together with
    // objects sharing overlapping object tags.

    //private GameObject game_object; // The GameObject the Component composes.
    

    // allow_swapping		When moving an object from one full group to another, the object you drop on will be moved to the original group.
    // cards_per_deck		Sets the size of decks made by the layout zone when it combines newly added cards.
    // combine_into_decks		Whether cards added to the zone should be combined into decks. You may specify the number of cards per deck.
    // max_objects_per_group		Each group in the zone may not contain more than this number of objects.
    // max_objects_per_new_group		When new objects are added to a zone, they will be gathered into groups of this many objects.
    // meld_direction		The direction the objects within a group will expand into.
    // meld_reverse_sort		When enabled the sort order inside a group is reversed
    // meld_sort		How groups are sorted internally.
    // meld_sort_existing		When enabled all groups will be sorted when laid out, rather than only newly added groups.
    // new_object_facing		Determines whether newly added objects are turned face-up or face-down.
    // randomize		Objects will be randomized whenever they are laid out
    // split_added_decks		Decks added to the zone will be split into their individual cards.
    // sticky_cards		When picked up, cards above the grabbed card will also be lifted.
    
    public boolean shuffle(){return true;} // Shuffles/shakes up contents of a deck or bag.
        // | deal(count, playerColor) | Distribui cartas para jogadores. | deck.deal(3, "Red") |
    // | flip() | Vira uma carta ou token. | card.flip() |

    

    public Map<Object, Object> getOptions(){return null;} // Returns the layout zones options (table).

    
// OU List<Object>
    public boolean setOptions(Map<Object, Object> options){return true;} // Sets the layout zone's options. If an option is not included in the
                                               // table, then the zone's value for that option will remain unchanged.
    
    
private Zona parent;
    List<Zona> children;
    List<Zona> components;

    Zona getChild(String name){return null;} // GameObject Returns a child GameObject matching the specified name.
    // getChildren() Returns the list of children GameObjects.

    public Zona getComponent(String name){return null;} // Component Returns a Component matching the specified name from the
                                                // Object's list of Components.

    public Zona getComponentInChildren(String name){return null;} // Component Returns a Component matching the specified name.
                                                          // Found by searching the Components of the Object and its
                                                          // children recursively (depth first).

    public List<Zona> getComponents(String name){return null;} // Returns the Object's list of Components. name is optional,
                                                       // when specified only Components with specified name will be
                                                       // included.

    public List<Zona> getComponentsInChildren(String name){return null;} // Returns a list of Components found by searching the
                                                                 // Object and its children recursively (depth first).
                                                                 // name is optional, when specified only Components
                                                                 // with specified name will be included.
    
    
    public static enum TipoZona {
        /* NINGUEM */PROTEGIDA, /* DONOS */PRIVADA, /* TODOS */PUBLICA;
    }

    protected TipoZona visivelPor;

    protected List<String> donos;

    transient protected boolean selecionada;

    // TODO fazer as zonas terem props como minDistX e minDistY passadas no
    // contrutor
    public Zona(String nome, String[] donos, /* Zona conteudo, */TipoZona visivelPor) {
        if (donos != null) {
            this.donos = Arrays.asList(donos);
        }
        this.visivelPor = visivelPor;
        // TODO remover isso???
        setName(nome);
        
    }

    public EstadoJogo getEstado() {
        Zona c = this;
        do {
            if (c instanceof EstadoJogo)
                return (EstadoJogo) c;
            c = c.getParent();
        } while (c != null);
        return null;
    }

    public Zona getZonaPrimeiraOrdem() {
        Container c = this;
        do {
            if (c.getParent() instanceof EstadoJogo)
                return (Zona) c;
            c = c.getParent();
        } while (c != null);
        return null;
    }

    

    @Override
    public Componente add(Componente comp) {
        return add(comp, -1);
    }

    @Override
    public Componente add(Componente comp, int index) {
        if (!(comp instanceof Zona)) {
            // Nao deve fazer nada
            System.err.println("Uma zona s� pode ter filhos do tipo Zona!");
            return null;
        }
        Container antesMove = comp.getParent();
        super.add(comp, index);
        Zona novaZona = (Zona) comp;
        // Quando uma zona muda de local ela muda de dono.
        novaZona.setDonos(donos);
        novaZona.visivelPor = visivelPor;
        reorganiza();
        firePropertyChange("moveu", antesMove, this);
        return comp;
    }

    @Override
    public void remove(Componente comp) {
        super.remove(comp);
        reorganiza();
        // firePropertyChange("component", 4, 5);
    }

    

    /*
     * public Set<String> getDonos() { return donos; }
     * 
     * public Set<String> getVisivelPor() { return visivelPor; }
     */

    public void setDonos(List<String> donos) {
        this.donos = donos;
    }

    public void setDonos(String[] donos) {
        this.donos = Arrays.asList(donos);
    }

    /*
     * public void setVisivelPor(Set<String> visivelPor) { this.visivelPor =
     * visivelPor; }
     */

    

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }

    

    // public TipoZona getTipoZona() {
    // return tipoZona;
    // }
    //
    // public void setTipoZona(TipoZona tipoZona) {
    // this.tipoZona = tipoZona;
    // }
    //
    public boolean isVisivelPossui(String pName) {
        return visivelPor == TipoZona.PUBLICA || possuida(pName);
    }

    public boolean possuida(String pName) {
        return donos != null && donos.contains(pName);
    }
    //
    // public void setVisivelPor(VisivelPor visivelPor) {
    // this.visivelPor = visivelPor;
    // }
}