package com.api.synco.module.room_verification.domain.value_object;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
@Builder
public class RoomVerificationForm {

    private boolean allOrganized;

    private String description;

    private String observations;

    private String ticket;

    private RoomVerificationForm() {
    }


    public boolean isAllOrganized() {
        return allOrganized;
    }

    public void setAllOrganized(boolean allOrganized) {
        this.allOrganized = allOrganized;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }




}
