package com.tresmoto.batch.constants;


public interface QueryConstant {

    String REPORT_JOB_QUERY = "SELECT START_TIME,END_TIME FROM (SELECT START_TIME,END_TIME FROM REPORT_JOB_DETAILS ORDER BY END_TIME DESC) WHERE ROWNUM = 1 ";

    String PURCHASE_TRANSACTION = "PURCHASE_TRANSACTION";

    String REFUND_TRANSACTION = "REFUND_TRANSACTION";

    String ABORTED = "ABORTED";

    String SQL_MAPPING = "Statement-SQL-Mapping";

    String FAILED = "FAILED";

    String PENDING = "PENDING";

    String ERROR_CODE = "PAYMENT_GATEWAY_EXCEPTION";

    String SEND_MAIL_FAILED_FOR_REPORT = "report job failed while sending mail";

    String DATE_TIME_FORMAT = "dd-MM-yyyy";

    String ADONP_JOB = "ADONP";

    String ADONP_REPORT = "ADONP_REPORT";

    String EVENT_JOB = "EVENT";

    String PAYMENT_TRANSACTION_JOB = "PAYMENT_TRANSACTION";

}
