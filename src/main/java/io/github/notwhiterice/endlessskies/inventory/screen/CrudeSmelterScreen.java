package io.github.notwhiterice.endlessskies.inventory.screen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.inventory.CrudeSmelterMenu;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrudeSmelterScreen extends BasicScreen<CrudeSmelterMenu> {
    public CrudeSmelterScreen(CrudeSmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    protected ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(Reference.modID, "textures/gui/crude_smelter.png");
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        super.renderBg(guiGraphics, v, i, i1);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(getTexture(), x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderHeatIndicators(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int width = menu.getScaledProgress();

            guiGraphics.blit(getTexture(), x + 80, y + 35, 176, 0, width, 16);
        }
    }
    private void renderHeatIndicators(GuiGraphics guiGraphics, int x, int y) {
        int yOff = menu.getScaledExcess();
        int xOff = menu.isSuperheated() ? 16 : 0;
        int height = menu.getScaledHeat();
        boolean isLit = menu.isLit();

        if(isLit) guiGraphics.blit(getTexture(), x + 57, y + 13-yOff + 47,  177 + xOff, 13-yOff+16, 14, yOff+1);
        guiGraphics.blit(getTexture(), x + 8, y + 58-height + 17, xOff+176, 30 + 58-height, 16, height);
    }
}
