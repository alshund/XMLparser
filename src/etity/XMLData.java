package etity;

public class XMLData implements XMLNode {

    private int depth;
    private String xmlData;

    public XMLData(int depth, String xmlData) {
        this.depth = depth;
        this.xmlData = xmlData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XMLData xmlData1 = (XMLData) o;

        return xmlData != null ? xmlData.equals(xmlData1.xmlData) : xmlData1.xmlData == null;
    }

    @Override
    public int hashCode() {
        return xmlData != null ? xmlData.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getTabulation() + "-" + xmlData + "\n";
    }

    private String getTabulation() {
        String tabulation = "";
        for (int tabulationIndex = 1; tabulationIndex <= depth; tabulationIndex++) {
            tabulation += "\t";
        }
        return tabulation;
    }
}
