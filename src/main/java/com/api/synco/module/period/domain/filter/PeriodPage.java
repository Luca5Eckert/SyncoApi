package com.api.synco.module.period.domain.filter;

import com.api.synco.module.period.domain.PeriodEntity;

public record PeriodPage(
        int pageNumber,
        int pageSize
) {

    public PeriodPage {
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize < 0 ){
            pageSize = 1;
        }
    }
}
