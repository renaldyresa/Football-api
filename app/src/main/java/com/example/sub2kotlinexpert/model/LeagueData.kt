package com.example.sub2kotlinexpert.model

import com.example.sub2kotlinexpert.R
import com.example.sub2kotlinexpert.entity.League

object LeagueData {

    private val leagueId = arrayOf(
        4328,
        4335,
        4331,
        4332,
        4334,
        4344

    )

    private val leagueName = arrayOf(
        "English Premier League",
        "Spanish La Liga",
        "German Bundesliga",
        "Italian Serie A",
        "French Ligue 1",
        "Portuguese Primeira Liga"

    )

    private val leagueImage = intArrayOf(
        R.drawable.premier_league_logo,
        R.drawable.la_liga,
        R.drawable.bundesliga_logo,
        R.drawable.serie_a,
        R.drawable.league_1,
        R.drawable.liga_portugal_logo
    )

    private val leagueDesc = arrayOf(
        "The Premier League, often referred to as the English Premier League or the EPL outside England, is the top level of the English football league system. Contested by 20 clubs, it operates on a system of promotion and relegation with the English Football League (EFL). Seasons run from August to May with each team playing 38 matches (playing all 19 other teams both home and away). Most games are played on Saturday and Sunday afternoons.",
        "The Campeonato Nacional de Liga de Primera División, commonly known as La Liga (LaLiga Santander for sponsorship reasons with Santander), is the men's top professional football division of the Spanish football league system. Administered by the Liga Nacional de Fútbol Profesional (English: National Professional Football League), also known as the Liga de Fútbol Profesional, and is contested by 20 teams, with the three lowest-placed teams at the end of each season relegated to the Segunda División and replaced by the top two teams and a play-off winner in that division.",
        "The Bundesliga  is a professional association football league in Germany and the football league with the highest average stadium attendance worldwide. At the top of the German football league system, the Bundesliga is Germany's primary football competition. The Bundesliga comprises 18 teams and operates on a system of promotion and relegation with the 2. Bundesliga.",
        "Serie A, also called Serie A TIM due to sponsorship by TIM, is a professional league competition for football clubs located at the top of the Italian football league system and the winner is awarded the Scudetto and the Coppa Campioni d'Italia. It has been operating as a round-robin tournament for over ninety years since the 1929–30 season. It had been organized by the Direttorio Divisioni Superiori until 1943 and the Lega Calcio until 2010, when the Lega Serie A was created for the 2010–11 season. Serie A is regarded as one of the best football leagues in the world and it is often depicted as the most tactical national league.",
        "Ligue 1, also called Ligue 1 Conforama for sponsorship reasons, is a French professional league for men's association football clubs. At the top of the French football league system, it is the country's primary football competition. Administrated by the Ligue de Football Professionnel, Ligue 1 is contested by 20 clubs and operates on a system of promotion and relegation with Ligue 2.",
        "The Primeira Liga, also known as Liga NOS for sponsorship reasons, is the top professional association football division of the Portuguese football league system. It is organised and supervised by the Liga Portuguesa de Futebol Profissional, also known as Liga Portugal. As of the 2014–15 season, the Primeira Liga is contested by 18 teams, with the two lowest placed teams relegated to the LigaPro and replaced by the top-two non-reserve teams from this division, except in the 2018–19 season in which the three lowest placed teams were relegated to the LigaPro due to the integration in the Primeira Liga of Gil Vicente in the following season. The Portuguese Football Federation appealed to proceed with this integration as soon as possible."
   )

    val listData: ArrayList<League>
        get() {
            val list = arrayListOf<League>()
            for (position in leagueName.indices){
                val league = League()
                league.name = leagueName[position]
                league.id = leagueId[position]
                league.desc = leagueDesc[position]
                league.image = leagueImage[position]
                list.add(league)
            }
            return list
        }



}