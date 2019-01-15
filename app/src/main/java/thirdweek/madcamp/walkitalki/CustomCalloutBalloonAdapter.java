package thirdweek.madcamp.walkitalki;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.ChatVer2;

@SuppressLint("ValidFragment")
class CustomCalloutBalloonAdapter extends Fragment implements CalloutBalloonAdapter{
    private final View mCalloutBalloon;
    private Chat chatver1;

    public CustomCalloutBalloonAdapter() {
        mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_balloon, null);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        ((ImageView) mCalloutBalloon.findViewById(R.id.image_dp)).setImageResource(R.drawable.kakao_default_profile_image);
        ((TextView) mCalloutBalloon.findViewById(R.id.textview_name)).setText(chatver1.username);
        ((TextView) mCalloutBalloon.findViewById(R.id.textview_msg)).setText(chatver1.content);
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return null;
    }
}