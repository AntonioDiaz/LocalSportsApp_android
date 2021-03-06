package com.adiaz.deportelocal.utilities.retrofit;

import com.adiaz.deportelocal.utilities.retrofit.entities.competition.CompetitionRestEntity;
import com.adiaz.deportelocal.utilities.retrofit.entities.competitiondetails.CompetitionDetails;
import com.adiaz.deportelocal.utilities.retrofit.entities.issue.Issue;
import com.adiaz.deportelocal.utilities.retrofit.entities.match.MatchRestEntity;
import com.adiaz.deportelocal.utilities.retrofit.entities.sport.SportsRestEntity;
import com.adiaz.deportelocal.utilities.retrofit.entities.town.TownRestEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by toni on 20/04/2017.
 */

public interface DeporteLocalRestApi {

	@GET("/server/towns/")
	Call<List<TownRestEntity>> townsQuery();

	@GET("/server/search_competitions/")
	Call<List<CompetitionRestEntity>> competitionsQuery(@Query("idTown")Long idTown);

	@GET("/server/matches/{idCompetition}")
	Call<List<MatchRestEntity>> matchesQuery(@Path("idCompetition")Long idCompetition);

	@GET("/server/competitiondetails/{idCompetition}")
	Call<CompetitionDetails> competitionDetailsQuery(@Path("idCompetition")Long idCompetition);

	@POST ("/server/issues")
	Call<Long> addIssue(@Body Issue issue);

	@GET ("/server/search_sports/")
    Call<List<SportsRestEntity>> sportsQuery(@Query("idTown") Long idTown);

}
