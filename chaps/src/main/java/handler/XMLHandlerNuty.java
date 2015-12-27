package handler;

import android.os.Environment;
import model.NutyXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Pawe³ on 2015-12-18.
 */
public class XMLHandlerNuty {

    public ArrayList<NutyXML> nutyList;

    private NutyXML nuty;

    public void fetchXML(String typ)
    {

        nutyList = new ArrayList<NutyXML>();

        try {
            FileInputStream f = new FileInputStream(Environment.getExternalStorageDirectory()
                    .getPath()  + "/" + "CHAPSApp/"+typ );

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(f);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("pozycja");

            for(int i = 0 ; i < nList.getLength(); i++)
            {
                Node nNode = nList.item(i);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nNode;

                    nuty = new NutyXML();

                    nuty.setTit(e.getElementsByTagName("tit").item(0).getTextContent());
                    if(e.getElementsByTagName("aut").item(0).getTextContent() != null && !e.getElementsByTagName("aut").item(0).getTextContent().equalsIgnoreCase("null"))
                    {
                        nuty.setAut(e.getElementsByTagName("aut").item(0).getTextContent());
                    }else
                    {
                        nuty.setAut(" ");
                    }

                    nuty.setFil(e.getElementsByTagName("fil").item(0).getTextContent());

                    nutyList.add(nuty);
                    }
                }



            f.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


    }
}
