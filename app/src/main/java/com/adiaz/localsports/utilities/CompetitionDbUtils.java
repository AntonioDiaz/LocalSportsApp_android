package com.adiaz.localsports.utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.adiaz.localsports.entities.Competition;
import com.adiaz.localsports.sync.retrofit.callbacks.CompetitionDetailsCallbak;
import com.adiaz.localsports.sync.retrofit.LocalSportsRestApi;
import com.adiaz.localsports.sync.retrofit.entities.competitiondetails.CompetitionDetails;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.adiaz.localsports.database.LocalSportsDbContract.CompetitionsEntry;

/**
 * Created by toni on 01/09/2017.
 */

public class CompetitionDbUtils {

	public static final Competition queryCompetition (ContentResolver contentResolver, Long idCompetition) {
		Competition competition = null;
		Uri uri = CompetitionsEntry.buildCompetitionUriWithServerId(idCompetition);
		Cursor cursor = contentResolver.query(uri, CompetitionsEntry.PROJECTION, null, null, null);
		try {
			if (cursor.moveToNext()) {
				competition = CompetitionsEntry.initEntity(cursor);
			}
		} finally {
			cursor.close();
		}
		return competition;
	}

	public static final boolean itIsNecesaryUpdate(ContentResolver contentResolver, Long idCompetition) {
		Uri uri = CompetitionsEntry.buildCompetitionUriWithServerId(idCompetition);
		Cursor cursor = contentResolver.query(uri, CompetitionsEntry.PROJECTION, null, null, null);
		cursor.moveToFirst();
		return CompetitionsEntry.initEntity(cursor).isDirty();
	}

	public static final void updateCompetition (Context context, CompetitionDetailsCallbak.OnFinishLoad onFinishLoad, Long idCompetition) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(LocalSportsConstants.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		LocalSportsRestApi localSportsRestApi = retrofit.create(LocalSportsRestApi.class);
		Call<CompetitionDetails> listCall = localSportsRestApi.competitionDetailsQuery(idCompetition);
		CompetitionDetailsCallbak competitionDetailsCallbak = new CompetitionDetailsCallbak(context, idCompetition, onFinishLoad);
		listCall.enqueue(competitionDetailsCallbak);
	}

}
