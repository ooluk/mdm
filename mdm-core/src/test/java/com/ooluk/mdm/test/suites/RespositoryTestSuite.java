package com.ooluk.mdm.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ooluk.mdm.core.meta.DynamicPropertyRepositoryTest;
import com.ooluk.mdm.core.meta.PropertyGroupRepositoryTest;
import com.ooluk.mdm.core.meta.app.LabelRepositoryTest;
import com.ooluk.mdm.core.meta.app.LabelTypeRepositoryTest;
import com.ooluk.mdm.core.meta.app.TagRepositoryTest;
import com.ooluk.mdm.core.meta.attribute.AttributeNoteRepositoryTest;
import com.ooluk.mdm.core.meta.attribute.AttributeRepositoryTest;
import com.ooluk.mdm.core.meta.attribute.AttributeSourceRepositoryTest;
import com.ooluk.mdm.core.meta.dataobject.DataObjectNoteRepositoryTest;
import com.ooluk.mdm.core.meta.dataobject.DataObjectRepositoryTest;
import com.ooluk.mdm.core.meta.dataobject.DataObjectSourceRepositoryTest;
import com.ooluk.mdm.core.meta.dataobject.NamespaceRepositoryTest;
import com.ooluk.mdm.core.meta.index.IndexAttributeRepositoryTest;
import com.ooluk.mdm.core.meta.index.IndexRepositoryTest;

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