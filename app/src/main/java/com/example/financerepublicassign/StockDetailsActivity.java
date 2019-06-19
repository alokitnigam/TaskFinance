package com.example.financerepublicassign;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financerepublicassign.Adapters.StockAdapter;
import com.example.financerepublicassign.Database.DBTools;
import com.example.financerepublicassign.Model.StockModel;
import com.example.financerepublicassign.Model.StockResponse;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDetailsActivity extends AppCompatActivity {
    Spinner stocksspinner;
    ArrayList<String> stocknames=new ArrayList<>();
    List<StockModel> stockslist;
    ArrayList<StockModel> addedList =new ArrayList<>();
    RecyclerView stocksaddedrv;
    StockAdapter stockAdapter;
    TextView save,totalvalue,totalcurrentvalue;
    int dataCounter=0;
    double total ,currentotal;
    int check = 0;
    long userid;
    static final String APIkey="JZZXMGQ4E2K22VQH";
    private boolean viewType;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIntentData();
        initViews();
        setupRecycler();
        setViewVisibility();
        attachListners();
        if (viewType){
            setupLocalData();

        }else{
            getStockList();

        }
    }

    private void setupLocalData() {
       addedList.addAll(DBTools.getDatabase(StockDetailsActivity.this).feedDao().getStocks(userid));
       stockAdapter.enableEdit(false);
       stockAdapter.notifyDataSetChanged();

        for (int i = 0; i < addedList.size(); i++) {
            getStockData(addedList.get(i),i,true);
        }

    }

    private void setViewVisibility() {
        if (viewType){
            stocksspinner.setVisibility(View.GONE);
            save.setText("Compute Results");
        }
    }

    private void getIntentData() {
        userid=getIntent().getLongExtra("id",0);
        viewType=getIntent().getBooleanExtra("viewType",false);
    }

    private void attachListners() {
        stocksspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(++check > 1) {
                    if(addedList.size()<15){
                        getStockData(stockslist.get(i+1), 0, false);

                    }else{
                        Toast.makeText(StockDetailsActivity.this,getString(R.string.cannotAddmore),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewType){

                    showResultDialog();
                }else{
                    if(addedList.size()>0 && isItemEmpty()){
                        DBTools.getDatabase(StockDetailsActivity.this).feedDao().upateStocks(addedList);
                        DBTools.getDatabase(StockDetailsActivity.this).feedDao().addedEdited(userid);
                        finish();
                    }else{
                        Toast.makeText(StockDetailsActivity.this,getString(R.string.empty),Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }

    private void setupRecycler() {
        stocksaddedrv.setLayoutManager(new LinearLayoutManager(this));
        stockAdapter = new StockAdapter(this,  addedList, new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                addedList.get(position).setUnit(Integer.parseInt(charSeq));
                updateTotalValue();
            }
        });
        stocksaddedrv.setAdapter(stockAdapter);
    }

    private void getStockList() {
        InputStream inputStream = getResources().openRawResource(R.raw.niftylist);
        ReadCSVFile csvFile = new ReadCSVFile(inputStream);
        stockslist = csvFile.read();
        for (int i = 1; i <stockslist.size() ; i++) {
            stocknames.add(stockslist.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(    this,
                android.R.layout.simple_spinner_item,stocknames);
        stocksspinner.setAdapter(adapter);
    }

    private void initViews() {
        stocksspinner=findViewById(R.id.stocksspinner);
        stocksaddedrv=findViewById(R.id.stocksaddedrv);
        totalcurrentvalue=findViewById(R.id.totalcurrentvalue);
        totalvalue=findViewById(R.id.totalvalue);
        save=findViewById(R.id.save);
        progressBar=new ProgressDialog(StockDetailsActivity.this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Fetching...");
    }

    private boolean isItemEmpty() {
        for (int i = 0; i < addedList.size(); i++) {
            if (addedList.get(i).getUnit()==0)
                return false;
        }
        return true;
    }

    private void updateTotalValue() {
        total=0;
        for (int i = 0; i < addedList.size(); i++) {
            total += addedList.get(i).getPrice()*addedList.get(i).getUnit();
        }
        totalvalue.setText("Total sum: "+(total));
    }

    private void updateTotalCurrentValue() {
        currentotal=0;
        for (int i = 0; i < addedList.size(); i++) {
            currentotal += addedList.get(i).getCurrentPrice()*addedList.get(i).getUnit();
        }
        totalcurrentvalue.setVisibility(View.VISIBLE);
        totalcurrentvalue.setText("Total sum for current: "+(currentotal));
        progressBar.dismiss();

    }

    private void getStockData(final StockModel stockModel, final int pos, final boolean getCurrentStock){
        progressBar.show();

        RetrofitClientInstance.getRetrofitInstance().getNewsList("TIME_SERIES_DAILY",stockModel.getSymbol()+".NSE",APIkey).enqueue(new Callback<StockResponse>() {

            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                if(response.isSuccessful()){

                    StockResponse stockResponse=response.body();
//                    stockResponse.timeSeries1min.get("open");
                    assert stockResponse != null;
                    if(stockResponse.getTimeSeries1min()!=null){
                        StockResponse.StockValue values = stockResponse.getTimeSeries1min().get(getTodaysDate());
                        if (values==null)
                        {
                            values = stockResponse.getTimeSeries1min().get(getYesterdaysDate());
                        }
                        if (!getCurrentStock){
                            stockModel.setUserid(userid);
                            stockModel.setPrice(Double.parseDouble(values.getOpen()));
                            stockModel.setDate(getTodaysDate());
                            addedList.add(stockModel);
                            stockAdapter.notifyItemInserted(addedList.size()-1);
                            progressBar.dismiss();


                        }else
                        {
                            stockModel.setCurrentPrice(Double.parseDouble(values.getOpen()));
                            stockModel.setCurrentdate(getTodaysDate());
                            dataCounter++;
                            stockAdapter.notifyItemChanged(pos);

                            if(dataCounter == addedList.size()){
                                updateTotalCurrentValue();
                            }
                        }
                    }else{
                        Toast.makeText(StockDetailsActivity.this,"Unable to fetch data from server.Try Again",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                Toast.makeText(StockDetailsActivity.this,getString(R.string.failed),Toast.LENGTH_LONG).show();
                progressBar.dismiss();
            }
        });
    }

    private String getYesterdaysDate() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(date);
        return formattedDate;
    }

    String getTodaysDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    void showResultDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.result_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView percenttv = dialog.findViewById(R.id.percenttv);
        TextView absolutetv = dialog.findViewById(R.id.absolutetv);
        double absValue=Math.abs(total-currentotal);
        double percentValue=(absValue/total)*100;
        absolutetv.setText(absValue+"");
        percenttv.setText(percentValue+"%");
        dialog.show();
    }
}
