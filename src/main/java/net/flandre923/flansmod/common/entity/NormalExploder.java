package net.flandre923.flansmod.common.entity;

import com.google.common.collect.Lists;
import net.flandre923.flansmod.common.item.ball.MaterialType;
import net.flandre923.flansmod.common.item.ball.NormalBallItem;
import net.flandre923.flansmod.common.tag.ModTags;
import net.flandre923.flansmod.common.util.NormalMaterialBallHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.TickEvent;

import java.util.*;
import java.util.function.Predicate;

public class NormalExploder {

  public final double r;
  private final double rr;
  public final int dist;
  private final double explosionStrength;
  private final int blocksPerIteration;
  public final int x, y, z;
  public final Level world;
  private final Entity exploder;
  private final NormalBallExplosion explosion;
  private final ItemStack breakItem;
  private final Player player;
  private int currentRadius;
  private int curX, curY, curZ;

  public List<ItemStack> droppedItems; // map containing all items dropped by the explosion and their amounts

///
///
  /**
   * 构造方法
   * @param world 爆炸的level
   * @param explosion 爆炸对象的引用
   * @param exploder 触发爆炸的实体
   * @param location 爆炸的中心位置
   * @param r  爆炸半径
   * @param explosionStrength 爆炸的破坏力
   * @param blocksPerIteration 每次迭代处理的方块次数
   */
  public NormalExploder(Level world, NormalBallExplosion explosion, Entity exploder, BlockPos location, double r, double explosionStrength, int blocksPerIteration,ItemStack item,Player player) {
    this.r = r;
    this.world = world;
    this.explosion = explosion;
    this.exploder = exploder;
    this.rr = r * r;
    this.dist = (int) r + 1;
    this.explosionStrength = explosionStrength;
    this.blocksPerIteration = blocksPerIteration;
    this.currentRadius = 0;

    this.x = location.getX();
    this.y = location.getY();
    this.z = location.getZ();

    this.curX = 0;
    this.curY = 0;
    this.curZ = 0;

    this.droppedItems = Lists.newArrayList();

    this.breakItem = item;
    this.player = player;
  }

