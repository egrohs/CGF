package bgf.controlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// GameEventBus.java - Sistema de eventos
public class GameEventBus {
    private static GameEventBus instance;
    private Map<Class<?>, List<EventHandler>> handlers;
    
    private GameEventBus() {
        handlers = new HashMap<>();
    }
    
    public static GameEventBus getInstance() {
        if (instance == null) {
            instance = new GameEventBus();
        }
        return instance;
    }
    
    public <T> void subscribe(Class<T> eventType, EventHandler<T> handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }
    
    public void publish(Object event) {
        Class<?> eventType = event.getClass();
        List<EventHandler> eventHandlers = handlers.get(eventType);
        
        if (eventHandlers != null) {
            for (EventHandler handler : eventHandlers) {
                handler.handle(event);
            }
        }
    }
    
    public interface EventHandler<T> {
        void handle(T event);
    }
}