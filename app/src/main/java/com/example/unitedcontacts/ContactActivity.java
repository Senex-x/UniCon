package com.example.unitedcontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener{

    public void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    int index = 0;
    String getName;
    boolean favorite, friend, work, family, favoritePush, isDeleted;
    String vkCheck, whatsappCheck, telegramCheck, viberCheck, twitterCheck, discordCheck, phoneString, mailString;
    TextView name, textFriend, textWork, textFamily;
    ImageView photo;
    ImageView imgVk, imgTelegram, imgWhatsapp, imgViber, imgTwitter, imgDiscord, favoriteButton;
    Button vk, telegram, whatsapp, viber, twitter, discord, phone, mail, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

        photo = findViewById(R.id.portrait);
        name = findViewById(R.id.name);
        textFriend = findViewById(R.id.textFriend);
        textWork = findViewById(R.id.textWork);
        textFamily = findViewById(R.id.textFamily);
        phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        vk = findViewById(R.id.vk);
        telegram = findViewById(R.id.telegram);
        whatsapp = findViewById(R.id.whatsapp);
        viber = findViewById(R.id.viber);
        twitter = findViewById(R.id.twitter);
        discord = findViewById(R.id.discord);
        delete = findViewById(R.id.delete);
        imgVk = findViewById(R.id.imgVk);
        imgTelegram = findViewById(R.id.imgTelegram);
        imgWhatsapp = findViewById(R.id.imgWhatsapp);
        imgViber = findViewById(R.id.imgViber);
        imgTwitter = findViewById(R.id.imgTwitter);
        imgDiscord = findViewById(R.id.imgDiscord);
        favoriteButton = findViewById(R.id.favorite);

        vk.setOnClickListener(this); // установка обработчика нажатий для кнопок, вызывающих переход по ссылке
        telegram.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        viber.setOnClickListener(this);
        twitter.setOnClickListener(this);
        discord.setOnClickListener(this);
        phone.setOnClickListener(this);
        mail.setOnClickListener(this);
        delete.setOnClickListener(this);

        getName = intent.getStringExtra("name"); //получение всех данных о форме из MaiActivity
        phoneString = intent.getStringExtra("phoneString");
        mailString = intent.getStringExtra("mailString");
        photo.setImageBitmap(StringToBitMap(intent.getStringExtra("bitmap")));
        favorite = intent.getBooleanExtra("favorite", favorite);
        friend = intent.getBooleanExtra("friend", friend);
        work = intent.getBooleanExtra("work", work);
        family = intent.getBooleanExtra("family", family);
        vkCheck = intent.getStringExtra("vk");
        telegramCheck = intent.getStringExtra("telegram");
        whatsappCheck = intent.getStringExtra("whatsapp");
        viberCheck = intent.getStringExtra("viber");
        twitterCheck = intent.getStringExtra("twitter");
        discordCheck = intent.getStringExtra("discord");
        index = intent.getIntExtra("index", 0);

        if (favorite) { // посредством значения полученного из MainActivity, определение, какую картинку отобразить
            favoriteButton.setImageResource(R.drawable.staryellowresize);
        }
        else {
            favoriteButton.setImageResource(R.drawable.stargreyresize);
        }

        if (friend) { //косметика
            textFriend.setTextColor(ContextCompat.getColor(this, R.color.lightGreen));
        }
        else  textFriend.setTextColor(ContextCompat.getColor(this, R.color.gray));

        if (work) {
            textWork.setTextColor(ContextCompat.getColor(this, R.color.lightGreen));
        }
        else  textWork.setTextColor(ContextCompat.getColor(this, R.color.gray));

        if (family) {
            textFamily.setTextColor(ContextCompat.getColor(this, R.color.lightGreen));
        }
        else  textFamily.setTextColor(ContextCompat.getColor(this, R.color.gray));

        if (vkCheck.equals("")) { // скрытие кнопок перехода на мессенджеры, если при создание не были указаны ссылки
            vk.setVisibility(View.GONE);
            imgVk.setVisibility(View.GONE);
        }

        if (telegramCheck.equals("")) {
            telegram.setVisibility(View.GONE);
            imgTelegram.setVisibility(View.GONE);
        }

        if (whatsappCheck.equals("")) {
            whatsapp.setVisibility(View.GONE);
            imgWhatsapp.setVisibility(View.GONE);
        }

        if (viberCheck.equals("")) {
            viber.setVisibility(View.GONE);
            imgViber.setVisibility(View.GONE);
        }

        if(twitterCheck.equals("")) {
            twitter.setVisibility(View.GONE);
            imgTwitter.setVisibility(View.GONE);
        }

        if(discordCheck.equals("")) {
            discord.setVisibility(View.GONE);
            imgDiscord.setVisibility(View.GONE);
        }

        name.setText(getName); // получение и утановка имени

        View.OnClickListener pushFavorite = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite) {
                    favoriteButton.setImageResource(R.drawable.stargreyresize);
                }
                else {
                    favoriteButton.setImageResource(R.drawable.staryellowresize);
                }
                if (favorite) favorite = false; //при каждом нажатии изменение чекеров на противоположные
                else favorite = true;
                if (favoritePush) favoritePush = false;
                else favoritePush = true;
            }
        };
        favoriteButton.setOnClickListener(pushFavorite);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.arrowback);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // возврат MainActivity при нажатии FAB
                Intent intent = new Intent();
                intent.putExtra("index", index);
                intent.putExtra("favoriteAdd", favoritePush);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritePush = false; // при каждом переходе в ContactActivity сброс чекера была ли нажата кнопка favorite
    }

    @Override
    public void onClick(View v) { // обработчики нажатия всех кнопок
        Intent intent = new Intent(this, ContactActivity.class);
        switch (v.getId()) {
            case (R.id.vk):
                goToUrl(vkCheck);
                break;
            case (R.id.telegram):
                goToUrl(telegramCheck);
                break;
            case (R.id.whatsapp):
                goToUrl(whatsappCheck);
                break;
            case (R.id.viber):
                goToUrl(viberCheck);
                break;
            case (R.id.twitter):
                goToUrl(twitterCheck);
                break;
            case (R.id.discord):
                goToUrl(discordCheck);
                break;
            case (R.id.phone):
                phoneString = "tel:" + phoneString;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(phoneString)));
                break;
            case (R.id.mail):
                if (!mailString.equals("")) goToUrl(mailString);
                else mail.setText("E-mail не указан");
                break;
            case (R.id.delete): //если была нажата кнопка удалить возврат в MainActivity с параметром isDeleted == true
                isDeleted = true;
                intent.putExtra("index", index);
                intent.putExtra("isDeleted", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
