package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import io.github.notwhiterice.endlessskies.item.factory.data.ItemModelFactory;
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
            ItemModelFactory factory = context.getModelFactory();
            String name = context.getName();
            if(!factory.doDatagen()) continue;
            logger.debug("ModItemModelProvider", "registerModels", "Processing the model factory for item: '" + context.getID() + "'");
            withExistingParent(name, ResourceLocation.parse(factory.parseParent()))
                    .texture("layer0", ResourceLocation.parse(factory.parseLayer0(context)));
        }
    }
}
