package com.jdkgroup.multiple;

/**
 * Created by kamlesh on 10/2/2016.
 */

public class Category
{
    private int id;
    private  String name, search, image;

    public Category(int id, String name, String search, String image) {
        this.id = id;
        this.name = name;
        this.search = search;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
