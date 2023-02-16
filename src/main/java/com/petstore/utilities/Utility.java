package com.petstore.utilities;

import com.petstore.pojo.AddPetPojo;
import com.petstore.pojo.Category;
import com.petstore.pojo.Tags;

import java.util.ArrayList;

public class Utility {

    public AddPetPojo pojoObject(String id, String name, String categoryId, String categoryName)
    {

        AddPetPojo pj = new AddPetPojo();
        Category cj = new Category();
        Tags tag = new Tags();
        tag.setId(id);
        tag.setName(name);
        ArrayList<Tags> tagList= new ArrayList<>();
        tagList.add(tag);
        cj.setId(categoryId);
        cj.setName(categoryName);
        pj.setId(id);
        pj.setName(name);
        pj.setCategory(cj);
        pj.setTags(tagList);
        pj.setStatus("available");

        return pj;

    }
}
