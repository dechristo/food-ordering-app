package com.food.ordering.app.common.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class StreetAddress {

    private UUID id;
    private String postalCode;
    private String street;
    private String city;

    public StreetAddress(UUID id, String postalCode, String street, String city) {
        this.id = id;
        this.postalCode = postalCode;
        this.street = street;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetAddress that = (StreetAddress) o;
        return id.equals(that.id) && postalCode.equals(that.postalCode) && street.equals(that.street) && city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postalCode, street, city);
    }
}
