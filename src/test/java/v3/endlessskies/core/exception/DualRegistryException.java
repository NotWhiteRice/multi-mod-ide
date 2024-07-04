package io.github.deprecated.v3.endlessskies.core.exception;

public class DualRegistryException extends Exception {
    public DualRegistryException() { super(); }

    public DualRegistryException(String msg) { super(msg); }

    public DualRegistryException(String registry, String duplID) {
        super("The ID: '" + duplID + "' has already been registered in registry: '" + registry + "'");
    }
}
