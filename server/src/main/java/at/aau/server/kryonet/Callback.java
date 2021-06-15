package at.aau.server.kryonet;

/**
 * Used for callbacks.
 */
public interface Callback<T> {


    void callback(T argument) throws InterruptedException;

}
