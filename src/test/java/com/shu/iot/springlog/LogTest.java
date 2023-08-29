package com.shu.iot.springlog;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTest {
    @Test
    public void test01(){
        save1("1号机","超声波","test");
    }

    public void save1(String firstName,String secondName,String data) {
        FileOutputStream fop = null;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM");
        String currentTime = simpleDateFormat.format(date);
        try {
            File file = new File(String.format("./log/%s/%s-%s",firstName,secondName,currentTime));
            File fileParent = file.getParentFile();
            if (!fileParent.exists()){
                fileParent.mkdirs();
            }
            if (!file.exists()){
                file.createNewFile();
            }
            fop = new FileOutputStream(file, true);
            byte[] contentInBytes = data.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        }catch (IOException ioException){
            System.out.printf(ioException.toString());
        }
    }
    @Test
    public void test02(){
        JSONObject totalData = new JSONObject();
        totalData.put("status",0);
        totalData.put("msg","");
        JSONArray data = new JSONArray();
        JSONObject o1 = new JSONObject();
        o1.put("conbineNum",1 );

    }
}

/*
{
  "status": 0,
  "msg": "",
  "data": {
    "combineNum": 1,
    "superHeaders": [
      [
        {
          "colspan": 1
        },
        {
          "name": "超声波",
          "colspan": 2
        },
        {
          "name": "修正仪",
          "colspan": 2
        },
        {
          "name": "膜式表",
          "colspan": 2
        },
        {
          "name": "命令检测",
          "colspan": 2
        }
      ]
    ],
    "columns": [
      {
        "name": "工作站",
        "id": "type"
      },

      {
        "name": "最近更新时间",
        "id": "xs1"
      },
      {
        "name": "运行状态",
        "id": "xl1"
      },
      {
        "name": "最近更新时间",
        "id": "xs2"
      },
      {
        "name": "运行状态",
        "id": "xl2"
      },
      {
        "name": "最近更新时间",
        "id": "xs3"
      },
      {
        "name": "运行状态",
        "id": "xl3"
      },
      {
        "name": "最近更新时间",
        "id": "xs4"
      },
      {
        "name": "运行状态",
        "id": "xl4"
      }
    ],
    "rows": [
      {
        "type": "1号机",
        "xs": 6888,
        "xl": 6,
        "xs1": 3954,
        "xl1": 86,
        "xs2": 7366,
        "xl2": 55,
        "xs3": 2241,
        "xl3": 16,
        "xs4": 7233,
        "xl4": 83
      },
      {
        "type": "2号机",
        "xs": 2196,
        "xl": 38,
        "xs1": 6614,
        "xl1": 84,
        "xs2": 245,
        "xl2": 10,
        "xs3": 5896,
        "xl3": 20,
        "xs4": 8885,
        "xl4": 96
      },
      {
        "type": "3号机",
        "xs": 5209,
        "xl": 68,
        "xs1": 1725,
        "xl1": 66,
        "xs2": 9155,
        "xl2": 43,
        "xs3": 3615,
        "xl3": 70,
        "xs4": 2896,
        "xl4": 34
      },
      {
        "type": "4号机",
        "xs": 6233,
        "xl": 3,
        "xs1": 8693,
        "xl1": 48,
        "xs2": 5891,
        "xl2": 55,
        "xs3": 8727,
        "xl3": 15,
        "xs4": 5462,
        "xl4": 32
      },
      {
        "type": "5号机",
        "xs": 2711,
        "xl": 13,
        "xs1": 8499,
        "xl1": 28,
        "xs2": 6206,
        "xl2": 94,
        "xs3": 8233,
        "xl3": 11,
        "xs4": 3492,
        "xl4": 45
      }
    ]
  }
}
 */