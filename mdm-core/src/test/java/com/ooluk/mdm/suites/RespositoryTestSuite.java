package com.ooluk.mdm.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ooluk.mdm.core.app.repository.LabelRepositoryTest;
import com.ooluk.mdm.core.app.repository.LabelTypeRepositoryTest;
import com.ooluk.mdm.core.app.repository.TagRepositoryTest;
import com.ooluk.mdm.core.attribute.repository.AttributeNoteRepositoryTest;
import com.ooluk.mdm.core.attribute.repository.AttributeRepositoryTest;
import com.ooluk.mdm.core.attribute.repository.AttributeSourceRepositoryTest;
import com.ooluk.mdm.core.base.repository.DynamicPropertyRepositoryTest;
import com.ooluk.mdm.core.base.repository.PropertyGroupRepositoryTest;
import com.ooluk.mdm.core.dataobject.repository.DataObjectNoteRepositoryTest;
import com.ooluk.mdm.core.dataobject.repository.DataObjectRepositoryTest;
import com.ooluk.mdm.core.dataobject.repository.DataObjectSourceRepositoryTest;
import com.ooluk.mdm.core.dataobject.repository.NamespaceRepositoryTest;
import com.ooluk.mdm.core.index.repository.IndexAttributeRepositoryTest;
import com.ooluk.mdm.core.index.repository.IndexRepositoryTest;

/**
 * Test suite for repositories.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    PropertyGroupRepositoryTest.class, 
    DynamicPropertyRepositoryTest.class, 
    
    LabelTypeRepositoryTest.class, 
    LabelRepositoryTest.class, 
    TagRepositoryTest.class,
    
    NamespaceRepositoryTest.class,
    DataObjectRepositoryTest.class,  
    DataObjectSourceRepositoryTest.class, 
    DataObjectNoteRepositoryTest.class,
    
    AttributeRepositoryTest.class,  
    AttributeSourceRepositoryTest.class, 
    AttributeNoteRepositoryTest.class,
    
    IndexRepositoryTest.class,  
    IndexAttributeRepositoryTest.class, 
 })
public class RespositoryTestSuite {
}