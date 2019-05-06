package edu.mum.library.model;

import edu.mum.library.model.base.IPrimaryKeyGetter;
import edu.mum.library.model.base.PrimaryKeyHelper;

public class Member extends Person implements IPrimaryKeyGetter<String> {

	/**
	 *
	 */
	private static final long serialVersionUID = -9033791289938541323L;
	private CheckoutRecord checkoutRecord = new CheckoutRecord();;

	private String memberId;

	public Member(String memberId, String firstName, String lastName, String phoneNumber) {
		super(firstName, lastName, phoneNumber);
		this.memberId = memberId;

	}

	public CheckoutEntry checkoutBook(BookCopy bc) {
		return checkoutRecord.checkoutBook(this, bc);
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public String getMemberId() {
		return memberId;
	}

	@Override
	public String getPrimaryKey() {
		return this.getMemberId();
	}

	@Override
	public boolean equals(Object obj) {
		return PrimaryKeyHelper.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return PrimaryKeyHelper.hashCode(this);
	}

	public String printCheckoutRecord() {
		return checkoutRecord.printCheckoutRecord();
	}
}
