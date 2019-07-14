package com.litewolf101.objects.entities;

import com.litewolf101.utils.ContainerHoarder;
import com.litewolf101.utils.INeedIllagerBoost;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityHoarder extends EntityAbstractIllagerPlus implements INeedIllagerBoost {
    private ContainerHoarder collections = new ContainerHoarder("Hoarder", false, 10);

    public EntityHoarder(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 2F);
    }

    @Override
    protected boolean canDespawn() {
        return this.collections.isEmpty();
    }

    public int getSizeInventory() {
        return collections.getSizeInventory();
    }

    public boolean isEmpty(){
        return this.collections.isEmpty();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIMoveTowardsItem(this));
        this.tasks.addTask(7, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityVindicator.class}));
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
    public void onLivingUpdate() {
        List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.posX - 0.3, this.posY, this.posZ - 0.3, this.posX + 0.3, this.posY + 0.1, this.posZ + 0.3));

        if (!this.isDead && this.deathTime < 1) {

            if (!list.isEmpty() && !isInventoryFull()) {
                for (int i = 0; i < list.size(); ++i) {
                    EntityItem entity = list.get(i);
                    if (entity != null) {
                        ItemStack stack = entity.getItem();
                        this.collections.setInventorySlotContents(checkFirstAvailableEmptySlot(), stack.copy());
                        world.playSound(null, this.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 0.2f, 2);
                        entity.setItem(ItemStack.EMPTY);
                    }
                }
            }
        }
        super.onLivingUpdate();
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
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i <= this.collections.getSizeInventory(); i++) {
            ItemStack itemstack = this.collections.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            this.collections.setInventorySlotContents(i, new ItemStack(nbttagcompound));
        }
    }

    private class EntityAIMoveTowardsItem extends EntityAIBase {
        private final EntityHoarder hoarder;

        public EntityAIMoveTowardsItem(EntityHoarder entityHoarder) {
            this.hoarder = entityHoarder;
        }

        @Override
        public void updateTask() {
            AxisAlignedBB checkBB = new AxisAlignedBB(hoarder.posX - 32, hoarder.posY - 7, hoarder.posZ - 32, hoarder.posX + 32, hoarder.posY + 7, hoarder.posZ + 32);
            List<EntityItem> nearbyItems = this.hoarder.world.getEntitiesWithinAABB(EntityItem.class, checkBB);
            if (!nearbyItems.isEmpty()) {
                Entity nearestItem = nearbyItems.get(0);
                this.hoarder.getNavigator().tryMoveToXYZ(nearestItem.posX, nearestItem.posY, nearestItem.posZ, 0.6);
            }
        }

        @Override
        public boolean shouldExecute() {
            AxisAlignedBB checkBB = new AxisAlignedBB(hoarder.posX - 32, hoarder.posY - 7, hoarder.posZ - 32, hoarder.posX + 32, hoarder.posY + 7, hoarder.posZ + 32);
            List<EntityItem> nearbyItems = hoarder.world.getEntitiesWithinAABB(EntityItem.class, checkBB);

            return !nearbyItems.isEmpty();
        }
    }
}
