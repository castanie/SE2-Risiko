package at.aau.server;

import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.CheatedMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.dto.EyeNumbersMessage;
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
            server.registerClass(DiceMessage.class);
            server.registerClass(EyeNumbersMessage.class);
            server.registerClass(CheatedMessage.class);

            // server.registerClass(CheatedMessage.class);
            server.registerClass(CardMessage.class);
            server.registerClass(ExchangeMessage.class);

            server.start();
            server.registerCallback(new Callback<BaseMessage>() {

                int readyBarrier = 0;
                int currentTurn = 0;
                //variables from DiceMessage
                Integer defenderIndex;
                Integer attackerIndex;
                String attackerCountryName;
                String defenderCountryName;
                Integer numDefenders;
                Integer numAttackers;
                //variables from EyeNumbersMessage
                int[] diceArrayAttacker;
                int[] diceArrayDefender;

                //variables for evaluating the winner
                int eyeNumberSumDefender;
                int eyeNumberSumAttacker;

                boolean isDoneRolling = false;
                boolean hasCheatedDefender = false;
                boolean hasCheatedAttacker = false;


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
                            server.sendMessage(currentTurn, new TurnMessage(currentTurn, true));
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

                        // Order Players to set Avatar:
                        server.broadcastMessage(new TurnMessage(currentTurn, false));

                        // Wake next player:
                        server.sendMessage(currentTurn, new TurnMessage(currentTurn, true));

                    }

                    // Message returned by Players after any kind of action:
                    else if (argument instanceof UpdateMessage) {
                        System.out.println("UpdateMessage received.");

                        // TODO: DO NOT SEND MESSAGE BACK TO SENDER; THIS CAUSES VARIOUS PROBLEMS!
                        // - INCLUDING RANDOM COLOR CHANGES IN FORTIFY STATE
                        ((UpdateMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }



                    // TODO: CardMessage
                    //Broadcasts a message when a card was drawn and which card was drawn from the Carddeck
                    else if (argument instanceof CardMessage) {
                        System.out.println("CardMessage received.");

                        // Simply broadcast which card was drawn:
                        ((CardMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }

                    // TODO: ExchangeMessage
                    // Broadcasts a message when cards were exchanged and which cards were exchanged

                    else if (argument instanceof ExchangeMessage) {
                        System.out.println("ExchangeMessage received.");

                        // Simply broadcast which cards were exchanged:
                        ((ExchangeMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }  // Message sent from Map requesting to start attack:
                    else if (argument instanceof DiceMessage) {
                        System.out.println("DiceMessage received.");

                        //save variables on server
                        defenderIndex = ((DiceMessage) argument).playerIndex;
                        //TODO: get senders index
                        attackerIndex = ((DiceMessage) argument).attackingIndex;
                        attackerCountryName = ((DiceMessage)argument).attackingCountryName;
                        defenderCountryName = ((DiceMessage)argument).defendingCountryName;
                        numAttackers = ((DiceMessage)argument).numAttackers;
                        numDefenders = ((DiceMessage)argument).numDefenders;



                        // Order Defender to roll dice:
                        server.sendMessage(((DiceMessage) argument).playerIndex, argument);

                    }

                    //
                    else if (argument instanceof EyeNumbersMessage){
                        System.out.println("EyeNumbersMessage received.");
                        /**
                         * If sender is DiceActivityDefender receiver is DiceActivityAttacker
                         * If sender is DiceActivityAttacker receiver is DiceActivityDefender
                         * Set the diceArrayAttacker and diceArrayDefender arrays, for the evaluation of the winner
                         * if both Activities have send a message set isDoneRolling to true
                         * TODO: don't forget to reset isDoneRolling to false after winner evaluation
                         */
                        //TODO: check who sender is and send to opposite
                        if(((EyeNumbersMessage)argument).isDefender) {
                            server.sendMessage(attackerIndex, argument);
                            System.out.println("EyeNumbersMessage sent to Defender");
                            diceArrayDefender = ((EyeNumbersMessage)argument).getMessage();
                            calcEyenumberSum(diceArrayDefender, "defender");
                        }else {
                            server.sendMessage(defenderIndex, argument);
                            System.out.println("EyeNumbersMessage sent to Attacker");
                            diceArrayAttacker = ((EyeNumbersMessage)argument).getMessage();
                            isDoneRolling = true;
                            calcEyenumberSum(diceArrayAttacker, "attacker");
                        }

                        //TODO: wait for CheatedMessage for about 5 seconds or so

                        if(isDoneRolling) {
                            evaluateWinner();
                            isDoneRolling = false;
                        }

                        
                    }
                    else if (argument instanceof CheatedMessage) {
                        System.out.println("CheatedMessage received.");
                        /**
                         * sender is one of the DiceActivities
                         */
                        if(((CheatedMessage)argument).getSenderIsDefender()) {
                            hasCheatedAttacker = true;
                        }else {
                            hasCheatedDefender = true;
                        }
                    }

                }


                private void calcEyenumberSum(int[] arr, String playersRoll) {
                    int sum = 0;
                    //i < arr.length-1 because the last element is just the indicator for cheating
                    for(int i = 0; i < arr.length-1; i++) {
                        sum += arr[i];
                    }

                    if (playersRoll.equals("defender")) {
                        eyeNumberSumDefender = sum;
                    }else if (playersRoll.equals("attacker")) {
                        eyeNumberSumAttacker = sum;
                    }

                }

                private void evaluateWinner() {
                    if (hasCheatedAttacker) {
                        //TODO: Defender won due to cheating of attacker, update GUI and show snackbar or smth
                    }else if (hasCheatedDefender) {
                        //TODO: Attacker won due to cheating of defender, update GUI and show snackbar or smth
                    }
                    //if draw defender has the advantage
                    else if (eyeNumberSumDefender >= eyeNumberSumAttacker) {
                        //TODO: Defender won, update GUI and show snackbar or smth
                    }else {
                        //TODO: Attacker won, update GUI and show snackbar or smth
                    }

                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}