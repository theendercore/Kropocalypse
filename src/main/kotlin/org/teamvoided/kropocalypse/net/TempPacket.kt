package org.teamvoided.kropocalypse.net

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.listener.ServerPacketListener
import net.minecraft.network.packet.Packet

class TempPacket(var int: Long = 0) : Packet<ServerPacketListener> {

    constructor(buf: PacketByteBuf) : this(buf.readLong())

    override fun write(buf: PacketByteBuf) {
        buf.writeLong(int)
    }

    override fun apply(serverPingPacketListener: ServerPacketListener) {
        throw Error("This should never run, send help")
    }

}