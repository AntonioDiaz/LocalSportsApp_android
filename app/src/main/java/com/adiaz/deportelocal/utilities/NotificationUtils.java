package com.adiaz.deportelocal.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.adiaz.deportelocal.R;
import com.adiaz.deportelocal.activities.CompetitionActivity;
import com.adiaz.deportelocal.activities.FavoriteTeamActivity;
import com.adiaz.deportelocal.entities.Competition;

/**
 * Created by toni on 31/08/2017.
 */

public class NotificationUtils {

	/**/
	private static final int UPDATED_COMPETITION_NOTIFICATION_ID = 1138;
	private static final int UPDATED_COMPETITION_PENDING_INTENT_ID = 3417;
	private static final int UPDATED_TEAM_PENDING_INTENT_ID = 3418;

	public static void remindUpdatedCompetition(Context context, Competition competition) {
		String category = DeporteLocalUtils.getStringResourceByName(context, competition.categoryName());
		String sport = DeporteLocalUtils.getStringResourceByName(context, competition.sportName());
		String notificationTitle = context.getString(R.string.update_competition_notification_title, competition.name());
		String notificationBody = context.getString(R.string.update_competition_notification_body, competition.name(), sport, category);
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
				.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
				.setSmallIcon(R.drawable.ic_notification)
				.setContentTitle(notificationTitle)
				.setContentText(notificationBody)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(notificationBody))
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setContentIntent(contentIntentCompetition(context, competition))
				.setAutoCancel(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
		}
		NotificationManager notificationManager = (NotificationManager)	context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(UPDATED_COMPETITION_NOTIFICATION_ID, notificationBuilder.build());
	}

	public static PendingIntent contentIntentCompetition(Context context, Competition competition) {
		Intent intent = new Intent(context, CompetitionActivity.class);
		intent.putExtra(DeporteLocalConstants.INTENT_COMPETITION, competition);
		PendingIntent pendingIntent = PendingIntent.getActivity(
				context, UPDATED_COMPETITION_PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}

	public static PendingIntent contentIntentTeam(Context context, Competition competition, String teamName) {
		Intent intent = new Intent(context, FavoriteTeamActivity.class);
		intent.putExtra(DeporteLocalConstants.INTENT_TEAM_NAME, teamName);
		intent.putExtra(DeporteLocalConstants.INTENT_ID_COMPETITION_SERVER, competition.serverId());
		PendingIntent pendingIntent = PendingIntent.getActivity (
				context, UPDATED_TEAM_PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}

	public static void remindUpdatedTeam(Context context, Competition competition, String teamName) {
		String category = DeporteLocalUtils.getStringResourceByName(context, competition.categoryName());
		String sport = DeporteLocalUtils.getStringResourceByName(context, competition.sportName());
		String notificationTitle = context.getString(R.string.update_team_notification_title, teamName);
		String notificationBody = context.getString(R.string.update_team_notification_body, teamName, sport, category);
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
				.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
				.setSmallIcon(R.drawable.ic_notification)
				.setContentTitle(notificationTitle)
				.setContentText(notificationBody)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(notificationBody))
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setContentIntent(contentIntentTeam(context, competition, teamName))
				.setAutoCancel(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
		}
		NotificationManager notificationManager = (NotificationManager)	context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(UPDATED_COMPETITION_NOTIFICATION_ID, notificationBuilder.build());
	}
}
