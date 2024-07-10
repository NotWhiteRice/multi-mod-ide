package io.github.notwhiterice.endlessskies.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModLogger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String modID;

    public ModLogger(String modID) {
        this.modID = modID;
    }

    public static String formatOutput(String modID, String level, String caller, String src, String msg) { return "["+ LocalDateTime.now().format(formatter)+"] ["+modID+":ES"+"/"+level+"] ["+caller+"/"+src+"]: "+msg; }

    public void fatal(String caller, String src, String msg) { System.out.println(formatOutput(modID, "FATAL", caller, src, msg)); }
    public void error(String caller, String src, String msg) { System.out.println(formatOutput(modID, "ERROR", caller, src, msg)); }
    public void warn(String caller, String src, String msg) { System.out.println(formatOutput(modID, "WARN", caller, src, msg)); }
    public void debug(String caller, String src, String msg) { System.out.println(formatOutput(modID, "DEBUG", caller, src, msg)); }
    public void info(String caller, String src, String msg) { System.out.println(formatOutput(modID, "INFO", caller, src, msg)); }
    public void trace(String caller, String src, String msg) { System.out.println(formatOutput(modID, "TRACE", caller, src, msg)); }
}
