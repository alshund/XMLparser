package dao;

public class XMLRegularExpressions {

    public static String PREAMBLE = "<\\?xml(.+?)\\?>";
    public static String XML_ELEMENT = "<(?<tag>[^\\/> ]+?)\\s*?(?<attrs>[^>]*)\\s*(?:\\/>|>(?<data>.*)<\\/\\1>)";
    public static String OPENING_TAG = "<(?<tag>[\\s\\?\\<\\>]+)(?:\\s+(?<attrs>.*?))?>";
    public static String CLOSING_TAG = "<\\/(?<tag>[\\s\\?\\<\\>]+)?>";
    public static String ATTR = "(?<name>\\w+)=[\"'](?<value>.+?)[\"']";
    public static String EMPTY_SPACE = "^[\\s\\r\\n]+|[\\s]{2,}|[\\s\\r\\n]+$";

    public static String TAG_GROUP_NAME = "tag";
    public static String ATTRS_GROUP_NAME = "attrs";
    public static String ATTR_NAME = "name";
    public static String ATTR_VALUE = "value";
    public static String DATA = "data";
}