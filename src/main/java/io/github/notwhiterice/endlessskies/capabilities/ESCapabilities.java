package io.github.notwhiterice.endlessskies.capabilities;

import io.github.notwhiterice.endlessskies.capabilities.heat.IHeatHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ESCapabilities {
    public static final Capability<IHeatHandler> HEAT_HANDLER = CapabilityManager.get(new CapabilityToken<IHeatHandler>() {});
}
