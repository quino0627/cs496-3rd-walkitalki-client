package thirdweek.madcamp.walkitalki;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.ChatVer2;
import thirdweek.madcamp.walkitalki.Model.User;

//TODO: 지워주세요 (나중에)
public class Fragment3 extends Fragment {
    public RecyclerView myRecylerView ;
    public List<ChatVer2> MessageList ;
    public SimpleChatAdapter simpleChatAdapter;
    private Socket socket;
    public EditText messagetxt ;
    public Button send ;    //declare socket object
    public String Name;
    public String ID ;

    public static String Kaka_name;

    View mview;

    public Fragment3() {
        //Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            socket = IO.socket("http://socrip4.kaist.ac.kr:1380/");
            socket.connect();

//            socket.emit("message", "CONNECTED!");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        requestMe();

        MessageList = new ArrayList<>();

        mview = inflater.inflate(R.layout.fragment_layout3, container, false);

        Name = getArguments().getString("user_name");


        //소켓에서 메시지 로드해서 맵에 찍기
        socket.on("new message", new Emitter.Listener() {
                                @Override
                                public void call(final Object... args) {
                                    if(getActivity() == null){
                                        return ;
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("newMSG", "NEW MSG!");
                                            JSONObject data = (JSONObject) args[0];
                                            try{
                                                String name = data.getString("username");
                                                String message = data.getString("message");
                                                User user = new User(name, 1L);

                                                Log.e("messagewhat", message);

                                                ChatVer2 m = new ChatVer2(user, message);
                                                MessageList.add(m);

                            Log.e("meglist", String.valueOf(MessageList));

                            simpleChatAdapter = new SimpleChatAdapter(getContext(), (ArrayList<ChatVer2>) MessageList);
                            simpleChatAdapter.notifyDataSetChanged();

                            myRecylerView.setAdapter(simpleChatAdapter);
                            myRecylerView.smoothScrollToPosition(myRecylerView.getAdapter().getItemCount());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        messagetxt = (EditText) mview.findViewById(R.id.message1);
        send = (Button) mview.findViewById(R.id.send);


        myRecylerView = (RecyclerView) mview.findViewById(R.id.messagelist);
       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());

        //message send action
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!messagetxt.getText().toString().isEmpty()){
                    socket.emit("detection", Name, messagetxt.getText().toString());
                    messagetxt.setText("");

                }
            }
        });
        return mview;
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

    //유저의 정보를 받아오는 함수
    protected void requestMe() {

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Kaka_name = result.getNickname();
            }
        });
    }
}
