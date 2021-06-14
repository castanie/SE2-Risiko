package at.aau.risiko.networking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.server.dto.LogMessage;
import at.aau.server.kryonet.KryoNetComponent;
import at.aau.server.kryonet.GameServer;
import at.aau.server.kryonet.GameClient;
import at.aau.server.kryonet.NetworkClient;
import at.aau.server.kryonet.NetworkServer;

public class NetworkingTest {

    // Peter Söllnbauer: Example Testcases for Networking from Android Network Wrapper
    // --> to extend

    private static final String REQUEST_TEST = "request test";
    private static final String RESPONSE_TEST = "response test";

    private AtomicBoolean request1Handled;
    private AtomicBoolean request2Handled;
    private AtomicBoolean responseHandled;

    @Before
    public void setup() {
        request1Handled = new AtomicBoolean(false);
        request2Handled = new AtomicBoolean(false);
        responseHandled = new AtomicBoolean(false);
    }

    @Test
    public void NetworkConnection_OneClient_SendAndReceiveText() throws IOException, InterruptedException {
        System.out.printf("Main Thread ID: %d%n", Thread.currentThread().getId());

        startServer();
        startClient();

        // wait for server and client to handle messages

        Thread.sleep(1500);

        Assert.assertTrue(request1Handled.get());
        Assert.assertTrue(request2Handled.get());
        Assert.assertTrue(responseHandled.get());
    }

    private void startServer() throws IOException {
        AtomicBoolean first = new AtomicBoolean(true);

        NetworkServer server = GameServer.getInstance();
        registerClassesForComponent((GameServer) server);

        server.start();
        server.registerCallback(argument -> {
                    System.out.printf("Server Thread ID: %d%n", Thread.currentThread().getId());

                    if (first.get()) {
                        first.set(false);

                        // check correct polymorphism
                        Assert.assertNotSame(argument.getClass(), LogMessage.class);
                        Assert.assertTrue(argument instanceof LogMessageSubClass);
                        request1Handled.set(true);
                    } else {

                        // check correct polymorphism
                        Assert.assertFalse(argument instanceof LogMessageSubClass);
                        Assert.assertTrue(argument instanceof LogMessage);

                        Assert.assertEquals(REQUEST_TEST, ((LogMessage) argument).text);
                        request2Handled.set(true);

                        server.broadcastMessage(new LogMessage(RESPONSE_TEST));
                    }
                }
        );
    }

    private void startClient() throws IOException {
        NetworkClient client = GameClient.getInstance();
        registerClassesForComponent((GameClient) client);

        client.connect("localhost");
        client.registerCallback(argument ->
                {
                    System.out.printf("Client Thread ID: %d%n", Thread.currentThread().getId());

                    Assert.assertTrue(argument instanceof LogMessage);
                    Assert.assertEquals(RESPONSE_TEST, ((LogMessage) argument).text);
                    responseHandled.set(true);
                }
        );

        client.sendMessage(new LogMessageSubClass());
        client.sendMessage(new LogMessage(REQUEST_TEST));
    }

    private void registerClassesForComponent(KryoNetComponent component) {
        component.registerClass(LogMessageSubClass.class);
        component.registerClass(LogMessage.class);
    }
}