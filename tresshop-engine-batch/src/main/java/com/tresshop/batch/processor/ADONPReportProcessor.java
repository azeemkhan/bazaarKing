package com.tresmoto.batch.processor;

import com.tresmoto.batch.constants.PaymentInstrument;
import com.tresmoto.batch.constants.QueryConstant;
import com.tresmoto.batch.entity.PaymentTransactionDetails;
import com.tresmoto.batch.entity.PaymentTransactionSplitUp;
import com.tresmoto.batch.model.ADONPRequestDetails;
import com.tresmoto.batch.model.ReportDetails;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ADONPReportProcessor implements ItemProcessor<ADONPRequestDetails, ReportDetails> {

    @Value("${report.file.location}")
    private String fileLocation;

    @Value("${report.file.name}")
    private String fileName;

    @Value("${report.file.zip.location}")
    private String zipFileLocation;

    @Value("${report.file.zip.name}")
    private String zipFileName;

    @Value("${report.file.adonp-report-name}")
    private String refundReportName;

    @Value("${report.file.purchase-transaction-report-name}")
    private String transactionReportName;

    private String filePath;

    private String zipFilePath;

    @PostConstruct
    public void init() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileLocation);
        stringBuilder.append(fileName);
        filePath = stringBuilder.toString();
        StringBuilder zipStringBuilder = new StringBuilder();
        zipStringBuilder.append(zipFileLocation);
        zipStringBuilder.append(zipFileName);
        zipFilePath = zipStringBuilder.toString();
    }

    private static final Logger log = LoggerFactory.getLogger(EventHandlerProcessor.class);

    private static String[] TRANSACTION_REPORT = {"Order Id", "PE Transaction Id", "Gateway Transaction Id"};

    private static String[] ADONP_REPORT = {"Tenant Transaction Id", "Order Id", "Total Amount", "Transaction Date", "PE Transaction Id", "Gateway Transaction Id", "Gateway Name", "Refund Amount", "Refund Status"};

    @Override
    public ReportDetails process(ADONPRequestDetails adonpRequestDetails) throws Exception {
        if (null != adonpRequestDetails) {
            Map<String, PaymentTransactionDetails> purchaseTransactionMap = adonpRequestDetails.getPurchaseTransactionMap();
            Map<String, PaymentTransactionDetails> refundTransactionsMap = adonpRequestDetails.getRefundTransactionsMap();
            List<PaymentTransactionSplitUp> refundTransactions = adonpRequestDetails.getRefundTransactions();
            Map<String, List<PaymentTransactionSplitUp>> purchaseTransactionSplitUps = adonpRequestDetails.getPurchaseTransactionSplitUps();
            Workbook wb = new HSSFWorkbook();
            OutputStream fileOut = new FileOutputStream(filePath);
            Sheet purchaseTransactionSheet = wb.createSheet(transactionReportName);
            Sheet adonpSheet = wb.createSheet(refundReportName);
            int startRowNumber = 0;
            Row purchaseTransactionRow = purchaseTransactionSheet.createRow(startRowNumber);
            Row adonpRow = adonpSheet.createRow(startRowNumber);
            for (int i = startRowNumber; i < ADONP_REPORT.length; i++) {
                if (i < TRANSACTION_REPORT.length) {
                    purchaseTransactionRow.createCell(i).setCellValue(TRANSACTION_REPORT[i]);
                }
                adonpRow.createCell(i).setCellValue(ADONP_REPORT[i]);
            }
            for (Map.Entry<String, PaymentTransactionDetails> paymentTransactionDetailsEntry : purchaseTransactionMap.entrySet()) {
                String key = paymentTransactionDetailsEntry.getKey();
                PaymentTransactionDetails paymentTransactionDetails = paymentTransactionDetailsEntry.getValue();
                List<PaymentTransactionSplitUp> paymentTransactionSplitUps = purchaseTransactionSplitUps.get(key);
                if (null != paymentTransactionSplitUps && null != paymentTransactionDetails) {
                    for (PaymentTransactionSplitUp paymentTransactionSplitUp : paymentTransactionSplitUps) {
                        if (null != paymentTransactionSplitUp) {
                            ++startRowNumber;
                            Row adonpSheetRow = purchaseTransactionSheet.createRow(startRowNumber);
                            adonpSheetRow.createCell(0).setCellValue(paymentTransactionDetails.getOrderId());
                            adonpSheetRow.createCell(1).setCellValue(paymentTransactionSplitUp.getPaymentTransactionSplitUpId());
                            adonpSheetRow.createCell(2).setCellValue(paymentTransactionSplitUp.getGateWayTransactionId());
                        }
                    }
                }
            }
            startRowNumber = 0;
            for (PaymentTransactionSplitUp refundTransaction : refundTransactions) {
                PaymentTransactionDetails refundTransactionDetails = refundTransactionsMap.get(refundTransaction.getPaymentEngineTransactionId());
                if (null != refundTransactionDetails) {
                    List<PaymentTransactionSplitUp> paymentTransactionSplitUps = purchaseTransactionSplitUps.get(refundTransactionDetails.getParentPaymentEngineTransactionId());
                    PaymentTransactionDetails paymentTransactionDetail = purchaseTransactionMap.get(refundTransactionDetails.getParentPaymentEngineTransactionId());
                    if (null != paymentTransactionSplitUps && null != paymentTransactionDetail) {
                        ++startRowNumber;
                        Row adonpSheetRow = adonpSheet.createRow(startRowNumber);
                        adonpSheetRow.createCell(0).setCellValue(paymentTransactionDetail.getTenantTransactionId());
                        adonpSheetRow.createCell(1).setCellValue(paymentTransactionDetail.getOrderId());
                        if (null != paymentTransactionDetail.getTotalAmount()) {
                            adonpSheetRow.createCell(2).setCellValue(String.valueOf(paymentTransactionDetail.getTotalAmount()));
                        }
                        if (null != paymentTransactionDetail.getCreatedOn()) {
                            adonpSheetRow.createCell(3).setCellValue(String.valueOf(paymentTransactionDetail.getCreatedOn()));
                        }
                        for (PaymentTransactionSplitUp paymentTransactionSplitUp : paymentTransactionSplitUps) {
                            if (null != paymentTransactionSplitUp) {
                                if (null != paymentTransactionSplitUp.getAmount() && paymentTransactionSplitUp.getAmount().equalsIgnoreCase(refundTransaction.getAmount())) {
                                    adonpSheetRow.createCell(4).setCellValue(paymentTransactionSplitUp.getPaymentTransactionSplitUpId());
                                    if (null != paymentTransactionSplitUp.getGateWayTransactionId()) {
                                        adonpSheetRow.createCell(5).setCellValue(paymentTransactionSplitUp.getGateWayTransactionId());
                                    }
                                    if (null != paymentTransactionSplitUp.getInstrumentCode() && PaymentInstrument.INTERNAL_WALLET.name().equalsIgnoreCase(paymentTransactionSplitUp.getInstrumentCode())) {
                                        adonpSheetRow.createCell(6).setCellValue(paymentTransactionSplitUp.getInstrumentInstanceCode());
                                    } else if (null != paymentTransactionDetail.getPaymentGatewayCode()) {
                                        adonpSheetRow.createCell(6).setCellValue(paymentTransactionDetail.getPaymentGatewayCode().name());
                                    }
                                    break;
                                }
                            }
                        }
                        adonpSheetRow.createCell(7).setCellValue(refundTransaction.getAmount());
                        if (QueryConstant.FAILED.equalsIgnoreCase(refundTransaction.getTransactionStatus()))
                            adonpSheetRow.createCell(8).setCellValue(QueryConstant.PENDING);
                        else {
                            adonpSheetRow.createCell(8).setCellValue(refundTransaction.getTransactionStatus());
                        }
                    }
                }
            }
            for (int i = 0; i < ADONP_REPORT.length; i++) {
                if (i < TRANSACTION_REPORT.length) {
                    purchaseTransactionSheet.autoSizeColumn(i);
                }
                adonpSheet.autoSizeColumn(i);
            }
            wb.write(fileOut);
            fileOut.close();
            ReportDetails reportDetails = new ReportDetails();
            zipReportingFile();
            reportDetails.setReportJobDetails(adonpRequestDetails.getReportJobDetails());
            return reportDetails;
        }
        return null;
    }


    private void zipReportingFile() {
        try {
            byte[] buffer = new byte[2048];
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(fileName);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(filePath);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            zos.close();
        } catch (Exception exp) {
            log.error("exception {} while creating zip file for reporting and message is {} ", exp, exp.getMessage());

        }

    }

}
