package ua.nykyforov.geoip.dbip.model;

import ua.nykyforov.geoip.dbip.api.GeoEntity;

import java.net.InetAddress;

public interface GeoAttributes {

    InetAddress getStartInetAddress();

    InetAddress getEndInetAddress();

    GeoEntity getGeoEntity();

    static GeoAttributesImpl.Builder newBuilder() {
        return new GeoAttributesImpl.Builder();
    }

}
