package edu.mum.library.model.base;

public class PrimaryKeyHelper {
	private PrimaryKeyHelper() {

	}

	public static <T> int hashCode(IPrimaryKeyGetter<T> entity) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity.getPrimaryKey() == null) ? 0 : entity.getPrimaryKey().hashCode());
		return result;
	}

	public static <T> boolean equals(IPrimaryKeyGetter<T> t, Object obj) {
		if (t == obj)
			return true;
		if (obj == null)
			return false;
		if (t.getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		IPrimaryKeyGetter<T> other = (IPrimaryKeyGetter<T>) obj;
		if (t.getPrimaryKey() == null) {
			if (other.getPrimaryKey() != null)
				return false;
		} else if (!t.getPrimaryKey().equals(other.getPrimaryKey()))
			return false;
		return true;
	}
}
