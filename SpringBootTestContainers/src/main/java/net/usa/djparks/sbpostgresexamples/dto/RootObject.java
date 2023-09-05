package net.usa.djparks.sbpostgresexamples.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RootObject {
    private FileLocation fileLocation;

    @JsonProperty("fileLocation")
    public FileLocation getFileLocation() {
        return fileLocation;
    }

    @JsonProperty("fileLocation")
    public void setFileLocation(FileLocation fileLocation) {
        this.fileLocation = fileLocation;
    }
}
