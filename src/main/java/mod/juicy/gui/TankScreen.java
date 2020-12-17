package mod.juicy.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.juicy.Juicy;
import mod.juicy.container.TankContainer;
import mod.juicy.tile.TankControllerTile;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TankScreen extends ContainerScreen<TankContainer>{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Juicy.MODID, "textures/gui/tank_gui.png");
	private TankControllerTile tile = null;

	public TankScreen(TankContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
		
		this.tile = container.tile;
		
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		this.font.drawString(matrixStack, this.title.getString(), 8f, 6f, 4210752);
		this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8f, 90f, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
	}
		
}
