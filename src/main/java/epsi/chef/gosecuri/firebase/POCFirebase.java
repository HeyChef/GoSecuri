package epsi.chef.gosecuri.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import epsi.chef.gosecuri.entity.Equipment;
import epsi.chef.gosecuri.entity.Location;
import epsi.chef.gosecuri.entity.Picture;
import epsi.chef.gosecuri.entity.User;

public class POCFirebase {

    private Firestore          db;

    private static POCFirebase INSTANCE = new POCFirebase();

    private POCFirebase() {
        System.out.println( "<<<<<<START>>>>>>" );
        FileInputStream serviceAccount = null;
        FirebaseApp app = null;
        String jsFname = "chef-epsi-java-firebase-adminsdk-4ejxg-a50e236708.json";
        String jsProject = null;

        try {

            serviceAccount = openJsonFile( jsFname );

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials( GoogleCredentials.fromStream( serviceAccount ) )
                    .setDatabaseUrl( "https://chef-epsi-java.firebaseio.com/" ).build();

            app = FirebaseApp.initializeApp( options );

            System.out.println( "INITIALIZE COMPLETE ...." );

        } catch ( FileNotFoundException e ) {
            System.out.println( "ERROR While get Google service :" + e );
        } catch ( IOException e ) {
            System.out.println( "ERROR While get Google service :" + e );
        }

        FirestoreOptions store = null;

        try {
            serviceAccount = openJsonFile( jsFname );
            store = FirestoreOptions.newBuilder()
                    .setTimestampsInSnapshotsEnabled( true ).setProjectId( jsProject )
                    .setCredentials( GoogleCredentials.fromStream( serviceAccount ) )
                    .build();
        } catch ( IOException e1 ) {
            System.out.println( "ERROR While FirestoreOptions BUILD : " + e1.getLocalizedMessage() );
        }

        this.db = store.getService();

    }

    public static final POCFirebase getInstance() {
        return INSTANCE;
    }

    public List<Picture> getPicture() {
        List<Picture> pictures = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection( "pictures" ).get();
            List<QueryDocumentSnapshot> documents;
            documents = future.get().getDocuments();
            for ( QueryDocumentSnapshot document : documents ) {
                Picture picture = document.toObject( Picture.class );
                picture.setDocId( document.getId() );
                pictures.add( picture );
            }
        } catch ( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pictures;
    }

    public List<Equipment> getEquipment() {
        List<Equipment> equipments = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection( "equipments" ).get();
            List<QueryDocumentSnapshot> documents;
            documents = future.get().getDocuments();
            for ( QueryDocumentSnapshot document : documents ) {
                Equipment equipment = document.toObject( Equipment.class );
                equipment.setDocId( document.getId() );
                equipments.add( equipment );
            }
        } catch ( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return equipments;
    }

    public Picture getOnePicture( String token ) {
        Picture picture = new Picture();
        try {
            ApiFuture<QuerySnapshot> future = db.collection( "pictures" ).whereEqualTo( "faceToken", token ).limit( 1 )
                    .get();
            List<QueryDocumentSnapshot> documents;
            documents = future.get().getDocuments();
            for ( QueryDocumentSnapshot document : documents ) {
                picture = document.toObject( Picture.class );
                picture.setDocId( document.getId() );
            }
        } catch ( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return picture;
    }

    public User getOneUser( String token ) {
        User user = new User();
        try {
            Picture picture = getOnePicture( token );
            ApiFuture<DocumentSnapshot> doc = db.collection( "users" ).document( picture.getUserId() ).get();
            user = doc.get().toObject( User.class );
            user.setUserId( picture.getUserId() );
            System.out.println( user.getName() );
        } catch ( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public void updateFaceToken( Picture picture ) {
        DocumentReference docRef = db.collection( "pictures" ).document( picture.getDocId() );
        ApiFuture<WriteResult> future = docRef.update( "FaceToken", picture.getFaceToken() );
    }

    public void addLocation( Location location ) {
        ApiFuture<WriteResult> future = db.collection( "locations" ).document().create( location );
        updateEquipment( location.getEquipmentsId(), "remove" );
    }

    public List<Location> getLocations( String userId ) {
        List<Location> locations = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection( "locations" ).whereEqualTo( "userId", userId ).get();
            List<QueryDocumentSnapshot> documents;
            documents = future.get().getDocuments();
            for ( QueryDocumentSnapshot document : documents ) {
                Location location = document.toObject( Location.class );
                location.setDocId( document.getId() );
                locations.add( location );
            }
        } catch ( InterruptedException | ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return locations;
    }

    public void deleteLocation( Location location ) {
        ApiFuture<WriteResult> writeResult = db.collection( "locations" ).document( location.getDocId() ).delete();
        updateEquipment( location.getEquipmentsId(), "add" );
    }

    public void updateEquipment( String docId, String operator ) {
        try {
            DocumentReference docRef = db.collection( "equipments" ).document( docId );
            ApiFuture<DocumentSnapshot> document = docRef.get();
            Long quantite = document.get().getLong( "quantite" );
            switch ( operator ) {
            case "add":
                quantite++;
                break;
            case "remove":
                quantite--;
                break;
            }
            docRef.update( "quantite", quantite );
        } catch ( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static FileInputStream openJsonFile( String fname )
            throws FileNotFoundException, UnsupportedEncodingException {
        FileInputStream serviceAccount;
        File file = new File(
                URLDecoder.decode( POCFirebase.class.getClassLoader().getResource( fname ).getFile(), "UTF-8" ) );
        if ( !file.exists() )
            System.out.println( "READ ERROR ...." );

        serviceAccount = new FileInputStream( file );
        return serviceAccount;
    }
}
