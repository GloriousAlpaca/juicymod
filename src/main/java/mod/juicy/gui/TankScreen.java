package mod.juicy.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import mod.juicy.Juicy;
import mod.juicy.container.TankContainer;
import mod.juicy.network.PacketHandler;
import mod.juicy.network.TankPacket;
import mod.juicy.tile.TankControllerTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TankScreen extends ContainerScreen<TankContainer>{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Juicy.MODID, "textures/gui/tank_gui.png");
	public static int gas;
	public static int gasCap;
	public static int juice;
	public static int juiceCap;
	public static int bacteria;
	public static int bacteriaCap;
	public static int intake;
	public static double temp;
	
	public TankScreen(TankContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
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
		PacketHandler.sendToServer(new TankPacket(container.getPos()));
		matrixStack.push();
		float scale = 0.75f;
		matrixStack.scale(scale, scale, scale);
		//Gasnumber
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(gas), Math.round(73/scale)+1, Math.round(63/scale)+1, 0xffffff);
		//Juicenumber
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(juice), Math.round(109/scale)+1, Math.round(63/scale)+1, 0xffffff);
		//Bacterianumber
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(bacteria), Math.round(145/scale)+1, Math.round(63/scale)+1, 0xffffff);
		matrixStack.pop();
		//Intake Number
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Integer.toString(intake), 8, 21, 0xffffff);
		//Temp Number
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, Double.toString(temp), 8, 47, 0xffffff);
		matrixStack.push();
		float scale2 = 0.75f;
		matrixStack.scale(scale, scale, scale);
		//Intake Title
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, new TranslationTextComponent("title.intake"), Math.round(7/scale2)+1, Math.round(30/scale2)+1, 0xffffff);
		//Temp Title
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, new TranslationTextComponent("title.temp"), Math.round(7/scale2)+1, Math.round(56/scale2)+1, 0xffffff);
		matrixStack.pop();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		int g = Math.round((float)gas/(float)gasCap*50);
		int j = Math.round((float)juice/(float)juiceCap*50);
		int b = Math.round((float)bacteria/((float)bacteriaCap*1.25f)*50);
		// GasBar
		this.blit(matrixStack, x + 79, y + 58 - g, 176, 50 - g, 12, g);
		// JuiceBar
		this.blit(matrixStack, x + 115, y + 58 - j, 188, 50 - j, 12, j);
		// BacteriaBar
		this.blit(matrixStack, x + 151, y + 58 - b, 200, 50 - b, 12, b);
	}
		
	
}
