package com.hotel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Booking")
public class Booking {
	@Id
	@Column(columnDefinition = "varchar(10)")
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", parameters = @Parameter(name = "prefix", value = "B"), strategy = "com.hotel.IdGenerator.MyGenerator")
	private String bookingId;
	@Column
	private String hhpId;
	@Column
	private String userid;
	@Column
	private int no_of_adults;
	@Column
	private int children;
	@Column
	private int no_of_rooms;
	@Column
	private String RoomType;
	@Column
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String CIND;
	@Column
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String COTD;

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getHhpId() {
		return hhpId;
	}

	public void setHhpId(String hhpId) {
		this.hhpId = hhpId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getNo_of_adults() {
		return no_of_adults;
	}

	public void setNo_of_adults(int no_of_adults) {
		this.no_of_adults = no_of_adults;
	}

	public String getCIND() {
		return CIND;
	}

	public void setCIND(String cIND) {
		CIND = cIND;
	}

	public String getCOTD() {
		return COTD;
	}

	public void setCOTD(String cOTD) {
		COTD = cOTD;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getNo_of_rooms() {
		return no_of_rooms;
	}

	public void setNo_of_rooms(int no_of_rooms) {
		this.no_of_rooms = no_of_rooms;
	}

	public String getRoomType() {
		return RoomType;
	}

	public void setRoomType(String roomType) {
		RoomType = roomType;
	}
}
