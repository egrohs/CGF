package cgf.listeners;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cgf.Constantes;
import cgf.Controle;
import cgf.Jogo;
import cgf.Util;
import cgf.rmi.IPlayer;

public class ListenersVisao {
    private Controle controle;

    public ListenersVisao(Controle controle) {
        this.controle = controle;
        // this.player = player;
        addPlayListener();
        addKeybordListener();
        addVezListener();
    }

    private void addPlayListener() {
        controle.getVisao().getPlayDialog().getPlay().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Controle player = controle;
                String action = player.getVisao().getPlayDialog().getBg().getSelection().getActionCommand();
                String nome = player.getVisao().getPlayDialog().getNome().getText();
                Jogo.nomePlayer = nome;
                player.getVisao().setTitle(nome);
                if ("novo".equals(action)) {
                    // host = true;
                    player.getPlayer().setNumPlayers((Integer) player.getVisao().getPlayDialog().getMinPlayer().getValue());
                    player.getPlayer().addObserver(nome, player.getPlayer());
                    Util.cadastra(player.getPlayer());
                    player.getVisao().setExtendedState(Frame.ICONIFIED);
                } else if ("salvo".equals(action)) {
                    // host = true;
                } else if ("remoto".equals(action)) {
                    // host = false;
                    try {
                        String ip = player.getVisao().getPlayDialog().getIp().getText();
                        IPlayer server = Util.getRemotePlayer(ip, Constantes.REMOTE_PLAYER + "0");
                        server.update(nome, player.getPlayer()/* ip */);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                player.getVisao().getPlayDialog().dispose();
            }
        });
    }

    private void addKeybordListener() {
        controle.getVisao().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    Mouse.deselecionaTudo();
                }
            }
        });
    }

    private void addVezListener() {
        controle.getVisao().getButtonVez().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("passou");
                controle.getJogo().passaVez();
            }
        });
    }

}
