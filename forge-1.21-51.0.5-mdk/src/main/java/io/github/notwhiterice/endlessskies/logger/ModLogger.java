package io.github.notwhiterice.endlessskies.logger;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class ModLogger {
    public final String modID;
    public static final Logger logger = LogUtils.getLogger();

    public ModLogger(String modID) {
        this.modID = modID;
    }


    /**
     * @param caller The class calling the logger
     * @param src The function the logger is being called in
     * @param msg The message being logged
     */
    public void fatal(String caller, String src, String msg) {

    }

    /**
     * @param caller The class calling the logger
     * @param src The function the logger is being called in
     * @param msg The message being logged
     */
    public void error(String caller, String src, String msg) {

    }

    /**
     * @param caller The class calling the logger
     * @param src The function the logger is being called in
     * @param msg The message being logged
     */
    public void debug(String caller, String src, String msg) {

    }

    /**
     * @param caller The class calling the logger
     * @param src The function the logger is being called in
     * @param msg The message being logged
     */
    public void info(String caller, String src, String msg) {

    }
}
