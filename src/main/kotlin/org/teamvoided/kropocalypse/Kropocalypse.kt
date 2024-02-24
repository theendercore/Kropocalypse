package org.teamvoided.kropocalypse

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.kropocalypse.commands.KropoCommands
import org.teamvoided.kropocalypse.misc.KeyBinds
import org.teamvoided.kropocalypse.misc.KropoServerTick
import org.teamvoided.kropocalypse.net.KropoNetworking

@Suppress("unused")
object Kropocalypse {
    const val MODID = "kropocalypse"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(Kropocalypse::class.simpleName)

    fun commonInit() {
        log.info("Kropocalypse from Common")
        KropoCommands.init()
        KropoNetworking.initC2S()
        KropoServerTick.init()
    }

    fun clientInit() {
        log.info("Kropocalypse from Client")
        KeyBinds.init()
    }

    fun id(path: String) = Identifier(MODID, path)
}
