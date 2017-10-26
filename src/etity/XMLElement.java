package etity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLElement implements XMLNode {

    private int depth;
    private String characters;
    private Map<String, String> attributes = new HashMap<>();
    private List<XMLNode> elements = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XMLElement that = (XMLElement) o;

        if (depth != that.depth) return false;
        if (characters != null ? !characters.equals(that.characters) : that.characters != null) return false;
        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) return false;
        return elements != null ? elements.equals(that.elements) : that.elements == null;
    }

    @Override
    public int hashCode() {
        int result = depth;
        result = 31 * result + (characters != null ? characters.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String elementToString = getTabulation() + this.characters + " " + attributesToString() + ":\n";
        for (XMLNode xmlNode : elements) {
            elementToString += xmlNode.toString();
        }
        return elementToString;
    }

    private String getTabulation() {
        String tabulation = "";
        for (int tabulationIndex = 1; tabulationIndex < depth; tabulationIndex++) {
            tabulation += "\t";
        }
        return tabulation;
    }

    private String attributesToString() {
        String attributesToString = "";
        if (!attributes.isEmpty()) {
            for (String key : attributes.keySet()) {
                attributesToString = key + "=" + attributes.get(key) + " ";
            }
        }
        return attributesToString;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public void putAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void addElement(XMLNode xmlNode) {
        elements.add(xmlNode);
    }
}
