package com.entite;

public class GeoPoint {
	private double lat;
	private double lng;
	
	public GeoPoint(){}
	public GeoPoint(Double la,Double ln){setLat(la);setLng(lng);}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}

}
