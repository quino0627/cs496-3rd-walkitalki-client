package thirdweek.madcamp.walkitalki;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.kakao.usermgmt.response.model.User;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.ChatVer2;
import thirdweek.madcamp.walkitalki.Model.Position;

public class MyUtil {

    Context mContext;

    public MyUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void popMyMsg(MapView mapView, ChatVer2 chat) {
        Location msgLocation = getLocation();
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(msgLocation.getLatitude(), msgLocation.getLongitude());
        MapPOIItem marker = new MapPOIItem();
        Log.e("asdf", "qwerty");
        Log.e(chat.chat_sender.user_name, chat.chat_content);
        marker.setItemName(chat.chat_sender.user_name + " : " + chat.chat_content);
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

        //move to pinned point
        mapView.setMapCenterPoint(MARKER_POINT, true);
    }

    public void popOthersMsg(MapView mapView, ChatVer2 chat, double latitude, double longitude) {
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        MapPOIItem marker = new MapPOIItem();
        Log.e("asdf", "qwerty");
        Log.e(chat.chat_sender.user_name, chat.chat_content);
        marker.setItemName(chat.chat_sender.user_name + " : " + chat.chat_content);
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

        //move to pinned point
        mapView.setMapCenterPoint(MARKER_POINT, true);
    }

    //TODO: POPCUSTOMMSG 부탁합니다
//    public void popaBalloon(MapView mapView, Chat chat, double latitude, double longitude){
//
//    }

    public Location getLocation() {
        LocationManager lm = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
    }

}
