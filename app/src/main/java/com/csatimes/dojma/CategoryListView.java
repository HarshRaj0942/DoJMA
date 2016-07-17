package com.csatimes.dojma;

/**
 * Created by yash on 16/7/16.
 */
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static java.security.AccessController.getContext;

public class CategoryListView extends ListActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Realm database;
    private RealmResults<HeraldNewsItemFormat> categories;
    private RealmList<HeraldNewsItemFormat> resultsList;



    //private String[] categorydata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                (CategoryListView.this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();
        categories=database.where(HeraldNewsItemFormat.class).findAllSorted("categoryTitle", Sort.ASCENDING);
        resultsList = new RealmList<>();
        Set<HeraldNewsItemFormat> set = new HashSet<>();
        set.addAll(categories);

        //ArrayList<String> myStringArray1 = new ArrayList<String>();
        List<String> catlist=new ArrayList<>(set.size());
        List<HeraldNewsItemFormat> catlistarr=new ArrayList<>();
        catlistarr.addAll(categories);
        int count=catlistarr.size();
        Set<HeraldNewsItemFormat> set2=new HashSet<>();
        set2.addAll(catlistarr);
        int count2=set2.size();
        catlistarr.clear();
        catlist.clear();


        for(HeraldNewsItemFormat temp:set2) {
            if (!set2.contains(catlist)) {
                catlist.add(temp.getCategoryTitle());
            }
        }
            List<String> catlist2=new ArrayList<>();



            for(int i=0;i<catlist.size();i++){
                int flag=0;
                for(int j=i+1;j<catlist.size();j++){
                    if(catlist.get(j).compareToIgnoreCase(catlist.get(i))==0){
                        flag=1;
                        break;

                    }
                }
                if(flag==0)
                {
                    if(catlist.get(i).compareToIgnoreCase("")!=0)
                    catlist2.add(catlist.get(i));
                }
            }




        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view,R.id.label, catlist2);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
       this.setListAdapter(adapter);



    }
}
