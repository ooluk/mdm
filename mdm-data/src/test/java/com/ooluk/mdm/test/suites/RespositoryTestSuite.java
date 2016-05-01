package com.ooluk.mdm.test.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ooluk.mdm.data.data.dataobject.DataObjectNoteRepositoryTest;
import com.ooluk.mdm.data.data.dataobject.DataObjectRepositoryTest;
import com.ooluk.mdm.data.data.dataobject.DataObjectSourceRepositoryTest;
import com.ooluk.mdm.data.data.dataobject.NamespaceRepositoryTest;
import com.ooluk.mdm.data.meta.DynamicPropertyRepositoryTest;
import com.ooluk.mdm.data.meta.PropertyGroupRepositoryTest;
import com.ooluk.mdm.data.meta.app.LabelRepositoryTest;
import com.ooluk.mdm.data.meta.app.LabelTypeRepositoryTest;
import com.ooluk.mdm.data.meta.app.TagRepositoryTest;
import com.ooluk.mdm.data.meta.attribute.AttributeNoteRepositoryTest;
import com.ooluk.mdm.data.meta.attribute.AttributeRepositoryTest;
import com.ooluk.mdm.data.meta.attribute.AttributeSourceRepositoryTest;
import com.ooluk.mdm.data.meta.index.IndexAttributeRepositoryTest;
import com.ooluk.mdm.data.meta.index.IndexRepositoryTest;

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