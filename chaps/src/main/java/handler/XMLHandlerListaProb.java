package handler;

import android.os.Environment;
import model.Proba;
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
public class XMLHandlerListaProb {

    public ArrayList<Proba> probaList = new ArrayList<Proba>();
    public ArrayList<String> listaDat = new ArrayList<String>();
    private Proba proba;

    public void fetchXML()
    {

        //FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/" + "planProby.sav");
        try {
            FileInputStream f = new FileInputStream(Environment.getExternalStorageDirectory()
                    .getPath()  + "/" + "CHAPSApp/plan.xml" );

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(f);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("proba");
            NodeList zList = doc.getElementsByTagName("pozycja");
            for(int i = 0 ; i < nList.getLength(); i++)
            {
                Node nNode = nList.item(i);
                Node zNode = zList.item(i);



                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) nNode;
                    Element ez = (Element) zNode;

                    proba = new Proba();
                    proba.setData(e.getElementsByTagName("data").item(0).getTextContent());
                    proba.setDzien(e.getElementsByTagName("dzien").item(0).getTextContent());
                    proba.setGodzina(ez.getElementsByTagName("godzina").item(0).getTextContent());
                    proba.setRodzaj(ez.getElementsByTagName("rodzaj").item(0).getTextContent());
                    proba.setProgram(ez.getElementsByTagName("program").item(0).getTextContent());
                    proba.setUwagi(ez.getElementsByTagName("uwagi").item(0).getTextContent());

                    listaDat.add(proba.getData());
                    probaList.add(proba);
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
