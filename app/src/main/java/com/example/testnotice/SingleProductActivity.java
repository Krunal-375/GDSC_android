package com.example.testnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleProductActivity extends AppCompatActivity {

    TextView singleDescription,singlePostedBy,singleNoticeHeadlin;
    ImageView singleImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        Toolbar noticeToolbar = findViewById(R.id.toolbarnotice);
        setSupportActionBar(noticeToolbar);


        singleNoticeHeadlin = findViewById(R.id.singleNoticeHeadline);
        singlePostedBy = findViewById(R.id.singlePostedBy);
        singleDescription = findViewById(R.id.singleAboutProduct);
        singleImage = findViewById(R.id.singleImage);

        Picasso.get().load(getIntent().getStringExtra("singleImage"))
                .placeholder(R.drawable.gdsc_sit_ngp)
                .into(singleImage);

        singleNoticeHeadlin.setText(getIntent().getStringExtra("singleHeadline"));
        singlePostedBy.setText(getIntent().getStringExtra("singlePostedBy"));
        singleDescription.setText(getIntent().getStringExtra("singleDescription"));

    }
}