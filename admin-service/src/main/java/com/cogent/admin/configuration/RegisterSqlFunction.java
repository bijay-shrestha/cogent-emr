package com.cogent.admin.configuration;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Configuration;

/**
 * @author smriti on 2019-08-01
 */

@Configuration
public class RegisterSqlFunction implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(
                "group_concat",
                new StandardSQLFunction("group_concat", StandardBasicTypes.STRING)
        );
    }
}
