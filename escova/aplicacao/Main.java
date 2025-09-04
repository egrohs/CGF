package cgf.aplicacao;

import cgf.estado.EstadoJogo;

public class Main {
    public static void main(String[] args) {
        //Controle.getInstancia().setJogo(new JogoEscova(new EstadoJogo()));
        new JogoEscova(new EstadoJogo());
    }
}