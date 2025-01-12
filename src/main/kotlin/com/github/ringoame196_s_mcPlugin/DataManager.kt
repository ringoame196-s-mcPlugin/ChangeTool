package com.github.ringoame196_s_mcPlugin

object DataManager {
    fun setData(playerUUID: String, changeEnduranceValue: Int) {
        Data.playerData[playerUUID] = changeEnduranceValue
    }

    fun acquisitionChangeEnduranceValue(playerUUID: String) = 15 // Data.playerData[playerUUID] ?:0
}
