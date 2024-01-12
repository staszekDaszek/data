package com.company;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FirebaseController {
    private static final String FILE_NAME = "./dylanek-f3c0a-firebase-adminsdk-nqmpx-009809ccd0.json";
    private static final String DATABASE_NAME = "https://dylanek-f3c0a-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String URL = "/scores";

    private static final String URL1 = "/";

    private static FirebaseDatabase db;

    public static void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(FILE_NAME);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(DATABASE_NAME)
                .build();

        FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance();
    }

    public static void addTeam(Team team, Tournament tournament) {
        DatabaseReference reference = db.getReference(URL);
        reference.child("tournaments/"+tournament.getName()+"/teams/"+team.getName()).push().setValueAsync(team);
    }

    public static void addTournament(Tournament tournament) {
        DatabaseReference reference = db.getReference(URL);
        reference.child("tournaments").push().setValueAsync(tournament);
        System.out.println("lmao");
    }


    public static void getTournaments() {
        DatabaseReference reference = db.getReference(URL);
        System.out.println("lol");
        reference.orderByKey().addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ArrayList<Tournament> fake = new ArrayList<>();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Tournament tournament = data.getValue(Tournament.class);
                    fake.add(tournament);
                }
                Main.tournamentList = fake;
                System.out.println(Main.tournamentList.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("error");
            }
        });
    }

}

