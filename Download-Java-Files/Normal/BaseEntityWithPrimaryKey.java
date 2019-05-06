package edu.mum.library.model.base;

public abstract class BaseEntityWithPrimaryKey<ID> extends BaseEntity implements IPrimaryKeyGetter<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4052475132651335313L;

	@Override
	public int hashCode() {
		return PrimaryKeyHelper.hashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return PrimaryKeyHelper.equals(this, obj);
	}

	public abstract ID getPrimaryKey();
}
