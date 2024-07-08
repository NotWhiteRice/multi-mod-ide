package io.github.notwhiterice.endlessskies.inventory.factory;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import io.github.notwhiterice.endlessskies.registry.object.base.InnerContextBase;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MenuContext<T extends AbstractContainerMenu> extends InnerContextBase<MenuContext<? extends AbstractContainerMenu>> {
    //Private static variables
    private static final Map<String, Map<String, MenuContext<? extends AbstractContainerMenu>>> instances = new HashMap<>();

    //Public static variables
    public static final MenuContext<?> dummy = new MenuContext<>(ModContext.dummy, null, null, null);

    //Public variables
    public RegistryObject<MenuType<T>> rObject;
    public IContainerFactory<T> factory;
    public MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen;

    //Constructor
    public MenuContext(ModContext context, String name, IContainerFactory<T> factory, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screen) {
        super(context.getName(), name, "MenuContext");
        if(context.equals(ModContext.dummy) || name == null || factory == null || screen == null) return;
        if(registerInstance(instances)) context.logger.info("MenuContext", "<init>(context, name, factory, screen)", "Registering menu context for menu: '" + context.getModID() + ":" + getName() + "'");
        else context.logger.info("MenuContext", "<init>(context, name, factory, screen)", "Registered duplicate menu context for menu: '" + context.getModID() + ":" + name + "' as '" + context.getModID() + ":" + getName() + "'");
        this.factory = factory;
        this.screen = screen;
    }

    //Getter functions
    public MenuType<T> getMenuType() { return rObject.get(); }
    public RegistryObject<MenuType<T>> getRegistryObject() {
        if(!EndlessSkies.canRegisterObject()) return null;
        if(rObject == null) rObject = ModContext.getContext(getModID()).MENUS.register(getName(), () -> IForgeMenuType.create(factory));
        return rObject;
    }

    /* Special getter functions
     * 1-increases in "depth"
     * 2-increases in "type strength"
     * 3-increases in "plurality"
     */
    public static boolean isModKnown(String modID) { return instances.containsKey(modID); }
    public static boolean doesContextExist(String modID, String name) { return isModKnown(modID) ? instances.get(modID).containsKey(name) : false; }
    public static MenuContext<? extends AbstractContainerMenu> getContext(String modID, String name) { return isModKnown(modID) ? instances.get(modID).get(name) : null; }
    public static Collection<String> listModMenus(String modID) { return isModKnown(modID) ? instances.get(modID).values().stream().map(MenuContext::getID).toList() : Collections.emptyList(); }
    public static Collection<MenuContext<? extends AbstractContainerMenu>> getModMenus(String modID) { return isModKnown(modID) ? instances.get(modID).values() : Collections.emptyList(); }
    public static Collection<String> listAllMenus() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).map(MenuContext::getID).toList(); }
    public static Collection<MenuContext<? extends AbstractContainerMenu>> getAllMenus() { return instances.values().stream().flatMap(modColl -> modColl.values().stream()).toList(); }

}
