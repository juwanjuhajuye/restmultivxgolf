package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.widget.EditText;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by linhb on 2015-08-21.
 */
public class Util {
    public static int findStringId(String[] arr, String targetValue) {
        return Arrays.asList(arr).indexOf(targetValue);
    }

    public static String paddingLine(String left, String right) {
        return "<tr><td align=\"left\">" + left + "</td><td align=\"right\">" + right + "</td></tr>";
    }

    public static String paddingLine(String text) {
        return "<tr><td colspan=\"2\" align=\"center\">" + text + "</td></tr>";
    }

    public static String findXMl(String data, String node) {
        String extData = "<root>" + data + "</root>";
        ByteArrayInputStream input;
        input = new ByteArrayInputStream(extData.getBytes());

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(input);

            Element rootElement = document.getDocumentElement();
            NodeList items = rootElement.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeName().equals(node))
                    return item.getFirstChild().getNodeValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * get value from input text
     *
     * @param edt ""
     * @param def if edt is null, or with no value
     * @return input value
     */
    public static String getStringFromEdit(EditText edt, String def) {
        if (null == edt) {
            return def;
        }
        String value = edt.getText().toString();
        if(value.length() == 0)
            return def;
        return value;
    }
}
