package io.github.notwhiterice.endlessskies.util;

import java.util.Collection;

public abstract class StringHelper {
    public String constructUniqueID(String original, Collection<String> keySet) {
        if(!keySet.contains(original)) return original;
        int i = 0;
        while(keySet.contains(original + "_" + i)) i++;
        return original + "_" + i;
    }
}
