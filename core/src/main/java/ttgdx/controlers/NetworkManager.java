package ttgdx.controlers;

import ttgdx.rules.GameAction;
import ttgdx.rules.RuleManager;

// NetworkManager.java - Gerenciador de rede
public class NetworkManager {
    // private GameClient client;
    // private GameServer server;
    // private NetworkMode mode;
    
    // public enum NetworkMode { LOCAL, SERVER, CLIENT }
    
    // public NetworkManager(NetworkMode mode) {
    //     this.mode = mode;
        
    //     if (mode == NetworkMode.SERVER) {
    //         server = new GameServer();
    //     } else if (mode == NetworkMode.CLIENT) {
    //         client = new GameClient();
    //     }
    // }
    
    // public void connect(String address, int port) {
    //     if (mode == NetworkMode.CLIENT && client != null) {
    //         client.connect(address, port);
    //     }
    // }
    
    // public void startServer(int port) {
    //     if (mode == NetworkMode.SERVER && server != null) {
    //         server.start(port);
    //     }
    // }
    
    // public void sendAction(GameAction action) {
    //     // Enviar ação para o servidor ou outros clientes
    //     if (mode == NetworkMode.SERVER) {
    //         // Validar e broadcast ação
    //         if (RuleManager.validateAction(action)) {
    //             action.execute();
    //             broadcastAction(action);
    //         }
    //     } else if (mode == NetworkMode.CLIENT) {
    //         // Enviar ação para o servidor
    //         client.sendAction(action);
    //     } else {
    //         // Modo local - executar diretamente
    //         action.execute();
    //     }
    // }
    
    // private void broadcastAction(GameAction action) {
    //     if (server != null) {
    //         server.broadcast(action);
    //     }
    // }
    
    // public void addNetworkListener(NetworkListener listener) {
    //     if (client != null) {
    //         client.addListener(listener);
    //     }
    // }
    
    // public interface NetworkListener {
    //     void onActionReceived(GameAction action);
    //     void onConnected();
    //     void onDisconnected();
    // }
}