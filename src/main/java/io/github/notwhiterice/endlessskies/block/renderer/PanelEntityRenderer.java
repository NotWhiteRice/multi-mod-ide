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
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class PanelEntityRenderer implements BlockEntityRenderer<PanelBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public PanelEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        context = ctx;
    }

    @Override
    public void render(PanelBlockEntity panelBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        Direction operable = panelBlockEntity.face;

        switch(operable) {
            case UP:
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.translate(0.0, -1.0, -1.0);
                break;
            case NORTH:
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                break;
            case WEST:
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                poseStack.mulPose(Axis.YP.rotationDegrees(270));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                break;
            case SOUTH:
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST:
                poseStack.mulPose(Axis.XP.rotationDegrees(270));
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                break;
        }

        //Draw down panel
        float x0 = 0.0F, y0 = 0.0F, z0 = 0.0F, x1 = 1.0F, y1 = 0.125F, z1 = 1.0F;

        ResourceLocation resLoc = ResourceLocation.fromNamespaceAndPath(Reference.modID, "item/error_texture");
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(resLoc);

        VertexConsumer builder = multiBufferSource.getBuffer(RenderType.solid());

        float u0 = sprite.getU0(), v0 = sprite.getV0(), u1 = sprite.getU1(), v1 = sprite.getV1();

        //UP face
        drawVertex(builder, poseStack, x0, y1, z0, x0*(u1-u0)+u0, z0*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y1, z1, x0*(u1-u0)+u0, z1*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y1, z1, x1*(u1-u0)+u0, z1*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y1, z0, x1*(u1-u0)+u0, z0*(v1-v0)+v0, i, 1.0F, 1.0F, 1.0F, 1.0F);
        //DOWN face
        drawVertex(builder, poseStack, x0, y0, z1, x0*(u1-u0)+u0, v1-z1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y0, z0, x0*(u1-u0)+u0, v1-z0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z0, x1*(u1-u0)+u0, v1-z0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z1, x1*(u1-u0)+u0, v1-z1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        //NORTH face
        drawVertex(builder, poseStack, x0, y1, z1, x0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y0, z1, x0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z1, x1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y1, z1, x1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        //WEST face
        drawVertex(builder, poseStack, x1, y1, z1, z0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z1, z0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z0, z1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y1, z0, z1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        //SOUTH face
        drawVertex(builder, poseStack, x1, y1, z0, x0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x1, y0, z0, x0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y0, z0, x1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y1, z0, x1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        //EAST face
        drawVertex(builder, poseStack, x0, y1, z0, z0*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y0, z0, z0*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y0, z1, z1*(u1-u0)+u0, v1-y0*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);
        drawVertex(builder, poseStack, x0, y1, z1, z1*(u1-u0)+u0, v1-y1*(v1-v0), i, 1.0F, 1.0F, 1.0F, 1.0F);


    }

    private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, float red, float blue, float green, float alpha) {
        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(1.0F, 0.0F, 0.0F);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float u0, float v0, float x1, float y1, float z1, float u1, float v1, int packedLight, float red, float blue, float green, float alpha) {
        //+x, +y, +z, 0, 0
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, red, blue, green, alpha);
        //+x, -y, +z, 0, 1
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, red, blue, green, alpha);
        //+x, -y, -z, 1, 1
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, red, blue, green, alpha);
        //+x, +y, -z, 1, 0
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, red, blue, green, alpha);
    }
}
