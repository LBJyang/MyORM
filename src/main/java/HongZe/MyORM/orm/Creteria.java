package HongZe.MyORM.orm;

import java.util.List;

public final class Creteria<T> {
	MyTemplate myTemplate;
	List<String> select = null;
	Mapper<T> mapper;
	String where;
	List<Object> whereParams = null;

	public Creteria(MyTemplate myTemplate) {
		// TODO Auto-generated constructor stub
		this.myTemplate = myTemplate;
	}

	String sql() {
		StringBuilder sb = new StringBuilder(128);
		sb.append("SELECT ");
		sb.append(select == null ? "*" : String.join(",", select));
		sb.append(" FROM ").append(mapper.tableName);
		if (where != null) {
			sb.append(" WHERE ").append(where);
		}
		return sb.toString();
	}

}
