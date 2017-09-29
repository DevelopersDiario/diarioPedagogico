package com.dese.diario.Utils.FirebaseService;

import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by Eduardo on 20/09/2017.
 */

public class FirebaseConection {


    public void setDatabaseUser(VariablesLogin variablesLogin) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(FirebaseReferences.AMDI);
        databaseReference.child(FirebaseReferences.USUARIO).push().setValue(variablesLogin);


    }

    public void setDatabaseDatosUser(DatosUsr du){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(FirebaseReferences.AMDI);
        databaseReference.child(FirebaseReferences.DatosUsers).push().setValue(du);
    }

}
