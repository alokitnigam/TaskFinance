package com.example.financerepublicassign;

import com.example.financerepublicassign.Model.StockModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSVFile {
    private InputStream inputStream;

    public ReadCSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List read(){
        List<StockModel> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                StockModel model=new StockModel(row);
                resultList.add(model);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}