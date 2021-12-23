package com.ndzindo.tmdb.cache

import android.content.Context

class MoviePreferences {

    companion object {
        private const val LIST_TYPE = "list_type"
        private const val DEFAULT_LIST_TYPE = "popular"

        fun getPreferredListType(context: Context?): String {
            val prefs = context?.getSharedPreferences(
                "com.ndzindo.tmdb_preferences",
                Context.MODE_PRIVATE
            )

            return prefs?.getString(LIST_TYPE, DEFAULT_LIST_TYPE) ?: DEFAULT_LIST_TYPE
        }

        fun setPreferredListType(context: Context?, listType: String) {
            val prefsEditor = context?.getSharedPreferences(
                "com.ndzindo.tmdb_preferences",
                Context.MODE_PRIVATE
            )?.edit()

            prefsEditor?.putString(LIST_TYPE, listType)
            prefsEditor?.apply()
        }

        fun resetMovieListPreferences(context: Context) {
            setPreferredListType(context, DEFAULT_LIST_TYPE)
        }
    }
}