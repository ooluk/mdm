package com.ooluk.mdm.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ooluk.mdm.core.app.data.Label;
import com.ooluk.mdm.core.app.data.LabelType;
import com.ooluk.mdm.core.app.data.Tag;
import com.ooluk.mdm.core.app.repository.LabelRepository;
import com.ooluk.mdm.core.app.repository.LabelRepositoryImpl;
import com.ooluk.mdm.core.app.repository.LabelTypeRepository;
import com.ooluk.mdm.core.app.repository.LabelTypeRepositoryImpl;
import com.ooluk.mdm.core.app.repository.TagRepository;
import com.ooluk.mdm.core.app.repository.TagRepositoryImpl;
import com.ooluk.mdm.core.attribute.data.Attribute;
import com.ooluk.mdm.core.attribute.repository.AttributeNoteRepository;
import com.ooluk.mdm.core.attribute.repository.AttributeNoteRepositoryImpl;
import com.ooluk.mdm.core.attribute.repository.AttributeRepository;
import com.ooluk.mdm.core.attribute.repository.AttributeRepositoryImpl;
import com.ooluk.mdm.core.attribute.repository.AttributeSourceRepository;
import com.ooluk.mdm.core.attribute.repository.AttributeSourceRepositoryImpl;
import com.ooluk.mdm.core.base.data.PropertyGroup;
import com.ooluk.mdm.core.base.repository.DynamicPropertyRepository;
import com.ooluk.mdm.core.base.repository.DynamicPropertyRepositoryImpl;
import com.ooluk.mdm.core.base.repository.ListValueRepository;
import com.ooluk.mdm.core.base.repository.ListValueRepositoryImpl;
import com.ooluk.mdm.core.base.repository.PropertyGroupRepository;
import com.ooluk.mdm.core.base.repository.PropertyGroupRepositoryImpl;
import com.ooluk.mdm.core.dataobject.data.DataObject;
import com.ooluk.mdm.core.dataobject.data.Namespace;
import com.ooluk.mdm.core.dataobject.repository.DataObjectNoteRepository;
import com.ooluk.mdm.core.dataobject.repository.DataObjectNoteRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.DataObjectRepository;
import com.ooluk.mdm.core.dataobject.repository.DataObjectRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.DataObjectSourceRepository;
import com.ooluk.mdm.core.dataobject.repository.DataObjectSourceRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.NamespaceRepository;
import com.ooluk.mdm.core.dataobject.repository.NamespaceRepositoryImpl;
import com.ooluk.mdm.core.index.data.Index;
import com.ooluk.mdm.core.index.repository.IndexAttributeRepository;
import com.ooluk.mdm.core.index.repository.IndexAttributeRepositoryImpl;
import com.ooluk.mdm.core.index.repository.IndexRepository;
import com.ooluk.mdm.core.index.repository.IndexRepositoryImpl;

