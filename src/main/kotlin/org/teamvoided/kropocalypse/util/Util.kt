package org.teamvoided.kropocalypse.util

import net.minecraft.util.math.Vec3i
import org.joml.Vector3f
import kotlin.math.pow

fun Vec3i(size: Int) = Vec3i(size, size, size)
fun Int.pow(i: Int) = this.toFloat().pow(i)
fun Vec3i.abs() = Vec3i(kotlin.math.abs(this.x), kotlin.math.abs(this.y), kotlin.math.abs(this.z))


typealias CoordinatePredicate = (point: Vec3i) -> Boolean
typealias CenteredCoordinatePredicate = (point: Vec3i, center: Vec3i, radius: Vector3f) -> Boolean