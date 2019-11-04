package me.kingcjy.was.core.utils.pattern;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParserTest {

    @Test
    public void URI_TO_PATTERN_TEST() throws Exception {
        PathPatternParser pathPatternParser = new PathPatternParser("/api/members/{id}/find/{boardId}");
        String uri = "/api/members/1/find/2";
        Assert.assertTrue(pathPatternParser.matches(uri));
    }
}
