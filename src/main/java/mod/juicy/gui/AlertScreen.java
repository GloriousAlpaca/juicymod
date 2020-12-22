package mod.juicy.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import mod.juicy.Juicy;
import mod.juicy.container.AlertContainer;
import mod.juicy.network.AlertEditPacket;
import mod.juicy.network.AlertPacket;
import mod.juicy.network.PacketHandler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AlertScreen extends ContainerScreen<AlertContainer>{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Juicy.MODID, "textures/gui/alertblock_gui.png");
	public static TextFieldWidget textField;
	public static Button modeButton;
	public static int operator;
	public static int amount;
	public static boolean mode;
	
	public AlertScreen(AlertContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void init() {
		super.init();
		PacketHandler.sendToServer(new AlertPacket(container.getPos()));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		textField = new TextFieldWidget(font, x+73, y+39, 89, 12, new StringTextComponent("amount"));
        textField.setEnableBackgroundDrawing(false);
        textField.setVisible(true);
        textField.setFocused2(true);
        this.children.add(textField);
        this.setFocusedDefault(textField);
        
		modeButton = new Button(x + 7, y + 36, 40, 14, new TranslationTextComponent("button.set"), (btn)->{
			mode = !mode;
			btn.setMessage(mode ? new TranslationTextComponent("button.juice") : new TranslationTextComponent("button.bacteria"));
		});
		modeButton.visible = true;
        modeButton.active = true;
        this.addButton(modeButton);
        
        ImageButton operatorButton = new ImageButton(x+50, y+36, 18, 14, 176, 0, 15, BACKGROUND_TEXTURE, (btn)->{
        	operator = operator%4+1;
        });
		operatorButton.visible = true;
        operatorButton.active = true;
        this.addButton(operatorButton);
		Button acceptButton = new Button(x + 115, y + 59, 54, 15, new TranslationTextComponent("button.set"),
				(btn)->PacketHandler.sendToServer(new AlertEditPacket(container.getPos(), operator, Integer.parseInt(textField.getText()), mode)));
		acceptButton.visible = true;
        acceptButton.active = true;
        this.addButton(acceptButton);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
		textField.render(matrixStack, mouseX, mouseY, partialTicks);
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
		switch (operator) {
		case 1:
			this.blit(matrixStack, x+53, y+58, 195, 2, 10, 10);
			break;
		case 2:
			this.blit(matrixStack, x+54, y+58, 195, 12, 10, 10);
			break;
		case 3:
			this.blit(matrixStack, x+53, y+58, 195, 22, 10, 10);
			break;
		case 4:
			this.blit(matrixStack, x+54, y+58, 195, 32, 10, 10);
			break;
		default:
			break;
		}
	}
	
}
