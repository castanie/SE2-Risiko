package at.aau.server.kryonet;

public interface KryoNetComponent {


    /**
     * Register a class for serialization.
     *
     * @param c
     */
    void registerClass(Class c);

}
