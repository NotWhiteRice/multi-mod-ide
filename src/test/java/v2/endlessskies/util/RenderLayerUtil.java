package io.github.deprecated.v2.endlessskies.util;

import net.minecraft.client.renderer.RenderType;

import java.util.IdentityHashMap;
import java.util.Map;

public enum RenderLayerUtil {
    SOLID(RenderType.solid()),
    CUTOUT(RenderType.cutout()),
    CUTOUT_MIPPED(RenderType.cutoutMipped()),
    TRANSLUCENT(RenderType.translucent()),
    TRIPWIRE(RenderType.tripwire())
    ;

    private static final Map<RenderType, RenderLayerUtil> reverseLookup = new IdentityHashMap<>();
    static {
        for(RenderLayerUtil obj : RenderLayerUtil.values()) reverseLookup.put(obj.get(), obj);
    }

    private final RenderType renderLayer;

    RenderLayerUtil(RenderType layer) { renderLayer = layer; }

    public RenderType get() { return renderLayer; }
    public static RenderType getLayerFromValue(RenderLayerUtil obj) { return obj.get(); }

    public static RenderLayerUtil fromLayer(RenderType layer) { return reverseLookup.get(layer); }
}
