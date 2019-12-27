package com.cogent.admin.query;

import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 10/24/19
 */
public class CategoryQuery {
    public final static String FETCH_CATEGORY_DETAILS =
            "SELECT" +
                    " c.name as name," +
                    " c.code as code," +
                    " c.status as status," +
                    " c.remarks as remarks" +
                    " FROM Category c" +
                    " WHERE c.id =:id" +
                    " AND c.status != 'D'";

    public final static String FETCH_CATEGORY_BY_NAME_AND_CODE =
            "SELECT " +
                    " c.name," +
                    " c.code" +
                    " FROM Category c" +
                    " WHERE (c.name =:name" +
                    " OR c.code =:code)" +
                    " AND c.status != 'D'";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_CATEGORY =
            " SELECT" +
                    " c.id as value," +
                    " c.name as label" +
                    " FROM Category c" +
                    " WHERE c.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_CATEGORY =
            " SELECT" +
                    " c.id as value," +
                    " c.name as label" +
                    " FROM Category c" +
                    " WHERE c.status != 'D'";


    public static final Function<CategorySearchRequestDTO, String> QUERY_TO_SEARCH_CATEGORY = (
            CategorySearchRequestDTO -> {

                String query =
                        " SELECT" +
                                " c.id as id," +
                                " c.name as name," +
                                " c.code as code," +
                                " c.status as status" +
                                " FROM Category c";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(CategorySearchRequestDTO));
            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(CategorySearchRequestDTO searchRequestDTO) {
        String query = " WHERE c.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND c.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND c.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND c.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND c.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY c.id DESC";

        return query;
    }

    public final static String FETCH_CATEGORY_ENTITY =
            "SELECT" +
                    " c" +
                    " FROM Category c" +
                    " WHERE c.id =:id" +
                    " AND c.status = 'Y'";

    public final static String CHECK_CATEGORY_NAME_AND_CODE_IF_EXIST =
            "SELECT" +
                    " c.name, " +
                    " c.code " +
                    " FROM" +
                    " Category c" +
                    " Where" +
                    " c.id!=:id" +
                    " AND" +
                    " (c.name =:name" +
                    " OR c.code =:code)" +
                    " AND c.status != 'D'";

}
