package com.segment.analytics;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

/**
 * An {@link AsyncTask} that fetches the advertising info and attaches it to the given {@link
 * AnalyticsContext} instance.
 */
class GetAdvertisingIdTask extends AsyncTask<Context, Void, AdvertisingIdClient.Info> {
  final AnalyticsContext analyticsContext;

  GetAdvertisingIdTask(AnalyticsContext analyticsContext) {
    this.analyticsContext = analyticsContext;
  }

  @Override protected AdvertisingIdClient.Info doInBackground(Context... contexts) {
    try {
      return AdvertisingIdClient.getAdvertisingIdInfo(contexts[0]);
    } catch (Exception ignored) {
      return null;
    }
  }

  @Override protected void onPostExecute(AdvertisingIdClient.Info info) {
    super.onPostExecute(info);
    if (info != null) {
      analyticsContext.device().putAdvertisingInfo(info.getId(), info.isLimitAdTrackingEnabled());
    }
  }
}
