//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mkowalski.spring.login.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "event"
)
public class Event {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @NotBlank
    @Size(
            max = 20
    )
    private String name;
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @JsonProperty("date")
    private LocalDateTime date;
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @JsonProperty("dateEnd")
    private LocalDateTime dateEnd;
    @NotBlank
    @Size(
            max = 40
    )
    private String shortDescription;
    @NotBlank
    @Size(
            max = 150
    )
    private String longDescription;
    private String pictureUrl;
    @NotBlank
    @Size(
            max = 40
    )
    private String type;

    public Event() {
    }

    public Event(String name, LocalDateTime date, LocalDateTime dateEnd, String shortDescription, String longDescription, String pictureUrl, String type) {
        this.name = name;
        this.date = date;
        this.dateEnd = dateEnd;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pictureUrl = pictureUrl;
        this.type = type;
    }

    public Long getId() {
        return this.id;
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

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @JsonProperty("date")
    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    @JsonProperty("dateEnd")
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
