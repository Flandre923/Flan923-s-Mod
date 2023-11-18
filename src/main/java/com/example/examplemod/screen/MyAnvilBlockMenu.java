package com.example.examplemod.screen;

import com.example.examplemod.block.ModBlocks;
import com.example.examplemod.item.custom.NormalBallItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public class MyAnvilBlockMenu extends AbstractContainerMenu {
    // slot
    public static final int INPUT_SLOT = 0;
    public static final int ADDITIONAL_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    protected final Player player;

    protected final Container inputSlots;
    private final List<Integer> inputSlotIndexes;
    protected final ResultContainer resultSlots = new ResultContainer();
    private final int resultSlotIndex;


    // position
    private static final int INPUT_SLOT_X_PLACEMENT = 27;
    private static final int ADDITIONAL_SLOT_X_PLACEMENT = 76;
    private static final int RESULT_SLOT_X_PLACEMENT = 134;
    private static final int SLOT_Y_PLACEMENT = 47;

    protected final ContainerLevelAccess access;

    public MyAnvilBlockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id,inv);
    }

    public MyAnvilBlockMenu(int id, Inventory inv) {
        this(id, inv, ContainerLevelAccess.NULL);
    }

    public MyAnvilBlockMenu(int id, Inventory inv, ContainerLevelAccess containerLevelAccess) {
        super(ModMenuTypes.MY_ANVIL_BLOCK_MENU.get(), id);
        this.access = containerLevelAccess;
        ItemCombinerMenuSlotDefinition itemcombinermenuslotdefinition = this.createInputSlotDefinitions();
        this.inputSlots = this.createContainer(itemcombinermenuslotdefinition.getNumOfInputSlots());
        this.inputSlotIndexes = itemcombinermenuslotdefinition.getInputSlotIndexes();
        this.resultSlotIndex = itemcombinermenuslotdefinition.getResultSlotIndex();
        this.createInputSlots(itemcombinermenuslotdefinition);
        this.createResultSlot(itemcombinermenuslotdefinition);
        this.player = inv.player;
        this.createInventorySlots(inv);
    }

    private void createInventorySlots(Inventory inv) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }
    }

    private void createInputSlots(ItemCombinerMenuSlotDefinition p_267172_) {
        for(final ItemCombinerMenuSlotDefinition.SlotDefinition itemcombinermenuslotdefinition$slotdefinition : p_267172_.getSlots()) {
            this.addSlot(
                    new Slot(
                            this.inputSlots,
                            itemcombinermenuslotdefinition$slotdefinition.slotIndex(),
                            itemcombinermenuslotdefinition$slotdefinition.x(),
                            itemcombinermenuslotdefinition$slotdefinition.y()
                    ) {
                        @Override
                        public boolean mayPlace(ItemStack p_267156_) {
                            return itemcombinermenuslotdefinition$slotdefinition.mayPlace().test(p_267156_);
                        }
                    }
            );
        }
    }

    private void createResultSlot(ItemCombinerMenuSlotDefinition p_267000_) {
        this.addSlot(new Slot(this.resultSlots, p_267000_.getResultSlot().slotIndex(), p_267000_.getResultSlot().x(), p_267000_.getResultSlot().y()) {
            @Override
            public boolean mayPlace(ItemStack p_39818_) {
                return false;
            }

            @Override
            public boolean mayPickup(Player p_39813_) {
                return MyAnvilBlockMenu.this.mayPickup(p_39813_, this.hasItem());
            }

            @Override
            public void onTake(Player p_150604_, ItemStack p_150605_) {
                MyAnvilBlockMenu.this.onTake(p_150604_, p_150605_);
            }
        });
    }

    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, itemStack -> (itemStack.getItem() instanceof  NormalBallItem))
                .withSlot(1, 76, 47, itemStack -> true)
                .withResultSlot(2, 134, 47)
                .build();
    }

    public boolean isNormalBallItem(ItemStack itemStack){
        return (itemStack.getItem() instanceof NormalBallItem);
    }

    private SimpleContainer createContainer(int p_267204_) {
        return new SimpleContainer(p_267204_) {
            @Override
            public void setChanged() {
                super.setChanged();
                MyAnvilBlockMenu.this.slotsChanged(this);
            }
        };
    }

    ///  quick move
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.getInventorySlotStart();
            int j = this.getUseRowEnd();
            if (index == this.getResultSlot()) {
                if (!this.moveItemStackTo(itemstack1, i, j, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (this.inputSlotIndexes.contains(index)) {
                if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canMoveIntoInputSlots(itemstack1) && index >= this.getInventorySlotStart() && index < this.getUseRowEnd()) {
                int k = this.getSlotToQuickMoveTo(itemstack);
                if (!this.moveItemStackTo(itemstack1, k, this.getResultSlot(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= this.getInventorySlotStart() && index < this.getInventorySlotEnd()) {
                if (!this.moveItemStackTo(itemstack1, this.getUseRowStart(), this.getUseRowEnd(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= this.getUseRowStart()
                    && index < this.getUseRowEnd()
                    && !this.moveItemStackTo(itemstack1, this.getInventorySlotStart(), this.getInventorySlotEnd(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
    public int getSlotToQuickMoveTo(ItemStack p_267159_) {
        return this.inputSlots.isEmpty() ? 0 : this.inputSlotIndexes.get(0);
    }
    protected boolean canMoveIntoInputSlots(ItemStack p_39787_) {
        return true;
    }
    private int getInventorySlotStart() {
        return this.getResultSlot() + 1;
    }
    private int getInventorySlotEnd() {
        return this.getInventorySlotStart() + 27;
    }
    private int getUseRowStart() {
        return this.getInventorySlotEnd();
    }
    private int getUseRowEnd() {
        return this.getUseRowStart() + 9;
    }
    ///  quick move

    @Override
    public boolean stillValid(Player player) {
        return this.access
                .evaluate(
                        (p_39785_, p_39786_) -> !this.isValidBlock(p_39785_.getBlockState(p_39786_))
                                ? false
                                : player.distanceToSqr((double)p_39786_.getX() + 0.5, (double)p_39786_.getY() + 0.5, (double)p_39786_.getZ() + 0.5) <= 64.0,
                        true
                );
    }

    protected boolean mayPickup(Player player, boolean p_39024_) {
        return true;
    }

    protected void onTake(Player player, ItemStack itemStack) {
        if (!player.getAbilities().instabuild) {
//            player.giveExperienceLevels(-this.cost.get());
        }

//        float breakChance = net.neoforged.neoforge.common.CommonHooks.onAnvilRepair(player, p_150475_, AnvilMenu.this.inputSlots.getItem(0), AnvilMenu.this.inputSlots.getItem(1));

        this.inputSlots.setItem(0, ItemStack.EMPTY);
//        if (this.repairItemCountCost > 0) {
            ItemStack itemstack = this.inputSlots.getItem(1);
//            if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
//                itemstack.shrink(this.repairItemCountCost);
//                this.inputSlots.setItem(1, itemstack);
//            } else {
//                this.inputSlots.setItem(1, ItemStack.EMPTY);
//            }
//        } else {
//            this.inputSlots.setItem(1, ItemStack.EMPTY);
//        }

//        this.cost.set(0);
        this.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
//            if (!player.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < breakChance) {
//                BlockState blockstate1 = AnvilBlock.damage(blockstate);
//                if (blockstate1 == null) {
//                    p_150479_.removeBlock(p_150480_, false);
//                    p_150479_.levelEvent(1029, p_150480_, 0);
//                } else {
//                    p_150479_.setBlock(p_150480_, blockstate1, 2);
//                    p_150479_.levelEvent(1030, p_150480_, 0);
//                }
//            } else {
//                p_150479_.levelEvent(1030, p_150480_, 0);
//            }
        });
    }

    protected boolean isValidBlock(BlockState blockState) {
        return blockState.is(ModBlocks.MY_ANVIL.get());
    }

    public void createResult() { // 定义一个公共方法，没有返回值，没有参数
        ItemStack itemstack = this.inputSlots.getItem(0); // 从输入槽中获取第一个物品，赋值给一个名为itemstack的变量
        int i = 0; // 定义一个整数变量i，初始值为0，用于记录附魔的消耗
        if (itemstack.isEmpty()) { // 如果第一个物品是空的，也就是没有物品
            this.resultSlots.setItem(0, ItemStack.EMPTY);// 那么将结果槽中的第一个物品设为空，也就是没有结果
        } else { // 否则，如果第一个物品不是空的，也就是有物品
            ItemStack itemstack1 = itemstack.copy();// 那么复制第一个物品，赋值给一个名为itemstack1的变量，这个变量将用于存储结果物品
            ItemStack itemstack2 = this.inputSlots.getItem(1);// 从输入槽中获取第二个物品，赋值给一个名为itemstack2的变量
            // 获取itemstack1的附魔，以一个映射的形式存储，键是附魔的类型，值是附魔的等级，赋值给一个名为map的变量
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
            boolean flag = false; // 定义一个布尔变量flag，初始值为false，用于记录第二个物品是否是一个有附魔的书

            if (!itemstack2.isEmpty()) {// 如果第二个物品不是空的，也就是有物品
                // 判断第二个物品是否是一个有附魔的书，如果是，那么将flag设为true，否则保持false
                flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(itemstack2).isEmpty();
                // 如果第一个物品是可以损坏的，而且第一个物品和第二个物品是可以用来修复的
                if (itemstack1.isDamageableItem() && itemstack1.getItem().isValidRepairItem(itemstack, itemstack2)) {
                    // 计算一个值l2，等于第一个物品的损坏值和最大损坏值的四分之一中的较小值，这个值表示每个第二个物品可以修复的损坏值
                    int l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
                    if (l2 <= 0) { // 如果l2小于等于0，也就是第一个物品没有损坏，或者损坏值太小
                        this.resultSlots.setItem(0, ItemStack.EMPTY); // 那么将结果槽中的第一个物品设为空，也就是没有结果
                        return;  // 结束这个方法，不再执行后面的代码
                    }

                    int i3;// 定义一个整数变量i3，用于记录循环的次数
                    for(i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); ++i3) {// 用一个for循环，从0开始，每次加1，直到l2小于等于0或者i3大于等于第二个物品的数量，也就是用第二个物品修复第一个物品，每个第二个物品可以修复l2的损坏值
                        int j3 = itemstack1.getDamageValue() - l2; // 计算一个值j3，等于第一个物品的损坏值减去l2，这个值表示修复后的损坏值
                        itemstack1.setDamageValue(j3); // 将第一个物品的损坏值设为j3，也就是修复它
                        ++i;  // 将i加1，表示修复消耗了1个附魔等级
                        l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4); // 重新计算l2的值，用于下一次循环
                    }

                } else { // 否则，如果第一个物品不是可以损坏的，或者第一个物品和第二个物品不是可以用来修复的
                    // 如果第二个物品不是一个有附魔的书，而且第一个物品和第二个物品不是同一种物品，或者第一个物品不是可以损坏的
                    if (!flag && (!itemstack1.is(itemstack2.getItem()) || !itemstack1.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);// 那么将结果槽中的第一个物品设为空，也就是没有结果
                        return; // 结束这个方法，不再执行后面的代码
                    }

                    if (itemstack1.isDamageableItem() && !flag) {// 如果第一个物品是可以损坏的，而且第二个物品不是一个有附魔的书
                        int l = itemstack.getMaxDamage() - itemstack.getDamageValue();// 计算一个值l，等于第一个物品的最大损坏值减去第一个物品的损坏值，这个值表示第一个物品的剩余耐久
                        int i1 = itemstack2.getMaxDamage() - itemstack2.getDamageValue(); // 计算一个值i1，等于第二个物品的最大损坏值减去第二个物品的损坏值，这个值表示第二个物品的剩余耐久
                        // 计算一个值j1，等于i1加上第一个物品的最大损坏值的百分之十二，这个值表示第二个物品可以提供的额外耐久
                        int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
                        // 计算一个值k1，等于l加上j1，这个值表示合成后的物品的总耐久
                        int k1 = l + j1;
                        // 计算一个值k1，等于l加上j1，这个值表示合成后的物品的总耐久
                        int l1 = itemstack1.getMaxDamage() - k1;
                        if (l1 < 0) { // 如果l1小于0，也就是合成后的物品的损坏值为负数
                            l1 = 0; // 那么将l1设为0，也就是合成后的物品没有损坏
                        }
                        // 如果l1小于第一个物品的损坏值，也就是合成后的物品的损坏值比第一个物品的损坏值小
                        if (l1 < itemstack1.getDamageValue()) {
                            itemstack1.setDamageValue(l1); // 那么将第一个物品的损坏值设为l1，也就是修复它
                            i += 2;// 将i加2，表示修复消耗了2个附魔等级
                        }
                    }
                    // 获取第二个物品的附魔，以一个映射的形式存储，键是附魔的类型，值是附魔的等级，赋值给一个名为map1的变量
                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
                    //用于记录是否有附魔被添加到结果物品上
                    boolean flag2 = false;
                    //用于记录是否有附魔因为不兼容而被忽略
                    boolean flag3 = false;
                    // 用一个for循环，遍历map1中的所有附魔类型，赋值给一个名为enchantment1的变量
                    for(Enchantment enchantment1 : map1.keySet()) {
                        if (enchantment1 != null) {// 如果enchantment1不是空的，也就是有附魔类型
                            //// 获取map中enchantment1对应的附魔等级，如果没有，就默认为0，赋值给一个名为i2的变量
                            int i2 = map.getOrDefault(enchantment1, 0);
                            int j2 = map1.get(enchantment1);// 获取map1中enchantment1对应的附魔等级，赋值给一个名为j2的变量
                            j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2); // 如果i2和j2相等，那么将j2加1，否则将j2设为i2和j2中的较大值，这个值表示合成后的附魔等级
                            boolean flag1 = enchantment1.canEnchant(itemstack);// 判断enchantment1是否可以附魔到第一个物品上，赋值给一个名为flag1的布尔变量
                            if (this.player.getAbilities().instabuild || itemstack.is(Items.ENCHANTED_BOOK)) {// 如果玩家有创造模式的能力，或者第一个物品是一个有附魔的书
                                flag1 = true; // 那么将flag1设为true，也就是可以附魔
                            }
                            if(enchantment1.category.equals(EnchantmentCategory.DIGGER)){
                                flag1 = true;
                            }

                            for(Enchantment enchantment : map.keySet()) { // 用一个for循环，遍历map中的所有附魔类型，赋值给一个名为enchantment的变量
                                if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) { // 如果enchantment和enchantment1不相同，而且enchantment1和enchantment不兼容
                                    flag1 = false; // 那么将flag1设为false，也就是不能附魔
                                    ++i;// 将i加1，表示附魔消耗了1个附魔等级
                                }
                            }

                            if (!flag1) {// 如果flag1是false，也就是不能附魔
                                flag3 = true;  // 那么将flag3设为true，表示有附魔被忽略
                            } else { // 否则，如果flag1是true，也就是可以附魔
                                flag2 = true;// 那么将flag2设为true，表示有附魔被添加
                                if (j2 > enchantment1.getMaxLevel()) {  // 如果j2大于enchantment1的最大等级，也就是合成后的附魔等级超过了限制
                                    j2 = enchantment1.getMaxLevel();// 那么将j2设为enchantment1的最大等级，也就是限制合成后的附魔等级
                                }

                                map.put(enchantment1, j2);// 将map中enchantment1对应的附魔等级设为j2，也就是更新合成后的附魔
                                int k3 = 0;// 定义一个整数变量k3，用于记录附魔的稀有度
                                switch(enchantment1.getRarity()) {// 用一个switch语句，根据enchantment1的稀有度，赋值给k3不同的值
                                    case COMMON:// 如果enchantment1的稀有度是普通的
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }

                                if (flag) {// 如果flag是true，也就是第二个物品是一个有附魔的书
                                    k3 = Math.max(1, k3 / 2);  // 那么将k3设为k3除以2和1中的较大值，也就是降低附魔的稀有度
                                }

                                i += k3 * j2;  // 将i加上k3乘以j2，表示附魔消耗了k3乘以j2个附魔等级
                                if (itemstack.getCount() > 1) {// 如果第一个物品的数量大于1，也就是有多个物品
                                    i = 40;// 那么将i设为40，也就是限制附魔的消耗
                                }
                            }
                        }
                    }

                    if (flag3 && !flag2) { // 如果flag3是true，而且flag2是false，也就是有附魔被忽略，而没有附魔被添加
                        this.resultSlots.setItem(0, ItemStack.EMPTY); // 那么将结果槽中的第一个物品设为空，也就是没有结果
                        return; // 结束这个方法，不再执行后面的代码
                    }
                }
            }
             // 如果flag是true，而且第一个物品不能被第二个物品附魔，也就是第二个物品是一个有附魔的书，而第一个物品不是一个可以被书附魔的物品，那么将itemstack1设为空，也就是没有结果
            if (flag && !itemstack1.isBookEnchantable(itemstack2)) itemstack1 = ItemStack.EMPTY;

            if (i <= 0) {// 如果i小于等于0，也就是附魔没有消耗任何附魔等级
                itemstack1 = ItemStack.EMPTY;// 那么将itemstack1设为空，也就是没有结果
            }

            if (!itemstack1.isEmpty()) {// 如果itemstack1不是空的，也就是有结果
                int k2 = itemstack1.getBaseRepairCost();// 获取itemstack1的基础修复消耗，赋值给一个名为k2的整数变量
                if (!itemstack2.isEmpty() && k2 < itemstack2.getBaseRepairCost()) {// 如果第二个物品不是空的，而且k2小于第二个物品的基础修复消耗，也就是第二个物品的修复消耗更高
                    k2 = itemstack2.getBaseRepairCost();// 那么将k2设为第二个物品的基础修复消耗，也就是使用第二个物品的修复消耗
                }
                itemstack1.setRepairCost(k2);// 将itemstack1的修复消耗设为k2，也就是更新结果物品的修复消耗
                EnchantmentHelper.setEnchantments(map, itemstack1);// 将map中的附魔应用到itemstack1上，也就是更新结果物品的附魔
            }

            this.resultSlots.setItem(0, itemstack1);// 将结果槽中的第一个物品设为itemstack1，也就是设置结果物品
            this.broadcastChanges(); // 广播这个方法的改变，通知其他玩家或实体
        }
    }

    @Override
    public void slotsChanged(Container p_39778_) {
        super.slotsChanged(p_39778_);
        if (p_39778_ == this.inputSlots) {
            this.createResult();
        }
    }

    @Override
    public void removed(Player p_39790_) {
        super.removed(p_39790_);
        this.access.execute((p_39796_, p_39797_) -> this.clearContainer(p_39790_, this.inputSlots));
    }

    public boolean setItemName() {
        this.createResult();
        return true;
    }

    public int getResultSlot() {
        return this.resultSlotIndex;
    }

}
