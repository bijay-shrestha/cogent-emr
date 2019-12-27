package com.cogent.admin.feign.configuration;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.val;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

/**
 * @author smriti on 2019-10-09
 */
public class FeignSpringFormEncoder extends FormEncoder {

    /**
     * Constructor with the default Feign's encoder as a delegate.
     */
    public FeignSpringFormEncoder() {
        this(new Default());
    }

    /**
     * Constructor with specified delegate encoder.
     *
     * @param delegate delegate encoder, if this encoder couldn't encode object.
     */
    public FeignSpringFormEncoder(Encoder delegate) {
        super(delegate);

        val processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
        processor.addWriter(new SpringSingleMultipartFileWriter());
        processor.addWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (bodyType.equals(MultipartFile[].class)) {
            val files = (MultipartFile[]) object;
            HashMap<String, List<Object>> data = new HashMap<>();
            data.put("file", new ArrayList<>(Arrays.asList(files)));
            super.encode(data, MAP_STRING_WILDCARD, template);

        } else if (bodyType.equals(MultipartFile.class)) {
            val file = (MultipartFile) object;
            val data = singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);

        } else if (isMultipartFileCollection(object)) {
            val iterable = (Iterable<?>) object;
            val data = new HashMap<String, Object>();
            for (val item : iterable) {
                val file = (MultipartFile) item;
                data.put(file.getName(), file);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        }

//        } else {
//            super.encode(object, bodyType, template);
//        }
    }

    private boolean isMultipartFileCollection(Object object) {
        if (!(object instanceof Iterable)) return false;

        val iterable = (Iterable<?>) object;
        val iterator = iterable.iterator();
        return iterator.hasNext() && iterator.next() instanceof MultipartFile;
    }
}
