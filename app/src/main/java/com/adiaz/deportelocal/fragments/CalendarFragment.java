package com.adiaz.deportelocal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiaz.deportelocal.R;
import com.adiaz.deportelocal.activities.CompetitionActivity;
import com.adiaz.deportelocal.adapters.CalendarAdapter;
import com.adiaz.deportelocal.entities.Match;
import com.adiaz.deportelocal.utilities.NonScrollExpandableListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/* Created by toni on 21/03/2017. */

public class CalendarFragment extends Fragment {
	private static final String TAG = CalendarFragment.class.getSimpleName();

	//private static final String TAG = CalendarAdapter.class.getSimpleName();

	@BindView(R.id.elv_jornadas) NonScrollExpandableListView nonScrollExpandableListView;
	@BindView(R.id.tv_empty_list_item) TextView tvEmptyListItem;

	public CalendarFragment() { }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_calendar, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<List<Match>> weeks = CompetitionActivity.mWeeks;
		CalendarAdapter calendarAdapter = new CalendarAdapter(getActivity(), weeks);
		nonScrollExpandableListView.setAdapter(calendarAdapter);
		nonScrollExpandableListView.setEmptyView(tvEmptyListItem);
		calendarAdapter.notifyDataSetChanged();
		registerForContextMenu(nonScrollExpandableListView);
	}

}
