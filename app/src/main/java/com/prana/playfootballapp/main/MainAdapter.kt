package com.prana.playfootballapp.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.prana.playfootballapp.R
import com.prana.playfootballapp.R.id.team_badge
import com.prana.playfootballapp.R.id.team_name
import com.prana.playfootballapp.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

/*
langkah membuat adapter

1. buat class : class MainAdapter (private val teams: List<Team>) {}

2. buat class untuk ViewHolder : class TeamViewHolder() --> Mewarisi kelas RecyclerView.ViewHolder.
    kelas TeamViewHolder() akan dijadikan parameter untuk kelas RecyclerView.Adapter yang kita wariskan ke kelas MainAdapter.
    Tambahkan juga -> ": RecyclerView.Adapter<TeamViewHolder>()" pada  class "MainAdapter" senghingga menjadi :
    " class MainAdapter (private val teams: List<Team>) : RecyclerView.Adapter<TeamViewHolder>() {} "

3. Menerapkan semua anggota dari kelas RecyclerView.Adapter (dengan ALT+ Enter pada MainAdapter)

4. Membuat Anko Layout Team UI

5. Deklarasi View yang dibutuhkan pada TeamViewHolder

6. Definisikan teams.size pada method getItemCount() : " override fun getItemCount(): Int = teams.size "

7. karena sudah definis teams.size sekarang --> ALT + Enter pada " view.find(team_badge) & view.find(team_name) " jadinya sudah terbaca value team_badge & team_name nya:
    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

8. pada method onBindViewHolder tambahkan kode berikut untuk menampilkan data pada posisi yang telah ditentukan : " holder.bindItem(teams[position]) "

9. Pada Menambahkan onCreateViewHolder() tambahkan " return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent))) "
 */

class MainAdapter (private val teams: List<Team>)
    : RecyclerView.Adapter<TeamViewHolder>() { // " : RecyclerView.Adapter<TeamViewHolder>" --> artinya Mewarisi kelas dengan Parameter TeamViewHOlder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size // "=" pengganti { return teams.size }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        holder.bindItem(teams[position])
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Deklarasikan semua view yang dibutuhkan dengan menambahkan variabel berikut di dalam kelas TeamViewHolder:

    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(teams: Team) {

//        Setelah itu kita bisa mengumpulkan objek dari data class ke masing-masing view tersebut:
            Picasso.get().load(teams.teamBadge).into(teamBadge)
            teamName.text = teams.teamName
    }
}

// Class Anko Layout
class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge // --> atribut id akan ditambahkan ke berkas ids.xml:
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView{
                    id = R.id.team_name // --> atribut id akan ditambahkan ke berkas ids.xml:
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }
            }
        }
    }


}
