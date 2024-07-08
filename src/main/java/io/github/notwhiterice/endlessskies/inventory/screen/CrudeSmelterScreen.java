package io.github.notwhiterice.endlessskies.inventory.screen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.CrudeSmelterBlockEntity;
import io.github.notwhiterice.endlessskies.block.entity.MineralInfuserBlockEntity;
import io.github.notwhiterice.endlessskies.inventory.CrudeSmelterMenu;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrudeSmelterScreen extends BasicScreen<CrudeSmelterMenu, CrudeSmelterBlockEntity> {
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
        if(menu.isSuperheated()) {
            guiGraphics.blit(getTexture(), x + 57, y + 47, 190, 16, 14, 14);
            guiGraphics.blit(getTexture(), x + 8, y + 17, 192, 30, 16, 58);
        } else {
            int yOff0 = menu.getScaledExcess();
            int height = menu.getScaledHeat();

            if(yOff0 != 0) guiGraphics.blit(getTexture(), x + 57, y + 13-yOff0 + 47, 176, 16 + 13-yOff0, 14, yOff0+1);
            guiGraphics.blit(getTexture(), x + 8, y + 58-height + 17, 176, 30 + 58-height, 16, height);
        }
    }
}
