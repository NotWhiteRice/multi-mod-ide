package io.github.notwhiterice.endlessskies.inventory.factory;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BasicScreen<T extends BasicMenu<U>, U extends BlockEntity & MenuProvider> extends AbstractContainerScreen<T> {
    public BasicScreen(T menu, Inventory inv, Component title) { super(menu, inv, title); }

    protected abstract ResourceLocation getTexture();

    protected void init() {
        super.init();
        this.inventoryLabelY = 1000000;
    }

    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getTexture());
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
