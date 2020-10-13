package com.example.unitedcontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Form {
    public String name;
    public boolean friend, work, family, favorite;
    public String emailAddress, phoneNumber, vkCheck, whatsappCheck, telegramCheck, discordCheck, viberCheck, twitterCheck;
    public Bitmap photo;

    public Form(String name, Bitmap photo, String phoneNumber, String emailAddress, boolean favorite, boolean friend, boolean work, boolean family, String vkCheck, String telegramCheck, String whatsappCheck,
                String viberCheck, String twitterCheck, String discordCheck) {
        this.name = name;
        this.vkCheck = vkCheck;
        this.telegramCheck = telegramCheck;
        this.whatsappCheck = whatsappCheck;
        this.viberCheck = viberCheck;
        this.twitterCheck = twitterCheck;
        this.discordCheck = discordCheck;
        this.friend = friend;
        this.work = work;
        this.family = family;
        this.favorite = favorite;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public String name() {
        return this.name;
    }

    public String vkCheck() {
        return this.vkCheck;
    }

    public String telegramCheck() {
        return this.telegramCheck;
    }

    public String whatsappCheck() {
        return this.whatsappCheck;
    }

    public String viberCheck() {
        return this.viberCheck;
    }

    public String twitterCheck() {
        return this.twitterCheck;
    }

    public String discordCheck() {
        return this.discordCheck;
    }

    public void setName(String name) {
        this.name = name;
    }
}


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
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

    int pushCount1 = 0, pushCount2 = 0, pushCount3 = 0, index = 0, tableRowsCounter = 10, contactsCounter = 10, height, size;
    Drawable drawable;
    TextView console;
    String nameCheck;
    Button friends, work, family;
    TableLayout tableLayout;
    ArrayList<Form> forms = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Button> contacts = new ArrayList<>();
    ArrayList<ImageView> photos = new ArrayList<>();
    ArrayList idPhotos = new ArrayList();
    ArrayList contactsId = new ArrayList();
    ArrayList<TableRow> tableRows = new ArrayList<>();
    Bitmap bitmap;
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    TableRow.LayoutParams photoParams = new TableRow.LayoutParams();
    TableRow.LayoutParams contactParams = new TableRow.LayoutParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        console = findViewById(R.id.console);
        tableLayout = findViewById(R.id.tableLayout);

        friends = findViewById(R.id.buttonSortFriends);
        View.OnClickListener pushFriends = new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //отображение форм группы друзья посредством скрытияя других форм
                for (int i = 0; i < forms.size(); i++) {
                    if (!forms.get(i).friend) {
                        photos.get(i).setVisibility(View.GONE);
                        contacts.get(i).setVisibility(View.GONE);
                    }
                    if (forms.get(i).friend && forms.get(i).name().charAt(forms.get(i).name().length()-1) != '1') { // костыль для того чтобы случайно не отобразить "удаленный" контакт
                        photos.get(i).setVisibility(View.VISIBLE);
                        contacts.get(i).setVisibility(View.VISIBLE);
                    }
                }
                if (++pushCount1 >= 2) {
                    for (int i = 0; i < forms.size(); i++) {
                        if (forms.get(i).name().charAt(forms.get(i).name().length()-1) != '1') { // костыль для того чтобы случайно не отобразить "удаленный" контакт
                            photos.get(i).setVisibility(View.VISIBLE);
                            contacts.get(i).setVisibility(View.VISIBLE);
                        }
                    }
                    pushCount1 = 0;
                }
                pushCount2 = 0;
                pushCount3 = 0;

            }
        };
        friends.setOnClickListener(pushFriends);

        work = findViewById(R.id.buttonSortWork);
        View.OnClickListener pushWork = new View.OnClickListener() { //отображение форм группы коллеги посредством скрытияя других форм
            @Override
            public void onClick(View v) {
                for (int i = 0; i < forms.size(); i++) {
                    if (!forms.get(i).work) {
                        photos.get(i).setVisibility(View.GONE);
                        contacts.get(i).setVisibility(View.GONE);
                    }
                    if (forms.get(i).work && forms.get(i).name().charAt(forms.get(i).name().length()-1) != '1') {
                        photos.get(i).setVisibility(View.VISIBLE);
                        contacts.get(i).setVisibility(View.VISIBLE);
                    }
                }
                if (++pushCount2 >= 2) {
                    for (int i = 0; i < forms.size(); i++) {
                        if (forms.get(i).name().charAt(forms.get(i).name().length()-1) != '1') {
                            photos.get(i).setVisibility(View.VISIBLE);
                            contacts.get(i).setVisibility(View.VISIBLE);
                        }
                    }
                    pushCount2 = 0;
                }
                pushCount1 = 0;
                pushCount3 = 0;
            }
        };
        work.setOnClickListener(pushWork);

        family = findViewById(R.id.buttonSortFamily);
        View.OnClickListener pushFamily = new View.OnClickListener() { //отображение форм группы семья посредством скрытияя других форм
            @Override
            public void onClick(View v) {
                for (int i = 0; i < forms.size(); i++) {
                    if (!forms.get(i).family) {
                        photos.get(i).setVisibility(View.GONE);
                        contacts.get(i).setVisibility(View.GONE);
                    }
                    if (forms.get(i).family && forms.get(i).name().charAt(forms.get(i).name().length()-1) != '1') {
                        photos.get(i).setVisibility(View.VISIBLE);
                        contacts.get(i).setVisibility(View.VISIBLE);
                    }
                }
                if (++pushCount3 >= 2) {
                    for (int i = 0; i < forms.size(); i++) {
                        if(forms.get(i).name().charAt(forms.get(i).name().length()-1) != '1') {
                            photos.get(i).setVisibility(View.VISIBLE);
                            contacts.get(i).setVisibility(View.VISIBLE);
                        }
                    }
                    pushCount3 = 0;
                }
                pushCount1 = 0;
                pushCount2 = 0;
            }
        };
        family.setOnClickListener(pushFamily);

        tableRows.add((TableRow) findViewById(R.id.tableRow1)); // заполнение массива контейнеров внутри таблицы
        tableRows.add((TableRow) findViewById(R.id.tableRow2));
        tableRows.add((TableRow) findViewById(R.id.tableRow3));
        tableRows.add((TableRow) findViewById(R.id.tableRow4));
        tableRows.add((TableRow) findViewById(R.id.tableRow5));
        tableRows.add((TableRow) findViewById(R.id.tableRow6));
        tableRows.add((TableRow) findViewById(R.id.tableRow7));
        tableRows.add((TableRow) findViewById(R.id.tableRow8));
        tableRows.add((TableRow) findViewById(R.id.tableRow9));
        tableRows.add((TableRow) findViewById(R.id.tableRow10));

        photos.add((ImageView) findViewById(R.id.photo1)); // создание массива ссылок на объекты типа ImageView
        photos.add((ImageView) findViewById(R.id.photo2));
        photos.add((ImageView) findViewById(R.id.photo3));
        photos.add((ImageView) findViewById(R.id.photo4));
        photos.add((ImageView) findViewById(R.id.photo5));
        photos.add((ImageView) findViewById(R.id.photo6));
        photos.add((ImageView) findViewById(R.id.photo7));
        photos.add((ImageView) findViewById(R.id.photo8));
        photos.add((ImageView) findViewById(R.id.photo9));
        photos.add((ImageView) findViewById(R.id.photo10));

        idPhotos.add(R.drawable.photodroid); //создание массива id фотографий
        idPhotos.add(R.drawable.photo10);
        idPhotos.add(R.drawable.photo1);
        idPhotos.add(R.drawable.photo2);
        idPhotos.add(R.drawable.testphoto);
        idPhotos.add(R.drawable.photo3);
        idPhotos.add(R.drawable.photo4);
        idPhotos.add(R.drawable.photo5);
        idPhotos.add(R.drawable.photo6);
        idPhotos.add(R.drawable.photo7);

        //заполнение стартового пула форм
        forms.add(new Form("Андроид Макович", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(0)), "79289714447", "https://mail.google.com/mail/u/0/#inbox",true, false, true, false, "https://vk.com/skyrimmine", "https://vk.com/skyrimmine", "http://www.facebook.com/egorkreed", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://discordapp.com/activity"));
        forms.add(new Form("Калыван", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(1)) , "79289714447", "https://mail.google.com/mail/u/0/#inbox",true, true, true, false, "https://vk.com/id209446691", "https://vk.com/skyrimmine", "https://www.facebook.com/DonaldTrump/", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://discordapp.com/activity"));
        forms.add(new Form("Белла Рин", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(2)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, false, false, true, "http://vk.com/kreed58", "https://vk.com/skyrimmine", "https://www.facebook.com/DonaldTrump/", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://discordapp.com/activity"));
        forms.add(new Form("Дин Винчестер", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(3)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, false, true, false, "https://vk.com/skyrimmine", "https://vk.com/skyrimmine", "http://www.facebook.com/egorkreed", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://vk.com/skyrimmine"));
        forms.add(new Form("Владимир Зеленский", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(4)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, false, true, false, "http://vk.com/kreed58", "https://vk.com/skyrimmine", "https://www.facebook.com/DonaldTrump/", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://discordapp.com/activity"));
        forms.add(new Form("Аннет Винд", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(5)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, false, true, false, "https://vk.com/skyrimmine", "https://vk.com/skyrimmine", "https://www.facebook.com/DonaldTrump/", "https://vk.com/skyrimmine", "https://twitter.com/egorkreed", "https://discordapp.com/activity"));
        forms.add(new Form("Джонатан Джостер", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(6)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, false, false, true, "http://vk.com/kreed58", "https://vk.com/skyrimmine", "http://www.facebook.com/egorkreed", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://discordapp.com/activity"));
        forms.add(new Form("Глад Валакас", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(7)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", true, false, false, false, "https://vk.com/id209446691", "https://vk.com/skyrimmine", "https://www.facebook.com/DonaldTrump/", "https://vk.com/skyrimmine", "https://twitter.com/realdonaldtrump", "https://discordapp.com/activity"));
        forms.add(new Form("Джошуа Смит", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(8)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, false, false, true, "http://vk.com/kreed58", "https://vk.com/skyrimmine", "http://www.facebook.com/egorkreed", "https://vk.com/skyrimmine", "https://twitter.com/egorkreed", "https://discordapp.com/activity"));
        forms.add(new Form("ОЛЕГ", BitmapFactory.decodeResource(getResources(), (Integer)idPhotos.get(9)), "79289714447", "https://mail.google.com/mail/u/0/#inbox", false, true, false, false, "https://vk.com/skyrimmine", "https://vk.com/skyrimmine", "https://www.facebook.com/DonaldTrump/", "https://vk.com/skyrimmine", "https://twitter.com/egorkreed", "hhttps://discordapp.com/activity"));

        for (int i = 0; i < forms.size(); i++) { // создание массива имен с возможностью сортировки
            names.add(forms.get(i).name());
        }

        contacts.add((Button) findViewById(R.id.contact1)); // создание масива ссылок на объекты типа Button
        contacts.add((Button) findViewById(R.id.contact2));
        contacts.add((Button) findViewById(R.id.contact3));
        contacts.add((Button) findViewById(R.id.contact4));
        contacts.add((Button) findViewById(R.id.contact5));
        contacts.add((Button) findViewById(R.id.contact6));
        contacts.add((Button) findViewById(R.id.contact7));
        contacts.add((Button) findViewById(R.id.contact8));
        contacts.add((Button) findViewById(R.id.contact9));
        contacts.add((Button) findViewById(R.id.contact10));

        contactsId.add(R.id.contact1); //создание массива id объектов типа Button
        contactsId.add(R.id.contact2);
        contactsId.add(R.id.contact3);
        contactsId.add(R.id.contact4);
        contactsId.add(R.id.contact5);
        contactsId.add(R.id.contact6);
        contactsId.add(R.id.contact7);
        contactsId.add(R.id.contact8);
        contactsId.add(R.id.contact9);
        contactsId.add(R.id.contact10);

        for (int i = 0; i < forms.size(); i++) { // устанавливаем обработчик каждому элементу массива кнопок
            contacts.get(i).setOnClickListener(this);
        }

        //создание параметров отображения новосозданного ImageView
        photoParams.width = 150;
        photoParams.height = 150;
        photoParams.topMargin = 24;

        //создание параметров для отображения новосозданных кнопок
        contactParams.height = 150;
        contactParams.topMargin = 24;
        contactParams.leftMargin = 32;
        contactParams.width = 750;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plusicon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreationActivity.class); //зауск CreationActivity
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //при возвращении из CreatonActivity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // Identify activity Creation
            nameCheck = data.getStringExtra("name");

            if(!nameCheck.equals("")) {
                tableRows.add(new TableRow(this)); // создание пустой ссылки на объект
                tableRows.get(tableRowsCounter).setId(tableRowsCounter); // задание Id в соответствии с порядковым номером
                tableLayout.addView(tableRows.get(tableRowsCounter)); // размещение нового tableRow в tableLayout

                contacts.add(new Button(this)); // создание пустой ссылки на объект кнопка
                contacts.get(contactsCounter).setId(contactsCounter); //присвоение id новосозданной кнопке
                contactsId.add(contactsCounter); // добавление нового id в общий пул id
                contacts.get(contactsCounter).setOnClickListener(this); //установка обработчика нажатий для новой кнопки
                photos.add(new ImageView(this)); // создание пустой ссылки на объект фото

                tableRows.get(tableRowsCounter).addView(photos.get(contactsCounter), photoParams); // добавление в созданную строку тоблицы созданной фотографии
                tableRows.get(tableRowsCounter).addView(contacts.get(contactsCounter), contactParams); // добавление в созданную строку тоблицы созданной кнопки

                contacts.get(contactsCounter).setBackgroundColor(getColor(R.color.hatColor)); //задание новой кнопке background color
                //создание новой формы с получением из CreationActivity всех параметров
                forms.add(new Form(nameCheck, StringToBitMap(data.getStringExtra("bitmap")), data.getStringExtra("phone"), data.getStringExtra("mail"), data.getBooleanExtra("favorite", false), data.getBooleanExtra("friend", false), data.getBooleanExtra("work", false), data.getBooleanExtra("family", false), data.getStringExtra("vkLink"), data.getStringExtra("telegramLink"), data.getStringExtra("whatsappLink"), data.getStringExtra("viberLink"), data.getStringExtra("twitterLink"), data.getStringExtra("discordLink")));
                tableRowsCounter++; // ==10 счетчик строк таблицы
                contactsCounter++; // ==10 счетчик количества кнопок
            }
        }
        if (requestCode == 2) { //при возврате из ContactActivity
            if (!data.getBooleanExtra("isDeleted", false)) {
                if (data.getBooleanExtra("favoriteAdd", false)) { //изменение параметра favorite формы по индексу элемента
                    index = data.getIntExtra("index", 0);
                    if (!forms.get(index).favorite) {
                        forms.get(index).favorite = true;
                    } else {
                        forms.get(index).favorite = false;
                    }
                }
            }
            else {
                // "удаление" элемента с индексом index
                index = data.getIntExtra("index", 0);
/*
                contacts.remove(index);
                photos.remove(index);
                idPhotos.remove(index);
                contactsId.remove(index);

                tableRows.get(index).removeAllViews(); //удаление обьектов из строки
                tableLayout.removeView(tableRows.get(index)); // удаление строки
                forms.remove(index);
*/
                contacts.get(index).setVisibility(View.GONE);
                photos.get(index).setVisibility(View.GONE);
                forms.get(index).setName(forms.get(index).name() + "1");

                for (int i = 0; i < forms.size(); i++) { // устанавливаем обработчик каждому элементу массива кнопок
                    contacts.get(i).setOnClickListener(this);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Collections.sort(forms, new Comparator<Form>() { // Сортировка каждый раз при входе в активность
            @Override
            public int compare(Form one, Form other) {
                return one.name().compareTo(other.name);
            }
        });
        for (int i = 0; i < forms.size(); i++) { // закрашивание кнопок массива contacts в соответствии с параметром favorite
            if (forms.get(i).favorite) {
                contacts.get(i).setBackgroundColor(ContextCompat.getColor(this, R.color.favoriteColor));
            } else {
                contacts.get(i).setBackgroundColor(ContextCompat.getColor(this, R.color.hatColor));
            }
            contacts.get(i).setText(forms.get(i).name); // заполнение массивов кнопок и фотографий значениями из массива форм соответственно
            photos.get(i).setImageBitmap(forms.get(i).photo);
        }
        for (int i = 0; i < forms.size(); i++) { // устанавливаем обработчик каждому элементу массива кнопок
            contacts.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ContactActivity.class);
        for (int i = 0; i < forms.size(); i++) { // пробежка по всем элементам массива id кнопок
            if (v.getId() == (Integer) contactsId.get(i)) { // для определения какая кнопка была нажата с получением ее идекса i, и отправкой всех данных о соответствующей форме для отображения
                bitmap = forms.get(i).photo;
                intent.putExtra("favorite", forms.get(i).favorite);
                intent.putExtra("friend", forms.get(i).friend);
                intent.putExtra("work", forms.get(i).work);
                intent.putExtra("family", forms.get(i).family);
                intent.putExtra("vk", forms.get(i).vkCheck());
                intent.putExtra("telegram", forms.get(i).telegramCheck());
                intent.putExtra("whatsapp", forms.get(i).whatsappCheck());
                intent.putExtra("viber", forms.get(i).viberCheck());
                intent.putExtra("twitter", forms.get(i).twitterCheck());
                intent.putExtra("discord", forms.get(i).discordCheck());
                intent.putExtra("bitmap", BitMapToString(bitmap));
                intent.putExtra("name", forms.get(i).name());
                intent.putExtra("index", i);
                intent.putExtra("phoneString", forms.get(i).phoneNumber);
                intent.putExtra("mailString", forms.get(i).emailAddress);
                startActivityForResult(intent, 2);
            }

        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
