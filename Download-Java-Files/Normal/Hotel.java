package com.hotel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Hotel")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hid", columnDefinition="varchar(10)")
	private int hid;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private String hname;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private String rating;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private double dfcc;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private boolean gym;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private boolean Sp;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private boolean Spa;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private boolean lounge;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private boolean restaurant;
	@NotNull
	@Column(columnDefinition = "varchar(10)")
	private boolean WiFi;
	@Column
	private String imageURL;
	@OneToOne
	@JoinColumn(name = "aid")
	private Address add;

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public double getDfcc() {
		return dfcc;
	}

	public void setDfcc(double dfcc) {
		this.dfcc = dfcc;
	}

	public boolean isGym() {
		return gym;
	}

	public void setGym(boolean gym) {
		this.gym = gym;
	}

	public boolean isSp() {
		return Sp;
	}

	public void setSp(boolean sp) {
		Sp = sp;
	}

	public boolean isSpa() {
		return Spa;
	}

	public void setSpa(boolean spa) {
		Spa = spa;
	}

	public boolean isLounge() {
		return lounge;
	}

	public void setLounge(boolean lounge) {
		this.lounge = lounge;
	}

	public boolean isRestaurant() {
		return restaurant;
	}

	public void setRestaurant(boolean restaurant) {
		this.restaurant = restaurant;
	}

	public boolean isWiFi() {
		return WiFi;
	}

	public void setWiFi(boolean wiFi) {
		WiFi = wiFi;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Address getAdd() {
		return add;
	}

	public void setAdd(Address add) {
		this.add = add;
	}

	@Override
	public String toString() {
		return "Hotel [hid=" + hid + ", hname=" + hname + ", rating=" + rating + ", dfcc=" + dfcc + ", gym=" + gym
				+ ", Sp=" + Sp + ", Spa=" + Spa + ", lounge=" + lounge + ", restaurant=" + restaurant + ", WiFi=" + WiFi
				+ ", imageURL=" + imageURL + ", add=" + add + "]";
	}


}
