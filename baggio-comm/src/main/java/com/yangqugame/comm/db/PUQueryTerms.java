package com.yangqugame.comm.db;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PUQueryTerms {
	List<Where> wheres = new LinkedList();
	List<String> orderBys = new LinkedList();
	int limitStart = 0;
	int limitEnd = -1;

	public PUQueryTerms() {
	}

	public static PUQueryTerms create() {
		return new PUQueryTerms();
	}

	public PUQueryTerms where(String key, Object value) {
		return this.where(key, "=", value);
	}

	public PUQueryTerms whereSql(String sql, Object... values) {
		PUQueryTerms.Where w = new PUQueryTerms.Where();
		w.sql = sql;

		for(int i = 0; i < values.length; ++i) {
			w.sqlValues.add(values[i]);
		}

		this.wheres.add(w);
		return this;
	}

	public PUQueryTerms where(String key, String op, Object value) {
		PUQueryTerms.Where w = new PUQueryTerms.Where();
		w.key = key;
		w.operator = op;
		w.value = value;
		this.wheres.add(w);
		return this;
	}

	public PUQueryTerms orderBy(String key) {
		this.orderBys.add(key);
		return this;
	}

	public PUQueryTerms limit(int start, int limit) {
		this.limitStart = start;
		this.limitEnd = limit;
		return this;
	}

	public PUQueryTerms limit(int end) {
		this.limitStart = 0;
		this.limitEnd = end;
		return this;
	}

	Object[] whereValues() {
		List<Object> ret = new LinkedList();
		Iterator var2 = this.wheres.iterator();

		while(var2.hasNext()) {
			PUQueryTerms.Where w = (PUQueryTerms.Where)var2.next();
			if(w.key != null) {
				ret.add(w.value);
			} else {
				ret.addAll(w.sqlValues);
			}
		}

		return ret.toArray();
	}

	public String whereStatement() {
		StringBuilder sql = new StringBuilder();
		sql.append(" ");
		Iterator var2 = this.wheres.iterator();

		while(var2.hasNext()) {
			PUQueryTerms.Where w = (PUQueryTerms.Where)var2.next();
			if(w.key != null) {
				sql.append(" and ");
				sql.append("`").append(w.key).append("` ");
				sql.append(w.operator).append(" ");
				if(w.operator.trim().equalsIgnoreCase("like")) {
					sql.append(" concat('%',?,'%') ");
				} else {
					sql.append(" ? ");
				}
			} else {
				sql.append(" " + w.sql + " ");
			}
		}

		sql.append(" ");
		return sql.toString();
	}

	static class Where {
		public String key;
		public Object value;
		public String operator;
		public String sql;
		public LinkedList<Object> sqlValues = new LinkedList();

		public Where() {
		}
	}
}
