package com.litewolf101.illagersplus.objects.entity;

import com.litewolf101.illagersplus.utils.INeedIllagerBoost;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Random;

public class EntityNecromancer extends EvokerEntity implements INeedIllagerBoost {

    @SuppressWarnings("unchecked")
    public EntityNecromancer(EntityType<? extends EntityNecromancer> type, World world) {
        super(type, world);
    }


    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(2, new AvoidEntityGoal(this, PlayerEntity.class, 8.0F, 0.6D, 1.0D));
        goalSelector.addGoal(4, new AIAttackSpell());
        goalSelector.addGoal(5, new AISummonSpell());
        goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtGoal(this, LivingEntity.class, 8.0F));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[] {EvokerEntity.class}));
        targetSelector.addGoal(2, (new NearestAttackableTargetGoal(this, PlayerEntity.class, true)).setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, (new NearestAttackableTargetGoal(this, VillagerEntity.class, false)).setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolemEntity.class, false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);

    }

    @Override
    public boolean isOnSameTeam(Entity entityIn)
    {
        if (entityIn == null)
        {
            return false;
        }
        else if (entityIn == this)
        {
            return true;
        }
        else if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof VexEntity)
        {
            return this.isOnSameTeam(((VexEntity)entityIn).getOwner());
        }
        else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_EVOKER_HURT;
    }

    @Override
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    class AISummonSpell extends SpellcastingIllagerEntity.UseSpellGoal{

        private AISummonSpell()
        {
            super();
        }

        @Override
        public boolean shouldExecute()
        {
            if (!super.shouldExecute())
            {
                return false;
            }
            else
            {
                int i = EntityNecromancer.this.world.getEntitiesWithinAABB(ZombieEntity.class, EntityNecromancer.this.getBoundingBox().grow(16.0D)).size();
                return EntityNecromancer.this.rand.nextInt(8) + 1 > i;
            }
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 200;
        }

        @Override
        protected void castSpell() {
            for (int i = 0; i <= 4; i++) {
                String Name = giveRandomName();
                double x = EntityNecromancer.this.posX + EntityNecromancer.this.rand.nextInt(6) - 3;
                double y = EntityNecromancer.this.posY + EntityNecromancer.this.rand.nextInt(2) - 1;
                double z = EntityNecromancer.this.posZ + EntityNecromancer.this.rand.nextInt(6) - 3;
                BlockPos zombiepos = isAvailableLocation(EntityNecromancer.this.world, x, y, z);
                ZombieEntity zombie = new ZombieEntity(EntityNecromancer.this.world);
                //ZombieVillagerEntity zombieVillager = new ZombieVillagerEntity(EntityNecromancer.this.world);
                zombie.setLocationAndAngles(zombiepos.getX(), zombiepos.getY() + 1, zombiepos.getZ(), EntityNecromancer.this.rotationYaw, EntityNecromancer.this.rotationPitch);
                zombie.setCustomName(new StringTextComponent(Name));
                zombie.setCustomNameVisible(true);
                zombie.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.LEATHER_HELMET));
                EntityNecromancer.this.world.addEntity(zombie);
                /*if (EntityNecromancer.this.rand.nextInt(10) == 1) {
                    zombieVillager.setProfession(EntityNecromancer.this.rand.nextInt(5));
                    zombieVillager.setLocationAndAngles(zombiepos.getX(), zombiepos.getY() + 1, zombiepos.getZ(), EntityNecromancer.this.rotationYaw, EntityNecromancer.this.rotationPitch);
                    //EntityNecromancer.this.world.spawnEntity(zombieVillager);
                }*/
            }
        }

        @Override
        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected SpellcastingIllagerEntity.SpellType getSpellType()
        {
            return SpellType.SUMMON_VEX;
        }

        public BlockPos isAvailableLocation (World world, double x, double y, double z){
            BlockPos position = new BlockPos(x, y, z);
            boolean isNotValidLocation = world.getBlockState(position).isSolid() || world.getBlockState(position.up()).isSolid();
            boolean check = false;
            if (isNotValidLocation) {
                check = true;
            }
            while (check) {
                position = new BlockPos((int) EntityNecromancer.this.posX + EntityNecromancer.this.rand.nextInt(6) - 3, (int) EntityNecromancer.this.posY + EntityNecromancer.this.rand.nextInt(2) - 1, (int) EntityNecromancer.this.posZ + EntityNecromancer.this.rand.nextInt(6) - 3);
                if (!world.getBlockState(position).isSolid() || !world.getBlockState(position.up()).isSolid()) {
                    check = false;
                    break;
                }
            }
            return position;
        }

        public String giveRandomName(){
            Random random = new Random();
            int chance = random.nextInt(3);
            if (chance == 0) {
                return "Cod";
            }
            if (chance == 1) {
                return "Zoomblez";
            }
            if (chance == 2) {
                return "NomNom";
            }
            if (chance == 3) {
                return "Stitches";
            } else return "";
        }
    }

    class AIAttackSpell extends SpellcastingIllagerEntity.UseSpellGoal
    {
        private AIAttackSpell()
        {
            super();
        }

        protected int getCastingTime()
        {
            return 20;
        }

        protected int getCastingInterval()
        {
            return 50;
        }

        protected void castSpell()
        {
            LivingEntity entitylivingbase = EntityNecromancer.this.getAttackTarget();
            double d0 = Math.min(entitylivingbase.posY, EntityNecromancer.this.posY);
            double d1 = Math.max(entitylivingbase.posY, EntityNecromancer.this.posY) + 1.0D;
            float f = (float) MathHelper.atan2(entitylivingbase.posZ - EntityNecromancer.this.posZ, entitylivingbase.posX - EntityNecromancer.this.posX);

            if (EntityNecromancer.this.getDistanceSq(entitylivingbase) < 10.0D)
            {
                for (int i = 0; i < 5; ++i)
                {
                    float f1 = f + (float)i * (float)Math.PI * 0.4F;
                    this.spawnFangs(EntityNecromancer.this.posX + (double)MathHelper.cos(f1) * 1.5D, EntityNecromancer.this.posZ + (double)MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0);
                }
                for (int k = 0; k < 8; ++k)
                {
                    float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
                    this.spawnFangs(EntityNecromancer.this.posX + (double)MathHelper.cos(f2) * 2.5D, EntityNecromancer.this.posZ + (double)MathHelper.sin(f2) * 2.5D, d0, d1, f2, 5);
                }
                for (int k = 0; k < 18; ++k)
                {
                    float f3 = f + (float)k * (float)Math.PI * 2.0F / 16.0F + ((float)Math.PI * 2F / 10F);
                    this.spawnFangs(EntityNecromancer.this.posX + (double)MathHelper.cos(f3) * 3.5D, EntityNecromancer.this.posZ + (double)MathHelper.sin(f3) * 3.5D, d0, d1, f3, 10);
                }
                for (int k = 0; k < 36; ++k)
                {
                    float f4 = f + (float)k * (float)Math.PI * 2.0F / 32.0F + ((float)Math.PI * 2F / 20F);
                    this.spawnFangs(EntityNecromancer.this.posX + (double)MathHelper.cos(f4) * 4.5D, EntityNecromancer.this.posZ + (double)MathHelper.sin(f4) * 4.5D, d0, d1, f4, 15);
                }
            }
        }

        private void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_)
        {
            BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
            boolean flag = false;
            double d0 = 0.0D;

            while(true) {
                BlockPos blockpos1 = blockpos.down();
                BlockState blockstate = EntityNecromancer.this.world.getBlockState(blockpos1);
                if (Block.hasSolidSide(blockstate, EntityNecromancer.this.world, blockpos1, Direction.UP)) {
                    if (!EntityNecromancer.this.world.isAirBlock(blockpos)) {
                        BlockState blockstate1 = EntityNecromancer.this.world.getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(EntityNecromancer.this.world, blockpos);
                        if (!voxelshape.isEmpty()) {
                            d0 = voxelshape.getEnd(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();
                if (blockpos.getY() < MathHelper.floor(p_190876_5_) - 1) {
                    break;
                }
            }

            if (flag) {
                EntityNecromancer.this.world.addEntity(new EvokerFangsEntity(EntityNecromancer.this.world, p_190876_1_, (double)blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, EntityNecromancer.this));
            }
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        protected SpellcastingIllagerEntity.SpellType getSpellType()
        {
            return SpellcastingIllagerEntity.SpellType.FANGS;
        }
    }
}
