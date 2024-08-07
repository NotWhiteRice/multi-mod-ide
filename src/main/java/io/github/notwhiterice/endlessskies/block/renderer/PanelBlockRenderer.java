package io.github.notwhiterice.endlessskies.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.notwhiterice.endlessskies.Reference;
import io.github.notwhiterice.endlessskies.block.entity.PanelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PanelBlockRenderer implements BlockEntityRenderer<PanelBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public PanelBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        context = ctx;
    }

    @Override
    public void render(PanelBlockEntity panelBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        Direction operable = panelBlockEntity.face;
        Direction facing = panelBlockEntity.facing;

        poseStack.pushPose();

        //Draw down panel
        float x0 = 0.0F, y0 = 0.0F, z0 = 0.0F, x1 = 1.0F, y1 = 0.125F, z1 = 1.0F;

        ResourceLocation resLoc = ResourceLocation.fromNamespaceAndPath(Reference.modID, "item/arrow_texture");
        if(facing == null) resLoc = ResourceLocation.withDefaultNamespace("block/smooth_stone");
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(resLoc);

        poseStack.translate(0.5F, 0.5F, 0.5F);

        Level level = panelBlockEntity.getLevel();
        BlockPos pos = panelBlockEntity.getBlockPos();
        BlockState state = level.getBlockState(pos);

        switch(operable) {
            case UP:
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                break;
            case NORTH:
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                break;
            case WEST:
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(270));
                break;
            case SOUTH:
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                break;
            case EAST:
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));
                break;
        }

        if(facing != null) {
            switch (facing) {
                case WEST:
                    poseStack.mulPose(Axis.YP.rotationDegrees(operable != Direction.UP ? 90 : 270));
                    break;
                case SOUTH:
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    break;
                case EAST:
                    poseStack.mulPose(Axis.YP.rotationDegrees(operable != Direction.UP ? 270 : 90));
                    break;
            }
        }

        poseStack.translate(-0.5F, -0.5F, -0.5F);

        VertexConsumer builder = multiBufferSource.getBuffer(RenderType.solid());

        float u0 = sprite.getU0(), v0 = sprite.getV0(), u1 = sprite.getU1(), v1 = sprite.getV1();

        //UP face
        drawVertex(builder, poseStack, x0, y1, z0, x0*(u1-u0)+u0, z0*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F);
        drawVertex(builder, poseStack, x0, y1, z1, x0*(u1-u0)+u0, z1*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y1, z1, x1*(u1-u0)+u0, z1*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y1, z0, x1*(u1-u0)+u0, z0*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F);
        //DOWN face
        drawVertex(builder, poseStack, x0, y0, z1, x0*(u1-u0)+u0, v1-z0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, -1.0F, 0.0F);
        drawVertex(builder, poseStack, x0, y0, z0, x0*(u1-u0)+u0, v1-z1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, -1.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y0, z0, x1*(u1-u0)+u0, v1-z1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, -1.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y0, z1, x1*(u1-u0)+u0, v1-z0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, -1.0F, 0.0F);
        //NORTH face
        drawVertex(builder, poseStack, x0, y1, z1, x0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, -1.0F);
        drawVertex(builder, poseStack, x0, y0, z1, x0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, -1.0F);
        drawVertex(builder, poseStack, x1, y0, z1, x1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, -1.0F);
        drawVertex(builder, poseStack, x1, y1, z1, x1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, -1.0F);
        //WEST face
        drawVertex(builder, poseStack, x1, y1, z1, z0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 0.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y0, z1, z0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 0.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y0, z0, z1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 0.0F, 0.0F);
        drawVertex(builder, poseStack, x1, y1, z0, z1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 0.0F, 0.0F);
        //SOUTH face
        drawVertex(builder, poseStack, x1, y1, z0, x0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z0, x0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y0, z0, x1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y1, z0, x1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
        //EAST face
        drawVertex(builder, poseStack, x0, y1, z0, z0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F);
        drawVertex(builder, poseStack, x0, y0, z0, z0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F);
        drawVertex(builder, poseStack, x0, y0, z1, z1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F);
        drawVertex(builder, poseStack, x0, y1, z1, z1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F);

        poseStack.popPose();
    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, float red, float blue, float green, float alpha, float normalX, float normalY, float normalZ) {
        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(normalX, normalY, normalZ);
    }
}
