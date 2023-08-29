package com.shu.iot.springlog.dao;

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
import java.util.Date;

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
            fop.flush();
            fop.close();
        }catch (IOException ioException){
            System.out.printf(ioException.toString());
        }
    }

    public String fetch(){

        return null;
    }

}
