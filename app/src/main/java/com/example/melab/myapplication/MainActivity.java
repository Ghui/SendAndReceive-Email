package com.example.melab.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.view.LayoutInflater;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    final Context context = this;
    private String fromEmail;
    private String fromPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button send = (Button) this.findViewById(R.id.mybtn);

        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.user);
                final EditText password = (EditText) promptsView.findViewById(R.id.password);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        fromEmail = userInput.getText().toString();
                                        fromPassword = password.getText().toString();

                                        //send a copy to yourself as well

                                        String toEmails = "ghl246@nyu.edu"+","+fromEmail;
                                        List<String> toEmailList = Arrays.asList(toEmails
                                                .split("\\s*,\\s*"));
                                        Log.i("SendMailActivity", "To List: " + toEmailList);
                                        String emailSubject = "Test for Android Email Send Button";
                                        String emailBody = "Sent within app";
                                        new SendMailTask(MainActivity.this).execute(fromEmail,
                                                fromPassword, toEmailList, emailSubject, emailBody);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();





            }
        });
    }
}
