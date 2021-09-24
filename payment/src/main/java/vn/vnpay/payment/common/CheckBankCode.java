package vn.vnpay.payment.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import vn.vnpay.payment.constant.SystemConstant;
import vn.vnpay.payment.dto.BankCode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckBankCode {

    private static final Logger logger = LogManager.getLogger(CheckBankCode.class);

    protected static String getPrivateKey(String key){
        File xmlFile = new File(SystemConstant.PATH_FILE_BANKCODE);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        String privateKey = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Code");
            List<BankCode> bankCodeList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                bankCodeList.add(getCode(nodeList.item(i)));
            }
            for (BankCode bankCode : bankCodeList) {
                if (bankCode.getName().equals(key)){
                    privateKey = bankCode.getPrivateKey();
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            logger.error("Error when read file ", e);
        }
        return privateKey;
    }

    private static BankCode getCode(Node node) {
        BankCode bankCode = new BankCode();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            bankCode.setName(getTagValue("name", element));
            bankCode.setPrivateKey(getTagValue("privateKey", element));
        }
        return bankCode;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
