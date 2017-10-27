package main;

import entity.XMLNode;

public class PrintXML {

    public static void printTree(XMLNode xmlElement) {

        System.out.println(getTabulation(xmlElement) + xmlElement);

        if(xmlElement.getElements() == null || xmlElement.getElements().isEmpty()) {
            return;
        }

        for (XMLNode element : xmlElement.getElements()) {
            printTree(element);
        }
    }

    private static String getTabulation(XMLNode xmlNode) {
        String tabulation = "";
        for (int tabulationIndex = 1; tabulationIndex < xmlNode.getDepth(); tabulationIndex++) {
            tabulation += "\t";
        }
        return tabulation;
    }
}
