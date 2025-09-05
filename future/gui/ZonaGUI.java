package cgf.gui;

import java.util.Map;

import javax.swing.BorderFactory;

import cgf.estado.CartaBaralho;
import cgf.estado.Zona;

public class ZonaGUI {
// VISUAL
    // alternate_direction		Objects added to a group will be aligned up or right, different from the preceding object in the group. Used, for example, in trick-taking games to make counting easier.
    // direction		The directions the groups in the zone expand into. This will determine the origin corner.
    // horizontal_group_padding		How much horizontal space is inserted between groups.
    // horizontal_spread		How far each object in a group is moved horizontally from the previous object.
    // instant_refill		When enabled, if ever a group is picked up or removed the rest of the layout will trigger to fill in the gap
    // manual_only		The zone will not automatically lay out objects: it must be triggered manually.
    // trigger_for_face_down		Face-Down objects dropped on zone will be laid out.
    // trigger_for_face_up		Face-Up objects dropped on zone will be laid out.
    // trigger_for_non_cards		Non-card objects dropped on zone will be laid out
    // vertical_group_padding		How much vertical space is inserted between groups.
    // vertical_spread		How far each object in a group is moved vertically from the previous object.

    public Map<Object, Object> getOptions(); // Returns the layout zones options (table).

    public boolean layout(); // Causes the layout zone to (re)layout.
// OU List<Object>
    public boolean setOptions(Map<Object, Object> options); // Sets the layout zone's options. If an option is not included in the
                                               // table, then the zone's value for that option will remain unchanged.
    
    public ZonaGUI() {
        if (!(this instanceof CartaBaralho)) {
                setBorder(BorderFactory.createTitledBorder(nome));
        }
        setMinimumSize(new Dimension(GUIPreferencias.deckX, GUIPreferencias.deckY));
    }

    public boolean layout(); // Causes the layout zone to (re)layout.

    // TODO deve ser recursivo? in-out ou out-in?
    public void reorganiza(/* Container comp */) {
        int nro = this.getComponentCount();
        if (nro > 0) {
            this.setPreferredSize(new Dimension((nro + 1) * GUIPreferencias.minDistX + GUIPreferencias.deckX, (nro + 2)
                    * GUIPreferencias.minDistY + GUIPreferencias.deckY));
            for (int i = 0; i < nro; i++) {
                Zona zona = (Zona) this.getComponent(i);
                zona.setLocation((nro - i) * GUIPreferencias.minDistX, (nro - i + 1) * GUIPreferencias.minDistY);
                // setComponentZOrder(zona, nro-1);
            }
        }
        // repaint();
        // for (Component c : comp.getComponents()) {
        // reorganiza((Zona) c, compress);
        // }
    }

    public void setaFoto() {
        // TODO Auto-generated method stub

    }

    public static void criaBorda(Zona zona, boolean cria) {
        if (zona != null && zona.getIcon() != null) {
            if (cria) {
                zona.setSize(zona.getIcon().getIconWidth() + GUIPreferencias.larguraBorda * 2, zona.getIcon().getIconHeight()
                        + GUIPreferencias.larguraBorda * 2);
                zona.setBorder(BorderFactory.createLineBorder(GUIPreferencias.corBorda, GUIPreferencias.larguraBorda));
                // EtchedBorder(EtchedBorder.RAISED));//BevelBorder(BevelBorder.RAISED));//
            } else {
                zona.setBorder(null);
                zona.setSize(zona.getIcon().getIconWidth(), zona.getIcon().getIconHeight());
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (selecionada) {
            GUIPreferencias.negative(g2, this);
        } else {
            super.paint(g);
        }
    }
}
