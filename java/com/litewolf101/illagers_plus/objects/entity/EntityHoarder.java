package com.litewolf101.illagers_plus.objects.entity;

import com.litewolf101.illagers_plus.utils.ContainerHoarder;
import com.litewolf101.illagers_plus.utils.INeedIllagerBoost;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityHoarder extends EntityAbstractIllagerPlus implements INeedIllagerBoost {
    private ContainerHoarder collections = new ContainerHoarder( 10);

    @SuppressWarnings("unchecked")
    public EntityHoarder(EntityType<? extends EntityHoarder> type, World world) {
        super(type, world);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return this.collections.isEmpty();
    }

    public int getSizeInventory() {
        return collections.getSizeInventory();
    }

    public boolean isEmpty(){
        return this.collections.isEmpty();
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{VindicatorEntity.class}));
        goalSelector.addGoal(6, new MoveTowardsItemGoal(this));
        goalSelector.addGoal(7, new AvoidEntityGoal(this, PlayerEntity.class, 8.0F, 0.6D, 0.6D));
        goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtGoal(this, LivingEntity.class, 8.0F));
    }

    @Override
    public void onDeath(DamageSource cause) {
        for (int i = 0; i <= this.collections.getSizeInventory() + 1; i++) {
            ItemStack stack = this.collections.getStackInSlot(i);
            entityDropItem(stack, 1);
        }
        this.collections.clear();
    }

    @Override
    public void livingTick() {
        List<ItemEntity> list = this.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(this.posX - 1, this.posY, this.posZ - 1, this.posX + 1, this.posY + 0.1, this.posZ + 1));

        if (!this.dead && this.deathTime < 1) {
            if (!list.isEmpty() && !isInventoryFull()) {
                for (int i = 0; i < list.size(); ++i) {
                    ItemEntity entity = list.get(i);
                    if (entity != null) {
                        ItemStack stack = entity.getItem();
                        this.collections.setInventorySlotContents(checkFirstAvailableEmptySlot(), stack.copy());
                        world.playSound(null, this.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 0.2f, 2);
                        entity.setItem(ItemStack.EMPTY);
                    }
                }
            }
        }
        super.livingTick();
    }

    public boolean isInventoryFull() {
        int check = 0;
        for (int i = 0; i <=this.collections.getSizeInventory(); i++) {
            if (this.collections.getStackInSlot(i) != ItemStack.EMPTY) {
                check++;
            }
        }
        return check == this.collections.getSizeInventory();
    }

    public int checkFirstAvailableEmptySlot() {
        int slotNum = 0;
        for (int i = 0; i <=this.collections.getSizeInventory(); i++) {
            if (this.collections.getStackInSlot(i) == ItemStack.EMPTY) {
                break;
            } else {
                slotNum++;
            }
        }
        return slotNum;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        ListNBT listnbt = new ListNBT();

        for(int i = 0; i < this.collections.getSizeInventory(); ++i) {
            ItemStack itemstack = this.collections.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                listnbt.add(itemstack.write(new CompoundNBT()));
            }
        }
        compound.put("Inventory", listnbt);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        ListNBT listnbt = compound.getList("Inventory", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            ItemStack itemstack = ItemStack.read(listnbt.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.collections.addItem(itemstack);
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VINDICATOR_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VINDICATOR_HURT;
    }

    private class MoveTowardsItemGoal extends Goal {
        private final EntityHoarder hoarder;

        public MoveTowardsItemGoal(EntityHoarder entityHoarder) {
            this.hoarder = entityHoarder;
        }

        @Override
        public void tick() {
            AxisAlignedBB checkBB = new AxisAlignedBB(hoarder.posX - 32, hoarder.posY - 7, hoarder.posZ - 32, hoarder.posX + 32, hoarder.posY + 7, hoarder.posZ + 32);
            List<ItemEntity> nearbyItems = this.hoarder.world.getEntitiesWithinAABB(ItemEntity.class, checkBB);
            if (!nearbyItems.isEmpty()) {
                Entity nearestItem = nearbyItems.get(0);
                this.hoarder.getNavigator().tryMoveToXYZ(nearestItem.posX, nearestItem.posY, nearestItem.posZ, 0.6);
            }
            super.tick();
        }

        @Override
        public boolean shouldExecute() {
            AxisAlignedBB checkBB = new AxisAlignedBB(hoarder.posX - 32, hoarder.posY - 7, hoarder.posZ - 32, hoarder.posX + 32, hoarder.posY + 7, hoarder.posZ + 32);
            List<ItemEntity> nearbyItems = hoarder.world.getEntitiesWithinAABB(ItemEntity.class, checkBB);

            return !nearbyItems.isEmpty();
        }
    }
}
