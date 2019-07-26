package com.litewolf101.objects.entities;

import com.litewolf101.utils.INeedIllagerBoost;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class EntityNecromancer extends EntityEvoker implements INeedIllagerBoost {

    public EntityNecromancer(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 0.6D, 1.0D));
        this.tasks.addTask(4, new AIAttackSpell());
        this.tasks.addTask(5, new AISummonSpell());
        this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityEvoker.class}));
        this.targetTasks.addTask(2, (new EntityAINearestAttackableTarget(this, EntityPlayer.class, true)).setUnseenMemoryTicks(300));
        this.targetTasks.addTask(3, (new EntityAINearestAttackableTarget(this, EntityVillager.class, false)).setUnseenMemoryTicks(300));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
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
        else if (entityIn instanceof EntityVex)
        {
            return this.isOnSameTeam(((EntityVex)entityIn).getOwner());
        }
        else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
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
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
    }

    @Override
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    class AISummonSpell extends EntitySpellcasterIllager.AIUseSpell {

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
                int i = EntityNecromancer.this.world.getEntitiesWithinAABB(EntityZombie.class, EntityNecromancer.this.getEntityBoundingBox().grow(16.0D)).size();
                return EntityNecromancer.this.rand.nextInt(8) + 1 > i;
            }
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 340;
        }

        @Override
        protected void castSpell() {
            for (int i = 0; i <= 4; i++) {
                String Name = giveRandomName();
                double x = EntityNecromancer.this.posX + EntityNecromancer.this.rand.nextInt(6) - 3;
                double y = EntityNecromancer.this.posY + EntityNecromancer.this.rand.nextInt(2) - 1;
                double z = EntityNecromancer.this.posZ + EntityNecromancer.this.rand.nextInt(6) - 3;
                BlockPos zombiepos = isAvailableLocation(EntityNecromancer.this.world, x, y, z);
                EntityZombie zombie = new EntityZombie(EntityNecromancer.this.world);
                EntityZombieVillager zombieVillager = new EntityZombieVillager(EntityNecromancer.this.world);
                zombie.setLocationAndAngles(zombiepos.getX(), zombiepos.getY() + 1, zombiepos.getZ(), EntityNecromancer.this.rotationYaw, EntityNecromancer.this.rotationPitch);
                zombie.setCustomNameTag(Name);
                EntityNecromancer.this.world.spawnEntity(zombie);
                if (EntityNecromancer.this.rand.nextInt(10) == 1) {
                    zombieVillager.setProfession(EntityNecromancer.this.rand.nextInt(5));
                    zombieVillager.setLocationAndAngles(zombiepos.getX(), zombiepos.getY() + 1, zombiepos.getZ(), EntityNecromancer.this.rotationYaw, EntityNecromancer.this.rotationPitch);
                    EntityNecromancer.this.world.spawnEntity(zombieVillager);
                }
            }
        }

        @Override
        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }

        @Override
        protected EntitySpellcasterIllager.SpellType getSpellType()
        {
            return SpellType.SUMMON_VEX;
        }

        public BlockPos isAvailableLocation (World world, double x, double y, double z){
            BlockPos position = new BlockPos(x, y, z);
            boolean isNotValidLocation = world.getBlockState(position).isFullBlock() || world.getBlockState(position.up()).isFullBlock();
            boolean check = false;
            if (isNotValidLocation) {
                check = true;
            }
            while (check) {
                position = new BlockPos((int) EntityNecromancer.this.posX + EntityNecromancer.this.rand.nextInt(6) - 3, (int) EntityNecromancer.this.posY + EntityNecromancer.this.rand.nextInt(2) - 1, (int) EntityNecromancer.this.posZ + EntityNecromancer.this.rand.nextInt(6) - 3);
                if (!world.getBlockState(position).isFullBlock() || !world.getBlockState(position.up()).isFullBlock()) {
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

    class AIAttackSpell extends EntitySpellcasterIllager.AIUseSpell
    {
        private AIAttackSpell()
        {
            super();
        }

        protected int getCastingTime()
        {
            return 40;
        }

        protected int getCastingInterval()
        {
            return 100;
        }

        protected void castSpell()
        {
            EntityLivingBase entitylivingbase = EntityNecromancer.this.getAttackTarget();
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

            while (true)
            {
                if (!EntityNecromancer.this.world.isBlockNormalCube(blockpos, true) && EntityNecromancer.this.world.isBlockNormalCube(blockpos.down(), true))
                {
                    if (!EntityNecromancer.this.world.isAirBlock(blockpos))
                    {
                        IBlockState iblockstate = EntityNecromancer.this.world.getBlockState(blockpos);
                        AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(EntityNecromancer.this.world, blockpos);

                        if (axisalignedbb != null)
                        {
                            d0 = axisalignedbb.maxY;
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();

                if (blockpos.getY() < MathHelper.floor(p_190876_5_) - 1)
                {
                    break;
                }
            }

            if (flag)
            {
                EntityEvokerFangs entityevokerfangs = new EntityEvokerFangs(EntityNecromancer.this.world, p_190876_1_, (double)blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, EntityNecromancer.this);
                EntityNecromancer.this.world.spawnEntity(entityevokerfangs);
            }
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }

        protected EntitySpellcasterIllager.SpellType getSpellType()
        {
            return EntitySpellcasterIllager.SpellType.FANGS;
        }
    }
}
