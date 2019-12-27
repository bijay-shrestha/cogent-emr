package com.cogent.admin.utils;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.Query;
import java.util.function.BiFunction;

/**
 * @author smriti on 2019-09-11
 */
public class QueryUtil {

    public static BiFunction<TestEntityManager, String, Query> getQuery = (testEntityManager, query) ->
            testEntityManager.getEntityManager().createQuery(query);

    public static BiFunction<TestEntityManager, String, Query> getNativeQuery = (testEntityManager, query) ->
            testEntityManager.getEntityManager().createNativeQuery(query);
}
