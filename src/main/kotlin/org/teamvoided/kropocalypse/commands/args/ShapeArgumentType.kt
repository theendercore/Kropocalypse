package org.teamvoided.kropocalypse.commands.args

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import java.util.concurrent.CompletableFuture

object ShapeArgumentType {
    fun shapeArg(name: String): RequiredArgumentBuilder<ServerCommandSource, String> {
        return CommandManager.argument(name, StringArgumentType.string()).suggests(::listSuggestions)
    }

    @Throws(CommandSyntaxException::class)
    fun getShape(context: CommandContext<ServerCommandSource>, name: String): Shape {
        val string = context.getArgument(name, String::class.java)
        try {
            return Shape.valueOf(string)
        } catch (e: IllegalArgumentException) {
            throw UNKNOWN_DISPLAY_LOCATION_TYPE_EXCEPTION.create(string)
        }
    }

    private fun <S> listSuggestions(c: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return if (c.source is CommandSource)
            CommandSource.suggestMatching(Shape.entries.map { it.toString() }, builder)
        else Suggestions.empty()
    }

    private val UNKNOWN_DISPLAY_LOCATION_TYPE_EXCEPTION =
        DynamicCommandExceptionType { Text.translatable("Shape %s not found!", it) }

    enum class Shape {
        SQUARE, SQUARE_HOLLOW,
        CUBOID, CUBOID_HOLLOW,
        SPHERE, SPHERE_HOLLOW,
        CYLINDER, CYLINDER_HOLLOW,
        PYRAMID, PYRAMID_HOLLOW,
        PRISM, PRISM_HOLLOW
    }
}