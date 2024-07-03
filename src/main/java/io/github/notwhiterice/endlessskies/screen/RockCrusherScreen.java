package io.github.notwhiterice.endlessskies.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.notwhiterice.endlessskies.EndlessSkies;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RockCrusherScreen extends AbstractContainerScreen<RockCrusherMenu> {
    private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(EndlessSkies.modID, "textures/gui/rock_crusher.png");

    public RockCrusherScreen(RockCrusherMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 1000000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressBar(guiGraphics, x, y);
    }

    private void renderProgressBar(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int width = menu.getScaledProgress();
            int animationTick = menu.getProgress() % 3;

            guiGraphics.blit(texture, x + 69, y + 35, 176, 0, width, 16);
            guiGraphics.blit(texture, x + 69, y + 51, 176, 16+animationTick*4, width, 4);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
