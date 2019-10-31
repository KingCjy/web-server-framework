package me.kingcjy.was.core.utils.pattern;

import org.junit.Assert;
import org.junit.Test;

public class PathPatternParserTest {

    @Test
    public void URI_TO_PATTERN_TEST() throws Exception {
        Assert.assertTrue(PathPatternParser.PATTERN.matcher("/api/members/{id}/boards/{boardId}").find());
        Assert.assertTrue(PathPatternParser.PATTERN.matcher("/api/members/{id}/boards/{boardId}").find());
        Assert.assertTrue(PathPatternParser.PATTERN.matcher("/api/members/{id}/boards/{boardId}").find());
        Assert.assertTrue(PathPatternParser.PATTERN.matcher("/api/members/{id}/boards/{boardId}").find());
        Assert.assertTrue(PathPatternParser.PATTERN.matcher("/api/members/{id}/boards/{boardId}").find());
        Assert.assertTrue(PathPatternParser.PATTERN.matcher("/api/members/{id}/boards/{boardId}").find());
    }
}
