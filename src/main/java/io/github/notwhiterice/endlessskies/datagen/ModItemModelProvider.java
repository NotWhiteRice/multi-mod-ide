package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.registry.object.ModContext;
import io.github.notwhiterice.endlessskies.registry.object.ModLogger;
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
        ModLogger logger = ModContext.getContext(modID).logger;
        for(ItemContext context : ItemContext.getModItems(modID)) {
            String name = context.getName();
            switch(context.getModelDataTag()) {
                case SIMPLE_ITEM:
                    logger.debug("ModItemModelProvider", "registerModels", "Generating a standard item/generated model for id: '" + context.getID() + "'");
                    withExistingParent(name, ResourceLocation.withDefaultNamespace("item/generated"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(modID, "item/" + name));
                    break;
                case HANDHELD_ITEM:
                    logger.debug("ModItemModelProvider", "registerModels", "Generating a standard item/handheld model for id: '" + context.getID() + "'");
                    withExistingParent(name, ResourceLocation.withDefaultNamespace("item/handheld"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(modID, "item/" + name));
                    break;
                case ITEM_WITHOUT_MODEL:
                    logger.debug("ModItemModelProvider", "registerModels", "Generating a item model using the default texture for id: '" + context.getID() + "'");
                    withExistingParent(name, ResourceLocation.withDefaultNamespace("item/generated"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Reference.modID, "item/unimplemented_texture"));
                    break;
            }
        }
    }
}
