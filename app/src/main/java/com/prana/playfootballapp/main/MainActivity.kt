package com.prana.playfootballapp.main

import android.R
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.prana.playfootballapp.R.array.league
import com.prana.playfootballapp.R.attr.colorAccent
import com.prana.playfootballapp.api.ApiRepository
import com.prana.playfootballapp.model.Team
import com.prana.playfootballapp.utils.invisible
import com.prana.playfootballapp.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(), MainView {

    // Menerapkan Presenter dan adapter
    private var teams               : MutableList<Team> = mutableListOf()
    private lateinit var presenter  : MainPresenter
    private lateinit var adapter   : MainAdapter


    // Deklarasi beberapa view sebagai variable
    // lateinit and lazy are important property initialization feature.
    // We must know when to use which property initialization.
    // Reference : https://blog.mindorks.com/learn-kotlin-lateinit-vs-lazy
    //
    // lateinit digunakan untuk menginisialisasi nilai variabel sebelum Anda mengaksesnya.
    private lateinit var listTeam       : RecyclerView
    private lateinit var progressBar    : ProgressBar
    private lateinit var swipeRefresh   : SwipeRefreshLayout
    private lateinit var spinner        : Spinner

    // Deklarasi variable nama liga, untuk digunakan pada spinner
    private lateinit var leagueName : String


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) // --> diclose karena menggunakan anko

        // ============================== ANKO LAYOUT ======================================
        // Layout lineraLayout harus di simpen diatas/ baris pertama pada onCreate agar " lateinit var listTeam " terbaca tidak error
        linearLayout {
            lparams(width= matchParent, height = wrapContent)
            orientation     = LinearLayout.VERTICAL
            topPadding      = dip(16)
            leftPadding     = dip(16)
            rightPadding    = dip(16)

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeColors(colorAccent,
                        R.color.holo_green_light,
                        R.color.holo_orange_light,
                        R.color.holo_red_light)

                relativeLayout{
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView{
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar{
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }


        // Deklarasi untuk Adapter
        adapter = MainAdapter(teams)
        listTeam.adapter = adapter

        // Deklarasi untuk Presenter
        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        // Setting SpinnerAdapter
        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter


        // Spinner onClick
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {  }

        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

    }

//    class MainActivityUI : AnkoComponent<MainActivity>{
//        override fun createView(ui: AnkoContext<MainActivity>): View {
//
//
//        }
//
//    }

    // Dari Interface MainView untuk mensupport Fungsi getListTeam(),
    // menerapkan interface MainView, ALT+Enter pada MainActivity()
    override fun showLoading() {
        progressBar.visible() // dari class Utils.kt
    }

    override fun hideLoading() {
        progressBar.invisible() // dari class Utils.kt
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
