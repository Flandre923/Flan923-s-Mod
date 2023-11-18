package com.example.examplemod.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MyAnvilBlockScreen extends AbstractContainerScreen<MyAnvilBlockMenu>  implements ContainerListener {

    private static final ResourceLocation TEXT_FIELD_SPRITE = new ResourceLocation("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE = new ResourceLocation("container/anvil/text_field_disabled");
    private static final ResourceLocation ERROR_SPRITE = new ResourceLocation("container/anvil/error");
    private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");
    private static final Component TOO_EXPENSIVE_TEXT = Component.translatable("container.repair.expensive");
    private EditBox name;

    private final Player player;


    public MyAnvilBlockScreen(MyAnvilBlockMenu myAnvilBlockMenu, Inventory inventory, Component component) {
        super(myAnvilBlockMenu, inventory, component);
        this.player = inventory.player;
        this.titleLabelX = 60;
    }
    @Override
    protected void init() {
        super.init();
        this.subInit();
        this.menu.addSlotListener( this);
    }

    protected void subInit() {
//        int i = (this.width - this.imageWidth) / 2;
//        int j = (this.height - this.imageHeight) / 2;
//        this.name = new EditBox(this.font, i + 62, j + 24, 103, 12, Component.translatable("container.repair"));
//        this.name.setCanLoseFocus(false);
//        this.name.setTextColor(-1);
//        this.name.setTextColorUneditable(-1);
//        this.name.setBordered(false);
//        this.name.setMaxLength(50);
//        this.name.setResponder(this::onNameChanged);
//        this.name.setValue("");
//        this.name.setEditable(false);
//        this.addWidget(this.name);
//        this.setInitialFocus(this.name);
//        this.name.setEditable(this.menu.getSlot(0).hasItem());
    }


    @Override
    public void resize(Minecraft p_97886_, int p_97887_, int p_97888_) {
//        String s = this.name.getValue();
        this.init(p_97886_, p_97887_, p_97888_);
//        this.name.setValue(s);
    }


    @Override
    public boolean keyPressed(int p_97878_, int p_97879_, int p_97880_) {
        if (p_97878_ == 256) {
            this.minecraft.player.closeContainer();
        }
        return super.keyPressed(p_97878_, p_97879_, p_97880_);
//        return !this.name.keyPressed(p_97878_, p_97879_, p_97880_) && !this.name.canConsumeInput() ? super.keyPressed(p_97878_, p_97879_, p_97880_) : true;
    }

//    private void onNameChanged(String p_97899_) {
//        Slot slot = this.menu.getSlot(0);
//        if (slot.hasItem()) {
//            String s = p_97899_;
//            if (!slot.getItem().hasCustomHoverName() && p_97899_.equals(slot.getItem().getHoverName().getString())) {
//                s = "";
//            }
//            if (this.menu.setItemName(s)) {
//            if (this.menu.setItemName()) {
//                this.minecraft.player.connection.send(new ServerboundRenameItemPacket(s));
//                ModMessage.sendToServer(new MyAnvilBlockC2S());
//            }
//        }
//    }
    public void render(GuiGraphics p_281810_, int p_283312_, int p_283420_, float p_282956_) {
        super.render(p_281810_, p_283312_, p_283420_, p_282956_);
        this.renderFg(p_281810_, p_283312_, p_283420_, p_282956_);
        this.renderTooltip(p_281810_, p_283312_, p_283420_);
    }
    public void renderFg(GuiGraphics p_283449_, int p_283263_, int p_281526_, float p_282957_) {
//        this.name.render(p_283449_, p_283263_, p_281526_, p_282957_);
    }


    @Override
    protected void renderLabels(GuiGraphics p_281442_, int p_282417_, int p_283022_) {
        super.renderLabels(p_281442_, p_282417_, p_283022_);
//        int i = this.menu.getCost();
//        if (i > 0) {
//            int j = 8453920;
//            Component component;
//            if (i >= 40 && !this.minecraft.player.getAbilities().instabuild) {
//                component = TOO_EXPENSIVE_TEXT;
//                j = 16736352;
//            } else if (!this.menu.getSlot(2).hasItem()) {
//                component = null;
//            } else {
//                component = Component.translatable("container.repair.cost", i);
//                if (!this.menu.getSlot(2).mayPickup(this.player)) {
//                    j = 16736352;
//                }
//            }

//            if (component != null) {
//                int k = this.imageWidth - 8 - this.font.width(component) - 2;
//                int l = 69;
//                p_281442_.fill(k - 2, 67, this.imageWidth - 8, 79, 1325400064);
//                p_281442_.drawString(this.font, component, k, 69, j);
//            }
//        }
    }

    @Override
    protected void renderBg(GuiGraphics p_283345_, float p_283412_, int p_282871_, int p_281306_) {
        p_283345_.blit(ANVIL_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderErrorIcon(p_283345_, this.leftPos, this.topPos);
        p_283345_.blitSprite( TEXT_FIELD_SPRITE, this.leftPos + 59, this.topPos + 20, 110, 16);
    }

    @Override
    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    protected void renderErrorIcon(GuiGraphics p_282905_, int p_283237_, int p_282237_) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
            p_282905_.blitSprite(ERROR_SPRITE, p_283237_ + 99, p_282237_ + 45, 28, 21);
        }
    }

    @Override
    public void slotChanged(AbstractContainerMenu p_97882_, int p_97883_, ItemStack p_97884_) {
        if (p_97883_ == 0) {
//            this.name.setValue(p_97884_.isEmpty() ? "" : p_97884_.getHoverName().getString());
//            this.name.setEditable(!p_97884_.isEmpty());
//            this.setFocused(this.name);
        }
    }

    @Override
    public void dataChanged(AbstractContainerMenu p_150524_, int p_150525_, int p_150526_) {

    }

}
