package at.aau.server.kryonet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.ArrayList;

import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.CheatedMessage;
import at.aau.server.dto.CloseDiceActivitiesMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.dto.EyeNumbersMessage;
import at.aau.server.dto.LogMessage;
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.RequestPlayerMessage;
import at.aau.server.dto.ResponsePlayerMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;

public class GameClient implements NetworkClient, KryoNetComponent {
    private static GameClient instance;
    private final Client client;
    private Callback<BaseMessage> callback;

    private GameClient() {
        client = new Client();
    }

    public static GameClient getInstance() {
        if (instance == null) {
            instance = new GameClient();
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
        instance.registerClass(CardMessage.class);
        instance.registerClass(ExchangeMessage.class);
        instance.registerClass(CloseDiceActivitiesMessage.class);

        return instance;
    }

    /*
    public InetSocketAddress getRemoteAddress() {
        return this.client.getRemoteAddressTCP();
    }
    */

    public void registerClass(Class c) {
        client.getKryo().register(c);
    }

    public void connect(String host) throws IOException {
        client.start();
        client.connect(5000, host, NetworkConstants.TCP_PORT);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (callback != null && object instanceof BaseMessage)
                    callback.callback((BaseMessage) object);
            }
        });
    }

    public void registerCallback(Callback<BaseMessage> callback) {
        this.callback = callback;
    }

    public void sendMessage(BaseMessage message) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                client.sendTCP(message);
            }
        };
        thread.start();
    }

}
