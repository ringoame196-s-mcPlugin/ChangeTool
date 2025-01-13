package com.github.ringoame196_s_mcPlugin

object Data {
    val playerData = mutableMapOf<String, Int>()

    var dbFilePath = ""

    const val TABLE_NAME = "playerData"
    const val MC_UUID_KEY = "mcUUID"
    const val CHANGE_ENDURANCE_VALUE_KEY = "changeEnduranceValue"
}
