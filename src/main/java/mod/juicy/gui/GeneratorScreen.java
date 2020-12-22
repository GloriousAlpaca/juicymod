package mod.juicy.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.juicy.Config;
import mod.juicy.Juicy;
import mod.juicy.container.GeneratorContainer;
import mod.juicy.network.GeneratorPacket;
import mod.juicy.network.PacketHandler;
import mod.juicy.network.TankPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GeneratorScreen extends ContainerScreen<GeneratorContainer>{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Juicy.MODID, "textures/gui/generator_gui.png");
	public static int energy;
	public static int gas;
	public static int lastGenerated;
	
	public GeneratorScreen(GeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
		if(container.getPos() != null)
			PacketHandler.sendToServer(new GeneratorPacket(container.getPos()));
		matrixStack.push();
		float scale = 0.75f;
		matrixStack.scale(scale, scale, scale);
		//Gasnumber
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(gas), Math.round(141/scale)+1, Math.round(38/scale)+1, 0xffffff);
		//Energynumber
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(energy), Math.round(141/scale)+1, Math.round(72/scale)+1, 0xffffff);
		//Last Amount of RF generated
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(lastGenerated), Math.round(109/scale)+7, Math.round(72/scale)+1, 0xffffff);
		matrixStack.pop();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		int g = Math.round((float)gas/(float)Config.GENERATOR_GASCAP.get()*170f);
		int e = Math.round((float)energy/(float)Config.GENERATOR_ENERGYCAP.get()*170f);
		this.blit(matrixStack, x+12, y+23, 0, 166, g, 12);
		this.blit(matrixStack, x+12, y+57, 0, 178, e, 12);
	}

}
