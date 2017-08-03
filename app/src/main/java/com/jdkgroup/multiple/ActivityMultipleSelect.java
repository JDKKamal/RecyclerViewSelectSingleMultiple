package com.jdkgroup.multiple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdkgroup.recyclerviewselectsinglemultiple.ActivityMain;
import com.jdkgroup.recyclerviewselectsinglemultiple.R;
import com.jdkgroup.signle.ActivitySingleSelectBackground;
import com.jdkgroup.utils.GlobalClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityMultipleSelect extends AppCompatActivity {

    private Toolbar toolbar;
    //private RelativeLayout bottomLayout;

    private Activity act;
    private Context context;

    private boolean flagSearch;
    private boolean flagsortingname;
    private boolean statussortingname;

    private TextView tvTitle;
    private EditText edtSearch;
    private ImageView ivBack, ivSearch, ivSort, ivDoneMultile;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private GlobalClass globalClass;
    private MockData mockData;
    private List<Category> alCategory;

    public static AdapterMultipleSelect adapterMultipleSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_select);

        globalClass = GlobalClass.getInstance();
        mockData = new MockData();
        alCategory = mockData.CategoryList();

        //TODO SELECTED ITEM STORE
        List<Integer> alSelectedItemsStore = globalClass.alSelectedItemsStore;
        alSelectedItemsStore.add(1);
        alSelectedItemsStore.add(3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        edtSearch = (EditText) toolbar.findViewById(R.id.edtSearch);
        ivBack = (ImageView) toolbar.findViewById(R.id.ivBack);
        ivSearch = (ImageView) toolbar.findViewById(R.id.ivSearch);
        ivSort = (ImageView) toolbar.findViewById(R.id.ivSort);
        ivDoneMultile = (ImageView) toolbar.findViewById(R.id.ivDoneMultile);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        //bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_recyclerView);

        act = this;
        context = getApplicationContext();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            tvTitle.setText("Multiple Select");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act, ActivityMain.class));
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagSearch == false) {
                    flagSearch = true;
                    statussortingname = false;

                    tvTitle.setVisibility(View.GONE);
                    ivSearch.setImageResource(R.drawable.va_close);
                    edtSearch.setVisibility(View.VISIBLE);

                    edtSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);

                    addTextListener();
                } else if (flagSearch == true) {
                    flagSearch = false;
                    statussortingname = true;

                    tvTitle.setVisibility(View.VISIBLE);
                    ivSearch.setImageResource(R.drawable.va_search);
                    edtSearch.setVisibility(View.GONE);

                    edtSearch.setText("");
                    edtSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                }
            }
        });

        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagsortingname == false) {
                    Comparator ascCategoryName = Collections.reverseOrder(new sortingascCategoryName());
                    Collections.sort(alCategory, ascCategoryName);
                    adapterMultipleSelect.setFilter(alCategory);

                    flagsortingname = true;
                    statussortingname = false;
                } else if (flagsortingname == true) {
                    Comparator descCategoryName = Collections.reverseOrder(new sortingdescCategoryName());
                    Collections.sort(alCategory, descCategoryName);
                    adapterMultipleSelect.setFilter(alCategory);

                    flagsortingname = false;
                    statussortingname = true;
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (statussortingname == false) {
                            Comparator ascCategoryName = Collections.reverseOrder(new sortingascCategoryName());
                            Collections.sort(alCategory, ascCategoryName);
                            adapterMultipleSelect.setFilter(alCategory);
                        } else if (statussortingname == true) {
                            Comparator descCategoryName = Collections.reverseOrder(new sortingdescCategoryName());
                            Collections.sort(alCategory, descCategoryName);
                            adapterMultipleSelect.setFilter(alCategory);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });

        ivDoneMultile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

                globalClass.alSelectedItemsStore = new ArrayList<>();

                if (adapterMultipleSelect.getSelectedItemCount() == 0) {
                    Toast.makeText(act, "Select any one name.", Toast.LENGTH_SHORT).show();
                } else {
                    edtSearch.setText("");

                    //TODO SELECT CATEGORY GET VALUES
                    System.out.println("Tag " + "SELECT CATEGORY GET VALUES");
                    List<Category> alselectedCategory = adapterMultipleSelect.selectedCategory();
                    for (Category category : alselectedCategory) {
                        int index = alselectedCategory.indexOf(category);
                        System.out.println("Tag " + category.getId() + " - " + category.getName());
                    }
                }

                //TODO SELECTED ITEMS ID
                System.out.println("Tag " + "SELECTED ITEMS ID");
                List<Integer> alselectedCategory = adapterMultipleSelect.getSelectedItems();
                for (Integer selectedcCategory : alselectedCategory) {

                    System.out.println("Tag " + selectedcCategory);
                    globalClass.alSelectedItemsStore.add(selectedcCategory);
                }

                //TODO STORE GLOBAL CLASS SELECTED ITEMS ID GET SIZE
                System.out.println("Tag " + "STORE GLOBAL CLASS SELECTED ITEMS GET SIZE");
                System.out.println("Tag " + globalClass.alSelectedItemsStore.size());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);

        adapterMultipleSelect = new AdapterMultipleSelect(act, alCategory);
        recyclerView.setAdapter(adapterMultipleSelect);

        /*recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                bottomLayout.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadingData();

                        bottomLayout.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });*/
    }

    /*public void loadingData() {
        List<Category> moreModels = mockData.CategoryList(adapter.getItemCount(), 10);
        int curSize = adapter.getItemCount();
        alCategory.addAll(moreModels);

        //adapter.notifyDataSetChanged();
        if (curSize > alCategory.size() - 1) {
            //Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT).show();
        }
    }*/


    private List<Category> filter(List<Category> alCategory, String query) {
        query = query.toLowerCase();

        final List<Category> filteredModelList = new ArrayList<>();
        for (Category category : alCategory) {
            final String text = category.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(category);
            }
        }
        return filteredModelList;
    }


    public void addTextListener() {

        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<Category> filteredModelList = filter(alCategory, query.toString());
                adapterMultipleSelect.setFilter(filteredModelList);
            }
        });
    }


    class sortingascCategoryName implements Comparator<Category> {
        public int compare(Category category1, Category category2) {
            return category1.getName().compareTo(category2.getName());
        }
    }

    class sortingdescCategoryName implements Comparator<Category> {
        public int compare(Category category1, Category category2) {
            return category2.getName().compareTo(category1.getName());
        }
    }
}
