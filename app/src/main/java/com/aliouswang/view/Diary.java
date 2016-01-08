package com.aliouswang.view;

import java.util.ArrayList;

/**
 * Diary model, and all demo pictures is from internet.
 * Created by aliouswang on 16/1/8.
 */
public class Diary {

    public static final String []
        pics = {"http://pic2.to8to.com/case/1512/19/20151219_489a714d67538d474ee9blrlrjl15gcv_sp.jpg",
            "http://pic2.to8to.com/case/1512/19/20151219_14920b3ee44ace4b7b9423a030l1x1g1_sp.jpg",
            "http://pic2.to8to.com/case/1512/19/20151219_4460f29ebc789f9d3e88kxu9acl1izrd_sp.jpg",
            "http://pic2.to8to.com/case/1512/23/20151223_a08fc926f35299de9a7cs62c7cl1x7cx_sp.jpg",
            "http://pic2.to8to.com/case/1512/23/20151223_11e56932ea4da3535103okt0jcl1q64c_sp.jpg",
            "http://pic2.to8to.com/case/1512/23/20151223_ec06b7096d0380af80ff6wg7ndl1iicb_sp.jpg",
            "http://pic1.to8to.com/case/1512/23/20151223_54197f57a1fbdd389fad721gjgl16n8w_sp.jpg",
            "http://pic2.to8to.com/case/1512/23/20151223_3bcb35bc9e31eda80c29457ju0l1j0wf_sp.jpg",
            "http://pic2.to8to.com/case/1512/23/20151223_deb3d3b239679ae695faesf5u5l1omv9_sp.jpg"
    };

    public ArrayList<String> photos;

    public Diary(int size) {
        size = Math.min(size, pics.length);
        if (size == 0) size = 9;
        photos = new ArrayList<>();
        for (int i = 0;i < size; i ++) {
            photos.add(pics[i]);
        }
    }
}
