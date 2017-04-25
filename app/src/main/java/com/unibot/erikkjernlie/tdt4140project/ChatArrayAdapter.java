/*  ChatArrayAdapter
 *
 *  ArrayAdapter that makes the listview appear on the screen as chatmessages
 *
 *  Created by Erik Kjernlie
 *  Copyright © uniBOT
 */

package com.unibot.erikkjernlie.tdt4140project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


//ChatArrayAdapter converts the arraylist so it's visible on a interface
class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object); //needs to add super as well, or it won't work
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //decides which side the chatbubble should be on
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.left_chat, parent, false);
        } else {
            row = inflater.inflate(R.layout.right_chat, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.message);
        return row;
    }
}