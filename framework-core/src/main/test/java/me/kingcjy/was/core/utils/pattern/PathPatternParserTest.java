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

    @Test
    public void PATTERN_TEST() throws Exception {

        String data = "Flight AA JFK.101.KRK[2016-12-06]";
        Pattern flightPattern = Pattern.compile("\\w+" + " "
                + "(?<airline>..) "
                + "(?<origin>...)\\." + "(?<number>\\d+)\\." + "(?<destination>...)"
                + "\\[(?<deptDate>\\d+-\\d+-\\d+)\\]");
        Matcher flight = flightPattern.matcher(data);
        flight.find();
        System.out.println("Airline: " + flight.group("airline"));
        System.out.println("Origin: " + flight.group("origin"));
        System.out.println("Number: " + flight.group("number"));
        System.out.println("Destination: " + flight.group("destination"));
        System.out.println("Departure date: " + flight.group("deptDate"));
    }
}
