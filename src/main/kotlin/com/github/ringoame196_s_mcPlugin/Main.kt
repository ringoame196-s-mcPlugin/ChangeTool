package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.commands.TabCompleter
import com.github.ringoame196_s_mcPlugin.events.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()
        saveResource("playerData.db", false)
        val dbFilePath = "${plugin.dataFolder.path}/playerData.db"
        server.pluginManager.registerEvents(BlockBreakEvent(), plugin)
        Data.dbFilePath = dbFilePath
        val command = getCommand("changetool")
        command!!.setExecutor(Command())
        command.tabCompleter = TabCompleter()
    }
}
