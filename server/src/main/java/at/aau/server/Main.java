package at.aau.server;


import java.util.concurrent.TimeUnit;
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
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.RequestPlayerMessage;
import at.aau.server.dto.ResponsePlayerMessage;
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

            server.start();
            server.registerCallback(new Callback<BaseMessage>() {

                int readyBarrier = 0;
                int backInMapBarrier = 0;
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
                boolean badGuessDefender = false;
                boolean badGuessAttacker = false;

                ArrayList<String>playerNames = new ArrayList<String>();

                @Override
                public void callback(BaseMessage argument) {

                    // Simple text message:
                    if (argument instanceof LogMessage) {
                        System.out.println("LogMessage: " + ((LogMessage) argument).text);

                    }

                    // Message sent from Login screen containing Player name:
                    else if (argument instanceof NameMessage) {
                        System.out.println("NameMessage: " + ((NameMessage) argument).name);

                        // Register Player name:
                        playerNames.add(((NameMessage) argument).name);
                        server.broadcastMessage(new ResponsePlayerMessage(playerNames));

                    }

                    // Message sent from Lobby screen requesting game start:
                    else if (argument instanceof StartMessage) {
                        System.out.println("StartMessage received.");

                        // Order Clients to start:
                        // ((StartMessage) argument).names = new String[] {"One", "Two", "Three"};
                        ((StartMessage) argument).names = playerNames.toArray(new String[0]);
                        ((StartMessage) argument).colors = new Integer[] {0xFFFFCC00, 0xFFFF44CC, 0x00CCFF};
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

                        // Wake next Player:
                        server.sendMessage(currentTurn, new TurnMessage(currentTurn, true));

                    }

                    // Message returned by Players after any kind of action:
                    else if (argument instanceof UpdateMessage) {
                        System.out.println("UpdateMessage received.");

                        //((UpdateMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }

                    // Broadcasts a message when a card was drawn from the CardDeck:
                    else if (argument instanceof CardMessage) {
                        System.out.println("CardMessage received.");

                        // Simply broadcast which card was drawn:
                        // ((CardMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }

                    // Broadcasts a message when cards were exchanged in CardActivity:
                    else if (argument instanceof ExchangeMessage) {
                        System.out.println("ExchangeMessage received.");

                        // Simply broadcast which cards were exchanged:
                        ((ExchangeMessage) argument).playerIndex = currentTurn;
                        server.broadcastMessage(argument);

                    }

                    // Message sent from Map requesting to start attack:
                    else if (argument instanceof DiceMessage) {
                        System.out.println("DiceMessage received.");

                        // Save variables on server
                        attackerIndex = ((DiceMessage) argument).playerIndex;
                        defenderIndex = ((DiceMessage) argument).defendingIndex;
                        attackerCountryName = ((DiceMessage)argument).attackingCountryName;
                        defenderCountryName = ((DiceMessage)argument).defendingCountryName;
                        numAttackers = ((DiceMessage)argument).numAttackers;
                        numDefenders = ((DiceMessage)argument).numDefenders;


                        // Order Defender to roll dice:
                        server.sendMessage(((DiceMessage) argument).defendingIndex, argument);

                    }

                    // Message sent from Attacker or Defender after rolling dice:
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
                            System.out.println("EyeNumbersMessage sent to Attacker");
                            for (int i : ((EyeNumbersMessage) argument).getMessage()) {
                                System.out.println(i);
                            }
                            diceArrayDefender = ((EyeNumbersMessage)argument).getMessage();
                            calcEyenumberSum(diceArrayDefender, "defender");
                        }else {
                            server.sendMessage(defenderIndex, argument);
                            System.out.println("EyeNumbersMessage sent to Defender");
                            for (int i : ((EyeNumbersMessage) argument).getMessage()) {
                                System.out.println(i);
                            }
                            diceArrayAttacker = ((EyeNumbersMessage)argument).getMessage();
                            isDoneRolling = true;
                            calcEyenumberSum(diceArrayAttacker, "attacker");
                        }


                        if(isDoneRolling) {

                            // Wait for 5 seconds to let players decide if other one has cheated

                            long currentTime = System.currentTimeMillis();
                            long endWait = currentTime + 4000; //wait 8 sec
                            int whileUseLessVar = 0;
                            while(System.currentTimeMillis() <= endWait) {
                                whileUseLessVar++;
                            }


                            //TODO: send messages to DiceActivities that they should finish themselves
                            server.sendMessage(attackerIndex, new CloseDiceActivitiesMessage());
                            server.sendMessage(defenderIndex, new CloseDiceActivitiesMessage());


                        }


                    }

                    // Message sent from Attacker or Defender after calling out a cheater:
                    else if (argument instanceof CheatedMessage) {
                        System.out.println("CheatedMessage received.");
                        /**
                         * sender is one of the DiceActivities
                         */
                        if (((CheatedMessage)argument).getSenderIsDefender() && ((CheatedMessage)argument).getMessage()) {
                            hasCheatedAttacker = true;
                            badGuessDefender = false;
                        }else if (((CheatedMessage)argument).getSenderIsDefender() && !((CheatedMessage)argument).getMessage()) {
                            hasCheatedAttacker = false;
                            badGuessDefender = true;
                        }else if (!((CheatedMessage)argument).getSenderIsDefender() && ((CheatedMessage)argument).getMessage()) {
                            hasCheatedDefender = true;
                            badGuessAttacker = false;
                        }else if (!((CheatedMessage)argument).getSenderIsDefender() && !((CheatedMessage)argument).getMessage()) {
                            hasCheatedDefender = false;
                            badGuessAttacker = true;
                        }
                    }

                    //
                    else if (argument instanceof BackInMapMessage) {
                        System.out.println("BackInMapMessage received.");

                        // Wait for all players to get ready:
                        if (isDoneRolling) {
                            ++backInMapBarrier;

                            // Once all are ready, start first turn:
                            if (backInMapBarrier == 2) {
                                // TODO: make sure players are back in MapActivity
                                evaluateWinner();

                                // Reset all booleans
                                isDoneRolling = false;
                                hasCheatedDefender = false;
                                hasCheatedAttacker = false;
                                badGuessDefender = false;
                                badGuessAttacker = false;

                                backInMapBarrier = 0;
                            }
                        }
                    }

                }


                //
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

                //
                private void evaluateWinner() {
                    if (hasCheatedDefender && hasCheatedAttacker) {
                        // Defender won due to both cheated
                        System.out.println("Defender because both cheated.");
                        server.broadcastMessage(new UpdateMessage(attackerCountryName, 1, attackerIndex, "Defender"));

                    }else if(badGuessDefender &&badGuessAttacker) {
                        // Defender won due to both bad guess
                        System.out.println("Defender won because both had a bad guess.");
                        server.broadcastMessage(new UpdateMessage(attackerCountryName, 1, attackerIndex, "Defender"));

                    }else if (hasCheatedAttacker) {
                        // Defender won due to cheating of attacker
                        System.out.println("Defender won because Attacker cheated.");
                        server.broadcastMessage(new UpdateMessage(attackerCountryName, 1, attackerIndex,"Defender"));

                    } else if (hasCheatedDefender) {

                        // Attacker won due to cheating of defender
                        System.out.println("Attacker won because Defender cheated.");
                        server.broadcastMessage(new UpdateMessage(defenderCountryName, numAttackers, attackerIndex,"Attacker"));
                        server.sendMessage(currentTurn, new ConqueredMessage());

                    } else if (badGuessAttacker) {

                        // Defender won due to wrong guess of attacker
                        System.out.println("Defender won due to bad guess.");
                        server.broadcastMessage(new UpdateMessage(attackerCountryName, 1, attackerIndex,"Defender"));

                    } else if (badGuessDefender) {

                        // Attacker won due to wrong guess of defender
                        System.out.println("Attacker won due to bad guess.");
                        server.broadcastMessage(new UpdateMessage(defenderCountryName, numAttackers, attackerIndex,"Attacker."));
                        server.sendMessage(currentTurn, new ConqueredMessage());

                    }

                    // If draw, defender has the advantage
                    else if (eyeNumberSumDefender >= eyeNumberSumAttacker) {

                        // Defender won
                        System.out.println("Defender won.");
                        server.broadcastMessage(new UpdateMessage(attackerCountryName, 1, attackerIndex,"Defender"));

                    } else {

                        // Attacker won
                        System.out.println("Attacker won.");
                        server.broadcastMessage(new UpdateMessage(defenderCountryName, numAttackers, attackerIndex,"Attacker"));
                        server.sendMessage(currentTurn, new ConqueredMessage());

                    }


                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}