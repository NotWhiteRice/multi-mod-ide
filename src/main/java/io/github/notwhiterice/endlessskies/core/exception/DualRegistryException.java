package io.github.notwhiterice.endlessskies.core.exception;

public class DualRegistryException extends Exception {
    public DualRegistryException() { super(); }

    public DualRegistryException(String msg) { super(msg); }

    public static DualRegistryException generate(String duplID, String registry) {
        return new DualRegistryException("The ID: " + duplID + " has already been registered in registry: " + registry);
    }
}
