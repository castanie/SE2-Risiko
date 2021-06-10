package at.aau.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

import at.aau.server.dto.NameMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TextMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;

public class Main {

    public static void main(String[] args) {

        /**
         * Players can request the server to open a lobby.
         * Players can see a list of lobbies in the LobbyActivity.
         * The lobby exists as long as 1 or more players are present.
         * The creator can start a game as soon as one other person has connected.
         *
         * To start the game, the creator sends a StartMessage to the server.
         * Upon receiving this message, the server broadcasts it to all players.
         * Once all players have sent a ReadyMessage in response, the server selects one player.
         * This player gets a TurnMessage and may play his turn.
         * After finishing, he sends a TurnMessage to the server, which forwards it to the next player.
         *
         * Rinse repeat!
         */

        try {
            Server server = new Server();
            server.getKryo().register(TextMessage.class);
            server.getKryo().register(NameMessage.class);
            server.getKryo().register(String[].class);
            server.getKryo().register(Integer[].class);
            server.getKryo().register(StartMessage.class);
            server.getKryo().register(ReadyMessage.class);
            server.getKryo().register(TurnMessage.class);
            server.getKryo().register(UpdateMessage.class);


            server.start();
            server.bind(54555);
            server.addListener(new Listener() {
                int turn = 0;
                int barrier = 0;

                HashMap<Connection, String> names = new HashMap<>();                

                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof TextMessage) {
                        System.out.println(((TextMessage) object).text);
                    } else if (object instanceof NameMessage) {
                        System.out.println(((NameMessage) object).playerName);
                        names.put(connection, ((NameMessage) object).playerName);
                    } else if (object instanceof StartMessage) {
                        System.out.println("StartMessage from " + connection.getRemoteAddressTCP().getHostString());
                        String[] playerNames = names.values().toArray(new String[0]);
                        Integer[] playerColors = new Integer[] {0xFFFFCC00, 0xFFFF00CC, 0xFF00CCFF, 0xFFCCFF00, 0xFF00FFCC};
                        ((StartMessage) object).names = playerNames;
                        ((StartMessage) object).colors = playerColors;
                        ((StartMessage) object).setup = true;
                        server.sendToTCP(names.keySet().toArray(new Connection[0])[0].getID(),object);
                        ((StartMessage) object).setup = false;
                        server.sendToAllExceptTCP(names.keySet().toArray(new Connection[0])[0].getID(),object);
                    } else if (object instanceof ReadyMessage) {
                        System.out.println("ReadyMessage from " + connection.getRemoteAddressTCP().getHostString());
                        ++barrier;
                        if (barrier == server.getConnections().length) {
                            server.sendToTCP(server.getConnections()[turn].getID(), object);
                        }
                    } else if (object instanceof TurnMessage) {
                        System.out.println("TurnMessage from " + connection.getRemoteAddressTCP().getHostString());
                        turn = turn < server.getConnections().length - 1 ? ++turn : 0;
                        ((TurnMessage) object).playerName = names.get(connection);
                        ((TurnMessage) object).playerIndex = turn;
                        server.sendToTCP(server.getConnections()[turn].getID(), object);
                    }
                    else if (object instanceof UpdateMessage) {
                        System.out.println("UpdateMessage from " + connection.getRemoteAddressTCP().getHostString());
                        // server.sendToAllExceptTCP(connection.getID(), object);
                        ((UpdateMessage) object).playerName = names.get(connection);
                        server.sendToAllTCP(object);
                    }
                }

                @Override
                public void disconnected(Connection connection) {
                    for (Connection c : names.keySet()) {
                        System.out.println(c.getID());
                    }
                    names.remove(connection);
                    for (Connection c : names.keySet()) {
                        System.out.println(c.getID());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}