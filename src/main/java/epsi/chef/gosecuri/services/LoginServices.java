package epsi.chef.gosecuri.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import epsi.chef.gosecuri.api.FaceDetection;
import epsi.chef.gosecuri.entity.Equipment;
import epsi.chef.gosecuri.entity.Location;
import epsi.chef.gosecuri.entity.Picture;
import epsi.chef.gosecuri.entity.User;
import epsi.chef.gosecuri.firebase.POCFirebase;

public class LoginServices {

    private POCFirebase   firebase      = POCFirebase.getInstance();
    private FaceDetection faceDetection = new FaceDetection();

    private void checkFace() {
        try {
            List<Picture> pictures = firebase.getPicture();
            List<String> faceSetTokens = new ArrayList<>();
            while ( faceSetTokens.isEmpty() ) {
                try {
                    JSONObject obj = new JSONObject( faceDetection.getFaceSetDetails() );
                    if ( obj.has( "face_tokens" ) ) {
                        JSONArray arr = obj.getJSONArray( "face_tokens" );
                        for ( int i = 0; i < arr.length(); i++ ) {
                            faceSetTokens.add( arr.getString( i ) );
                        }
                    }
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
            List<Picture> pictureToAdd = new ArrayList<>();
            for ( Picture picture : pictures ) {
                if ( faceSetTokens.contains( picture.getFaceToken() ) ) {
                    faceSetTokens.remove( picture.getFaceToken() );
                } else {
                    pictureToAdd.add( picture );
                }
            }
            boolean requestOK = false;
            int requestMax = 0;
            for ( Picture picture : pictureToAdd ) {
                String url = picture.getImageURL();
                if ( url != "" && url != null ) {
                    while ( !requestOK && requestMax < 10 ) {
                        try {
                            requestMax++;
                            JSONObject obj = new JSONObject( faceDetection.getFaceToken( picture.getImageURL() ) );
                            if ( obj.has( "faces" ) ) {
                                JSONArray arr = obj.getJSONArray( "faces" );
                                picture.setFaceToken( arr.getJSONObject( 0 ).getString( "face_token" ) );
                                requestOK = true;
                                firebase.updateFaceToken( picture );
                            }
                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    }
                    requestOK = false;
                    requestMax = 0;
                    while ( !requestOK && requestMax < 10 ) {
                        try {
                            requestMax++;
                            JSONObject obj = new JSONObject( faceDetection.addFaceToFaceSet( picture.getFaceToken() ) );
                            if ( obj.has( "error_message" ) ) {
                                requestOK = false;
                            } else {
                                requestOK = true;
                            }
                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            requestOK = false;
            for ( String token : faceSetTokens ) {
                while ( !requestOK && requestMax < 10 ) {
                    try {
                        requestMax++;
                        JSONObject obj = new JSONObject( faceDetection.removeFaceFromFaceSet( token ) );
                        if ( obj.has( "error_message" ) ) {
                            requestOK = true;
                        } else {
                            requestOK = true;
                        }
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public User getUser( String token ) {
        User user = firebase.getOneUser( token );
        return user;
    }

    public List<Equipment> getEquipments( String userId ) {
        List<Equipment> equipments = firebase.getEquipment();
        List<Location> locations = getLocations( userId );
        for ( Location location : locations ) {
            String locEquipment = location.getEquipmentsId();
            for ( Equipment equipment : equipments ) {
                String equip = equipment.getDocId();
                if ( equip.equals( locEquipment ) ) {
                    equipment.setChecked( true );
                    break;
                }

            }
        }
        return equipments;
    }

    public void deleteEquipment( List<Location> locations ) {
        for ( Location location : locations ) {
            firebase.deleteLocation( location );
        }
    }

    public void addEquipment( Map<String, Object> equipments, String userId ) {
        List<Location> locations = firebase.getLocations( userId );
        for ( Map.Entry<String, Object> entry : equipments.entrySet() ) {
            Boolean addLocation = true;
            for ( Location location : locations ) {
                if ( location.getEquipmentsId().equals( entry.getKey() ) ) {
                    locations.remove( location );
                    addLocation = false;
                    break;
                }
            }
            if ( addLocation ) {
                Location newLocation = new Location( userId, entry.getKey() );
                firebase.addLocation( newLocation );
            }
        }
        deleteEquipment( locations );
    }

    public List<Location> getLocations( String userId ) {
        List<Location> locations = firebase.getLocations( userId );
        return locations;
    }

    public User testLogin( String base64 ) {
        checkFace();
        boolean requestOK = false;
        int requestMax = 0;
        User user = new User();
        while ( !requestOK && requestMax < 10 ) {
            try {
                requestMax++;
                JSONObject obj = new JSONObject( faceDetection.searchFace( base64 ) );
                if ( obj.has( "faces" ) ) {
                    if ( obj.getJSONArray( "faces" ).isNull( 0 ) ) {
                        requestOK = true;
                    }
                }
                if ( obj.has( "results" ) ) {
                    requestOK = true;
                    JSONArray arr = obj.getJSONArray( "results" );
                    Float coef = BigDecimal.valueOf( arr.getJSONObject( 0 ).getDouble( "confidence" ) ).floatValue();
                    if ( coef >= 70.0 ) {
                        System.out.println( arr.getJSONObject( 0 ).getString( "face_token" ) );
                        user = getUser( arr.getJSONObject( 0 ).getString( "face_token" ) );
                    }
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        return user;
    }

}
