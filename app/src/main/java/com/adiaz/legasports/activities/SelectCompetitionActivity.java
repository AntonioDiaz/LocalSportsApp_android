package com.adiaz.legasports.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.adiaz.legasports.R;
import com.adiaz.legasports.adapters.CompetitionsAdapter;
import com.adiaz.legasports.database.LegaSportsDbContract;
import com.adiaz.legasports.utilities.LegaSportsConstants;
import com.adiaz.legasports.utilities.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.adiaz.legasports.database.LegaSportsDbContract.CompetitionsEntry;

public class SelectCompetitionActivity extends AppCompatActivity implements CompetitionsAdapter.ListItemClickListener {

	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
	@BindView(R.id.rv_competitions) RecyclerView recyclerView;
	@BindView(R.id.tv_title) TextView tvTitle;

	private static final String TAG = SelectCompetitionActivity.class.getSimpleName();
	private String sportTag;
	private String sportTitle;
	private Cursor mCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_competition);
		ButterKnife.bind(this);
		sportTag = getIntent().getStringExtra(LegaSportsConstants.INTENT_SPORT_TAG);
		sportTitle = Utils.getStringResourceByName(this, sportTag);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		collapsingToolbar.setTitle(sportTitle);
		// TODO: 25/04/2017 should get the competitions from the contentprovider.
		Uri uriWithSport = LegaSportsDbContract.CompetitionsEntry.buildCompetitionsUriWithSports(sportTag);
		mCursor = getContentResolver().query(uriWithSport, CompetitionsEntry.PROJECTION, null, null, CompetitionsEntry.COLUMN_CATEGORY_ORDER);
		CompetitionsAdapter competitionsAdapter = new CompetitionsAdapter(this, this);
		competitionsAdapter.setCompetitions(mCursor);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(competitionsAdapter);
		recyclerView.setNestedScrollingEnabled(false);
		SharedPreferences preferences = getDefaultSharedPreferences(this);
		String townSelect = preferences.getString(LegaSportsConstants.TOWN_SELECTED_NAME, null);
		tvTitle.setText(townSelect + " - " + getString(R.string.app_name));
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}

	@Override
	public void onListItemClick(int clickedItemIndex) {
		mCursor.moveToPosition(clickedItemIndex);
		Intent intent = new Intent(this, CompetitionActivity.class);
		intent.putExtra(LegaSportsConstants.INTENT_ID_COMPETITION_SERVER, mCursor.getString(CompetitionsEntry.INDEX_ID_SERVER));
		intent.putExtra(LegaSportsConstants.INTENT_SPORT_TAG, mCursor.getString(CompetitionsEntry.INDEX_SPORT));
		intent.putExtra(LegaSportsConstants.INTENT_CATEGORY_TAG, mCursor.getString(CompetitionsEntry.INDEX_CATEGORY));
		intent.putExtra(LegaSportsConstants.INTENT_COMPETITION_NAME, mCursor.getString(CompetitionsEntry.INDEX_NAME));
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		mCursor.close();
		super.onDestroy();
	}
}
