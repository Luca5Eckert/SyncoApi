package com.api.synco.module.room_verification.domain.filter;

public record RoomVerificationPage(
        int pageNumber,
        int pageSize
) {

    public RoomVerificationPage {
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize < 0 ){
            pageSize = 1;
        }
    }

}
