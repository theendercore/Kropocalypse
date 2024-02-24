package org.teamvoided.kropocalypse.misc

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.MinecraftServer
import kotlin.math.abs

object KropoServerTick {

    private var cooldown = 0
        set(value) {
            field = abs(value)
        }

    fun init() {
        ServerTickEvents.END_SERVER_TICK.register(::tick)
    }

    private fun tick(server: MinecraftServer) {
        if (cooldown > 0) cooldown--
    }

    fun notOnCooldown() = cooldown == 0
    fun addCooldown(x: Int) {
        cooldown += abs(x)
    }
}


