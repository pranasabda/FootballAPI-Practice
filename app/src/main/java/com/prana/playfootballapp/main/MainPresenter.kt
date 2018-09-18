package com.prana.playfootballapp.main

import android.util.Log
import com.google.gson.Gson
import com.prana.playfootballapp.api.ApiRepository
import com.prana.playfootballapp.api.TheSportDBApi
import com.prana.playfootballapp.model.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

// Presenter sebagai jembatan untuk view dan model

class MainPresenter(private val view: MainActivity,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    /*
    TODO:
    Di dalam presenter tersebut, kita perlu menambahkan beberapa behaviour, antara lain:

    Ketika presenter sedang menunggu respon, ProgressBar akan ditampilkan.

    Ketika respon berhasil didapatkan, ProgressBar akan hilang dan data akan ditampilkan ke dalam view.
    */

//    Fungsi untuk menerapkan behavior  diatas
    fun getTeamList(league: String?){
        view.showLoading()

            doAsync {
                val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java
                )

                Log.e("Debug", "data log: " + data)
                uiThread {
                    view.hideLoading()
                    view.showTeamList(data.teams)
                }
            }
    }

    /*
    Ketika fungsi di atas ( getTeamList() ) dijalankan, otomatis juga akan memanggil fungsi showLoading dan doAsync.
    Fungsi doAsync dari Anko bisa digunakan untuk menjalankan asynchronous task.
    Ia akan memanggil fungsi doRequest, dan jika berhasil data akan ditampilkan oleh uiThread.
    * */
}
