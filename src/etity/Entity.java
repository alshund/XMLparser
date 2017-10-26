package etity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {
    private int depth;
    private String characters;
    private String textElement;
    private Map<String, String> attributes = new HashMap<>();
    private List<Entity> subsidiariesEntities = new ArrayList<>();

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

    public String getTextElement() {
        return textElement;
    }

    public void setTextElement(String textElement) {
        this.textElement = textElement;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<Entity> getSubsidiariesEntities() {
        return subsidiariesEntities;
    }

    public void setSubsidiariesEntities(List<Entity> subsidiariesEntities) {
        this.subsidiariesEntities = subsidiariesEntities;
    }
}
