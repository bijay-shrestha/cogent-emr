package com.cogent.admin.query;

/**
 * @author smriti on 2019-11-05
 */
public class FollowUpSetupQuery {

    public static String QUERY_TO_FETCH_FOLLOW_UP_SETUP =
            "SELECT f.id as id," +                                           //[0]
                    " f.followUpIntervals as followUpIntervals," +           //[1]
                    " f.numberOfFollowUps as numberOfFollowUps," +           //[2]
                    " f.followUpApplicable as followUpApplicable," +         //[3]
                    " f.patientType.name as patientTypeName," +              //[4]
                    " f.status as status" +
                    " FROM FollowUpSetup f" +
                    " WHERE f.status!= 'D'";

    public static String QUERY_TO_FETCH_FOLLOW_UP_SETUP_DETAILS =
            "SELECT " +
                    " f.followUpIntervals as followUpIntervals," +           //[0]
                    " f.numberOfFollowUps as numberOfFollowUps," +           //[1]
                    " f.followUpApplicable as followUpApplicable," +         //[2]
                    " f.patientType.id as patientTypeId," +                  //[3]
                    " f.patientType.name as patientTypeName," +              //[4]
                    " f.status as status," +                                 //[5]
                    " f.remarks as remarks" +                                //[6]
                    " FROM FollowUpSetup f" +
                    " WHERE" +
                    " f.status!= 'D'" +
                    " AND f.id = :id";
}
