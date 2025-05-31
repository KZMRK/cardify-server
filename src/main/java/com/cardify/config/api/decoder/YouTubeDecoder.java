package com.cardify.config.api.decoder;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.jaxb.JAXBDecoder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component
@AllArgsConstructor
public class YouTubeDecoder implements Decoder {

    private final JAXBDecoder jaxbDecoder;

    @Override
    @SneakyThrows
    public Object decode(Response response, Type type) throws FeignException {
        if (!StringUtils.equals(response.headers().get("Content-Type").iterator().next(), "text/xml; charset=UTF-8")) {
            return Util.toString(new InputStreamReader(response.body().asInputStream()));
        }
        return jaxbDecoder.decode(response, type);
    }
}
