package me.kingcjy.was.core.web.method.resolver.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.annotations.web.PathVariable;
import me.kingcjy.was.core.annotations.web.RequestMapping;
import me.kingcjy.was.core.annotations.web.RequestMethod;
import me.kingcjy.was.core.utils.pattern.PathPatternParser;
import me.kingcjy.was.core.web.method.MethodParameter;
import me.kingcjy.was.core.web.method.resolver.HandlerMethodArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PathVariableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(PathVariableHandlerMethodArgumentResolver.class);

    private Map<String, PathPatternParser> pathPatternParserCache = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PathVariable.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request) {
        RequestMapping requestMapping = parameter.getMethod().getAnnotation(RequestMapping.class);
        PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
        String uri = request.getRequestURI();

        if(pathPatternParserCache.get(requestMapping.value()) == null) {
            PathPatternParser pathPatternParser = new PathPatternParser(requestMapping.value());
            pathPatternParserCache.put(requestMapping.value(), pathPatternParser);
        }

        PathPatternParser pathPatternParser = pathPatternParserCache.get(requestMapping.value());

        Map<String, String> pathParameterMap = pathPatternParser.getPathParameterMap(uri);
        try {
            Object object =  objectMapper.readValue(pathParameterMap.get(pathVariable.name()), parameter.getParameterType());
            return object;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
