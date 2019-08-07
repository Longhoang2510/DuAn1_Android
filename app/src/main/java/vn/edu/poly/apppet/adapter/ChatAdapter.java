package vn.edu.poly.apppet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.model.Chat;

public class ChatAdapter extends ArrayAdapter<Chat>{

    private List<Chat> chats;
    private LayoutInflater inflater;

    public ChatAdapter(@NonNull Context context, int resource, @NonNull List<Chat> objects) {
        super (context, resource, objects);
        this.chats = objects;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;
        if (convertView == null) {//lan dau tao view item
            holder = new viewHolder();
            convertView = inflater.inflate(R.layout.item_chat, parent, false);
            holder.tvNameChat = convertView.findViewById(R.id.tvNameChat);
            holder.tvNoiDungChat = convertView.findViewById(R.id.tvNoiDungChat);
            holder.tvTimeChat = convertView.findViewById(R.id.tvTimeChat);
            holder.imvAvtChat = convertView.findViewById (R.id.imvAvtChat);

            convertView.setTag(holder);

        } else {// da tao view cho item roi
            holder = (viewHolder) convertView.getTag();

        }

        //thiet lap gia tri cho item
        //lay phan tu mang dang lam viec voi item


        Chat chat = chats.get (position);
        holder.tvNameChat.setText (chat.nameChat+": ");
        holder.tvNoiDungChat.setText (chat.noidungchat);
        holder.tvTimeChat.setText (chat.timeChat);
//        Glide.with (convertView).load (chat.linkimage).into (holder.imvAvtChat);
        Picasso.get ().load(chat.linkimage).into(holder.imvAvtChat);

        return convertView;
    }

    /**
     * dungf đẻ tối ưu hóa khi cuốn lên cuấn xuống
     */
    public static class viewHolder {
        public TextView tvNameChat;
        public TextView tvNoiDungChat;
        public TextView tvTimeChat;
        public ImageView imvAvtChat;

    }

}
