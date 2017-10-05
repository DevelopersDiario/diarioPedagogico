package com.dese.diario.Utils.FirebaseService;

/**
 * Created by Eduardo on 04/10/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostJSON  {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token, username, titulo;


    public PostJSON(String username, String titulo, String token){
        this.titulo=titulo;
        this.username=username;
        this.token=token;

        new PostingJSON().execute();

    }
    private class PostingJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //PostJSON example = new PostJSON();
            String json = bowlingJson(token, titulo);
            String response = null;
            try {
                response = post("https://fcm.googleapis.com/fcm/send", json);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Notificacion FMS", e.getMessage());
            }
            System.out.println(response);
            return null;
        }
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("authorization", "key=AAAA_3Iy5Jc:APA91bHVmICBI1cYSipI6YaKwKZAqZKTQ5ajayT_79AmaWNQnl7DT893p0mgPXU67ymAMDFtmQo_ioCjkVKs3i-vJJPdx9YBF0XZrX2FPEGuMfhHJOgBAVNno0c7qODrmPNdaknT7eyw")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    String bowlingJson(String token, String titulo) {
        return "{\n" +
                "  \"to\": \""+token+"\",\n" +
                "  \"notification\": {\n" +
                "     \"title\": \"Nueva notificación de "+username+"\",\n" +
                "     \"body\": \""+titulo+"\"\n" +
                "  },\n" +
                "}";
    }
}
