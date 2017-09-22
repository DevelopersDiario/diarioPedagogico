package com.dese.diario.Utils.FirebaseService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dese.diario.Publication;
import com.dese.diario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Eduardo on 20/09/2017.
 */

public class FirebaseConection {

    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    public void notification(final Context c){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Publication pub= dataSnapshot.getValue(Publication.class);

                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr =(NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);

                int icono = R.mipmap.ic_launcher;
                Intent intent = new Intent(c, Publication.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(c, 0,intent, 0);

                mBuilder =new NotificationCompat.Builder(c)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icono)
                        .setContentTitle("Nueva Publicación")
                        .setContentText(pub.getTitle())
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setAutoCancel(true);

                mNotifyMgr.notify(1, mBuilder.build());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseConection.error",databaseError.getMessage());
            }
        });
    }

    public void setDatabasePublication(Context c, String idusuario,
                                       String titulo, String observaciones, String sentimientos, String evaluacion, String analisis, String conclusion, String plan, String padre){
       // databaseReference.setValue();
    }

}
