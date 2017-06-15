package ua.nykyforov.geoip.dbip.model;

import ua.nykyforov.geoip.dbip.api.GeoEntity;

import java.net.InetAddress;

public final class GeoAttributesImpl implements GeoAttributes {

    private final String country;
    private final String countryCode;
    private final InetAddress startInetAddress;
    private final InetAddress endInetAddress;

    private GeoAttributesImpl(final Builder builder) {
        this.startInetAddress = builder.startInetAddress;
        this.endInetAddress = builder.endInetAddress;
        this.country = builder.country;
        this.countryCode = builder.countryCode;
    }

    public static class Builder {
        private InetAddress startInetAddress;
        private InetAddress endInetAddress;
        private String country;
        private String countryCode;

        public Builder withStartInetAddress(final InetAddress startInetAddress) {
            this.startInetAddress = startInetAddress;
            return this;
        }

        public Builder withEndInetAddress(final InetAddress endInetAddress) {
            this.endInetAddress = endInetAddress;
            return this;
        }

        public Builder withCountryCode(final String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder withCountry(final String country) {
            this.country = country;
            return this;
        }

        public GeoAttributesImpl build() {
            return new GeoAttributesImpl(this);
        }
    }

    @Override
    public InetAddress getStartInetAddress() {
        return startInetAddress;
    }

    @Override
    public InetAddress getEndInetAddress() {
        return endInetAddress;
    }

    @Override
    public GeoEntity getGeoEntity() {
        return new GeoEntity.Builder()
                .withCountry(country)
                .withCountryCode(countryCode)
                .build();
    }


}
