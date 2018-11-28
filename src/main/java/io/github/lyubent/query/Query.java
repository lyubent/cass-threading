package io.github.lyubent.query;

public class Query {

    private static final String LOCAL_DC = "dc1";
    private static final String REMOTE_DC = "dc2";
    private static final String RF = "1";

    public static String getLocalDC() {
        return LOCAL_DC;
    }

    public static String getRemoteDC() {
        return REMOTE_DC;
    }

    public static String getKeyspaceDefThread() {
        return "CREATE KEYSPACE IF NOT EXISTS multithread WITH REPLICATION = " +
               "{ 'class' : 'NetworkTopologyStrategy', '" + LOCAL_DC + "' : " + RF + ", '" + REMOTE_DC + "' : " + RF + " };";
    }

    public static String getKeyspaceDefSession() {
        return "CREATE KEYSPACE IF NOT EXISTS multisession WITH REPLICATION = " +
                "{ 'class' : 'NetworkTopologyStrategy', '" + LOCAL_DC + "' : " + RF + ", '" + REMOTE_DC + "' : " + RF + " };";
    }

    public static String getTblDefThread() {
        return "CREATE TABLE IF NOT EXISTS multithread.tbl( col1 int PRIMARY KEY, col2 text, col3 int);";
    }

    public static String getPreparedInsertThread() {
        return "INSERT INTO multithread.tbl (col1, col2, col3) values (?, ?, ?)";
    }

    public static String getTblDefSession() {
        return "CREATE TABLE IF NOT EXISTS multisession.tbl( col1 int PRIMARY KEY, col2 text, col3 int);";
    }

    public static String getPreparedInstertSession() {
        return "INSERT INTO multisession.tbl (col1, col2, col3) values (?, ?, ?)";
    }
}
