package com.mashibing.tank.Serialize;

import java.io.*;


public class SerializeUtil {

    public static byte[] serialize(Object object){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try{
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                baos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais ;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();

        } catch (Exception e) {
        }

        return null;
    }



}
