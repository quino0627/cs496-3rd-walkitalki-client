package thirdweek.madcamp.walkitalki;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import java.util.ArrayList;

import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.ChatVer2;
import thirdweek.madcamp.walkitalki.Model.User;

import static thirdweek.madcamp.walkitalki.Fragment3.Kaka_name;

public class SimpleChatAdapter extends RecyclerView.Adapter<SimpleChatAdapter.MyViewHolder> {

    //TODO: CHATVER2 바꿔주세요
    private ArrayList<ChatVer2> chatList;
    private Context mContext;

    View itemView;
    Chat chat;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public static TextView username;
        public TextView chatItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name);
            chatItem = itemView.findViewById(R.id.message);
        }
    }

    public SimpleChatAdapter(Context c, ArrayList<ChatVer2> chatList) {
        mContext = c;
        this.chatList = chatList;
    }

    @Override
    public int getItemViewType(int position) {

        Log.e("KAKAONAMEE", Kaka_name);
        Log.e("lalalalallalalala", chatList.get(position).chat_sender.user_name);
        if (chatList.get(position).chat_sender.user_name.equals(Kaka_name)){
            Log.e("itemViewTypew", "0번");
           return 0;
        } else {
            Log.e("itemViewTypew", "1번");
            return 1;
        }
    }

    @NonNull
    @Override
    public SimpleChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("fikwwq", String.valueOf(i));
        switch (i){
            case 0: {
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_right, viewGroup, false);
                break;
            }
            case 1:{
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_left, viewGroup, false);
                break;
            }
        }
//        if (chatList.get(i).chat_sender.user_name == Kaka_name){
//            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_right, viewGroup, false);
//        } else {
//            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_left, viewGroup, false);
//        }

        return new SimpleChatAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        final ChatVer2 m = chatList.get(i);
        myViewHolder.username.setText(m.getChat_sender().user_name);
        myViewHolder.chatItem.setText(m.getChat_content());
        myViewHolder.chatItem.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

}
