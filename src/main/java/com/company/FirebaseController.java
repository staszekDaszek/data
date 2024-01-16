package com.company;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseController {
    private static final String FILE_NAME = "./dylanek-f3c0a-firebase-adminsdk-nqmpx-009809ccd0.json";
    private static final String DATABASE_NAME = "https://dylanek-f3c0a-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String URL = "/scores";

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

    public static void updateTournament(String name, List<Team> newTeams){
        System.out.println("c1");
        DatabaseReference reference = db.getReference(URL);
        DatabaseReference deepRef = reference.child("tournaments");
        Query query = deepRef.orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = "";
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    key = snapShot.getKey(); //Key
                }
                System.out.println(key);
                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put(key +"/teams", newTeams);
                deepRef.updateChildrenAsync(userUpdates);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }

        });

    }

    public static void deleteTournament(String name){
        System.out.println("c1");
        DatabaseReference reference = db.getReference(URL);
        DatabaseReference deepRef = reference.child("tournaments");
        Query query = deepRef.orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = "";
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    key = snapShot.getKey(); //Key
                }
                deepRef.child(key).removeValueAsync();
                System.out.println(key);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }

        });

    }

    public static void getTournaments() {
        DatabaseReference reference = db.getReference(URL);
        System.out.println("lol");
        reference.orderByKey().addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("XD");
                ArrayList<Tournament> fake = new ArrayList<>();
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    System.out.println(data.getValue().toString());
                    Tournament tournament = new Tournament();
                    try {
                        tournament = data.getValue(Tournament.class);
                    }catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    Main.tourKeyMap.put(tournament, data.getKey().toString());
                    System.out.println(tournament.toString());
                    fake.add(tournament);
                }
                System.out.println("7");
                Main.tournamentList = fake;
                System.out.println(Main.tournamentList.toString() + " 1000");
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

