package at.aau.server.kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;

import at.aau.server.dto.BackInMapMessage;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.CheatedMessage;
import at.aau.server.dto.CloseDiceActivitiesMessage;
import at.aau.server.dto.ConqueredMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.dto.EyeNumbersMessage;
import at.aau.server.dto.LogMessage;
import at.aau.server.dto.LostMessage;
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.QuitMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.RequestPlayerMessage;
import at.aau.server.dto.ResponsePlayerMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.dto.WonMessage;

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

        instance.registerClass(int[].class);
        instance.registerClass(Integer[].class);
        instance.registerClass(String[].class);
        instance.registerClass(ArrayList.class);
        instance.registerClass(LogMessage.class);
        instance.registerClass(NameMessage.class);
        instance.registerClass(RequestPlayerMessage.class);
        instance.registerClass(ResponsePlayerMessage.class);
        instance.registerClass(StartMessage.class);
        instance.registerClass(ReadyMessage.class);
        instance.registerClass(TurnMessage.class);
        instance.registerClass(UpdateMessage.class);
        instance.registerClass(DiceMessage.class);
        instance.registerClass(EyeNumbersMessage.class);
        instance.registerClass(CheatedMessage.class);
        instance.registerClass(ConqueredMessage.class);
        instance.registerClass(BackInMapMessage.class);
        instance.registerClass(CardMessage.class);
        instance.registerClass(ExchangeMessage.class);
        instance.registerClass(CloseDiceActivitiesMessage.class);
        instance.registerClass(LostMessage.class);
        instance.registerClass(WonMessage.class);
        instance.registerClass(QuitMessage.class);

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

            @Override
            public void received(Connection connection, Object object) {
                if (messageCallback != null && object instanceof BaseMessage)
                    messageCallback.callback((BaseMessage) object);
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
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
