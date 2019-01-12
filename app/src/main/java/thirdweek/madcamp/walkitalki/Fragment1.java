package thirdweek.madcamp.walkitalki;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.helper.log.Logger;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.User;

public class Fragment1 extends Fragment {

    public List<Chat> MessageList;
    private Socket socket;
    public EditText messagetxt2;
    public Button sendBtn;
    public String Name;

    public static String KAKAONAME;
    public static long KAKAOID;

    public static User myUser;

    private static double longitude;
    private static double latitude;

    public Fragment1() {
        //Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            socket = IO.socket("http://socrip4.kaist.ac.kr:1380/");
            socket.connect();
            this.requestMe();

//            socket.emit("message", "CONNECTED!");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_layout1, container, false);

        MessageList = new ArrayList<>();

        messagetxt2 = (EditText) v.findViewById(R.id.message2);
        sendBtn = (Button) v.findViewById(R.id.send2);


        //소켓에서 메시지 로드해서 맵에 찍기
        socket.on("map new message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("newMSG", "NEW MSG!");
                        JSONObject data = (JSONObject) args[0];
                        try {
                            String name = data.getString("username");
                            String message = data.getString("message");
                            User user = MainActivity.myUser;

                            Log.e("messagewhat", message);

                            Chat m = new Chat(user, message);
                            MessageList.add(m);

                            Log.e("meglist", String.valueOf(MessageList));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        //지도
        final MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        // 줌 레벨 변경
        mapView.setZoomLevel(4, true);


        //마커 찍기
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);

        //LocationListener
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                // 중심점 변경
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //LocationManager
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);


        FloatingActionButton button_mylocation = v.findViewById(R.id.button_mylocation);
        button_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//                // 중심점 변경
//                double longitude = location.getLongitude();
//                double latitude = location.getLatitude();

                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude), true);

            }
        });

        final MyUtil myUtil = new MyUtil(getContext());
        User user = new User("sdw627", 1L);
        Chat chat = new Chat(user, "vvvv");
        //myUtil.popMyMsg(mapView, chat);


        //message send action
        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!messagetxt2.getText().toString().isEmpty()){
                    socket.emit("map detection", KAKAONAME, messagetxt2.getText().toString(), myUtil.getLocation().getLongitude(), myUtil.getLocation().getLatitude());
                    messagetxt2.setText("");
                }
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            socket = IO.socket("http://socrip4.kaist.ac.kr:1380/");
            socket.connect();

            socket.emit("message", "CONNECTED!");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    protected void requestMe() { //유저의 정보를 받아오는 함수

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }

            @Override
            public void onSuccess(MeV2Response result) {
                KAKAOID = result.getId();
                KAKAONAME = result.getNickname();

                Log.d("KAKAOID", String.valueOf(KAKAOID));
                Log.d("KAKAOAME", KAKAONAME);

                myUser = new User(KAKAONAME, KAKAOID);
            }
        });
    }
}
