//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mkowalski.spring.login.payload.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class EventRequest {
    @NotBlank
    private String name;
    @NotBlank
    @DateTimeFormat
    private LocalDateTime date;
    @DateTimeFormat
    private LocalDateTime dateEnd;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String longDescription;
    private String pictureUrl;
    @NotBlank
    private String type;

    public EventRequest() {
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public LocalDateTime getDateEnd() {
        return this.dateEnd;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public String getType() {
        return this.type;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public void setDateEnd(final LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setShortDescription(final String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(final String longDescription) {
        this.longDescription = longDescription;
    }

    public void setPictureUrl(final String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
