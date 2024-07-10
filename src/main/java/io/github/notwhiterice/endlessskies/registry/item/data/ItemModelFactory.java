package io.github.notwhiterice.endlessskies.registry.item.data;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.registry.item.ItemContext;

public enum ItemModelFactory {
    SIMPLE_ITEM("minecraft:item/generated", null, true),
    HANDHELD_ITEM("minecraft:item/handheld", null, true),
    ITEM_WITHOUT_MODEL("minecraft:item/generated", Reference.modID + ":item/unimplemented_texture", true),
    SKIP_DATAGEN(null, null,true);

    private final String parent;
    private final String layer0;
    private final boolean isIndependent;

    ItemModelFactory(String parent, String layer0, boolean isIndependent) {
        this.parent = parent;
        this.layer0 = layer0;
        this.isIndependent = isIndependent;
    }

    public boolean doDatagen() { return parent != null; }
    public String parseParent() { return parent; }
    public String parseLayer0(ItemContext context) {
        if(layer0 == null) return context.getModID() + ":item/" + context.name;
        if(isIndependent) return layer0;
        return context.getModID() + ":" + layer0;
    }
}
