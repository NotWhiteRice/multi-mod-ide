package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EndlessSkies.modID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        simpleItem(ItemInit.itemHandWrench);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> rObj) {
        return withExistingParent(rObj.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "item/" + rObj.getId().getPath()));
    }
}
