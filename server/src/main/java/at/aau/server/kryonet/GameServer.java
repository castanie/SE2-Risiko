package at.aau.server.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import at.aau.server.dto.BaseMessage;

public class GameServer implements NetworkServer, KryoNetComponent {
    private static GameServer instance;
    private final Server server;
    private Callback<BaseMessage> messageCallback;

    private GameServer() {
        server = new Server();
    }

    public static GameServer getInstance() {
        if (instance == null) {
            instance = new GameServer();
        }
        return instance;
    }

    public Connection[] getConnections() {
        return this.server.getConnections();
    }

    public void registerClass(Class c) {
        server.getKryo().register(c);
    }

    public void start() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT);

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (messageCallback != null && object instanceof BaseMessage)
                    messageCallback.callback((BaseMessage) object);
            }
        });
    }

    public void registerCallback(Callback<BaseMessage> callback) {
        this.messageCallback = callback;
    }

    public void sendMessage(int playerIndex, BaseMessage message) {
        server.getConnections()[playerIndex].sendTCP(message);
    }

    public void broadcastMessage(BaseMessage message) {
        for (Connection connection : server.getConnections())
            connection.sendTCP(message);
    }
}
