package cgf.estado;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import cgf.visao.GUIPreferencias;


public class Zona extends JLabel implements Cloneable {
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
        // TODO remover isso???
        setName(nome);
        if (!(this instanceof CartaBaralho)) {
            setBorder(BorderFactory.createTitledBorder(nome));
        }
        this.visivelPor = visivelPor;
        setMinimumSize(new Dimension(GUIPreferencias.deckX, GUIPreferencias.deckY));
    }

    public EstadoJogo getEstado() {
        Container c = this;
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

    @Override
    public Component add(Component comp) {
        return add(comp, -1);
    }

    @Override
    public Component add(Component comp, int index) {
        if (!(comp instanceof Zona)) {
            // Nao deve fazer nada
            System.err.println("Uma zona só pode ter filhos do tipo Zona!");
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
    public void remove(Component comp) {
        super.remove(comp);
        reorganiza();
        // firePropertyChange("component", 4, 5);
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

    public void setaFoto() {
        // TODO Auto-generated method stub

    }

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
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
