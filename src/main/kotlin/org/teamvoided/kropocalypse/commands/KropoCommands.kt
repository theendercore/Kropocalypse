package org.teamvoided.kropocalypse.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import org.teamvoided.kropocalypse.Kropocalypse.log
import org.teamvoided.kropocalypse.commands.args.ShapeArgumentType
import org.teamvoided.kropocalypse.commands.args.ShapeArgumentType.shapeArg
import org.teamvoided.kropocalypse.util.CoordinateCallback
import org.teamvoided.kropocalypse.util.Shapes

object KropoCommands {
    fun init() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _/*c*/, _/*env*/ ->
            shapeCommand(dispatcher)
        }
    }


    fun shapeCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
        val shapeNode = literal("shape").build()
        dispatcher.root.addChild(shapeNode)

        val shapeArg = shapeArg("shape").build()
        shapeNode.addChild(shapeArg)

        val sizeArg = argument("size", IntegerArgumentType.integer(1)).executes(::makeShape).build()
        shapeArg.addChild(sizeArg)
    }

    private fun makeShape(c: CommandContext<ServerCommandSource>): Int {
        val src = c.source

        val world = src.world
        val player = src.player ?: return 0
        val pos = player.blockPos

        val shape = ShapeArgumentType.getShape(c, "shape")
        val size = IntegerArgumentType.getInteger(c, "size")
        var blocks = 0

        theList[shape]?.let { it(pos, world, size) { blocks++ } }
        val str = "Placed $blocks blocks!"
        player.sendSystemMessage(Text.of(str), true)
        log.info(str)
        return 1
    }

    private val theList = mapOf(
        Pair(ShapeArgumentType.Shape.CUBE, Shapes::testCube),
        Pair(ShapeArgumentType.Shape.CUBE_HOLLOW, Shapes::testCubeHollow),
        Pair(ShapeArgumentType.Shape.CUBOID, Shapes::testCuboid),
        Pair(ShapeArgumentType.Shape.CUBOID_HOLLOW, Shapes::testCuboidHollow),

        Pair(ShapeArgumentType.Shape.SPHERE, Shapes::testSphere),
        Pair(ShapeArgumentType.Shape.SPHERE_HOLLOW, Shapes::testSphereHollow),
        Pair(ShapeArgumentType.Shape.CYLINDER, Shapes::testCylinder),
        Pair(ShapeArgumentType.Shape.CYLINDER_HOLLOW, Shapes::testCylinderHollow),

        Pair(ShapeArgumentType.Shape.PYRAMID, ::notDone),
        Pair(ShapeArgumentType.Shape.PYRAMID_HOLLOW, ::notDone),
        Pair(ShapeArgumentType.Shape.PRISM, ::notDone),
        Pair(ShapeArgumentType.Shape.PRISM_HOLLOW, ::notDone)

    )

    @Suppress("UNUSED_PARAMETER")
    private fun notDone(a: BlockPos, b: ServerWorld, c: Int, d: CoordinateCallback) = println("Not Finished")
}
