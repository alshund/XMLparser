package etity;

public class XMLData implements XMLNode {

    private String xmlData;

    public XMLData(String xmlData) {
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
        return xmlData;
    }
}
