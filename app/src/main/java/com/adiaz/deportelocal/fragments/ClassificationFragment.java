package com.adiaz.deportelocal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adiaz.deportelocal.R;
import com.adiaz.deportelocal.activities.CompetitionActivity;
import com.adiaz.deportelocal.adapters.ClassificationRecyclerViewAdapter;
import com.adiaz.deportelocal.entities.Classification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Created by toni on 21/03/2017. */

public class ClassificationFragment extends Fragment {

	@BindView(R.id.rv_classification) RecyclerView recyclerView;
	@BindView(R.id.tv_empty_list_item) TextView tvEmptyListItem;
	@BindView(R.id.ll_classification) LinearLayout llClassification;
	@BindView(R.id.tv_sanctions) TextView tvSanctions;

	public ClassificationFragment() {}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_classification, container, false);
		ButterKnife.bind(this, view);
		return view;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<Classification> classificationList = CompetitionActivity.mClassificationList;
		ClassificationRecyclerViewAdapter adapter = new ClassificationRecyclerViewAdapter(getActivity(), classificationList);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		if (classificationList.isEmpty()) {
			llClassification.setVisibility(View.GONE);
			tvEmptyListItem.setVisibility(View.VISIBLE);
		} else {
			llClassification.setVisibility(View.VISIBLE);
			tvEmptyListItem.setVisibility(View.GONE);
		}
		boolean sanctionsFound = false;
		for (Classification classification : classificationList) {
			if (classification.getSanctions()>0) {
				String sanctionStr = getActivity().getString(
						R.string.sanctions_team, classification.getTeam(), classification.getSanctions().toString());
				tvSanctions.append("\n" + sanctionStr);
				sanctionsFound = true;
			}
		}
		if (!sanctionsFound) {
			tvSanctions.setText(getString(R.string.sanctions_empty));
		}
	}
}
