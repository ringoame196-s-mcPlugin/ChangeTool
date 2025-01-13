package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.Data

object DataManager {
    private val dataBaseManager = DataBaseManager(Data.dbFilePath)

    fun setData(playerUUID: String, changeEnduranceValue: Int) {
        val sql = "INSERT INTO ${Data.TABLE_NAME} (${Data.MC_UUID_KEY},${Data.CHANGE_ENDURANCE_VALUE_KEY}) VALUES (?,?) ON CONFLICT(${Data.MC_UUID_KEY}) DO UPDATE SET ${Data.MC_UUID_KEY} = excluded.${Data.MC_UUID_KEY}, ${Data.CHANGE_ENDURANCE_VALUE_KEY} = excluded.${Data.CHANGE_ENDURANCE_VALUE_KEY};"
        dataBaseManager.executeUpdate(sql, mutableListOf(playerUUID, changeEnduranceValue))
        Data.playerData[playerUUID] = changeEnduranceValue
    }

    fun acquisitionChangeEnduranceValue(playerUUID: String): Int {
        return if (Data.playerData[playerUUID] == null) {
            val sql = "SELECT ${Data.CHANGE_ENDURANCE_VALUE_KEY} FROM ${Data.TABLE_NAME} WHERE ${Data.MC_UUID_KEY} = ?;"
            val value = dataBaseManager.acquisitionValue(sql, mutableListOf(playerUUID), Data.CHANGE_ENDURANCE_VALUE_KEY).toString().toIntOrNull() ?: 0
            Data.playerData[playerUUID] = value
            value
        } else {
            Data.playerData[playerUUID] ?: 0
        }
    }
}
