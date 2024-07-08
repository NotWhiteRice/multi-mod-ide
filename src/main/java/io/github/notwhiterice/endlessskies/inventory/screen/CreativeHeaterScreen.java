package io.github.notwhiterice.endlessskies.inventory.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.CreativeHeaterBlockEntity;
import io.github.notwhiterice.endlessskies.inventory.CreativeHeaterMenu;
import io.github.notwhiterice.endlessskies.inventory.MineralInfuserMenu;
import io.github.notwhiterice.endlessskies.inventory.factory.BasicScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CreativeHeaterScreen extends BasicScreen<CreativeHeaterMenu, CreativeHeaterBlockEntity> {
    public CreativeHeaterScreen(CreativeHeaterMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    protected ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(Reference.modID, "textures/gui/creative_heater.png");
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        super.renderBg(guiGraphics, v, i, i1);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(getTexture(), x, y, 0, 0, imageWidth, imageHeight);
    }
}
