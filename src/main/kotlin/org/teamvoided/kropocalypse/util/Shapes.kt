package org.teamvoided.kropocalypse.util

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import org.joml.Vector3f

object Shapes {

    private val block = Blocks.CALCITE.defaultState
    fun testCube(pos: BlockPos, world: ServerWorld, size: Int = 7) =
        cube(pos, world, size, block)

    fun testCubeHollow(pos: BlockPos, world: ServerWorld, size: Int = 7) =
        cubeHollow(pos, world, size, block)

    fun testCuboid(pos: BlockPos, world: ServerWorld, size: Int = 7) =
        cuboid(pos, world, Vec3i(size, size / 2, size * 2), block)

    fun testCuboidHollow(pos: BlockPos, world: ServerWorld, size: Int = 7) =
        cuboidHollow(pos, world, Vec3i(size, size / 2, size * 2), block)

    fun testSphere(pos: BlockPos, world: ServerWorld, size: Int = 7) =
        sphere(pos, world, size, block)


    fun cube(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }
    ) = regularShape(pos, world, size, state, shouldPlace)

    fun cubeHollow(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState, shouldPlace: CoordinatePredicate = { true }
    ) = cube(pos, world, size, state)
    { ((it.x == 1 || it.y == 1 || it.z == 1) || it.x == size || it.y == size || it.z == size) && shouldPlace(it) }

    fun cuboid(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState, shouldPlace: CoordinatePredicate = { true }
    ) = shape(pos, world, size, state,
        {
            ((it.x == 1 || it.y == 1 || it.z == 1) || it.x == size.x || it.y == size.y || it.z == size.z)
                    && shouldPlace(it)
        })

    fun cuboidHollow(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState, shouldPlace: CoordinatePredicate = { true }
    ) = shape(pos, world, size, state, shouldPlace)

    fun sphere(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }
    ) = regularShape(pos, world, size, state, shouldPlace)
    { point, center, radius ->
        ((center.x - point.x).pow(2) + (center.y - point.y).pow(2) + (center.z - point.z).pow(2)) <= (radius.x * radius.x) - .5
    }


    fun regularShape(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate,
        shapeCondition: CenteredCoordinatePredicate = { _, _, _ -> true }
    ) = shape(pos, world, Vec3i(size), state, shouldPlace, shapeCondition)


    fun shape(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState,
        shouldPlace: CoordinatePredicate,
        shapeCondition: CenteredCoordinatePredicate = { _, _, _ -> true }
    ) {
        val mod = Vec3i(-(size.x / 2) - 1, -(size.y / 2) - 1, -(size.z / 2) - 1)
        val center = pos.add(mod)
        val radius = Vector3f(size.x.toFloat() / 2, size.y.toFloat() / 2, size.z.toFloat() / 2)

        for (i in 1..size.x) {
            for (j in 1..size.z) {
                for (k in 1..size.y) {
                    if (shapeCondition(Vec3i(i, k, j), mod.abs(), radius) && shouldPlace(Vec3i(i, k, j))) {
                        world.setBlockState(center.add(i, k, j), state)
                    }
                }
            }
        }
    }
}