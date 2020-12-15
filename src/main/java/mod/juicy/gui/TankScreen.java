package mod.juicy.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.juicy.Juicy;
import mod.juicy.container.TankContainer;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TankScreen extends ContainerScreen<TankContainer>{

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Juicy.MODID, "textures/gui/tank_gui.png");
	

	public TankScreen(TankContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
		
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		// TODO Auto-generated method stub
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		// TODO Auto-generated method stub
		
	}
		
}
