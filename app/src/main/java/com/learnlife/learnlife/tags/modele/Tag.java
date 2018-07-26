package com.learnlife.learnlife.tags.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Adama on 16/05/2018.
 */

public class Tag {
    @SerializedName("_id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("category") private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;

        if(obj != null && obj instanceof Tag){
            if(this.id == ((Tag) obj).id)
                res = true;
        }

        return res;
    }

    /**
     * Objet mappé en JSON et utilisé pour update les Tag d'un user via la WebApi
     */
    public static class JsonTag {
        public String[] tags;

        public JsonTag(String[] tags) {
            this.tags = tags;
        }
    }
}
