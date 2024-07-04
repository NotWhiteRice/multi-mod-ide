package io.github.deprecated.v3.endlessskies.datagen;

import io.github.deprecated.v3.endlessskies.registry.object.ItemContext;
import io.github.deprecated.v2.endlessskies.EndlessSkies;
import io.github.deprecated.v3.endlessskies.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    private final String modID;

    public ModItemModelProvider(PackOutput output, String modID, ExistingFileHelper fileHelper) {
        super(output, modID, fileHelper);
        this.modID = modID;
    }

    @Override
    public void registerModels() {
        if(ModRegistry.isModKnown(modID)) {
            for(ItemContext context : ModRegistry.getModItems(modID)) {
                String name = context.getName();
                switch(context.getModelProviderTag()) {
                    case SIMPLE_ITEM:
                        withExistingParent(name, ResourceLocation.withDefaultNamespace("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modID, "item/" + name));
                        break;
                    case HANDHELD_ITEM:
                        withExistingParent(name, ResourceLocation.withDefaultNamespace("item/handheld"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modID, "item/" + name));
                        break;
                    case ITEM_WITH_MODEL:
                        withExistingParent(name, ResourceLocation.fromNamespaceAndPath(modID, "item/" + name));
                        break;
                    case ITEM_WITHOUT_MODEL:
                        withExistingParent(name, ResourceLocation.withDefaultNamespace("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "item/unimplemented_texture"));
                        break;
                }
            }
        }
    }
}
