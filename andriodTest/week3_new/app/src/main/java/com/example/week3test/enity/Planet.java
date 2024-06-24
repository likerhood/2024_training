package com.example.week3test.enity;

import com.example.week3test.R;

import java.util.ArrayList;
import java.util.List;

public class Planet {

    public int image;
    public String name;
    public String desc;

    public Planet(int image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

    private static int[] iconArray = {
            R.drawable.iphone,
            R.drawable.oppo,
            R.drawable.vivo,
            R.drawable.huawei,
            R.drawable.xiaomi,
            R.drawable.rongao,
    };

    private static String[] nameArray = {
            "水星",
            "木星",
            "火星",
            "地球",
            "金星",
            "天王星"
    };

    private static String[] descArray = {
            "水星是速度追溯到没时间思考督察内审这款尺码，都将是一个帮我快",
            "木星让他方法是v规划用途的都是些产业体系",
            "火星让她以后改变v担心安全folk九年美国",
            "地球二ui我仍然科比v也是回来你可刺蛾我才不要可使用",
            "金星波斯人伊萨里冰川上饶呀卡机从来都是v有",
            "天王星测牛哥润茶渴望成为莫目光vi人也v被我是"
    };


    // 返回一个默认的集合
    public static List<Planet> getDefaultList(){
        List<Planet> planetList = new ArrayList<>();
        for (int i = 0; i < iconArray.length; i++) {
            planetList.add(new Planet(iconArray[i],nameArray[i], descArray[i]));
        }

        return planetList;
    }


}
