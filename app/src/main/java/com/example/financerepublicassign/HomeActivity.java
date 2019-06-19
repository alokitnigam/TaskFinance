package com.example.financerepublicassign;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financerepublicassign.Adapters.UserSavedAdapter;
import com.example.financerepublicassign.Database.DBTools;
import com.example.financerepublicassign.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView addedstocksrv;
    EditText editText;
    Button add;
    UserSavedAdapter userSavedAdapter;
    List<UserModel> models=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        editText=findViewById(R.id.editText);
        add=findViewById(R.id.button);

        addedstocksrv=findViewById(R.id.addedstocksrv);
        addedstocksrv.setLayoutManager(new LinearLayoutManager(this));
        userSavedAdapter=new UserSavedAdapter(this,models);
        addedstocksrv.setAdapter(userSavedAdapter);
        DBTools.getDatabase(this).feedDao().getUserStocks()
                .observe(this, new Observer<List<UserModel>>() {
                    @Override
                    public void onChanged(@Nullable List<UserModel> list) {
                        models.clear();
                        models.addAll(list);
                    }
                });
        userSavedAdapter.notifyDataSetChanged();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().length() == 0){
                    Toast.makeText(HomeActivity.this,"Name cannot be empty",Toast.LENGTH_LONG).show();
                }else{
                    UserModel userModel=new UserModel();
                    userModel.setName(editText.getText().toString());
                    Long id=DBTools.getDatabase(HomeActivity.this).feedDao().updateUser(userModel);
                    Log.i("", "onClick: Added ");
                    Toast.makeText(HomeActivity.this,"User Added",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(HomeActivity.this, StockDetailsActivity.class)
                    .putExtra("id",id).putExtra("viewType",false));

                }
            }
        });
    }
}
