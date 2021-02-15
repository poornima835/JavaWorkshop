package com.example.javaworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteActionCompatParcelizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Increament(View view) {
        if(quantity==100){
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }
    public void decreament(View view) {
        if(quantity==1){
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField =(EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MainActivity","Name:"+name);
        CheckBox WhippedCreamCheckBox =(CheckBox) findViewById(R.id.Whipped_cream_checkBox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.Chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.v("MainActivity","Has whipped cream" + hasWhippedCream);
        Log.v("MainActivity","Has chocolate" +hasChocolate);

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);
        displayMessage(priceMessage);

    }





    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int baseprice =5;
        if(addWhippedCream){
            baseprice =baseprice +1;
        }
        if(addChocolate){
            baseprice= baseprice + 2;
        }

        return quantity*baseprice;
    }

    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate ){
        String priceMessage="Name:" + name;
        priceMessage+="\nAdd Whipped Cream?" + addWhippedCream;
        priceMessage+="\nAdd Chocolate?" + addChocolate;
        priceMessage +=  "\nQuantity:" + quantity;
        priceMessage+=   "\nTotal: $"+ price;
        priceMessage+=  "\nThankyou!";
        return priceMessage;
    }




    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView OrderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number); }
}
