package at.aau.server.kryonet;

import java.io.IOException;

import at.aau.server.dto.BaseMessage;

public interface NetworkClient {

    /**
     * Connects to a host.
     *
     * @param host
     * @throws IOException
     */
    void connect(String host) throws IOException;

    /**
     * Registers a callback which gets called if a message is received.
     *
     * @param callback
     */
    void registerCallback(Callback<BaseMessage> callback);

    /**
     * Sends a message to the server.
     *
     * @param message
     */
    void sendMessage(BaseMessage message);

}
