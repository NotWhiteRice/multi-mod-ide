package io.github.notwhiterice.endlessskies.registry.inventory;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.ModObject;
import io.github.notwhiterice.endlessskies.registry.ModContext;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class MenuContext<T extends AbstractContainerMenu> extends ModObject<MenuType<T>> {
    public static final List<MenuContext<?>> instances = new ArrayList<>();

    public IContainerFactory<T> factory;
    public MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen;

    public MenuContext(ModContext context, String name, IContainerFactory<T> factory, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen) {
        super(context, name);
        if(context == null || name == null || factory == null || screen == null) return;
        if(registerInstance(instances)) context.logger.info("MenuContext", "<init>", "Registering menu '" + getID(":") + "'");
        else context.logger.info("MenuContext", "<init>", "Registered duplicate menu for '" + getModID() + ":" + name + "' as '" + getID(":") + "'");
        this.factory = factory;
        this.screen = screen;
    }

    public MenuType<T> get() { return super.get(); }
    public RegistryObject<MenuType<T>> getRegistry() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(registry == null) registry = parentMod.MENUS.register(name, () -> IForgeMenuType.create(factory));
        return registry;
    }
}
