package com.parser.lk.services.requester.centralbank;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CentralBankRequester {

    private final RestTemplate restTemplate;

    @Value("${application.cbr.currency.url}")
    private String requestUrl;


    public CentralBankRequester() {
        this.restTemplate = new RestTemplate();
    }


    public Double getCurrencyByAlias(String alias) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(this.requestUrl).openConnection();
        connection.setRequestMethod("GET");

        InputStream responseStream = connection.getInputStream();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(responseStream);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Valute");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element valuteElement = (Element) nodeList.item(i);
            String code = valuteElement.getElementsByTagName("CharCode").item(0).getTextContent();
            String nominal = valuteElement.getElementsByTagName("Nominal").item(0).getTextContent();
            if (code.equals(alias)) {
                String valueStr = valuteElement.getElementsByTagName("Value").item(0).getTextContent();
                double valueDouble = Double.parseDouble(valueStr.replace(",", "."));
                double nominalDouble = Double.parseDouble(nominal.replace(",", "."));
                return valueDouble / nominalDouble;
            }
        }

        throw new IllegalArgumentException("Валюта с кодом " + alias + " не найдена");
    }

}
