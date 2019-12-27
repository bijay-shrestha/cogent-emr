package com.cogent.admin.utils;

import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author smriti on 2019-09-02
 */
public class FileUtils {

   public static MockMultipartHttpServletRequestBuilder configureMultipartForPutMethod(String URL){
      MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload(URL);
      builder.with(request -> {
         request.setMethod("PUT");
         return request;
      });
      return builder;
   }
}
