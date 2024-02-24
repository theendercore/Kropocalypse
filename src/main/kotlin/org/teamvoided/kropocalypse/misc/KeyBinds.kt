package org.teamvoided.kropocalypse.misc

import com.mojang.blaze3d.platform.InputUtil
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.option.KeyBind
import net.minecraft.network.PacketByteBuf
import org.teamvoided.kropocalypse.net.KropoNetworking

object KeyBinds {

    private var key: KeyBind = KeyBindingHelper.registerKeyBinding(
        KeyBind("bomb.time", InputUtil.KEY_R_CODE, "Bomb Key")
    )

    fun init() {
        ClientTickEvents.END_CLIENT_TICK.register {
            if (key.wasPressed()) ClientPlayNetworking.send(KropoNetworking.YES, PacketByteBuf(Unpooled.buffer()))
        }
    }
}