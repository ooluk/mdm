package com.ooluk.mdm.core.test.database;

import static com.ooluk.mdm.core.test.DatabaseUtils.deleteSQL;
import static com.ooluk.mdm.core.test.DatabaseUtils.insertSQL;
import static com.ooluk.mdm.core.test.DatabaseUtils.json;
import static com.ooluk.mdm.core.test.DatabaseUtils.parseJson;
import static com.ooluk.mdm.core.test.DatabaseUtils.quote;
import static com.ooluk.mdm.core.test.DatabaseUtils.selectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.ooluk.mdm.core.base.data.PropertyGroup;
import com.ooluk.mdm.core.test.TestUtils;

/**
 * A {@link DatabaseTable} implementation for {@link PropertyGroup}.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 * @deprecated
 *
 */
@Deprecated
@Component
public class PropertyGroupTable extends AbstractDatabaseTable<PropertyGroup> {
	
	private String[] fields = { "pgroup_id", "pgroup_name", "pgroup_properties" }; 

	@Override
	public void insert(Long id, PropertyGroup pgroup) {

		TestUtils.setIdField(pgroup, id);
		StringBuilder values = (new StringBuilder())
				.append(pgroup.getId()).append(",")
				.append(quote(pgroup.getName())).append(",")
				.append(quote(json(pgroup.getProperties())));

		String sql = insertSQL("mdm_property_group", fields, values.toString());
		jdbc.execute(sql);
	}

	@Override
	public PropertyGroup select(Long id) {

		String sql = selectSQL("mdm_property_group", fields, "pgroup_id", id);
		
		PropertyGroup pgroup = jdbc.query(sql, new ResultSetExtractor<PropertyGroup>() {
			@Override
			public PropertyGroup extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					PropertyGroup pgroup = new PropertyGroup();
					TestUtils.setIdField(pgroup, rs.getLong("pgroup_id"));
					return pgroup.setName(rs.getString("pgroup_name"))
							.setProperties(parseJson(rs.getString("pgroup_properties")));
				}
				return null;
			}
		});
		return pgroup;
	}
	
	@Override
	public void delete(Long id) {
		String sql = deleteSQL("mdm_property_group", "pgroup_id", id);
		jdbc.execute(sql);
	}
}