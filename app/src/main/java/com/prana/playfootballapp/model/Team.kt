package com.prana.playfootballapp.model

import com.google.gson.annotations.SerializedName

// data class -> berisi data object (data obejct json yang di perlukan)

/* Pada data class ini, setiap objek diberi anotasi @SerializedName
Parameter (nilai) dari anotasi tersebut adalah nama yang akan digunakan pada saat mengkomersialkan dan deserializing objek.
Misalnya, field teamName direpresentasikan sebagai strTeam di JSON.
*/
data class Team(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
    )