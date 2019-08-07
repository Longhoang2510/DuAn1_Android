package vn.edu.poly.apppet.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.adapter.ChatAdapter;
import vn.edu.poly.apppet.model.Chat;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


public class MessengerFragment extends Fragment {
    private View ib;
    private String user_name, room_name, chat_nd;
    private byte[] imvByte;

    private Button btnSend;
    private EditText edChat;
    private ListView lvChat;
    private DatabaseReference mData;


    //adapter
    List<Chat> chats;
    ChatAdapter adapter;

    //Stogre
    FirebaseStorage storage = FirebaseStorage.getInstance ();
    int REQUEST_CODE_IMAGE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ib = inflater.inflate (R.layout.fragment_messenger, container, false);
        final StorageReference storageRef = storage.getReference ();

        intivity ();

        //lay du lieu
        Bundle bundle = getArguments ();
        room_name = bundle.getString ("room_name");
        user_name = bundle.getString ("user_name");
        imvByte = bundle.getByteArray ("imvByte");


        //add thêm dữ liệu
//        Chat chat = new Chat ("Long", "Xin chào", "11:55 PM");
//        mData.child ("ChatDogHN").push ().setValue (chat);


        //xet adpterr
        chats = new ArrayList<> ();
        adapter = new ChatAdapter (getActivity (), R.layout.item_chat, chats);
        lvChat.setAdapter (adapter);

        //Storage


        //lấy chat
        LoadDataChat ();


        //send noi dung
        btnSend.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                chat_nd = edChat.getText ().toString ().trim ();
                edChat.setText ("");
                DateFormat df = new SimpleDateFormat ("h:mm a, d '/' MM '/' yyyy");
                final String date = df.format (Calendar.getInstance ().getTime ());


                //laasy time he thong
                Calendar calendar = Calendar.getInstance ();
                final StorageReference mountainsRef = storageRef.child ("image" + calendar.getTimeInMillis () + ".jpg");

                final UploadTask uploadTask = mountainsRef.putBytes (imvByte);
                uploadTask.addOnFailureListener (new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText (getActivity (), "Lỗi upload ảnh", Toast.LENGTH_SHORT).show ();
                    }
                }).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        final Uri downUri = taskSnapshot.getUploadSessionUri ();
//                        Toast.makeText (getActivity (), "thành công", Toast.LENGTH_SHORT).show ();


                    }
                });


                Task<Uri> urlTask = uploadTask.continueWithTask (new Continuation<UploadTask.TaskSnapshot, Task<Uri>> () {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful ()) {
                            throw task.getException ();
                        }

                        // Continue with the task to get the download URL
                        return mountainsRef.getDownloadUrl ();
                    }
                }).addOnCompleteListener (new OnCompleteListener<Uri> () {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful ()) {
                            Uri downloadUri = task.getResult ();


                            // send chat xet dk
                            if (chat_nd.equals ("")) {
                                Toast.makeText (getActivity (), "Yêu cầu nhập nội dung tin nhắn!", Toast.LENGTH_SHORT).show ();
                            } else {

                                //add chat send
                                Chat chat = new Chat (user_name, chat_nd, date, String.valueOf (downloadUri));
                                mData.child (room_name).push ().setValue (chat, new DatabaseReference.CompletionListener () {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                        if (databaseError == null) {
                                            Toast.makeText (getActivity (), "Đã gửi", Toast.LENGTH_SHORT).show ();
                                        } else {
                                            Toast.makeText (getActivity (), "Lỗi chat!", Toast.LENGTH_SHORT).show ();
                                        }
                                    }
                                });


                            }


                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });


//                if (chat_nd.equals ("")) {
//                    Toast.makeText (getActivity (), "Yêu cầu nhập nội dung tin nhắn!", Toast.LENGTH_SHORT).show ();
//                }else {
//                    Chat chat = new Chat (user_name, chat_nd, date, null);
//                    mData.child (room_name).push ().setValue (chat);
//                    edChat.setText ("");
//
//                }

            }
        });


        return ib;
    }

    private void LoadDataChat() {
        mData.child (room_name).addChildEventListener (new ChildEventListener () {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat c = dataSnapshot.getValue (Chat.class);
                chats.add (c);
                adapter.notifyDataSetChanged ();
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

            }
        });
    }

    private void intivity() {
        mData = FirebaseDatabase.getInstance ().getReference ();
        btnSend = ib.findViewById (R.id.btn_send);
        edChat = ib.findViewById (R.id.edChat);
        lvChat = ib.findViewById (R.id.lvChat);

    }


}
