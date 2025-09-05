package aplicacao;

import ttgdx.TabletopGDX;

public class Escova extends TabletopGDX {
    public Escova() {
        super();
        inicializarJogo();
    }

    private void inicializarJogo() {
        // Use os métodos do BoardGameFramework para configurar o jogo
        // Exemplo:
        //this.board = BoardFactory.createBoard(boardConfig);
        // this.turnManager.initialize(jogadores, turnConfig.getType(), turnConfig.getParameters());
        // ...outros setups...
    }

    public static void main(String[] args) {
        // O usuário implementa uma subclasse concreta de Escova e instancia aqui
        //Escova jogo = new Escova();
        // Métodos do framework podem ser chamados conforme necessário
        new Escova();
    }
}