package com.shu.iot.springlog.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.iot.springlog.common.Mapping;
import com.shu.iot.springlog.controller.LogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class LogDao {
    private static final Logger logger = LoggerFactory.getLogger(LogDao.class);
    public void save(String firstName,String secondName,String data)  {
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
            fop.write("\r\n".getBytes());
            fop.flush();
            fop.close();
        }catch (IOException ioException){
            System.out.printf(ioException.toString());
        }
    }
    public String getNameFromFile(File f){
        String[] strs = f.getPath().split("/");
        String name = strs[strs.length-1];
        return name;
    }

    public String convertTimeName(String s){
        if (s.contains("manager")){
            return "todo";
        }
        if (s.contains("csb")){
            return "time1";
        }
        if (s.contains("xzy")){
            return "time2";
        }
        if (s.contains("ml")){
            return "time3";
        }
        if (s.contains("msb")){
            return "time4";
        }
        if (s.contains("yff")){
            return "time5";
        }
        return "error";
    }

    public String convertStatusName(String s){
        if (s.contains("manager")){
            return "todo";
        }
        if (s.contains("csb")){
            return "status1";
        }
        if (s.contains("xzy")){
            return "status2";
        }
        if (s.contains("ml")){
            return "status3";
        }
        if (s.contains("msb")){
            return "status4";
        }
        if (s.contains("yff")){
            return "status5";
        }
        return "error";
    }
    public String fetchAllData(){
        JSONObject data = generateHeader();
        String path = "./log";
        File file = new File(path);
        File[] fs = file.listFiles();
        List<Map<String,String >> result = new ArrayList<>();
        for (int i = 0 ; i < fs.length; i++){// 工作站层面
            File f = fs[i];
            if (f.isDirectory()){
                Map<String,String> map = new HashMap<>();
                String stationName = getNameFromFile(f);
                map.put("type", Mapping.mapping.get(stationName));
                File[] plats = f.listFiles();
                for (int j = 0 ; j < plats.length; j++){// 检测软件层面
                    File plat = plats[j];
                    String platFileName = getNameFromFile(plat);
                    map.put(convertTimeName(platFileName),String.format("%d",plat.lastModified()));
                    map.put(convertStatusName(platFileName),"mock数据");
                }
                result.add(map);
            }
        }
        JSONArray js = new JSONArray();
        for (int i = 0 ; i < result.size() ; i++){
            Map<String,String> plat = result.get(i);
            JSONObject jsonPlat = new JSONObject();
            jsonPlat.put("type",plat.get("type"));
            jsonPlat.put("time1",plat.get("time1"));
            jsonPlat.put("time2",plat.get("time2"));
            jsonPlat.put("time3",plat.get("time3"));
            jsonPlat.put("time4",plat.get("time4"));
            jsonPlat.put("time5",plat.get("time5"));
            jsonPlat.put("status1",plat.get("status1"));
            jsonPlat.put("status2",plat.get("status2"));
            jsonPlat.put("status3",plat.get("status3"));
            jsonPlat.put("status4",plat.get("status4"));
            jsonPlat.put("status5",plat.get("status5"));
            js.add(jsonPlat);
        }
        data.put("rows",js);
        return data.toJSONString();
    }


    private JSONObject generateHeader(){
        JSONObject data = new JSONObject();
        data.put("conbineNum",1 );
        JSONArray superHeadersWrapper = new JSONArray();
        JSONArray superHeaders = new JSONArray();
        JSONObject o1 = new JSONObject();
        o1.put("colspan",1);
        superHeaders.add(o1);
        JSONObject o2 = new JSONObject();
        o2.put("name","超声波");
        o2.put("colspan",2);
        superHeaders.add(o2);
        JSONObject o3 = new JSONObject();
        o3.put("name","修正仪");
        o3.put("colspan",2);
        superHeaders.add(o3);
        JSONObject o4 = new JSONObject();
        o4.put("name","命令检测");
        o4.put("colspan",2);
        superHeaders.add(o4);
        JSONObject j1 = new JSONObject();
        j1.put("name","膜式表");
        j1.put("colspan",2);
        superHeaders.add(j1);
        JSONObject j2 = new JSONObject();
        j2.put("name","预付费");
        j2.put("colspan",2);
        superHeaders.add(j2);
        superHeadersWrapper.add(superHeaders);
        data.put("superHeaders",superHeadersWrapper);
        JSONArray columns = new JSONArray();
        JSONObject o5 = new JSONObject();
        o5.put("name","工作站");
        o5.put("id","type");
        columns.add(o5);
        JSONObject o6 = new JSONObject();
        o6.put("name","最近更新时间");
        o6.put("id","time1");
        columns.add(o6);
        JSONObject o7 = new JSONObject();
        o7.put("name","运行状态");
        o7.put("id","status1");
        columns.add(o7);
        JSONObject o8 = new JSONObject();
        o8.put("name","最近更新时间");
        o8.put("id","time2");
        columns.add(o8);
        JSONObject o9 = new JSONObject();
        o9.put("name","运行状态");
        o9.put("id","status2");
        columns.add(o9);
        JSONObject o10 = new JSONObject();
        o10.put("name","最近更新时间");
        o10.put("id","time3");
        columns.add(o10);
        JSONObject o11 = new JSONObject();
        o11.put("name","运行状态");
        o11.put("id","status3");
        columns.add(o11);
        JSONObject o12 = new JSONObject();
        o12.put("name","最近更新时间");
        o12.put("id","time4");
        columns.add(o12);
        JSONObject o13 = new JSONObject();
        o13.put("name","运行状态");
        o13.put("id","status4");
        columns.add(o13);
        JSONObject o14 = new JSONObject();
        o14.put("name","最近更新时间");
        o14.put("id","time5");
        columns.add(o14);
        JSONObject o15 = new JSONObject();
        o15.put("name","运行状态");
        o15.put("id","status5");
        columns.add(o15);
        data.put("columns", columns);
        return data;
    }

}
