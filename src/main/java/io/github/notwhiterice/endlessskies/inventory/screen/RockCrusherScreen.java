package io.github.notwhiterice.endlessskies.inventory.screen;

import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.inventory.RockCrusherMenu;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RockCrusherScreen extends BasicScreen<RockCrusherMenu> {
    public RockCrusherScreen(RockCrusherMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    protected ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(Reference.modID, "textures/gui/rock_crusher.png");
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        super.renderBg(guiGraphics, v, i, i1);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(getTexture(), x, y, 0, 0, imageWidth, imageHeight);

        renderProgressBar(guiGraphics, x, y);
    }

    private void renderProgressBar(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int width = menu.getScaledProgress();
            int animationTick = menu.getProgress() % 3;

            guiGraphics.blit(getTexture(), x + 69, y + 35, 176, 0, width, 16);
            guiGraphics.blit(getTexture(), x + 69, y + 51, 176, 16+animationTick*4, width, 4);
        }
    }
}
