package com.scottruth.timeoffandroid.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.scottruth.timeoffandroid.module.OkHttpClientFactory;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * Created by santiago on 22/09/15.
 * TODO: convert to module
 */
public class PicassoHelper {
    private static final OkHttpDownloader OK_HTTP_CLIENT = new OkHttpDownloader(OkHttpClientFactory.createPicassoOkHttpClient());
    private static final Picasso.Listener LISTENER = (picasso, uri, exception) -> {
        if (uri != null) {
            Crashlytics.setString(GeneralErrorHelper.CRASHLYTICS_KEY_URL, uri.toString());
        }
        Timber.w(exception, "Picasso image load failed");
    };
    private static Picasso.Builder picassoBuilder;
    private static Picasso picasso;
    private static Picasso layerPicasso;

    @NonNull
    public static Picasso getPicasso(@NonNull final Context context) {
        if (picasso == null) {
            picasso = getPicassoBuilder(context)
                    .build();
        }
        return picasso;
    }

    @NonNull
    private static Picasso.Builder getPicassoBuilder(@NonNull final Context context) {
        if (picassoBuilder == null) {
            picassoBuilder = new Picasso.Builder(context)
                    .downloader(OK_HTTP_CLIENT)
                    //.indicatorsEnabled(BuildConfig.DEBUG)
                    .listener(LISTENER);
                    //.loggingEnabled(BuildConfig.DEBUG);
        }
        return picassoBuilder;
    }
}