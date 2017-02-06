package com.example.fengyi.fmao1_sizebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class EditActivity extends AppCompatActivity {

    private int index;
    private SelectList selectList = new SelectList();

    @BindView(R.id.text_edit_name) EditText textName;
    @BindView(R.id.text_edit_date) TextView textDate;
    @BindView(R.id.text_edit_neck) TextView textNeck;
    @BindView(R.id.text_edit_bust) TextView textBust;
    @BindView(R.id.text_edit_chest) TextView textChest;
    @BindView(R.id.text_edit_waist) TextView textWaist;
    @BindView(R.id.text_edit_hip) TextView textHip;
    @BindView(R.id.text_edit_inseam) TextView textInseam;
    @BindView(R.id.text_edit_comment) EditText textComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ButterKnife.bind(this);

        // Get index from Intent of MainActivity (-1) or DetailActivity (position)
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);

        // Set TextView depends on index
        if (index < 0) {
            // Add new person
            // hints have already set in layout
        } else {
            // Edit an exited person
            // Set old data
            JSONManagement jsonManagement = new JSONManagement(EditActivity.this);
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

        // Set floatActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ButterKnife.bind(EditActivity.this);

                // Check name
                if (textName.getText().toString().isEmpty()){
                    Snackbar.make(view, "NAME is required.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    // Save edit
                    Person newPerson = new Person();
                    newPerson.name = textName.getText().toString();
                    if(!textDate.getText().toString().isEmpty()){
                        newPerson.date = textDate.getText().toString();
                    }
                    if(!textComment.getText().toString().isEmpty()){
                        newPerson.comment = textComment.getText().toString();
                    }
                    try {

                        if(!textNeck.getText().toString().replaceAll("[a-z]","").replace(" ", "").isEmpty()) {
                            newPerson.neck = Double.parseDouble(textNeck.getText().toString().replaceAll("[a-z]","").replace(" ", ""));
                        }
                        if(!textBust.getText().toString().replaceAll("[a-z]","").replace(" ", "").isEmpty()) {
                            newPerson.bust = Double.parseDouble(textBust.getText().toString().replaceAll("[a-z]","").replace(" ", ""));
                        }
                        if(!textChest.getText().toString().replaceAll("[a-z]","").replace(" ", "").isEmpty()) {
                            newPerson.chest = Double.parseDouble(textChest.getText().toString().replaceAll("[a-z]","").replace(" ", ""));
                        }
                        if(!textWaist.getText().toString().replaceAll("[a-z]","").replace(" ", "").isEmpty()) {
                            newPerson.waist = Double.parseDouble(textWaist.getText().toString().replaceAll("[a-z]","").replace(" ", ""));
                        }
                        if(!textHip.getText().toString().replaceAll("[a-z]","").replace(" ", "").isEmpty()) {
                            newPerson.hip = Double.parseDouble(textHip.getText().toString().replaceAll("[a-z]","").replace(" ", ""));
                        }
                        if(!textInseam.getText().toString().replaceAll("[a-z]","").replace(" ", "").isEmpty()) {
                            newPerson.inseam = Double.parseDouble(textInseam.getText().toString().replaceAll("[a-z]","").replace(" ", ""));
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    JSONManagement jsonManagement = new JSONManagement(EditActivity.this);
                    if (index < 0){
                        jsonManagement.add(newPerson);
                    } else {
                        Log.d("EditActivity", "update index: " + String.valueOf(index));
                        jsonManagement.update(newPerson, index);
                    }
                }
            }
        });

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_3, null);
                builder.setView(view);

                final WheelView wheelView1 = (WheelView) view.findViewById(R.id.wheel_3_1);
                wheelView1.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView1.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView1.setWheelData(selectList.year);  // 数据集合
                wheelView1.setSelection(80); // 设置初始选项

                final WheelView wheelView2 = (WheelView) view.findViewById(R.id.wheel_3_2);
                wheelView2.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView2.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView2.setWheelData(selectList.month);  // 数据集合

                final WheelView wheelView3 = (WheelView) view.findViewById(R.id.wheel_3_3);
                wheelView3.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView3.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView3.setWheelData(selectList.day);  // 数据集合

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("EditActivity", "date position: " + String.valueOf(wheelView1.getCurrentPosition()) + " " + String.valueOf(wheelView2.getCurrentPosition()) + " " + String.valueOf(wheelView3.getCurrentPosition()));
                        int year = 1900 + wheelView1.getCurrentPosition();
                        int month = 1 + wheelView2.getCurrentPosition();
                        int day = 1 + wheelView3.getCurrentPosition();
                        Log.d("EditActivity", "date data: " + String.valueOf(year) + " " + String.valueOf(month) + " " + String.valueOf(day));
                        String format = "%4d-%02d-%02d";
                        textDate.setText(String.format(format, year, month, day));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
            }
        });

        textNeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_1, null);
                builder.setView(view);

                final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_1);

                wheelView.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView.setWheelData(selectList.neck);  // 数据集合
                wheelView.setSelection(31); // 设置初始选项
                
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double length = (1 + wheelView.getCurrentPosition()) * 0.5;
                        String format = "%-4.1f inches";
                        textNeck.setText(String.format(format, length));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                
                builder.create().show();
            }
        });

        textBust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_1, null);
                builder.setView(view);

                final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_1);

                wheelView.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView.setWheelData(selectList.bust);  // 数据集合
                wheelView.setSelection(31); // 设置初始选项

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double length = (1 + wheelView.getCurrentPosition()) * 0.5;
                        String format = "%-4.1f inches";
                        textBust.setText(String.format(format, length));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
            }
        });

        textChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_1, null);
                builder.setView(view);

                final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_1);

                wheelView.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView.setWheelData(selectList.chest);  // 数据集合
                wheelView.setSelection(31); // 设置初始选项

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double length = (1 + wheelView.getCurrentPosition()) * 0.5;
                        String format = "%-4.1f inches";
                        textChest.setText(String.format(format, length));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
            }
        });

        textWaist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_1, null);
                builder.setView(view);

                final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_1);

                wheelView.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView.setWheelData(selectList.waist);  // 数据集合
                wheelView.setSelection(31); // 设置初始选项

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double length = (1 + wheelView.getCurrentPosition()) * 0.5;
                        String format = "%-4.1f inches";
                        textWaist.setText(String.format(format, length));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
            }
        });

        textHip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_1, null);
                builder.setView(view);

                final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_1);

                wheelView.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView.setWheelData(selectList.hip);  // 数据集合
                wheelView.setSelection(31); // 设置初始选项

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double length = (1 + wheelView.getCurrentPosition()) * 0.5;
                        String format = "%-4.1f inches";
                        textHip.setText(String.format(format, length));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
            }
        });

        textInseam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.wheel_1, null);
                builder.setView(view);

                final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_1);

                wheelView.setWheelAdapter(new ArrayWheelAdapter(EditActivity.this)); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // Holo皮肤
                wheelView.setWheelData(selectList.inseam);  // 数据集合
                wheelView.setSelection(31); // 设置初始选项

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double length = (1 + wheelView.getCurrentPosition()) * 0.5;
                        String format = "%-4.1f inches";
                        textInseam.setText(String.format(format, length));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
            }
        });
    }
}
