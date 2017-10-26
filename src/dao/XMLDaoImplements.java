package dao;

import etity.Entity;

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
    private Stack<Entity> entities = new Stack<>();

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

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processData(String expression) {
        expression = expression.replaceAll(XMLRegularExpressions.EMPTY_SPACE, "");
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
            String XMLElement = popXMLElement(expression);
            System.out.println(XMLElement);
        }
    }

    private void processOpeningTag(String expression) {
        if (expression.matches(XMLRegularExpressions.OPENING_TAG)) {
            expressions.push(expression);
            depth++;
        }
//        TODO throwException
    }

    private String popXMLElement(String expression) {
        String XMLData = "";
        while (!expressions.isEmpty() && !expressions.peek().matches(XMLRegularExpressions.OPENING_TAG)) {
            XMLData += expressions.pop();
        }
        return expressions.pop() + XMLData + expression;
    }


    @Override
    public Entity valueOf() {

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
