package org.teamvoided.kropocalypse.net

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.teamvoided.kropocalypse.Kropocalypse.id
import org.teamvoided.kropocalypse.misc.KropoServerTick

object KropoNetworking {
    val YES = id("yes")


    fun initC2S() {
        ServerPlayNetworking.registerGlobalReceiver(YES) { server, player, _, packet, _ ->
            if (KropoServerTick.notOnCooldown()) {
                KropoServerTick.addCooldown(20)

//                Util.testCube(player.blockPos, player.serverWorld)
            }
        }
    }

}