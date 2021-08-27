package com.example.moviesbymoviedb.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun restoreList(string: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>): String {
        val gson = Gson()
        return gson.toJson(listOfString)
    }

    @TypeConverter
    fun restoreIntList(string: String): List<Int> {
        val list: MutableList<Int> = ArrayList()

        val array: List<String> = string.split(",")

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s.toInt())
            }
        }
        return list
    }

    @TypeConverter
    fun fromListOfInts(listOfInts: List<Int>): String {
        var emptyString = ""
        for (i in listOfInts) {
            emptyString += ",$i"
        }
        return emptyString
    }
}