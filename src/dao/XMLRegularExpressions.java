package dao;

public class XMLRegularExpressions {

    public static String PREAMBLE = "<\\?xml(.+?)\\?>";
    public static String XML_ELEMENT = "<(?<tag>[^\\s?<>\"\"'']+?)\\s*?(?<attrs>[^>]*)\\s*(?:\\/>|>(?<data>.*)<\\/\\1>)";
    public static String OPENING_TAG = "<(?<tag>[^\\s?<>\"\"'']+)?(?:\\s+(?<attrs>.*?))?>";
    public static String CLOSING_TAG = "<\\/(?<tag>[^\\s\\?\\<\\>]+)?>";
    public static String SINGLE_TAG = "<(?<tag>[^\\s?<>\"\"'']+?)\\s+?(?<attrs>[^>]+)\\s*(?:\\/)>";
    public static String ATTR = "(?<name>\\w+)=[\"'](?<value>.+?)[\"']";
    public static String EMPTY_SPACE = "^[\\s\\r\\n]+|[\\s]{2,}|[\\s\\r\\n]+$";

    public static String TAG = "tag";
    public static String ATTRS = "attrs";
    public static String DATA = "data";
    public static String ATTR_NAME = "name";
    public static String ATTR_VALUE = "value";
}