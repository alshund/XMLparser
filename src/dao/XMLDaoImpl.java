package dao;

import dao.exceptions.DAOException;
import entity.XMLData;
import entity.XMLElement;
import entity.XMLNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDaoImpl implements XMLDao {

    public static final String EMPTY_STRING = "";
    public static final String OPENING_TAG_STRING = "<";
    public static final char OPENING_TAG_CHAR = '<';
    public static final char CLOSING_TAG_CHAR = '>';
    public static final String PROBLEMS_WHILE_READING_A_FILE = "Problems while reading a file!";
    public static final String NO_SUCH_ELEMENT = "No such element!";

    private URL resources;

    private int depth = 0;
    private Stack<String> expressions = new Stack<>();
    private Stack<XMLElement> entities = new Stack<>();

    @Override
    public void setFileAddress(String fileAddress) {

        resources = XMLDaoImpl.class.getResource(fileAddress);
    }

    @Override
    public void readFile() throws DAOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(resources.getPath()));
            int readSymbol;
            String expression = EMPTY_STRING;
            try {

                while((readSymbol = reader.read()) != -1) {
                    expression += (char) readSymbol;
                    expression = this.processSymbol((char) readSymbol, expression);
                }

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            throw new DAOException(PROBLEMS_WHILE_READING_A_FILE);
        }
    }

    @Override
    public XMLNode valueOf() {
        return entities.peek();
    }

    private String processSymbol(char symbol, String expression) throws DAOException {
        if (symbol == OPENING_TAG_CHAR) {
            expression = expression.replace(OPENING_TAG_STRING, EMPTY_STRING);
            this.processData(expression);
            expression = OPENING_TAG_STRING;
        }
        else if (symbol == CLOSING_TAG_CHAR) {
            this.processTag(expression);
            expression = EMPTY_STRING;
        }
        return expression;
    }

    private void processData(String expression) {
        expression = expression.replaceAll(XMLRegularExpressions.EMPTY_SPACE, EMPTY_STRING);
        expressions.push(expression);
    }

    private void processTag(String expression) throws DAOException {
        if (expression.matches(XMLRegularExpressions.CLOSING_TAG)) {
            this.processClosingTag(expression);
        }
        else if (expression.matches(XMLRegularExpressions.SINGLE_TAG)) {
            this.processSingleTag(expression);
        }
        else if (expression.matches(XMLRegularExpressions.OPENING_TAG)) {
            this.processOpeningTag(expression);
        } else {
            throw new DAOException(NO_SUCH_ELEMENT);
        }
    }

    private void processClosingTag(String expression) {
        String XMLElement = popExpressions() + expression;
        this.processingXMLElement(XMLElement);
    }

    private void processOpeningTag(String expression) {
        expressions.push(expression);
        depth++;
    }

    private void processSingleTag(String expression) {
        expressions.push(expression);
        this.processingXMLElement(expression);
    }

    private String popExpressions() {
        String XMLData = EMPTY_STRING;
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
            xmlElement.setCharacters(matcher.group(XMLRegularExpressions.TAG));
            this.addAttributes(xmlElement, matcher.group(XMLRegularExpressions.ATTRS));
            this.addElement(xmlElement, matcher.group(XMLRegularExpressions.DATA));
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
        if (expression != null && !expression.isEmpty()) {
            xmlElement.addElement(new XMLData(depth, expression));
            entities.push(xmlElement);
        }
        else if (entities.isEmpty() || isCurrentElementDeeperThanOther()) {
            entities.push(xmlElement);
        }
        else {
            this.popXMLElementsInTo(xmlElement);
            entities.push(xmlElement);
        }
    }

    private boolean isCurrentElementDeeperThanOther() {
        return entities.peek().getDepth() <= depth;
    }

    private void popXMLElementsInTo(XMLElement xmlElement) {
        while(!entities.isEmpty() && isSubsidiaryInTheStack()) {
            xmlElement.addElement(entities.pop());
        }
    }

    private boolean isSubsidiaryInTheStack() {
        return entities.peek().getDepth() != depth;
    }

    private Matcher getMatcher(String regularExpression, String expression) {
        Pattern pattern = Pattern.compile(regularExpression);
        return pattern.matcher(expression);
    }
}