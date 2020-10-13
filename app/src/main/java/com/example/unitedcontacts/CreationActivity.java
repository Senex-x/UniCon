package com.example.unitedcontacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreationActivity extends AppCompatActivity {

    EditText nameText, phoneText, mailText, vkText, telegramText, whatsappText, viberText, twitterText, discordText;
    ImageView imageView;
    CheckBox checkFavorite, checkFriend, checkWork, checkFamily;
    String name, phone, mail;
    Button photoUpload;
    Bitmap bitmap = null;

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void selectImage(Context context) { // метод для создания всплывающего меню для выбора способа загрузки фотографии
        final CharSequence[] options = {"Сделать фотографию", "Выбрать из галереи", "Отмена"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Выберите фото профиля");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Сделать фотографию")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 3);
                }
                else if (options[item].equals("Выбрать из галереи")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 4);
                }
                else if (options[item].equals("Отмена")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameText = findViewById(R.id.name);
        phoneText = findViewById(R.id.phone);
        mailText = findViewById(R.id.mail);
        checkFavorite = findViewById(R.id.checkFavorite);
        checkFriend = findViewById(R.id.checkFriend);
        checkWork = findViewById(R.id.checkWork);
        checkFamily = findViewById(R.id.checkFamily);
        vkText = findViewById(R.id.vkLink);
        telegramText = findViewById(R.id.telegramLink);
        whatsappText = findViewById(R.id.whatsappLink);
        viberText = findViewById(R.id.viberLink);
        twitterText = findViewById(R.id.twitterLink);
        discordText = findViewById(R.id.discordLink);
        imageView = findViewById(R.id.imageView);
        photoUpload = findViewById(R.id.photoUpload);

        photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(CreationActivity.this);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plusicon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.putExtra("name", nameText.getText().toString());
                if (bitmap == null) { // если изображение не было загружения загрузка андроида как аватара
                    bitmap = BitmapFactory.decodeResource(CreationActivity.this.getResources(), R.drawable.photodroid);
                }
                intent.putExtra("bitmap", BitMapToString(bitmap)); // отправка изображение ввиде строки
                intent.putExtra("phone", phoneText.getText().toString());
                intent.putExtra("phoneString", phoneText.getText().toString());
                intent.putExtra("mail", mailText.getText().toString());
                intent.putExtra("mailString", mailText.getText().toString());
                intent.putExtra("favorite", checkFavorite.isChecked());
                intent.putExtra("friend", checkFriend.isChecked());
                intent.putExtra("work", checkWork.isChecked());
                intent.putExtra("family", checkFamily.isChecked());
                intent.putExtra("vkLink", vkText.getText().toString());
                intent.putExtra("vk", vkText.getText().toString());
                intent.putExtra("telegramLink", telegramText.getText().toString());
                intent.putExtra("whatsappLink", whatsappText.getText().toString());
                intent.putExtra("viberLink", viberText.getText().toString());
                intent.putExtra("twitterLink", twitterText.getText().toString());
                intent.putExtra("discordLink", discordText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // метод, выполняющийся, после загрузки фотографиии пользователем
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView = findViewById(R.id.imageView);

        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 3: // если фото было сделвно
                    if (resultCode == RESULT_OK && data != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
                case 4: // если фото было загружено из галереи
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}

