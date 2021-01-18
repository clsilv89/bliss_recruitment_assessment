package com.caio.modulo1.data.api

import com.caio.modulo1.data.model.Emoji
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONObject
import java.lang.reflect.Type


class EmojiConverterFactory : JsonDeserializer<List<Emoji>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ArrayList<Emoji> {
        return returnJSONArrayList(json, Emoji::class.java, context)
    }

    private fun returnJSONArrayList(
        json: JsonElement?, type: Type?, ctx:
        JsonDeserializationContext?
    ): ArrayList<Emoji> {
        val emojiList = arrayListOf<Emoji>()
        json?.let { json ->
            val `object` = JSONObject(json.toString())
            val it = `object`.keys()
            while (it.hasNext()) {
                val key = it.next()
                try {
                    val object1 = `object`.getString(key)
                    emojiList.add(
                        Emoji(
                            key,
                            object1
                        )
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        return emojiList
    }
}