package me.kingcjy.was.core.web.method.resolver.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kingcjy.was.core.annotations.Autowired;
import me.kingcjy.was.core.annotations.Component;
import me.kingcjy.was.core.annotations.web.RequestBody;
import me.kingcjy.was.core.web.method.MethodParameter;
import me.kingcjy.was.core.web.method.resolver.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class RequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper objectMapper;

    private HttpMessageConverter converter;

    public RequestBodyMethodArgumentResolver() {
        converter = new HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RequestBody.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request) {
        Class<?> parameterType = methodParameter.getParameterType();

        Object returnArgument = null;
        try {
            returnArgument = converter.readBody(request.getInputStream(), parameterType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnArgument;
    }

    private static class HttpMessageConverter {

        private ObjectMapper objectMapper;

        public HttpMessageConverter() { }

        public <T> Object readBody(InputStream inputStream, Class<T> returnType) {

            String body = null;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            T result = null;

            try {
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                }
                body = stringBuilder.toString();
                result = objectMapper.readValue(body, returnType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        public ObjectMapper getObjectMapper() {
            return objectMapper;
        }

        public void setObjectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }
    }

}
