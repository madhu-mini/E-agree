package com.example.rushi.e_agree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Sell_page extends AppCompatActivity implements View.OnClickListener{


    String email;
    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private EditText editTextName,productprice,productquantity,productowenerno,productoweneraddress;


    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://eduolx.890m.com/Eagree_upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
            private String KEY_PRICE = "price";
            private String KEY_QUANTITY="quantity";
            private String KEY_NO="mobileno";
            private String KEY_ADDRESS="address";
            private String KEY_EMAIL="email1";




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        //Receives the mail from navigation activiy
        Intent intent=getIntent();
        email=intent.getStringExtra("Email1");
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        editTextName= (EditText)findViewById(R.id.productname);
   productprice=(EditText)findViewById(R.id.productprice);
    productquantity=(EditText)findViewById(R.id.product_quantity);
    productowenerno=(EditText)findViewById(R.id.user_contactnumber);
    productoweneraddress=(EditText)findViewById(R.id.user_address);

        imageView  = (ImageView) findViewById(R.id.imageView);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Log.d("From databse",s);
                        //Toast.makeText(Sell_page.this, s , Toast.LENGTH_LONG).show();




                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        try {
                            Toast.makeText(Sell_page.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "inside erroe listener", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new Hashtable<String,String>();


                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                //Getting Image Name
                String name = editTextName.getText().toString().trim();
                String price=productprice.getText().toString().trim();
                String quantity=productquantity.getText().toString().trim();
                String mobileno=productowenerno.getText().toString().trim();
                String email1=email.trim();
                String address=productoweneraddress.getText().toString().trim();
                try {
                    Toast.makeText(getApplicationContext(),"all value obtain",Toast.LENGTH_SHORT).show();
                    Toast.makeText(Sell_page.this,price, Toast.LENGTH_LONG).show();
                    Toast.makeText(Sell_page.this,quantity,  Toast.LENGTH_LONG).show();
                    Toast.makeText(Sell_page.this,mobileno,  Toast.LENGTH_LONG).show();
                    Toast.makeText(Sell_page.this,email1 , Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                params.put(KEY_PRICE,price);
                params.put(KEY_QUANTITY,quantity);
                params.put(KEY_NO,mobileno);
                params.put(KEY_EMAIL,email1);
                params.put(KEY_ADDRESS,address);
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonChoose){
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
            Intent i = new Intent(Sell_page.this, Navigation_page.class);
            //EditText email = (EditText) findViewById(R.id.login_email);
            //String Email=email.getText().toString();
            i.putExtra("Email1",email);
            startActivity(i);
        }
    }

}
