package com.nace.nacemicroservice.constants;

/**
 * @author Fozia
 */
public enum NaceEnum {
    ADD_RECORDS_SUCCESS_MSG("Successfully imported CSV file. CSV rows count: "),
    ADD_RECORDS_FAILURE_MSG("Failed in importing CSV file."),
    RECORDS_MSG_TXT(" records. "),
    STATUS_SUCCESS("SUCCESS"),
    STATUS_FAILED("FAILED"),
    ZERO("0");

    private String value;

    private NaceEnum(String value) {
     this.value=value;
    }

    public String getValue(){
        return this.value;
    }
    public enum Errors {
        BAD_REQUEST("BAD_REQUEST"),
        NO_RECORD_FOUND("NO_RECORD_FOUND"),
        INTERNAL_SERVER("INTERNAL_SERVER_ERROR"),
        NO_RECORD_FOUND_MSG("No record found for given order."),
        INTERNAL_SERVER_ERROR_MSG("Something went wrong. Internal server error occured.Please try later.");

        private String value;

        private Errors(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

    }
}
