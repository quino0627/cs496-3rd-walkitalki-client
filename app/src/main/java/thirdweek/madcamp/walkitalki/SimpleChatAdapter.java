package thirdweek.madcamp.walkitalki;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.ChatVer2;

public class SimpleChatAdapter extends RecyclerView.Adapter<SimpleChatAdapter.MyViewHolder> {

    //TODO: CHATVER2 바꿔주세요
    private ArrayList<ChatVer2> chatList = new ArrayList<ChatVer2>();
    private Context mContext;

    View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
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

    @NonNull
    @Override
    public SimpleChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_right, viewGroup, false);
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
