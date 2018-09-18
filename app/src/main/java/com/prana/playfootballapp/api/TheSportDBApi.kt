package com.prana.playfootballapp.api

import android.net.Uri
import com.prana.playfootballapp.BuildConfig


// Kelas Objeck untuk membuat fungsi yang menampung Alamat EndPoint e.q : /search_all_team.php?l=+LEAGUE_NAME

object TheSportDBApi {
    fun getTeams(league: String?): String {
        // return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league

        // Penulisan fungsi di atas bisa juga dilakukan dengan cara yang lebih sederhana,
        // yaitu dengan memanfaatkan fungsi Uri.Builder dari Android. Sehingga, kodenya menjadi seperti berikut:

        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("search_all_teams.php")
                .appendQueryParameter("l", league) // --> huruf L bukan 1
                .build()
                .toString()

        // dengan Uri.Builder diatas kode akan terlihat lebih rapi
    }
}