/**
 * A common superclass for repository tests.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration
@Transactional
//@Rollback(false)
public abstract class AbstractRepositoryTest {

	@Autowired
	protected DataSource dataSource;
	@Autowired
	protected JdbcTemplate jdbc;
	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected Database database;
	
	@Configuration
	@Import({TestConfig.class})
	static class ContextConfiguration {	
		
		@Bean
		public PropertyGroupRepository propertyGroupRepository() {
			return new PropertyGroupRepositoryImpl();
		}
		
		@Bean
		public DynamicPropertyRepository dynamicPropertyRepository() {
			return new DynamicPropertyRepositoryImpl();
		}
		
		@Bean
		public ListValueRepository listValueRepository() {
			return new ListValueRepositoryImpl();
		}
		
		@Bean
		public LabelTypeRepository labelTypeRepository() {
			return new LabelTypeRepositoryImpl();
		}
		
		@Bean
		public LabelRepository labelRepository() {
			return new LabelRepositoryImpl();
		}
		
		@Bean
		public TagRepository tagRepository() {
			return new TagRepositoryImpl();
		}
		
		@Bean
		public NamespaceRepository nsRepository() {
			return new NamespaceRepositoryImpl();
		}
		
		@Bean
		public DataObjectRepository objRepository() {
			return new DataObjectRepositoryImpl();
		}
		
		@Bean
		public DataObjectSourceRepository osRepository() {
			return new DataObjectSourceRepositoryImpl();
		}
		
		@Bean
		public DataObjectNoteRepository dnoteRepository() {
			return new DataObjectNoteRepositoryImpl();
		}
		
		@Bean
		public AttributeRepository attrRepository() {
			return new AttributeRepositoryImpl();
		}
		
		@Bean
		public AttributeSourceRepository asRepository() {
			return new AttributeSourceRepositoryImpl();
		}
		
		@Bean
		public AttributeNoteRepository anoteRepository() {
			return new AttributeNoteRepositoryImpl();
		}
		
		@Bean
		public IndexRepository idxRepository() {
			return new IndexRepositoryImpl();
		}
		
		@Bean
		public IndexAttributeRepository iaRepository() {
			return new IndexAttributeRepositoryImpl();
		}
	}	
	
	@Autowired
	protected PropertyGroupRepository pgRepository;	
	@Autowired
	protected DynamicPropertyRepository dpRepository;	
	@Autowired
	protected ListValueRepository lvRepository;	
	@Autowired
	protected LabelTypeRepository lblTypeRepository;
	@Autowired
	protected LabelRepository lblRepository;
	@Autowired
	protected TagRepository tagRepository;
	@Autowired
	protected NamespaceRepository nsRepository;
	@Autowired
	protected DataObjectRepository objRepository;
	@Autowired
	protected DataObjectSourceRepository osRepository;
	@Autowired
	protected DataObjectNoteRepository dnoteRepository;
	@Autowired
	protected AttributeRepository attrRepository;
	@Autowired
	protected AttributeSourceRepository asRepository;
	@Autowired
	protected AttributeNoteRepository anoteRepository;
	@Autowired
	protected IndexRepository idxRepository;
	@Autowired
	protected IndexAttributeRepository iaRepository;
	
	/**
	 * Verifies row count using JDBC. This ensures data was reflected in the database.
	 * 
	 * @param table
	 *            table name
	 * @param count
	 *            expected row count
	 */
	protected void verifyRowCount(String table, int count) {
		// Ensure property is there
		String sql = DatabaseUtils.selectSQL(table, new String[]{"count(*)"}, null, null);
		long rows = jdbc.query(sql, new ResultSetExtractor<Long>() {
			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getLong(1);
				}
				fail("No rows");
				return null;
			}
		});
		assertEquals(count, rows);
	}
	
	protected void flush() {
		Session session = sessionFactory.getCurrentSession();
		session.flush();		
	}
	
	protected void evict(Object entity) {
		Session session = sessionFactory.getCurrentSession();
		session.evict(entity);
	}
	
	protected void flushAndEvict(Object entity) {
		Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.evict(entity);		
	}
	
	/*
	 * These methods insert data into the database and are used to create test data.
	 */
	private void commit(Object entity, boolean evict) {
		if (evict)
			flushAndEvict(entity);
		else
			flush();
	}
	
	/**
	 * Inserts a property group into the database.
	 */
	protected PropertyGroup createPropertyGroup(String name) {
		return createPropertyGroup(name, true);
	}

	/**
	 * Inserts a property group into the database.
	 */
	protected PropertyGroup createPropertyGroup(String name, boolean evict) {
		PropertyGroup entity = TestData.getPropertyGroup(name);
		pgRepository.create(entity);
		commit(entity, evict);
		return entity;
	}

	/**
	 * Inserts a label type into the database.
	 */
	protected LabelType createLabelType(String name) {
		return this.createLabelType(name, true); 
	}

	/**
	 * Inserts a label type into the database.
	 */
	protected LabelType createLabelType(String name, boolean evict) {
		LabelType entity = TestData.getLabelType(name);
		lblTypeRepository.create(entity);
		commit(entity, evict);
		return entity;
	}

	/**
	 * Inserts a label into the database.
	 */
	protected Label createLabel(String type, String name) {
		return createLabel(type, name, true);
	}

	/**
	 * Inserts a label into the database.
	 */
	protected Label createLabel(String type, String name, boolean evict) {
		Label entity = TestData.getLabel(type, name);
		entity.setType(createLabelType(type));
		lblRepository.create(entity);
		commit(entity, evict);
		return entity;
	}

	/**
	 * Inserts a tag into the database.
	 */
	protected Tag createTag(String name) {
		return createTag(name, true);
	}

	/**
	 * Inserts a tag into the database.
	 */
	protected Tag createTag(String name, boolean evict) {
		Tag entity = TestData.getTag(name);
		tagRepository.create(entity);
		commit(entity, evict);
		return entity;
	}

	/**
	 * Inserts a namespace into the database.
	 */
	protected Namespace createNamespace(String name) {
		return createNamespace(name, true);
	}

	/**
	 * Inserts a namespace into the database.
	 */
	protected Namespace createNamespace(String name, boolean evict) {
		Namespace entity = TestData.getNamespace(name);
		pgRepository.create(entity.getPropertyGroup());
		nsRepository.create(entity);
		commit(entity, evict);
		return entity;
	}

	/**
	 * Inserts a data object into the database.
	 */
	protected DataObject createDataObject(String ns, String name) {
		return createDataObject(ns, name, true);
	}

	/**
	 * Inserts a data object into the database.
	 */	
	protected DataObject createDataObject(String ns, String name, boolean evict) {
		DataObject entity = TestData.getDataObject(ns, name);
		entity.setNamespace(createNamespace(ns));
		objRepository.create(entity);
		commit(entity, evict);		
		return entity;
	}

	/**
	 * Inserts an attribute into the database.
	 */	
	protected Attribute createAttribute(String ns, String obj, String attr) {
		return createAttribute(ns, obj, attr, true);
	}

	/**
	 * Inserts an attribute into the database.
	 */	
	protected Attribute createAttribute(String ns, String obj, String attr, boolean evict) {
		Attribute entity = TestData.getAttribute(ns, obj, attr);
		DataObject o = createDataObject(ns, obj);
		o.addAttribute(entity);
		attrRepository.create(entity);
		commit(entity, evict);		
		return entity;
	}

	/**
	 * Inserts an index into the database.
	 */	
	protected Index createIndex(String ns, String obj, String idx) {
		return createIndex(ns, obj, idx, true);
	}

	/**
	 * Inserts an index into the database.
	 */	
	protected Index createIndex(String ns, String obj, String idx, boolean evict) {
		Index entity = TestData.getIndex(ns, obj, idx);
		DataObject o = createDataObject(ns, obj);
		o.addIndex(entity);
		idxRepository.create(entity);
		commit(entity, evict);		
		return entity;
	}
}