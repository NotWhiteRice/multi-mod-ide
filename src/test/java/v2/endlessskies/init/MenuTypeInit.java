package io.github.deprecated.v2.endlessskies.init;

import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.deprecated.v2.endlessskies.screen.MineralInfuserMenu;
import io.github.deprecated.v2.endlessskies.screen.RockCrusherMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, EndlessSkies.modID);

    public static final RegistryObject<MenuType<MineralInfuserMenu>> menuMineralInfuser =
            MENU_TYPES.register("mineral_infuser_menu", () -> IForgeMenuType.create(MineralInfuserMenu::new));
    public static final RegistryObject<MenuType<RockCrusherMenu>> menuRockCrusher =
            MENU_TYPES.register("rock_crusher_menu", () -> IForgeMenuType.create(RockCrusherMenu::new));

    public static void register(IEventBus bus) { MENU_TYPES.register(bus); }
}
