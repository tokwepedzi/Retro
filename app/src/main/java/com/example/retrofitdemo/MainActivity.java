package com.example.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<PostModel>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                }

                List<PostModel> postModelList = response.body();
                for(PostModel post : postModelList){
                    String content = "";
                    content += "ID: "+ post.getId()+"\n";
                    content += "USER ID: "+ post.getUserId()+"\n";
                    content += "TITLE: "+ post.getTitle()+"\n";
                    content += "TEXT: "+ post.getText()+"\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                //Onfailure means something went wrong in communicating with the server or processing the response
                textViewResult.setText(t.getMessage());
            }
        });
    }
}