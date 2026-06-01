package com.example.conversor.data

import android.content.Context
import com.example.conversor.model.LinearConversion
import org.json.JSONArray
import org.json.JSONObject

class SharedPreferencesCustomConversionStore(context: Context) : CustomConversionStore {
    private val preferences = context.getSharedPreferences("custom_conversions_store", Context.MODE_PRIVATE)
    private val key = "custom_conversions"

    override fun load(): List<LinearConversion> {
        val raw = preferences.getString(key, "[]") ?: "[]"
        val array = JSONArray(raw)
        val result = mutableListOf<LinearConversion>()

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            result.add(
                LinearConversion(
                    id = item.getString("id"),
                    title = item.getString("title"),
                    fromUnit = item.getString("fromUnit"),
                    toUnit = item.getString("toUnit"),
                    factor = item.getDouble("factor"),
                    offset = item.optDouble("offset", 0.0)
                )
            )
        }

        return result
    }

    override fun save(conversions: List<LinearConversion>) {
        val array = JSONArray()

        conversions.forEach { conversion ->
            val item = JSONObject()
            item.put("id", conversion.id)
            item.put("title", conversion.title)
            item.put("fromUnit", conversion.fromUnit)
            item.put("toUnit", conversion.toUnit)
            item.put("factor", conversion.factor)
            item.put("offset", conversion.offset)
            array.put(item)
        }

        preferences.edit().putString(key, array.toString()).apply()
    }
}