  /**
   * 启动爆炸 静态方法
   * @param world 爆炸的level
   * @param explosion 爆炸的引用
   * @param entity 爆炸的实体
   * @param location 爆炸的位置
   * @param r  爆炸的半径
   * @param explosionStrength 爆炸的强度
   */
  public static void startExplosion(Level world, NormalBallExplosion explosion, Entity entity, BlockPos location, double r, double explosionStrength,ItemStack item,Player player) {
    // 创建类
    NormalExploder normalExploder = new NormalExploder(world, explosion, entity, location, r, explosionStrength, Math.max(50, (int) (r * r * r / 10d)),item,player);
    // 提前处理爆炸范围内的实体
    normalExploder.handleEntities(explosion);
    // 播放爆炸的声音
    world.playSound(null, location, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
    // 在事件总线上注册exploer，用于在tick时迭代处理
    NeoForge.EVENT_BUS.register(normalExploder);
  }

  /**
   * 处理爆炸涉及到的实体
   */
  public void handleEntities(NormalBallExplosion explosion) {
    // 定义了一个Predicate判断条件，用于过滤实体
    final Predicate<Entity> predicate = entity -> entity != null
      && !entity.ignoreExplosion(explosion)
      && EntitySelector.NO_SPECTATORS.test(entity)
      && EntitySelector.ENTITY_STILL_ALIVE.test(entity)
      && entity.position().distanceToSqr(this.x, this.y, this.z) <= this.r * this.r;

    // 使用Predicate获得爆炸范围内的实体
    // damage and blast back entities
    List<Entity> list = this.world.getEntities(this.exploder,
      new AABB(this.x - this.r - 1,
        this.y - this.r - 1,
        this.z - this.r - 1,
        this.x + this.r + 1,
        this.y + this.r + 1,
        this.z + this.r + 1),
      predicate
    );
    // 触发爆炸开始事件
    EventHooks.onExplosionDetonate(this.world, this.explosion, list, this.r * 2);

    for (Entity entity : list) {
      // move it away from the center depending on distance and explosion strength
      // 计算爆炸中心到实体方向的向量dir
      Vec3 dir = entity.position().subtract(this.exploder.position().add(0, -this.r / 2, 0));
      // 计算实体距离爆炸中心的距离占爆炸半径的比例，作为震动系数str
      double str = (this.r - dir.length()) / this.r;
      //
      str = Math.max(0.3, str);
      //
      dir = dir.normalize();
      //
      dir = dir.scale(this.explosionStrength * str * 0.3);
      // 给实体添加一个dir的力
      entity.push(dir.x, dir.y + 0.5, dir.z);
      // 实体受伤
      entity.hurt(entity.damageSources().explosion(this.explosion), (float) (str * this.explosionStrength));

      if (entity instanceof ServerPlayer) {
//        TinkerNetwork.getInstance().sendTo(new EntityMovementChangePacket(entity), (ServerPlayer) entity);
      }
    }
  }

  /**
   * tick执行
   * @param event 世界Tick回调事件
   */
  @SubscribeEvent
  public void onTick(TickEvent.LevelTickEvent event) {
    // 给定世界Tick结束时候回调
      if(event.level == this.world && event.phase == TickEvent.Phase.END){
        // 调用iteration进行一次爆炸迭代，返回false表示爆炸结束，放置范围过大，导致一次破坏方块过多卡死
        if(!this.iteration()){
          // 爆炸结束，调用finish
          this.finish();
        }
      }
  }

  /**
   * 爆炸结束下执行
   */
  public void finish() {
    // 爆炸半径的一半
    final int d = (int) this.r / 2;
    // 根据爆炸中心和爆炸半径计算掉落的中心位置
    final BlockPos pos = new BlockPos(this.x - d, this.y - d, this.z - d);
    // 创建随机数生成器
    final Random random = new Random();
    // 创建列表存储掉落的物品
    List<ItemStack> aggregatedDrops = Lists.newArrayList();
    //
    for (ItemStack drop : this.droppedItems) {
      boolean notInList = true;

      // check if it's already in our list
      // 检查是否在列表中，如果在则合并数量
      for (ItemStack stack : aggregatedDrops) {
        if (ItemStack.isSameItem(drop, stack) && ItemStack.isSameItemSameTags(drop, stack)) {
          stack.grow(drop.getCount());
          notInList = false;
          break;
        }
      }

      if (notInList) {
        aggregatedDrops.add(drop);
      }
    }

    // actually drop the aggregated items
    // 遍历合在一起的物品，分批掉落
    for (ItemStack drop : aggregatedDrops) {
      int stacksize = drop.getCount();
      do {
        NormalBallItem item = (NormalBallItem) breakItem.getItem();
        BlockPos spawnPos = pos.offset(random.nextInt((int) this.r), random.nextInt((int) this.r), random.nextInt((int) this.r));
        ItemStack dropItemstack = drop.copy();
        dropItemstack.setCount(Math.min(stacksize, 64));

        // 根据不同的material处理
        NormalMaterialBallHelper.MaterialHandler handler = NormalMaterialBallHelper.getMaterialHandlers().get(item.materialType);
        if (handler != null) {
          handler.handle(this.world, spawnPos, dropItemstack, player);
        }

        stacksize -= dropItemstack.getCount();
      }
      while (stacksize > 0);
    }
    // 取消事件总线上的注册
    NeoForge.EVENT_BUS.unregister(this);
  }

  /**
   * Explodes away all blocks for the current iteration
   * 每次迭代执行
   */
  private boolean iteration() {
    // 本次迭代的方块个数
    int count = 0;
    // 清除上次的迭代处理的方块
    this.explosion.clearToBlow();
    // 当处理方块小于每次迭代方块，并且当前半径小于爆炸半径r
    while (count < this.blocksPerIteration && this.currentRadius < (int) this.r + 1) {
      double d = this.curX * this.curX + this.curY * this.curY + this.curZ * this.curZ;
      // inside the explosion?
      // 当前位置是否在爆炸范围内
      if (d <= this.rr) {
        //
        BlockPos blockpos = new BlockPos(this.x + this.curX, this.y + this.curY, this.z + this.curZ);
        BlockState blockState = this.world.getBlockState(blockpos);
        FluidState ifluidstate = this.world.getFluidState(blockpos);

        // no air blocks
        // 如果不是方块 或者流体不为空。
        if (!blockState.isAir() || !ifluidstate.isEmpty()) {
          // explosion "strength" at the current position
          // 随着爆炸范围的扩展爆炸强度减小
          double f = this.explosionStrength * (1f - d / this.rr);
          // 获得方块或者液体的抗爆炸强度
          float f2 = Math.max(blockState.getExplosionResistance(this.world, blockpos, this.explosion), ifluidstate.getExplosionResistance(this.world, blockpos, this.explosion));
          // 如果当前产生爆炸的实体不为空
          if (this.exploder != null) {
            // 获得当前产生爆炸实体的抗爆炸能力
            f2 = this.exploder.getBlockExplosionResistance(this.explosion, this.world, blockpos, blockState, ifluidstate, f2);
          }
          // 判断是否抗爆炸
          f -= (f2 + 0.3F) * 0.3F;
          // 如果该方块应该被炸坏
          if (f > 0.0F && (this.exploder == null || this.exploder.shouldBlockExplode(this.explosion, this.world, blockpos, blockState, (float) f))) {
            // block should be exploded
            // 爆炸方块++
            count++;
            // 将该方块添加到列表中
            this.explosion.addAffectedBlock(blockpos);
          }
        }
      }
      // get next coordinate;
      this.step();
    }
    // 触发爆炸事件
    EventHooks.onExplosionDetonate(this.world, this.explosion, Collections.emptyList(), this.r * 2);
    // 对每个记录的方块处理
    this.explosion.getToBlow().forEach(this::explodeBlock);
    // 所有方块是否处理完，若不等于blocksPerIteration则说明处理完了，否则就是没处理完。
    return count == this.blocksPerIteration; // can lead to 1 more call where nothing is done, but that's ok
  }

  // get the next coordinate

  /**
   * 计算下一个block坐标
   */
  private void step() {
    // we go X/Z plane wise from top to bottom
    if (++this.curX > this.currentRadius) {
      this.curX = -this.currentRadius;
      if (++this.curZ > this.currentRadius) {
        this.curZ = -this.currentRadius;
        if (--this.curY < -this.currentRadius) {
          this.currentRadius++;
          this.curX = this.curZ = -this.currentRadius;
          this.curY = this.currentRadius;
        }
      }
    }
    // we skip the internals
    if (this.curY != -this.currentRadius && this.curY != this.currentRadius) {
      // we're not in the top or bottom plane
      if (this.curZ != -this.currentRadius && this.curZ != this.currentRadius) {
        // we're not in the X/Y planes of the cube, we can therefore skip the x to the end if we're inside
        if (this.curX > -this.currentRadius) {
          this.curX = this.currentRadius;
        }
      }
    }
  }

  /**
   * 处理单个爆炸方块
   * @param blockpos
   */
  private void explodeBlock(BlockPos blockpos) {
    BlockState blockstate = this.world.getBlockState(blockpos);
    // 将该方块的掉落物添加到list中
    if (!this.world.isClientSide && blockstate.canDropFromExplosion(this.world, blockpos, this.explosion)) {


      BlockEntity tileentity = blockstate.hasBlockEntity() ? this.world.getBlockEntity(blockpos) : null;
      LootParams.Builder builder = (new LootParams.Builder((ServerLevel) this.world)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).withParameter(LootContextParams.TOOL, this.breakItem).withOptionalParameter(LootContextParams.BLOCK_ENTITY, tileentity);

      this.droppedItems.addAll(blockstate.getDrops(builder));
    }
    // 在该方块位置生成粒子效果
    if (this.world instanceof ServerLevel) {
      for (ServerPlayer serverplayerentity : ((ServerLevel) this.world).players()) {
        ((ServerLevel) this.world).sendParticles(serverplayerentity, ParticleTypes.POOF, true, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 2, 0, 0, 0, 0d);
        ((ServerLevel) this.world).sendParticles(serverplayerentity, ParticleTypes.SMOKE, true, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 1, 0, 0, 0, 0d);
      }
    }
    // 方块爆炸
    blockstate.onBlockExploded(this.world, blockpos, this.explosion);
  }



}
