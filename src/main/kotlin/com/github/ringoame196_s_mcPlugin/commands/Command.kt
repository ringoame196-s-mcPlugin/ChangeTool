package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.managers.DataManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Command : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            val message = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return true
        }
        if (args.isEmpty()) return false

        val changeEnduranceValue = args[0].toIntOrNull()
        if (changeEnduranceValue != null) {
            DataManager.setData(sender.uniqueId.toString(), changeEnduranceValue)
            val message = "${ChatColor.GOLD}ツール変更の耐久値を${changeEnduranceValue}に変更しました"
            sender.sendMessage(message)
        } else {
            val message = "${ChatColor.RED}耐久値を入力してください"
            sender.sendMessage(message)
        }
        return true
    }
}
