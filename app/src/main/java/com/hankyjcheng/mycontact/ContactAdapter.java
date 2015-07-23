package com.hankyjcheng.mycontact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private List<Contact> contactList = new ArrayList<>();
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Contact getItem(int arg0) {
        return contactList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.contact_row, arg2, false);
        }
        TextView contactNameTextView = (TextView) arg1.findViewById(R.id.contact_name_textView);
        TextView contactPhoneTextView = (TextView) arg1.findViewById(R.id.contact_phone_textView);

        Contact contact = contactList.get(arg0);
        contactNameTextView.setText(contact.getName());
        contactPhoneTextView.setText(contact.getWorkPhone());

        ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        NetworkImageView imageView = (NetworkImageView) arg1.findViewById(R.id.contact_image_small_imageView);
        imageView.setImageUrl(contact.getSmallImageURL(), imageLoader);
        return arg1;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

}