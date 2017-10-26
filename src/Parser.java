import etity.Entity;
import dao.XMLRegularExpressions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private int depth = -1;
    private String xmlElement = "";
    private Stack<String> xmlElements = new Stack<>();
    private Stack<Entity> entities = new Stack<>();

    public Entity parseFile(URL resources) {
        try {

            int symbol;
            String xmlElement = "";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(resources.getPath()));
            while ((symbol = bufferedReader.read()) != -1) {
                xmlElement += (char) symbol;
                switch (symbol) {
                    case '<':
                        xmlElement = xmlElement.replace("<", "");
                        xmlElements.push(xmlElement);
                        xmlElement = "<";
                        break;
                    case '>':
                        parseTag(xmlElement);
                        xmlElement = "";
                        break;

                }
             }

            System.out.println(entities.peek().getSubsidiariesEntities().get(0).getSubsidiariesEntities().get(0).getTextElement());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseTag(String expression) {

        if (isClosingTagValid(expression)) {

            parseClosingTag(expression);
        }
        else if (isOpeningTagValid(expression)) {

            parseOpeningTag(expression);
        }
    }

    private void parseOpeningTag(String expression) {
        xmlElements.push(getOpeningTag(expression));
        depth++;
        xmlElement = "";
    }

    private String getOpeningTag(String expression) {
        String openingTag = "";
        Matcher openingTagMatcher = getPatternMatcher(XMLRegularExpressions.OPENING_TAG, expression);
        if (openingTagMatcher.find()) {
            openingTag = openingTagMatcher.group();
        }
        return openingTag;
    }

    private void parseClosingTag(String expression) {

        String closingTagName = getTagName(XMLRegularExpressions.CLOSING_TAG, expression);
        String textElement = popTextElement();
        String openingTag = xmlElements.pop();

        Entity entity = new Entity();
        entity.setTextElement(textElement);
        entity.setCharacters(closingTagName);
        entity.setAttributes(getTagAttributes(openingTag));
        entity.setDepth(depth);

        if (entities.isEmpty() || entities.peek().getDepth() <= depth) {

            entities.push(entity);
        }
        else {
            while(!entities.isEmpty() && entities.peek().getDepth() != depth) {
                entity.getSubsidiariesEntities().add(entities.pop());
            }
            entities.push(entity);
        }
        depth--;

    }

    private String getTagName(String regular, String expression) {
        String tagName = "";
        Matcher tagMatcher = getPatternMatcher(regular, expression);
        if (tagMatcher.find()) {
            tagName = tagMatcher.group(XMLRegularExpressions.TAG_GROUP_NAME);
        }
        return tagName;
    }

    private String popTextElement() {
        String textElement = "";
        while(!xmlElements.isEmpty() && !xmlElements.peek().matches(XMLRegularExpressions.OPENING_TAG)) {
            textElement += xmlElements.pop();
        }
        return textElement;
    }

    private boolean isOpeningTagValid(String expression) {
        Matcher openingTagValid = getPatternMatcher(XMLRegularExpressions.OPENING_TAG, expression);
        return openingTagValid.find();
    }

    private boolean isClosingTagValid(String expression) {
        Matcher closingTagValid = getPatternMatcher(XMLRegularExpressions.CLOSING_TAG, expression);
        return closingTagValid.find();
    }

    private boolean isPreamble(String expression) {
        Matcher preamble = getPatternMatcher(XMLRegularExpressions.PREAMBLE, expression);
        return preamble.find();
    }

    private Matcher getPatternMatcher(String regularExpression, String expressions) {
        Pattern pattern = Pattern.compile(regularExpression);
        return pattern.matcher(expressions);
    }

    private Map<String, String> getTagAttributes(String expression) {
        Map <String, String> attributes = new HashMap<>();
        Matcher openingTagMatcher = getPatternMatcher(XMLRegularExpressions.OPENING_TAG, expression);
        openingTagMatcher.find();
        String attrs = openingTagMatcher.group(XMLRegularExpressions.ATTRS_GROUP_NAME);
        if (attrs != null) {
            Matcher tagAttributesMatcher = getPatternMatcher(XMLRegularExpressions.ATTR, attrs);
            while (tagAttributesMatcher.find()) {
                String key = tagAttributesMatcher.group(XMLRegularExpressions.ATTR_NAME);
                String value = tagAttributesMatcher.group(XMLRegularExpressions.ATTR_VALUE);
                attributes.put(key, value);
            }
        }

        return attributes;
    }
}
