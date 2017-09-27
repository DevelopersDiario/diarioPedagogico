package com.dese.diario.Utils.FirebaseService;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Eduardo on 20/09/2017.
 */

public class FirebaseConection {



 /*   public void notification(final Context c){
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
    }*/

    public void setDatabaseUser(Context c,String User, String Token ){
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child(FirebaseReferences.USUARIO);

        databaseReference.setValue(User);
    }

}
