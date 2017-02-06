package com.example.fengyi.fmao1_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private int index;

    @BindView(R.id.text_detail_name) TextView textName;
    @BindView(R.id.text_detail_date) TextView textDate;
    @BindView(R.id.text_detail_neck) TextView textNeck;
    @BindView(R.id.text_detail_bust) TextView textBust;
    @BindView(R.id.text_detail_chest) TextView textChest;
    @BindView(R.id.text_detail_waist) TextView textWaist;
    @BindView(R.id.text_detail_hip) TextView textHip;
    @BindView(R.id.text_detail_inseam) TextView textInseam;
    @BindView(R.id.text_detail_comment) TextView textComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get index from Intent of MainActivity (actually of NormalRecyclerViewAdapter)
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);

        // Set floatActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start EditActivity, send index
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("index", index);
                DetailActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){

        super.onStart();

        ButterKnife.bind(this);

        // Read file, Show details
        JSONManagement jsonManagement = new JSONManagement(this);
        Person person = jsonManagement.personOf(index);

        textName.setText(person.name);
        if (person.hasDate()) textDate.setText(person.date);
        if (person.hasComment()) textComment.setText(person.comment);
        String format = "%-4.1f inches";
        if (person.hasNeck()) textNeck.setText(String.format(format, person.neck));
        if (person.hasBust()) textBust.setText(String.format(format, person.bust));
        if (person.hasChest()) textChest.setText(String.format(format, person.chest));
        if (person.hasWaist()) textWaist.setText(String.format(format, person.waist));
        if (person.hasHip()) textHip.setText(String.format(format, person.hip));
        if (person.hasInseam()) textInseam.setText(String.format(format, person.inseam));
    }
}
