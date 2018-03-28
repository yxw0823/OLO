package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Xml2JsonUtil {
    public static void main(String[] args) throws Exception {
        /*
         * String xmlStr= readFile("D:/ADA/et/Issue_20130506_back.xml");
         * Document doc= DocumentHelper.parseText(xmlStr); JSONObject json=new
         * JSONObject(); dom4j2Json(doc.getRootElement(),json);
         */
        JSONObject json=new  JSONObject();
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<DOCUMENT>";
        xml += "<ROOT>";
        xml += "<NAME>李四</NAME> <!-- 顾客姓名 -->";
        xml += "<TEL>13855554444</TEL>    <!-- 顾客手机号 -->";
        xml += "<LOGINID>olo-6666</LOGINID> <!-- 当前操作人登录id -->";
        xml += "<OPPNAME>全屋定制</OPPNAME> <!-- 商机名称 -->";
        xml += "<OPPADDR>XXX路XXX小区</OPPADDR> <!-- 商机地址 -->";
        xml += "</ROOT>";
        xml += "</DOCUMENT>";
        json =xml2Json(xml);
        System.out.println("xml2Json:" + json.toString(4));

    }

    public static String readFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
        // fc向buffer中读入数据
        fc.read(bb);
        bb.flip();
        String str = new String(bb.array(), "UTF8");
        fc.close();
        fis.close();
        return str;

    }

    /**
     * xml转json
     * 
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        JSONObject json = new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     * 
     * @param element
     * @param json
     */
    public static void dom4j2Json(Element element, JSONObject json) {
        // 如果是属性
        for (Object o : element.attributes()) {
            Attribute attr = (Attribute) o;
            if (!isEmpty(attr.getValue())) {
                json.put("@" + attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl = element.elements();
        if (chdEl.isEmpty() && !isEmpty(element.getText())) {// 如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }

        for (Element e : chdEl) {// 有子元素
            if (!e.elements().isEmpty()) {// 子元素也有子元素
                JSONObject chdjson = new JSONObject();
                dom4j2Json(e, chdjson);
                Object o = json.opt(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {// 如果此元素已存在,则转为jsonArray
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.put(jsono);
                        jsona.put(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.put(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else {
                    try {
                        json.put(e.getName(), chdjson);
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        // e1.printStackTrace();
                    }
                }

            } else {// 子元素没有子元素
                for (Object o : element.attributes()) {
                    Attribute attr = (Attribute) o;
                    if (!isEmpty(attr.getValue())) {
                        json.put("@" + attr.getName(), attr.getValue());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    public static boolean isEmpty(String str) {

        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
}