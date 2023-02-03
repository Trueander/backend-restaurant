package com.abs.restaurant.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * @author Entelgy
 */
public final class ObjectMapperHelper {

    private static final ObjectMapperHelper INSTANCE = new ObjectMapperHelper();
    private final ObjectMapper mapper;

    private ObjectMapperHelper() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static ObjectMapperHelper getInstance() {
        return INSTANCE;
    }

    public <T> T readValue(final InputStream src, final Class<T> valueType) throws IOException {
        return mapper.readValue(src, valueType);
    }
}
