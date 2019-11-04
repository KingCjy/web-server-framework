package me.kingcjy.was.core.utils.pattern;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParser {

    public static final String PATTERN = "\\/\\{(.*?)\\}";

    private Pattern URIPattern;
    private Set<String> keys = new HashSet<>();

    public PathPatternParser(String uri) {
        String regex = uri;
        Matcher matcher = Pattern.compile(PATTERN).matcher(uri);
        while(matcher.find()) {
            regex = regex.replace(matcher.group(0), "" +
                    "/(?<" + matcher.group(1) + ">(.*?))");

            this.keys.add(matcher.group(1));
        }
        regex+="$";
        URIPattern = Pattern.compile(regex);
    }

    public boolean matches(String uri) {
        Matcher matcher = URIPattern.matcher(uri);
        return matcher.find();
    }

    public Map<String, String> getPathParameterMap(String uri) {
        Map<String, String> pathParameterMap = new HashMap<>();
        Matcher matcher = URIPattern.matcher(uri);
        boolean find = matcher.find();

        if(find == false) {
            return new HashMap<>();
        }

        keys.forEach(key -> pathParameterMap.put(key, matcher.group(key)));
        return pathParameterMap;
    }
}
