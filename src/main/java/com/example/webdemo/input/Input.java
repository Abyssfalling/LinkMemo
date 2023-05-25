package com.example.webdemo.input;
import com.example.webdemo.dao.*;
import java.util.*;
import java.io.*;
import java.util.Vector;


public class Input {
    static public boolean judge_if_related(String line) {
        if (line.charAt(0) >= 'a')
            return false;
        else return true;
    }

    static public Vector<String> lineSplit(String line) {
        String result = line.substring(0, line.length() - 2);
        String[] V = result.split(",");

        Vector<String> Vec = new Vector();
        for (int i = 0; i < V.length; i++) {
            Vec.add(V[i]);
        }
        return Vec;

    }

    public static void main(String[] args) {
        File fileIn = new File("D:\\FAOS\\Github\\link_word\\src\\main\\java\\com\\example\\webdemo\\input\\input.txt");
        File fileOut = new File("D:\\FAOS\\Github\\link_word\\src\\main\\java\\com\\example\\webdemo\\input\\error.txt");
        try {

            InputStreamReader fReader = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
            BufferedReader reader = new BufferedReader(fReader);
            OutputStreamWriter fWriter = new OutputStreamWriter(new FileOutputStream(fileOut), "UTF-8");
            BufferedWriter writer = new BufferedWriter(fWriter);
            Link_Dao myDao = new Link_Dao();
            String str;
            boolean flag;
            int point_id, link_index, number;


            while ((str = reader.readLine()) != null) {
                flag = judge_if_related(str);//判断是否中心链接
                str = str.toLowerCase();//
                Vector<String> Vec1 = lineSplit(str);//分割字符串
                Vector<String> Vec=new Vector<String>();
                int length=Vec1.size();
                for (int i=0;i<length;i++){
                    Vec.add(Vec1.get(i).trim());

                }
                link_index = Integer.parseInt((String.valueOf(str.charAt(str.length() - 1))));//判断关系类型
                number = Vec.size();//单词数量
                int point_idX[] = new int[number];//id集合

                try{

                    for ( int i = 0; i < number; i++) {
                        point_idX[i] = myDao.get_center_id(Vec.get(i));
                    }
                    point_id = point_idX[0];//首词id
                    //System.out.println(point_id);
                    //boolean exist = myDao.checkAdd(point_id);//判断链接是否存在
                    //System.out.println(exist);
                    boolean exist = false;
                    Arrays.sort(point_idX);//排序

                    String link = "";
                    System.out.println(link);
                /*for (int i = 1; i < number; i++) {
                    if(i!=1)
                    link += "," + String.valueOf(point_idX[i]);
                    else link+=String.valueOf(point_idX[i]);
                }*/
                    boolean judge = false;
                    if (flag) {

                    } else {
                        for (int i = 0; i < number; i++) {
                            exist = myDao.checkAdd(point_idX[i]);
                            judge = false;
                            link = "";
                            for (int j = 0; j < number; j++) {
                                if (j != i) {
                                    if (judge) {
                                        link += ",";
                                    }
                                    link += String.valueOf(point_idX[j]);
                                    judge = true;
                                }

                            }
                            if (exist) {
                                System.out.println(point_idX[i]);
                                myDao.Renew(point_idX[i], link, link_index);
                            } else {
                                myDao.Add(point_idX[i], link, link_index);
                            }
                        }


                    }


                    System.out.println("数据导入成功");
                }
                catch(Exception e){
                    System.out.println(str);
                    writer.write(str+"\n");
                }

            }
                reader.close();
                fReader.close();
                writer.close();
                fWriter.close();

        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
