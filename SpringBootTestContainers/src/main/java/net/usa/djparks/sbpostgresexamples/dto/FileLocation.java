package net.usa.djparks.sbpostgresexamples.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileLocation {
    private String[] receiptNumbers;

    @JsonProperty("receiptNumbers")
    public String[] getReceiptNumbers() {
        return receiptNumbers;
    }

    @JsonProperty("receiptNumbers")
    public void setReceiptNumbers(String[] receiptNumbers) {
        this.receiptNumbers = receiptNumbers;
    }
}