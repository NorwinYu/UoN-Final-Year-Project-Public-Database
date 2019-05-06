package edu.mum.library.view.dto;

import edu.mum.library.model.Member;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberDto extends PersonDto<Member> {

	private StringProperty memberId;

	public MemberDto(Member member) {
		super(member);
		this.memberId = new SimpleStringProperty(member.getMemberId());
	}

	public String getMemberId() {
		return memberId.getValue();
	}

	public StringProperty memberIdProperty() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId.set(memberId);
	}

}
