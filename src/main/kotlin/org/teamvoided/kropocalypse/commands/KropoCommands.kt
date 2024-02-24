package org.teamvoided.kropocalypse.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandBuildContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.teamvoided.kropocalypse.util.Shapes
import org.teamvoided.kropocalypse.commands.args.ShapeArgumentType
import org.teamvoided.kropocalypse.commands.args.ShapeArgumentType.shapeArg

object KropoCommands {
    fun init() {
        CommandRegistrationCallback.EVENT.register(::register)
    }

    private fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>,
        context: CommandBuildContext, env: CommandManager.RegistrationEnvironment
    ) {
        shapeCommand(dispatcher)
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


        when (shape) {
            ShapeArgumentType.Shape.SQUARE -> Shapes.testCube(pos, world, size)
            ShapeArgumentType.Shape.SQUARE_HOLLOW -> Shapes.testCubeHollow(pos, world, size)
            ShapeArgumentType.Shape.CUBOID -> Shapes.testCuboid(pos, world, size)
            ShapeArgumentType.Shape.CUBOID_HOLLOW -> Shapes.testCuboidHollow(pos, world, size)
            ShapeArgumentType.Shape.SPHERE -> Shapes.testSphere(pos, world, size)
            ShapeArgumentType.Shape.SPHERE_HOLLOW -> println("Ohio")
            ShapeArgumentType.Shape.CYLINDER -> println("Ohio")
            ShapeArgumentType.Shape.CYLINDER_HOLLOW -> println("Ohio")
            ShapeArgumentType.Shape.PYRAMID -> println("Ohio")
            ShapeArgumentType.Shape.PYRAMID_HOLLOW -> println("Ohio")
            ShapeArgumentType.Shape.PRISM -> println("Ohio")
            ShapeArgumentType.Shape.PRISM_HOLLOW -> println("Ohio")
        }

        return 1
    }

}
