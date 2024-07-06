package io.github.notwhiterice.endlessskies.datagen;

import io.github.notwhiterice.endlessskies.block.factory.BlockContext;
import io.github.notwhiterice.endlessskies.item.factory.ItemContext;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public class ModLanguageProvider extends LanguageProvider {
    public static final Map<String, Map<String, Map<String, String>>> translations = new HashMap<>();

    private final String modID;
    private final String locale;

    public ModLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
        this.modID = modid;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        for(Map.Entry<String, String> translation : translations.get(locale).get(modID).entrySet()) {
            if(ItemContext.doesContextExist(modID, translation.getKey())) add(ItemContext.getContext(modID, translation.getKey()).get(), translation.getValue());
            if(BlockContext.doesContextExist(modID, translation.getKey())) add(BlockContext.getContext(modID, translation.getKey()).get(), translation.getValue());
        }
    }
}
