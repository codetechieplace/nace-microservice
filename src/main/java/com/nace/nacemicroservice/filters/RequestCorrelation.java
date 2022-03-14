package com.nace.nacemicroservice.filters;

/**
 * @author Fozia
 */
public class RequestCorrelation {
    public static final String CORRELATION_ID_HEADER = "CORRELATION_ID";

    private static final ThreadLocal<String> id = new ThreadLocal<>();

    public static void setId(String correlationId){
        id.set(correlationId);
    }

    public static String getId(){
        return id.get();
    }
}
