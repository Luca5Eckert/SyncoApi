package com.api.synco.module.room_verification.domain.value_object;

public class RoomVerificationForm {

    private boolean allOrganized;

    private String description;

    private String observations;

    private String ticket;

    public RoomVerificationForm(Builder builder) {
        this.allOrganized = builder.allOrganized;
        this.description = builder.description;
        this.observations = builder.observations;
        this.ticket = builder.ticket;
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

    public class Builder {

        private final boolean allOrganized;

        private String description;

        private String observations;

        private String ticket;

        public Builder(boolean allOrganized) {
            this.allOrganized = allOrganized;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder observations(String observations) {
            this.observations = observations;
            return this;
        }

        public RoomVerificationForm build() {
            return new RoomVerificationForm(this);
        }
    }


}
