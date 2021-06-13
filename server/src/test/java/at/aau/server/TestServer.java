package at.aau.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.LogMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameClient;
import at.aau.server.kryonet.GameServer;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestServer {

    GameServer server = GameServer.getInstance();
    GameClient client = GameClient.getInstance();

    // TODO: SPLIT INTO MULTIPLE TESTS!
    @Test
    public void testConnection() {
        LogMessage request = new LogMessage("Marco!");
        LogMessage response = new LogMessage("Polo!");

        server.registerClass(LogMessage.class);
        client.registerClass(LogMessage.class);

        server.registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                assertEquals("Marco!", ((LogMessage) argument).text);
                server.broadcastMessage(response);
            }
        });
        client.registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                assertEquals("Polo!", ((LogMessage) argument).text);
            }
        });

        Thread serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    server.start();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        };
        serverThread.start();

        Thread clientThread = new Thread() {
            @Override
            public void run() {
                try {
                    client.connect("localhost");
                    client.sendMessage(request);
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        };
        clientThread.start();
    }

}
