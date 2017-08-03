package com.jdkgroup.multiple;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdkgroup.recyclerviewselectsinglemultiple.R;
import com.jdkgroup.utils.GlobalClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdapterMultipleSelect extends RecyclerView.Adapter<AdapterMultipleSelect.ViewHolder> {

    private Activity act;
    private List<Category> alCategory;

    private List<Category> alSelectCategory;
    private SparseBooleanArray selectedItems;

    private GlobalClass globalClass;
    public AdapterMultipleSelect(Activity act, List<Category> alCategory) {
        this.act = act;
        this.alCategory = alCategory;
        alSelectCategory = new ArrayList<>();

        selectedItems = new SparseBooleanArray();

        globalClass = GlobalClass.getInstance();
        List<Integer> alSelectedItemsStore = globalClass.alSelectedItemsStore;

        for(Integer selecteditemsstore : alSelectedItemsStore)
        {
            //TODO STORE THE ITEMS SELECT
            selectedItems.put(selecteditemsstore, true);
            alSelectCategory.add(new Category(selecteditemsstore, alCategory.get(selecteditemsstore).getName(), alCategory.get(selecteditemsstore).getSearch(), alCategory.get(selecteditemsstore).getImage()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_multiple_select, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Category category = alCategory.get(position);
        viewHolder.tvCategoryName.setText(category.getName());

        if (getSelectedItems().contains(category.getId())) {
            //viewHolder.llvStatusBorder.setBackgroundColor(Color.parseColor("#AE6A42"));
            viewHolder.ivUnFavourite.setVisibility(View.GONE);
            viewHolder.ivFavourite.setVisibility(View.VISIBLE);
        } else {
            //viewHolder.llvStatusBorder.setBackgroundColor(Color.parseColor("#DBBFAD"));
            viewHolder.ivUnFavourite.setVisibility(View.VISIBLE);
            viewHolder.ivFavourite.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return (null != alCategory ? alCategory.size() : 0);
    }

    public void setFilter(List<Category> alFilter) {
        alCategory = new ArrayList<>();
        alCategory.addAll(alFilter);
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    //TODO SELECTED ITEMS CLICK CALL
    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    //TODO ALL SELECTED ITEM CLEAR
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    //TODO SELETED ITEMS GET VALUES
    public void selectCategory(int id, String name, String search, String image) {
        boolean check = containsCategoryID(alSelectCategory, id); //Check Category Id
        if (check == true) {
            //Remove Category
            Iterator<Category> iterator = alSelectCategory.iterator();
            while (iterator.hasNext()) {
                Category categorydelete = iterator.next();
                if (categorydelete.getId() == id) {
                    iterator.remove();
                }
            }
        } else {
            //Add Category
            alSelectCategory.add(new Category(id, name, search, image));
        }
    }

    //TODO ISID CONTAINS(STORE) OR NOT
    public boolean containsCategoryID(List<Category> listCategory, int id) {
        for (Category category : listCategory) {
            if (category != null && category.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    //TODO SELECTEDITEMS ID GET
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryName;
        private final LinearLayout llvStatusBorder;
        private final ImageView ivUnFavourite, ivFavourite;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCategoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);
            llvStatusBorder = (LinearLayout) itemView.findViewById(R.id.llvStatusBorder);
            ivUnFavourite = (ImageView) itemView.findViewById(R.id.ivUnFavourite);
            ivFavourite = (ImageView) itemView.findViewById(R.id.ivFavourite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category category = alCategory.get(getAdapterPosition());

                    //TODO SELECTED ITEMS CLICK CALL
                    toggleSelection(category.getId());

                    selectCategory(category.getId(), category.getName(), category.getSearch(), category.getImage());

                    if (getSelectedItems().contains(alCategory.get(getAdapterPosition()).getId())) {

                        //viewHolder.llvStatusBorder.setBackgroundColor(Color.parseColor("#AE6A42"));
                        ivUnFavourite.setVisibility(View.GONE);
                        ivFavourite.setVisibility(View.VISIBLE);
                    } else {

                        //viewHolder.llvStatusBorder.setBackgroundColor(Color.parseColor("#DBBFAD"));
                        ivUnFavourite.setVisibility(View.VISIBLE);
                        ivFavourite.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    //TODO SELECT ITEM VALUES STORE
    public List<Category> selectedCategory() {
        return alSelectCategory;
    }
}

