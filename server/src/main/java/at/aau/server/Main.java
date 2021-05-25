package at.aau.server;

import com.esotericsoftware.kryonet.*;

import java.io.IOException;

import at.aau.server.networking.dto.ReadyMessage;
import at.aau.server.networking.dto.StartMessage;
import at.aau.server.networking.dto.TextMessage;
import at.aau.server.networking.dto.TurnMessage;

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
            server.getKryo().register(StartMessage.class);
            server.getKryo().register(ReadyMessage.class);
            server.getKryo().register(TurnMessage.class);


            server.start();
            server.bind(54555);
            server.addListener(new Listener() {
                int turn = 0;
                int barrier = 0;
                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof TextMessage) {
                        System.out.println(((TextMessage) object).text);
                    } else if (object instanceof StartMessage) {
                        System.out.println("StartMessage from " + connection.getRemoteAddressTCP().getHostString());
                        server.sendToAllTCP(object);
                    } else if (object instanceof ReadyMessage) {
                        System.out.println("ReadyMessage from " + connection.getRemoteAddressTCP().getHostString());
                        ++barrier;
                        if (barrier == server.getConnections().length) {
                            server.sendToTCP(server.getConnections()[turn].getID(), object);
                        }
                    } else if (object instanceof TurnMessage) {
                        turn = turn < server.getConnections().length - 1 ? ++turn : 0;
                        server.sendToTCP(server.getConnections()[turn].getID(), object);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}