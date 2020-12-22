package mod.juicy.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.juicy.Juicy;
import mod.juicy.container.ThermContainer;
import mod.juicy.network.PacketHandler;
import mod.juicy.network.ThermButtonPacket;
import mod.juicy.network.ThermPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ThermScreen extends ContainerScreen<ThermContainer>{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Juicy.MODID, "textures/gui/theval_gui.png");
	public static TextFieldWidget textFieldHigh;
	public static TextFieldWidget textFieldLow;
	public static double high;
	public static double low;
	
	public ThermScreen(ThermContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void init() {
		super.init();
		PacketHandler.sendToServer(new ThermPacket(container.getPos()));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		textFieldHigh = new TextFieldWidget(font, x+45, y+25, 120, 12, new StringTextComponent("temperatureHigh"));
        textFieldHigh.setEnableBackgroundDrawing(false);
        textFieldHigh.setVisible(true);
        textFieldHigh.setFocused2(true);
		textFieldLow = new TextFieldWidget(font, x+45, y+53, 120, 12,  new StringTextComponent("temperatureLow"));
        textFieldLow.setEnableBackgroundDrawing(false);
        textFieldLow.setVisible(true);
        
        this.children.add(textFieldHigh);
        this.setFocusedDefault(textFieldHigh);
        this.children.add(textFieldLow);
        
		Button button = new Button(x + 133, y + 68, 36, 11, new TranslationTextComponent("button.set"),
				(btn) -> PacketHandler.sendToServer(new ThermButtonPacket(container.getPos(),
						Double.parseDouble(textFieldHigh.getText()), Double.parseDouble(textFieldLow.getText()))));
		button.visible = true;
        button.active = true;
        this.addButton(button);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
		this.textFieldHigh.render(matrixStack, mouseX, mouseY, partialTicks);
		this.textFieldLow.render(matrixStack, mouseX, mouseY, partialTicks);
	}
    
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
	}

}
