package org.teamvoided.kropocalypse.util

import kotlinx.datetime.Clock
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.Vec3i
import kotlin.math.pow
import kotlin.random.Random


fun Vec3i(size: Int) = Vec3i(size, size, size)
fun Int.pow(i: Int) = this.toFloat().pow(i)
fun Vec3i.abs() = Vec3i(kotlin.math.abs(this.x), kotlin.math.abs(this.y), kotlin.math.abs(this.z))

val blockList = listOf(
    Blocks.WHITE_WOOL,
    Blocks.ORANGE_WOOL,
    Blocks.MAGENTA_WOOL,
    Blocks.LIGHT_BLUE_WOOL,
    Blocks.YELLOW_WOOL,
    Blocks.LIME_WOOL,
    Blocks.PINK_WOOL,
    Blocks.GRAY_WOOL,
    Blocks.LIGHT_GRAY_WOOL,
    Blocks.CYAN_WOOL,
    Blocks.PURPLE_WOOL,
    Blocks.BLUE_WOOL,
    Blocks.BROWN_WOOL,
    Blocks.GREEN_WOOL,
    Blocks.RED_WOOL,
    Blocks.BLACK_WOOL,
    Blocks.WHITE_CONCRETE,
    Blocks.ORANGE_CONCRETE,
    Blocks.MAGENTA_CONCRETE,
    Blocks.LIGHT_BLUE_CONCRETE,
    Blocks.YELLOW_CONCRETE,
    Blocks.LIME_CONCRETE,
    Blocks.PINK_CONCRETE,
    Blocks.GRAY_CONCRETE,
    Blocks.LIGHT_GRAY_CONCRETE,
    Blocks.CYAN_CONCRETE,
    Blocks.PURPLE_CONCRETE,
    Blocks.BLUE_CONCRETE,
    Blocks.BROWN_CONCRETE,
    Blocks.GREEN_CONCRETE,
    Blocks.RED_CONCRETE,
    Blocks.BLACK_CONCRETE,
    Blocks.WHITE_TERRACOTTA,
    Blocks.ORANGE_TERRACOTTA,
    Blocks.MAGENTA_TERRACOTTA,
    Blocks.LIGHT_BLUE_TERRACOTTA,
    Blocks.YELLOW_TERRACOTTA,
    Blocks.LIME_TERRACOTTA,
    Blocks.PINK_TERRACOTTA,
    Blocks.GRAY_TERRACOTTA,
    Blocks.LIGHT_GRAY_TERRACOTTA,
    Blocks.CYAN_TERRACOTTA,
    Blocks.PURPLE_TERRACOTTA,
    Blocks.BLUE_TERRACOTTA,
    Blocks.BROWN_TERRACOTTA,
    Blocks.GREEN_TERRACOTTA,
    Blocks.RED_TERRACOTTA,
    Blocks.BLACK_TERRACOTTA,
    Blocks.WHITE_STAINED_GLASS,
    Blocks.ORANGE_STAINED_GLASS,
    Blocks.MAGENTA_STAINED_GLASS,
    Blocks.LIGHT_BLUE_STAINED_GLASS,
    Blocks.YELLOW_STAINED_GLASS,
    Blocks.LIME_STAINED_GLASS,
    Blocks.PINK_STAINED_GLASS,
    Blocks.GRAY_STAINED_GLASS,
    Blocks.LIGHT_GRAY_STAINED_GLASS,
    Blocks.CYAN_STAINED_GLASS,
    Blocks.PURPLE_STAINED_GLASS,
    Blocks.BLUE_STAINED_GLASS,
    Blocks.BROWN_STAINED_GLASS,
    Blocks.GREEN_STAINED_GLASS,
    Blocks.RED_STAINED_GLASS,
    Blocks.BLACK_STAINED_GLASS,
    Blocks.CALCITE,
)

fun block(): BlockState = blockList.random(Random(Clock.System.now().nanosecondsOfSecond)).defaultState

typealias CoordinateCallback = (point: Vec3i) -> Unit
typealias CoordinatePredicate = (point: Vec3i) -> Boolean
typealias CenteredCoordinatePredicate = (point: Vec3i, center: Vec3i) -> Boolean