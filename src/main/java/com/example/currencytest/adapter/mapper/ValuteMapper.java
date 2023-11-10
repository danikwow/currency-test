package com.example.currencytest.adapter.mapper;

import com.example.currencytest.adapter.dto.request.Valute;
import com.example.currencytest.domain.CurrencyRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ValuteMapper {
    @Mapping(source = "numCode", target = "numCode")
    @Mapping(source = "charCode", target = "charCode")
    @Mapping(source = "nominal", target = "nominal")
    @Mapping(source = "valuteName", target = "valuteName")
    @Mapping(source = "rate", target = "rate")
    CurrencyRate toCurrencyRate(Valute valute);
}
