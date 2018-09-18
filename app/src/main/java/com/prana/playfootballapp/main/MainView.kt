package com.prana.playfootballapp.main

import com.prana.playfootballapp.model.Team


// Buat Interface untuk menampilkan, Loading dan menampilkan data list

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}