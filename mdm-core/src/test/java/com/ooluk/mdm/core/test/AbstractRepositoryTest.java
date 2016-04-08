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

import com.ooluk.mdm.core.app.repository.LabelRepository;
import com.ooluk.mdm.core.app.repository.LabelRepositoryImpl;
import com.ooluk.mdm.core.app.repository.LabelTypeRepository;
import com.ooluk.mdm.core.app.repository.LabelTypeRepositoryImpl;
import com.ooluk.mdm.core.app.repository.TagRepository;
import com.ooluk.mdm.core.app.repository.TagRepositoryImpl;
import com.ooluk.mdm.core.attribute.repository.AttributeNoteRepository;
import com.ooluk.mdm.core.attribute.repository.AttributeNoteRepositoryImpl;
import com.ooluk.mdm.core.attribute.repository.AttributeRepository;
import com.ooluk.mdm.core.attribute.repository.AttributeRepositoryImpl;
import com.ooluk.mdm.core.attribute.repository.AttributeSourceRepository;
import com.ooluk.mdm.core.attribute.repository.AttributeSourceRepositoryImpl;
import com.ooluk.mdm.core.base.repository.DynamicPropertyRepository;
import com.ooluk.mdm.core.base.repository.DynamicPropertyRepositoryImpl;
import com.ooluk.mdm.core.base.repository.ListValueRepository;
import com.ooluk.mdm.core.base.repository.ListValueRepositoryImpl;
import com.ooluk.mdm.core.base.repository.PropertyGroupRepository;
import com.ooluk.mdm.core.base.repository.PropertyGroupRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.DataObjectNoteRepository;
import com.ooluk.mdm.core.dataobject.repository.DataObjectNoteRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.DataObjectRepository;
import com.ooluk.mdm.core.dataobject.repository.DataObjectRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.DataObjectSourceRepository;
import com.ooluk.mdm.core.dataobject.repository.DataObjectSourceRepositoryImpl;
import com.ooluk.mdm.core.dataobject.repository.NamespaceRepository;
import com.ooluk.mdm.core.dataobject.repository.NamespaceRepositoryImpl;
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
}