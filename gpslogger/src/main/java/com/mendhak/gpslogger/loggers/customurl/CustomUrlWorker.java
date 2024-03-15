package com.mendhak.gpslogger.loggers.customurl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mendhak.gpslogger.common.AppSettings;
import com.mendhak.gpslogger.common.Strings;
import com.mendhak.gpslogger.common.network.Networks;
import com.mendhak.gpslogger.common.slf4j.Logs;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.X509TrustManager;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomUrlWorker extends Worker {

    private static final Logger LOG = Logs.of(CustomUrlWorker.class);
    public CustomUrlWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        LOG.info("CustomUrlWorker doWork");
        Data data = getInputData();
        String[] urlRequests = data.getStringArray("urlRequests");
        CustomUrlRequest customUrlRequest = Strings.deserializeFromJson(urlRequests[0], CustomUrlRequest.class);
        LOG.info(customUrlRequest.getLogURL());
        LOG.info(customUrlRequest.getHttpBody());
        LOG.info(customUrlRequest.getHttpHeaders().toString());
        LOG.info("CustomUrlWorker doWork data: " + data.toString());

        if(getRunAttemptCount() < 3){
            return Result.retry();
        }


        return Result.success();
    }
}
