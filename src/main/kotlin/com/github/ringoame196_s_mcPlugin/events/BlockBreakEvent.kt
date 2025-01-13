package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.managers.DataManager
import com.github.ringoame196_s_mcPlugin.managers.ToolManager
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakEvent : Listener {
    private val toolManager = ToolManager()

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val player = e.player
        val uuid = player.uniqueId.toString()
        val inventory = player.inventory
        val item = inventory.itemInMainHand
        val slot = inventory.heldItemSlot

        if (player.isSneaking) return
        if (!toolManager.isTool(item)) return
        if (!toolManager.haveEnchant(item)) return

        val damage = toolManager.acquisitionEnduranceValue(item) ?: return
        val changeEnduranceValue = DataManager.acquisitionChangeEnduranceValue(uuid)
        if (changeEnduranceValue == 0) return
        if (damage > changeEnduranceValue) return

        val changeItem = toolManager.changeItem(player, item, slot)

        // 通知
        val title: String
        val message: String
        val sound: Sound
        if (changeItem != null) {
            title = "${ChatColor.YELLOW}アイテムを切り替えました"
            val changeItemName = if (!changeItem.itemMeta?.displayName.isNullOrBlank()) {
                changeItem.itemMeta?.displayName
            } else {
                changeItem.type.name.replace("_", " ").lowercase()
            }
            message = "${ChatColor.GOLD}${changeItemName}${ChatColor.GOLD}に切り替えました"
            sound = Sound.BLOCK_ANVIL_USE
        } else {
            e.isCancelled = true
            title = "${ChatColor.RED}切り替え失敗"
            message = "${ChatColor.RED}アイテム保護のため破壊をブロックしました\n" +
                "${ChatColor.YELLOW}続行するにはシフトして破壊してください"
            sound = Sound.BLOCK_CHEST_LOCKED
        }
        player.sendTitle("", title)
        player.sendMessage(message)
        player.playSound(player, sound, 1f, 1f)
    }
}
