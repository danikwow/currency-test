package com.example.currencytest.adapter.dto.request;

import com.example.currencytest.adapter.mapper.CustomDoubleDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "Valute")
public class Valute {
    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String valuteId;

    @JacksonXmlProperty(localName = "NumCode")
    private Integer numCode;

    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;

    @JacksonXmlProperty(localName = "Nominal")
    private Integer nominal;

    @JacksonXmlProperty(localName = "Name")
    private String valuteName;

    @JacksonXmlProperty(localName = "Value")
    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    private Double rate;

    @JacksonXmlProperty(localName = "VunitRate")
    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    private Double vunitRate;
}
