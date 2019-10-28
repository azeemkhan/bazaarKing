package com.tresmoto.batch.reader;

import com.tresmoto.batch.helper.ReportHelper;
import com.tresmoto.batch.model.ADONPRequestDetails;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ADONPReportReader implements ItemReader<ADONPRequestDetails> {

    private ADONPRequestDetails adonpRequestDetails;

    @Autowired
    private ReportHelper reportHelper;

    @Override
    public ADONPRequestDetails read() throws Exception {
        if (null == adonpRequestDetails) {
            adonpRequestDetails = reportHelper.getADONPReportDetails();
            return adonpRequestDetails;
        }
        adonpRequestDetails = null;
        return null;

    }
}
