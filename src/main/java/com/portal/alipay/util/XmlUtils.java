package com.portal.alipay.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xml工具类
 * @author sushiting
 * @date 2016-09-07
 * @since 1.0
 */
public class XmlUtils {


    public static Map<String, Object> Dom2Map(Document doc){
        Map<String, Object> map = new HashMap<String, Object>();
        if(doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next();
            //System.out.println(e.getName());
            List list = e.elements();
            if(list.size() > 0){
                map.put(e.getName(), Dom2Map(e));
            }else
                map.put(e.getName(), e.getText());
        }
        return map;
    }


    public static Map Dom2Map(Element e){
        Map map = new HashMap();
        List list = e.elements();
        if(list.size() > 0){
            for (int i = 0;i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if(iter.elements().size() > 0){
                    Map m = Dom2Map(iter);
                    if(map.get(iter.getName()) != null){
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    }else
                        map.put(iter.getName(), m);
                }
                else{
                    if(map.get(iter.getName()) != null){
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    }else
                        map.put(iter.getName(), iter.getText());
                }
            }
        }else
            map.put(e.getName(), e.getText());
        return map;
    }

    /**
     * xml转map
     * @param xmlString
     * @return
     * @throws DocumentException
     */
    public static Map<String, Object> xml2Map(String xmlString) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xmlString));
        Map<String, Object> map = XmlUtils.Dom2Map(document);
        return map;

    }

    public static void main(String[] args) throws DocumentException {
        SAXReader reader = new SAXReader();
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<projectDescription>\n" +
                "  <name>MemberManagement</name>\n" +
                "  <comment></comment>\n" +
                "  <projects>\n" +
                "    <project>PRJ1</project>\n" +
                "    <project>PRJ2</project>\n" +
                "    <project>PRJ3</project>\n" +
                "    <project>PRJ4</project>\n" +
                "  </projects>\n" +
                "  <buildSpec>\n" +
                "    <buildCommand>\n" +
                "      <name>org.eclipse.jdt.core.javabuilder</name>\n" +
                "      <arguments>\n" +
                "      </arguments>\n" +
                "    </buildCommand>\n" +
                "  </buildSpec>\n" +
                "  <natures>\n" +
                "    <nature>org.eclipse.jdt.core.javanature</nature>\n" +
                "  </natures>\n" +
                "</projectDescription>";
        Document document = reader.read(new StringReader(xml));
        Map<String, Object> stringObjectMap = XmlUtils.Dom2Map(document);
        for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
            System.out.println(stringObjectEntry.getKey());
        }


    }
}
