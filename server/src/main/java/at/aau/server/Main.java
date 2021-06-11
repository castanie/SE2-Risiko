package at.aau.server;

import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.LogMessage;
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameServer;

public class Main {

    public static void main(String[] args) {

        /**
         * Players can request the server to open a lobby. Players can see a list of
         * lobbies in the LobbyActivity. The lobby exists as long as 1 or more players
         * are present. The creator can start a game as soon as one other person has
         * connected.
         *
         * To start the game, the creator sends a StartMessage to the server. Upon
         * receiving this message, the server broadcasts it to all players. Once all
         * players have sent a ReadyMessage in response, the server selects one player.
         * This player gets a TurnMessage and may play his turn. After finishing, he
         * sends a TurnMessage to the server, which forwards it to the next player.
         *
         * Rinse repeat!
         */

        try {
            GameServer server = GameServer.getInstance();
            server.registerClass(String[].class);
            server.registerClass(Integer[].class);
            server.registerClass(LogMessage.class);
            server.registerClass(NameMessage.class);
            server.registerClass(StartMessage.class);
            server.registerClass(ReadyMessage.class);
            server.registerClass(TurnMessage.class);
            server.registerClass(UpdateMessage.class);

            server.start();
            server.registerCallback(new Callback<BaseMessage>() {

                int readyBarrier = 0;
                int currentTurn = 0;

                @Override
                public void callback(BaseMessage argument) {

                    // Simple text message:
                    if (argument instanceof LogMessage) {
                        System.out.println("LogMessage: " + ((LogMessage) argument).text);
                    }

                    // Message sent from Login screen containing player name:
                    else if (argument instanceof NameMessage) {
                        System.out.println("NameMessage: " + ((NameMessage) argument).name);

                        // Register Player name:

                        
                    }

                    // Message sent from Lobby screen requesting game start:
                    else if (argument instanceof StartMessage) {
                        System.out.println("StartMessage received.");

                        // Order Clients to start:
                        ((StartMessage) argument).names = new String[] {"One", "Two"};
                        ((StartMessage) argument).colors = new Integer[] {0xFFFFCC00, 0xFFFF44CC};
                        server.broadcastMessage(argument);

                        readyBarrier = 0;

                    }

                    // Message returned by Players after starting game:
                    else if (argument instanceof ReadyMessage) {
                        System.out.println("ReadyMessage received.");

                        // Wait for all players to get ready:
                        ++readyBarrier;

                        // Once all are ready, start first turn:
                        if (readyBarrier == server.getConnections().length) {
                            server.sendMessage(currentTurn, new TurnMessage());
                        }

                    }

                    // Message returned by Players after finishing turn:
                    else if (argument instanceof TurnMessage) {
                        System.out.println("TurnMessage received.");

                        // Set next turn and possibly wrap around:
                        currentTurn++;
                        if (currentTurn >= server.getConnections().length) {
                            currentTurn = 0;
                        }

                        // Wake next player:
                        server.sendMessage(currentTurn, new TurnMessage(currentTurn));

                    }

                    // Message returned by Players after any kind of action:
                    else if (argument instanceof UpdateMessage) {
                        System.out.println("UpdateMessage received.");

                        // Broadcast update:
                        ((UpdateMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }

                    // TODO: Dice request
                    else if (false) {
                        System.out.println("UpdateMessage received.");

                        //


                    }

                    // TODO: Dice response
                    else if (false) {
                        System.out.println("UpdateMessage received.");

                        //


                    }

                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}