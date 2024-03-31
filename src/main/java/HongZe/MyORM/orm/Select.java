/**
 * 
 */
package HongZe.MyORM.orm;

import java.util.Arrays;

/**
 * 
 * 
 */
@SuppressWarnings("rawtypes")
public final class Select extends CriteriaQuery {

	@SuppressWarnings({ "unchecked" })
	public Select(Criteria criteria, String... selectFields) {
		super(criteria);
		// TODO Auto-generated constructor stub
		if (selectFields.length > 0) {
			this.criteria.select = Arrays.asList(selectFields);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> From<T> from(Class<T> entityClass) {
		return new From<T>(this.criteria, this.criteria.myTemplate.getMapper(entityClass));
	}

}
