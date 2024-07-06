package io.github.notwhiterice.endlessskies.core.exception;

public class ClosedContextException extends IllegalStateException {
    public ClosedContextException() { super(); }
    public ClosedContextException(Exception e) { super("Error ClosedContextException found--message: '" + e.getMessage() + "'"); }
    public ClosedContextException(String msg) { super(msg); }
    public ClosedContextException(String src, String contextType) { super("'" + src + "' had attempted to edit a '" + contextType + "' that had already been closed"); }
}
