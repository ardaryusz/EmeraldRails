package com.ardaryusz.emeraldrails.mixin;

import com.ardaryusz.emeraldrails.registry.ModBlocks;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartController;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({DefaultMinecartController.class, ExperimentalMinecartController.class})
public abstract class EmeraldBoostMinecartControllerMixin extends MinecartController {

    @Unique private static final double MULT = 15.0;
    @Unique private static final double EPS = 1.0e-4;

    // acquired by the environment powered rail speed.
    @Unique private static double emeraldrails$learnedPoweredAccel = 0.06;

    @Unique private Vec3d emeraldrails$preVel = Vec3d.ZERO;

    protected EmeraldBoostMinecartControllerMixin(AbstractMinecartEntity minecart) {
        super(minecart);
    }

    @Inject(method = "moveOnRail", at = @At("HEAD"))
    private void emeraldrails$storePreVel(ServerWorld world, CallbackInfo ci) {
        emeraldrails$preVel = this.getVelocity();
    }

    @Inject(method = "moveOnRail", at = @At("TAIL"))
    private void emeraldrails$afterMove(ServerWorld world, CallbackInfo ci) {
        BlockState state = emeraldrails$getRailStateUnder(world);
        if (state == null) return;

        if (state.isOf(Blocks.POWERED_RAIL) && state.get(PoweredRailBlock.POWERED)) {
            emeraldrails$updateLearnedAccel(emeraldrails$preVel, this.getVelocity());
            return; // don't mess with vanilla powered rails
        }

        if (!state.isOf(ModBlocks.EMERALD_RAIL)) return;
        if (!state.get(PoweredRailBlock.POWERED)) return;

        Vec3d vel = this.getVelocity();
        Vec3d boosted = emeraldrails$applyBoostNoSelfStart(vel, emeraldrails$learnedPoweredAccel * MULT);
        this.setVelocity(this.limitSpeed(boosted));
    }

    @Unique
    private void emeraldrails$updateLearnedAccel(Vec3d pre, Vec3d post) {
        double preS = Math.sqrt(pre.x * pre.x + pre.z * pre.z);
        double postS = Math.sqrt(post.x * post.x + post.z * post.z);
        double delta = postS - preS;

        if (delta > 1.0e-6) {

            emeraldrails$learnedPoweredAccel = emeraldrails$learnedPoweredAccel * 0.9 + delta * 0.1;
        }
    }

    @Unique
    private Vec3d emeraldrails$applyBoostNoSelfStart(Vec3d vel, double accel) {
        double vx = vel.x;
        double vz = vel.z;

        double speed = Math.sqrt(vx * vx + vz * vz);

        if (speed <= EPS) return vel;

        double ax = (vx / speed) * accel;
        double az = (vz / speed) * accel;

        return new Vec3d(vx + ax, vel.y, vz + az);
    }

    @Unique
    private BlockState emeraldrails$getRailStateUnder(ServerWorld world) {
        BlockPos pos = this.minecart.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof AbstractRailBlock) return state;

        state = world.getBlockState(pos.down());
        return (state.getBlock() instanceof AbstractRailBlock) ? state : null;
    }
}
