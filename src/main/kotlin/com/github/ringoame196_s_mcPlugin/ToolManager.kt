package com.github.ringoame196_s_mcPlugin

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

class ToolManager {
    fun isTool(item: ItemStack): Boolean {
        if (!item.hasItemMeta()) return false
        val meta = item.itemMeta ?: return false
        return meta is Damageable
    }

    fun haveEnchant(item: ItemStack): Boolean {
        return item.enchantments.isNotEmpty()
    }

    fun acquisitionEnduranceValue(item: ItemStack): Int? {
        if (!isTool(item)) return null
        val meta = item.itemMeta ?: return null
        meta as? Damageable ?: return null
        val damage = meta.damage
        return item.type.maxDurability - damage
    }

    fun changeItem(player: Player, item: ItemStack, useSlot: Int): ItemStack? {
        val inventory = player.inventory
        val slot = acquisitionChangeItemSlot(player, item.type) ?: return null

        val changeItem = inventory.getItem(slot)?.clone()
        val useItem = inventory.getItem(useSlot)?.clone()
        inventory.setItem(slot, useItem)
        inventory.setItem(useSlot, changeItem)

        return changeItem
    }

    private fun acquisitionChangeItemSlot(player: Player, material: Material): Int? {
        val inventory = player.inventory
        val changeEnduranceValue = DataManager.acquisitionChangeEnduranceValue(player.uniqueId.toString())

        for (i in 9 until inventory.size) {
            val item = inventory.getItem(i) ?: continue
            if (item.type != material) continue
            val damage = acquisitionEnduranceValue(item) ?: continue
            if (damage <= changeEnduranceValue) continue
            return i
        }
        return null
    }
}
