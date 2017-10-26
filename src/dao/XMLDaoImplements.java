package dao;

import etity.XMLData;
import etity.XMLElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDaoImplements implements XMLDao {

    private URL resources;

    private int depth = 0;
    private Stack<String> expressions = new Stack<>();
    private Stack<XMLElement> entities = new Stack<>();

    @Override
    public void setFileAddress(String fileAddress) {
        resources = XMLDaoImplements.class.getResource(fileAddress);
    }

    @Override
    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(resources.getPath()));
            int readSymbol = -1;
            String expression = "";
            try {

                while((readSymbol = reader.read()) != -1) {
                    expression += (char) readSymbol;
                    if (readSymbol == '<') {
                        processData(expression.replace("<", ""));
                        expression = "<";
                    } else if (readSymbol == '>') {
                        processTag(expression);
                        expression = "";
                    }
                }
                System.out.println(entities.peek());
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processData(String expression) {
        expression = expression.replaceFirst(XMLRegularExpressions.EMPTY_SPACE, "");
        expressions.push(expression);
    }

    private void processTag(String expression) {
        if (isMatch(XMLRegularExpressions.CLOSING_TAG, expression)) {
            processClosingTag(expression);
        } else if (isMatch(XMLRegularExpressions.OPENING_TAG, expression)) {
            processOpeningTag(expression);
        }
    }

    private void processClosingTag(String expression) {
        if (expression.matches(XMLRegularExpressions.CLOSING_TAG)) {
            String XMLElement = popXMLElement() + expression;
            processingXMLElement(XMLElement);
        }
    }

    private void processOpeningTag(String expression) {
        if (expression.matches(XMLRegularExpressions.OPENING_TAG)) {
            expressions.push(expression);
            depth++;
        }
    }

    private String popXMLElement() {
        String XMLData = "";
        while (!expressions.isEmpty() && !expressions.peek().matches(XMLRegularExpressions.OPENING_TAG)) {
            XMLData += expressions.pop();
        }
        return expressions.pop() + XMLData;
    }

    private void processingXMLElement(String expression) {
        Matcher matcher = getMatcher(XMLRegularExpressions.XML_ELEMENT, expression);
        if (matcher.find()) {
            XMLElement xmlElement = new XMLElement();
            xmlElement.setDepth(this.depth);
            xmlElement.setCharacters(matcher.group(XMLRegularExpressions.TAG_GROUP_NAME));
            this.addAttributes(xmlElement, matcher.group(XMLRegularExpressions.ATTRS_GROUP_NAME));
            addElement(xmlElement, matcher.group(XMLRegularExpressions.DATA));
            depth--;
        }
    }

    private void addAttributes(XMLElement xmlElement, String expressions) {
        Matcher matcher = getMatcher(XMLRegularExpressions.ATTR, expressions);
        while (matcher.find()) {
            String key = matcher.group(XMLRegularExpressions.ATTR_NAME);
            String value = matcher.group(XMLRegularExpressions.ATTR_VALUE);
            xmlElement.putAttribute(key, value);
        }
    }

    private void addElement(XMLElement xmlElement, String expression) {
        if (!expression.isEmpty()) {
            xmlElement.addElement(new XMLData(expression));
            entities.push(xmlElement);
        } else if (entities.isEmpty() || entities.peek().getDepth() <= depth) {
            entities.push(xmlElement);
        } else {
            while(!entities.isEmpty() && entities.peek().getDepth() != depth) {
                xmlElement.addElement(entities.pop());
            }
            entities.push(xmlElement);
        }
    }

    @Override
    public XMLElement valueOf() {

        return entities.peek();
    }

    private boolean isMatch(String regularExpression, String expression) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(expression);
        return matcher.matches();
    }

    private Matcher getMatcher(String regularExpression, String expression) {
        Pattern pattern = Pattern.compile(regularExpression);
        return pattern.matcher(expression);
    }

}