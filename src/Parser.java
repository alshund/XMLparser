import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public Entity parseFile(URL resources) {
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(resources.getPath()));
            String line = bufferedReader.readLine();
            parseExpression(line);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseExpression(String expression) {
        if (isTagsAmountValid(expression)) {

        }
    }

    private void processToken(String token) {

    }

    private boolean isTagsAmountValid(String expression) {
        Matcher openingTagsValid = getPatternMatcher(RegularExpressions.OPENING_TAG_VALID, expression);
        Matcher closingTagsValid = getPatternMatcher(RegularExpressions.CLOSING_TAG_VALID, expression);
        int openingTagsAmount = getNumberOfOccurrences(openingTagsValid);
        int closingTagsAmount = getNumberOfOccurrences(closingTagsValid);
        return openingTagsAmount == closingTagsAmount;
    }

    private Matcher getPatternMatcher(String regularExpression, String expressions) {
        Pattern pattern = Pattern.compile(regularExpression);
        return pattern.matcher(expressions);
    }

    private int getNumberOfOccurrences(Matcher matcher) {
        int numberOfOccurrences = 0;
        while(matcher.find()) {
            numberOfOccurrences++;
        }
        return numberOfOccurrences;
    }
}
