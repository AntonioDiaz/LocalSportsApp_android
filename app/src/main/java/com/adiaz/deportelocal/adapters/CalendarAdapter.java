package com.adiaz.deportelocal.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.adiaz.deportelocal.R;
import com.adiaz.deportelocal.entities.Match;
import com.adiaz.deportelocal.utilities.DeporteLocalConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Created by toni on 23/03/2017. */

public class CalendarAdapter extends BaseExpandableListAdapter {

    private static final String TAG = CalendarAdapter.class.getSimpleName();
    private Context mContext;
    private List<List<Match>> weeksList;
    private List<String> weeksNames;

    @Nullable
    @BindView(R.id.childItem_teamlocal)
    TextView tvLocalTeam;
    @Nullable
    @BindView(R.id.childItem_teamvisitor)
    TextView tvVisitorTeam;
    @Nullable
    @BindView(R.id.childItem_date)
    TextView date;
    @Nullable
    @BindView(R.id.childItem_place)
    TextView place;
    @Nullable
    @BindView(R.id.childItem_result)
    TextView result;
    @Nullable
    @BindView(R.id.heading)
    TextView textViewHeading;

    public CalendarAdapter(Context mContext, List<List<Match>> weeksList) {
        this.mContext = mContext;
        this.weeksList = weeksList;
        this.weeksNames = new ArrayList<>();
        for (List<Match> matches : weeksList) {
            String weekName = "";
            if (matches.size()>0 && !TextUtils.isEmpty(matches.get(0).weekName())) {
                weekName = matches.get(0).weekName();
            }
            this.weeksNames.add(weekName);
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Match> matches = weeksList.get(groupPosition);
        Match match = matches.get(childPosition);
        return match;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        Match match = (Match) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listitem_child_calendar, null);
        }
        ButterKnife.bind(this, view);
        view.setTag(match);
        String teamLocal = match.teamLocal();
        String teamVisitor = match.teamVisitor();
        if (teamLocal.equals(DeporteLocalConstants.UNDEFINDED_FIELD) && teamVisitor.equals(DeporteLocalConstants.UNDEFINDED_FIELD)) {
            tvLocalTeam.setText(mContext.getString(R.string.undefined_match));
            tvVisitorTeam.setText("");
        } else {
            if (teamLocal.equals(DeporteLocalConstants.UNDEFINDED_FIELD)) {
                teamLocal = mContext.getString(R.string.rest_team);
            }
            tvLocalTeam.setText(teamLocal);
            if (teamVisitor.equals(DeporteLocalConstants.UNDEFINDED_FIELD)) {
                teamVisitor = mContext.getString(R.string.rest_team);
            }
            tvVisitorTeam.setText(teamVisitor);
        }
        date.setText(match.obtainDateStr(mContext));
        place.setText(match.obtainCenterNameFull(mContext));
        String strResult = "";
        switch (match.state()) {
            case DeporteLocalConstants.STATE_PENDING:
                strResult = mContext.getString(R.string.match_pending);
                break;
            case DeporteLocalConstants.STATE_CANCELED:
                strResult = mContext.getString(R.string.match_cancel);
                break;
            case DeporteLocalConstants.STATE_RESTING:
                strResult = DeporteLocalConstants.UNDEFINDED_FIELD;
                break;
            case DeporteLocalConstants.STATE_PLAYED:
                strResult = String.format("%d - %d", match.scoreLocal(), match.scoreVisitor());
                break;
        }
        result.setText(strResult);
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.weeksList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return weeksList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return weeksList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listitem_header_calendar, null);
        }
        ButterKnife.bind(this, view);
        TextView textViewHeading = (TextView) view.findViewById(R.id.heading);
        String weekName = weeksNames.get(groupPosition);
        if (TextUtils.isEmpty(weekName)) {
            textViewHeading.setText(mContext.getString(R.string.jornada_header, (groupPosition + 1)));
        } else {
            textViewHeading.setText(mContext.getString(R.string.jornada_header_calendar, (groupPosition + 1), weekName));
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}