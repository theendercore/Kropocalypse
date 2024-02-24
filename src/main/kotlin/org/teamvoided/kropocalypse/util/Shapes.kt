package org.teamvoided.kropocalypse.util

import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import org.joml.Vector3f

@Suppress("MemberVisibilityCanBePrivate")
object Shapes {

    private val block = blockList.random().defaultState
    fun testCube(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        cube(pos, world, size, block, success = success)

    fun testCubeHollow(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        cubeHollow(pos, world, size, block, success = success)

    fun testCuboid(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        cuboid(pos, world, Vec3i(size, size / 2, size * 2), block, success = success)

    fun testCuboidHollow(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        cuboidHollow(pos, world, Vec3i(size, size / 2, size * 2), block, success = success)

    fun testSphere(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        sphere(pos, world, size, block, success = success)

    fun testSphereHollow(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        sphereHollow(pos, world, size, block, success = success)

    fun testCylinder(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        cylinder(pos, world, Vec3i(size, size + (size / 2), size), block, success = success)

    fun testCylinderHollow(pos: BlockPos, world: ServerWorld, size: Int = 7, success: CoordinateCallback = {}) =
        cylinderHollow(pos, world, Vec3i(size, size + (size / 2), size), block, success = success)

    fun cube(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback,
    ) = regularShape(pos, world, size, state, shouldPlace, success)

    fun cubeHollow(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback,
    ) = cube(
        pos, world, size, state,
        { ((it.x == 1 || it.y == 1 || it.z == 1) || it.x == size || it.y == size || it.z == size) && shouldPlace(it) },
        success
    )

    fun cuboid(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback,
    ) = shape(pos, world, size, state, shouldPlace, success)

    fun cuboidHollow(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback,
    ) = cuboid(
        pos, world, size, state,
        {
            ((it.x == 1 || it.y == 1 || it.z == 1) || it.x == size.x || it.y == size.y || it.z == size.z)
                    && shouldPlace(it)
        }, success
    )

    fun sphere(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback,
    ) = regularShape(pos, world, size, state, shouldPlace, success) { point, center, radius ->
        ((center.x - point.x).pow(2) + (center.y - point.y).pow(2) + (center.z - point.z).pow(2)) <= radius.x * radius.x
    }

    fun sphereHollow(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback,
    ) = regularShape(pos, world, size, state, shouldPlace, success) { point, center, radius ->
        val x = ((center.x - point.x).pow(2) + (center.y - point.y).pow(2) + (center.z - point.z).pow(2))
        x == radius.x * radius.x || x == (radius.x * radius.x) - 1
    }

    fun cylinder(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback
    ) = shape(pos, world, size, state, shouldPlace, success) { point, center, radius ->
        ((center.x - point.x).pow(2) + (center.z - point.z).pow(2)) <= (radius.x * radius.x)
    }

    fun cylinderHollow(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState,
        shouldPlace: CoordinatePredicate = { true }, success: CoordinateCallback
    ) = shape(pos, world, size, state, shouldPlace, success) { point, center, radius ->
        ((center.x - point.x).pow(2) + (center.z - point.z).pow(2)) == (radius.x * radius.x)
    }


    fun regularShape(
        pos: BlockPos, world: ServerWorld, size: Int, state: BlockState,
        shouldPlace: CoordinatePredicate, success: CoordinateCallback,
        shapeCondition: CenteredCoordinatePredicate = { _, _, _ -> true }
    ) = shape(pos, world, Vec3i(size), state, shouldPlace, success, shapeCondition)


    fun shape(
        pos: BlockPos, world: ServerWorld, size: Vec3i, state: BlockState,
        shouldPlace: CoordinatePredicate, success: CoordinateCallback,
        shapeCondition: CenteredCoordinatePredicate = { _, _, _ -> true },
    ) {
        val mod = Vec3i(-(size.x / 2) - 1, -(size.y / 2) - 1, -(size.z / 2) - 1)
        val center = pos.add(mod)
        val radius = Vector3f(size.x.toFloat() / 2, size.y.toFloat() / 2, size.z.toFloat() / 2)

        for (i in 1..size.x) {
            for (j in 1..size.z) {
                for (k in 1..size.y) {
                    if (shapeCondition(Vec3i(i, k, j), mod.abs(), radius) && shouldPlace(Vec3i(i, k, j))) {
                        world.setBlockState(center.add(i, k, j), state)
                        success(center.add(i, k, j))
                    }
                }
            }
        }
    }
}