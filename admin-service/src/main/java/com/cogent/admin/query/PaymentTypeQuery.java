package com.cogent.admin.query;

/**
 * @author Sauravi Thapa 12/11/19
 */
public class PaymentTypeQuery {
    public final static String FETCH_DROP_DOWN_LIST =
            "SELECT " +
                    " pt.id as value," +                        //[0]
                    " pt.paymentMode as label" +                         //[1]
                    " FROM PaymentType pt" +
                    " WHERE pt.status != 'D'";

    public final static String FETCH_ACTIVE_DROP_DOWN_LIST =
            "SELECT " +
                    " pt.id as value," +                        //[0]
                    " pt.paymentMode as label" +                         //[1]
                    " FROM PaymentType pt" +
                    " WHERE pt.status = 'Y'";
}
