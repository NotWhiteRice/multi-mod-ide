package io.github.notwhiterice.endlessskies.registry.object;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class ModLogger {
    public static final Logger logger = LogUtils.getLogger();
    public final String modID;

    public ModLogger(String modID) {
        this.modID = modID;
    }

    public void fatal(String caller, String src, String msg, Exception e) {
        logger.info("[FATAL ("+modID+"): in "+caller+" at "+src+": "+msg);
        throw new RuntimeException(e);
    }

    public void error(String caller, String src, String msg) { logger.info("[ERROR ("+modID+"): in "+caller+" at "+src+": "+msg); }

    public void debug(String caller, String src, String msg) { logger.info("[DEBUG ("+modID+"): in "+caller+" at "+src+": "+msg); }

    public void info(String caller, String src, String msg) { logger.info("[INFO ("+modID+"): in "+caller+" at "+src+": "+msg); }

    public void trace(String caller, String src, String msg) { logger.info("[TRACE ("+modID+"): in "+caller+" at "+src+": "+msg); }
}
