package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.EndlessSkies;
import io.github.notwhiterice.endlessskies.registries.object.ItemContext;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModItemModelProvider extends ItemModelProvider {
    private final String modID = EndlessSkies.modID;

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EndlessSkies.modID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        if(ItemContext.isModKnown(modID)) {
            for(Map.Entry<String, ItemContext> entry : ItemContext.getContextsForMod(modID).entrySet()) {
                String name = entry.getKey();
                ItemContext context = entry.getValue();
                String path = context.asRegistryObject().getId().getPath();
                switch(context.getModelProviderTag()) {
                    case SIMPLE_ITEM:
                        withExistingParent(path, ResourceLocation.withDefaultNamespace("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modID, "item/" + path));
                        break;
                    case HANDHELD_ITEM:
                        withExistingParent(path, ResourceLocation.withDefaultNamespace("item/handheld"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modID, "item/" + path));
                        break;
                    case ITEM_WITH_MODEL:
                        withExistingParent(path, ResourceLocation.fromNamespaceAndPath(modID, "item/" + path));
                        break;
                    case ITEM_WITHOUT_MODEL:
                        withExistingParent(path, ResourceLocation.withDefaultNamespace("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "item/unimplemented_texture"));
                        break;
                }
            }
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> rObj) {
        return withExistingParent(rObj.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "item/" + rObj.getId().getPath()));
    }
}
