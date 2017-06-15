package ua.nykyforov.geoip.dbip.api;


public final class GeoEntity {
    private final String country;
    private final String countryCode;

    public GeoEntity(final GeoEntity.Builder builder) {
        this.country = builder.country;
        this.countryCode = builder.countryCode;
    }

    public static class Builder {
        private String countryCode;
        private String country;

        public GeoEntity.Builder withCountryCode(final String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public GeoEntity.Builder withCountry(final String country) {
            this.country = country;
            return this;
        }

        public GeoEntity build() {
            return new GeoEntity(this);
        }
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "GeoEntity{" +
                "country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
