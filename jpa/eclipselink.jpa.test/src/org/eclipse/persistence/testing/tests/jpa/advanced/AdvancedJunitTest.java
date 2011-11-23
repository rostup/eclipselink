/*******************************************************************************
 * Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.testing.tests.jpa.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;

import junit.framework.*;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.RepeatableWriteUnitOfWork;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.testing.framework.junit.JUnitTestCase;
import org.eclipse.persistence.testing.models.jpa.advanced.AdvancedTableCreator;

import org.eclipse.persistence.testing.models.jpa.advanced.Address;
import org.eclipse.persistence.testing.models.jpa.advanced.Employee;
import org.eclipse.persistence.testing.models.jpa.advanced.Man;
import org.eclipse.persistence.testing.models.jpa.advanced.PartnerLinkPK;
import org.eclipse.persistence.testing.models.jpa.advanced.Woman;
import org.eclipse.persistence.testing.models.jpa.advanced.Golfer;
import org.eclipse.persistence.testing.models.jpa.advanced.GolferPK;
import org.eclipse.persistence.testing.models.jpa.advanced.Vegetable;
import org.eclipse.persistence.testing.models.jpa.advanced.VegetablePK;
import org.eclipse.persistence.testing.models.jpa.advanced.WorldRank;
import org.eclipse.persistence.testing.models.jpa.advanced.PartnerLink;
import org.eclipse.persistence.testing.models.jpa.advanced.LargeProject;
import org.eclipse.persistence.testing.models.jpa.advanced.entities.SimpleEntity;
import org.eclipse.persistence.testing.models.jpa.advanced.entities.SimpleNature;
import org.eclipse.persistence.testing.models.jpa.advanced.entities.SimpleLanguage;

public class AdvancedJunitTest extends JUnitTestCase {
    public AdvancedJunitTest() {
        super();
    }
    
    public AdvancedJunitTest(String name) {
        super(name);
    }
    
    
    public static Test suite() {
        TestSuite suite = new TestSuite("AdvancedJunitTest");

        suite.addTest(new AdvancedJunitTest("testSetup"));
        suite.addTest(new AdvancedJunitTest("testGF1818"));
        suite.addTest(new AdvancedJunitTest("testEL254937"));
        suite.addTest(new AdvancedJunitTest("testGF1894"));
        suite.addTest(new AdvancedJunitTest("testGF894"));
        suite.addTest(new AdvancedJunitTest("testManAndWoman"));
        suite.addTest(new AdvancedJunitTest("testStringArrayField"));
        suite.addTest(new AdvancedJunitTest("testCreateDerivedPKFromPKValues"));
        suite.addTest(new AdvancedJunitTest("testElementCollectionClear"));
        suite.addTest(new AdvancedJunitTest("testElementCollectionEntityMapKeyRemove"));
        
        return suite;
    }
    
    /**
     * The setup is done as a test, both to record its failure, and to allow execution in the server.
     */
    public void testSetup() {
        new AdvancedTableCreator().replaceTables(JUnitTestCase.getServerSession());

        clearCache();
    }
    
    public void testGF1818() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        
        try {
            Vegetable vegetable = new Vegetable();
            vegetable.setId(new VegetablePK("Carrot", "Orange"));
            vegetable.setCost(2.09);
        
            em.persist(vegetable);
            commitTransaction(em);
            
        } catch (Exception e) {
            fail("An exception was caught: [" + e.getMessage() + "]");
        }
        
        closeEntityManager(em);
    }
    
    public void testEL254937(){
        // Should not run in the server - bug 264589
        if (! isOnServer()) {
            EntityManager em = createEntityManager();
            beginTransaction(em);
            LargeProject lp1 = new LargeProject();
            lp1.setName("one");
            em.persist(lp1);
            commitTransaction(em);
            em = createEntityManager();
            beginTransaction(em);
            em.remove(em.find(LargeProject.class, lp1.getId()));
            em.flush();
            JpaEntityManager eclipselinkEm = (JpaEntityManager)em.getDelegate();
            RepeatableWriteUnitOfWork uow = (RepeatableWriteUnitOfWork)eclipselinkEm.getActiveSession();
            //duplicate the beforeCompletion call
            uow.issueSQLbeforeCompletion();
            //commit the transaction
            uow.setShouldTerminateTransaction(true);
            uow.commitTransaction();
            //duplicate the AfterCompletion call.  This should merge, removing the LargeProject from the shared cache
            uow.mergeClonesAfterCompletion();
            em = createEntityManager();
            LargeProject cachedLargeProject = em.find(LargeProject.class, lp1.getId());
            closeEntityManager(em);
            assertTrue("Entity removed during flush was not removed from the shared cache on commit", cachedLargeProject==null);
        }
    }
    
    public void testGF1894() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        Employee emp = new Employee();
        emp.setFirstName("Guy");
        emp.setLastName("Pelletier");
        
        Address address = new Address();
        address.setCity("College Town");
        
        emp.setAddress(address);
            
        try {   
            Employee empClone = em.merge(emp);
            assertNotNull("The id field for the merged new employee object was not generated.", empClone.getId());
            commitTransaction(em);
            
            Employee empFromDB = em.find(Employee.class, empClone.getId());
            assertNotNull("The version locking field for the merged new employee object was not updated after commit.", empFromDB.getVersion());
            
            beginTransaction(em);
            Employee empClone2 = em.merge(empFromDB);
            assertTrue("The id field on a existing merged employee object was modified on a subsequent merge.", empFromDB.getId().equals(empClone2.getId()));
            commitTransaction(em);
        } catch (javax.persistence.OptimisticLockException e) {
            fail("An optimistic locking exception was caught on the merge of a new object. An insert should of occurred instead.");
        }
        
        closeEntityManager(em);
    }
    
    public void testGF894() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        
        try {
            for (int i = 0; ; i++) {
                GolferPK golferPK = new GolferPK(i);
                Golfer golfer = em.find(Golfer.class, golferPK);
            
                if (golfer == null) {
                    golfer = new Golfer();
                    golfer.setGolferPK(golferPK);
                
                    WorldRank worldRank = new WorldRank();
                    worldRank.setId(i);
                    golfer.setWorldRank(worldRank);
                
                    em.persist(worldRank);
                    em.persist(golfer);
                    commitTransaction(em);
                
                    break;
                } 
            }
        } catch (Exception e) {
            fail("An exception was caught: [" + e.getMessage() + "]");
        }
        
        closeEntityManager(em);
    }
    
    public void testManAndWoman() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        
        try {
            PartnerLink pLink1 = new PartnerLink();
            pLink1.setMan(new Man());
            em.persist(pLink1);
            
            PartnerLink pLink2 = new PartnerLink();
            pLink2.setWoman(new Woman());
            em.persist(pLink2);
            
            PartnerLink pLink3 = new PartnerLink();
            pLink3.setMan(new Man());
            pLink3.setWoman(new Woman());
            em.persist(pLink3);
            
            commitTransaction(em);
            
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            
            closeEntityManager(em);
            fail("An exception was caught: [" + e.getMessage() + "]");
        }
        
        closeEntityManager(em);
    }

    
    // https://bugs.eclipse.org/bugs/show_bug.cgi?id=279634
    public void testCreateDerivedPKFromPKValues() {

        EntityManager em = createEntityManager();
        beginTransaction(em);
        try {
            PartnerLink pLink3 = new PartnerLink();
            pLink3.setMan(new Man());
            pLink3.setWoman(new Woman());
            em.persist(pLink3);
            commitTransaction(em);
            ClassDescriptor descriptor = getServerSession().getClassDescriptor(PartnerLink.class);
            Object pks = descriptor.getObjectBuilder().extractPrimaryKeyFromObject(pLink3, getServerSession());
            PartnerLinkPK createdPK = (PartnerLinkPK) descriptor.getCMPPolicy().createPrimaryKeyInstanceFromId(pks, getServerSession());
            PartnerLinkPK usedPk = new PartnerLinkPK(pLink3.getManId(), pLink3.getWomanId());
            assertTrue("PK's do not match.", usedPk.equals(createdPK));

        } finally {
            if (isTransactionActive(em)) {
                rollbackTransaction(em);
            }
            closeEntityManager(em);
        }
    }

    // GF1673, 2674 Java SE 6 classloading error for String[] field
    public void testStringArrayField() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        
        VegetablePK pk = new VegetablePK("Tomato", "Red");
        String[] tags = {"California", "XE"};
        try {
            Vegetable vegetable = new Vegetable();
            vegetable.setId(pk);
            vegetable.setCost(2.09);
            vegetable.setTags(tags);
        
            em.persist(vegetable);
            commitTransaction(em);
            
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            throw e;
        } finally {
            closeEntityManager(em);
        }
        
        em = createEntityManager();
        beginTransaction(em);
        Vegetable vegetable;
        try {
            vegetable = em.find(Vegetable.class, pk);
            commitTransaction(em);
            assertNotNull(vegetable);
            assertTrue(Arrays.equals(tags, vegetable.getTags()));
            
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            throw e;
        } finally {
            closeEntityManager(em);
        }
    }
    
    public void testElementCollectionClear() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        
        try {
            SimpleEntity se = new SimpleEntity();
            se.setId(101L);
            se.setDescription("Element Collection Clear Test Record");
            Collection<String> nature = new ArrayList<String>();
            nature.add(SimpleNature.PERSONALITY[0]);
            nature.add(SimpleNature.PERSONALITY[1]);
            nature.add(SimpleNature.PERSONALITY[2]);
            nature.add(SimpleNature.PERSONALITY[3]);
            nature.add(SimpleNature.PERSONALITY[4]);
            nature.add(SimpleNature.PERSONALITY[5]);
            se.setSimpleNature(nature);
        
            em.persist(se);
            commitTransaction(em);
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            throw e;
        } finally {
            closeEntityManager(em);
        }
        
        // Clear Cache.
        clearCache();
        em = createEntityManager();
        SimpleEntity se;
        try {
            se = em.find(SimpleEntity.class, 101L);
            // Detach all entities.
            em.clear();
            closeEntityManager(em);
            
            // Clear lazily loaded ElementCollection.
            se.getSimpleNature().clear();
            
            em = createEntityManager();
            beginTransaction(em);
            em.merge(se);
            commitTransaction(em);
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            throw e;
        } finally {
            closeEntityManager(em);
        }
        
        // Clear Cache
        clearCache();
        em = createEntityManager();
        beginTransaction(em);
        try {
            se = em.find(SimpleEntity.class, 101L);
            Collection<String> natureList = se.getSimpleNature();
            int count = 0;
            for(String nature : natureList) {
                // Iterate to load the collection.
                count++;
            }
            Assert.assertEquals("All entries of collection have not been removed from database for ElementCollection Test.", 0, count);
        } catch (RuntimeException e) {
           throw e;
        } finally {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            closeEntityManager(em);
        }
    }
    
    public void testElementCollectionEntityMapKeyRemove() {
        EntityManager em = createEntityManager();
        beginTransaction(em);
        
        try {
            SimpleEntity se = new SimpleEntity();
            se.setId(102L);
            se.setDescription("Element Collection Entity MapKey Remove Test Record");
            
            SimpleLanguage slen = new SimpleLanguage();
            slen.setCode("EN");
            slen.setDescription("English");
            
            SimpleLanguage slfr = new SimpleLanguage();
            slfr.setCode("FR");
            slfr.setDescription("French");
            
            se.getSimpleLanguage().put(slen, "Modest");
            se.getSimpleLanguage().put(slfr, "Modeste");
        
            em.persist(slen);
            em.persist(slfr);
            em.persist(se);
            commitTransaction(em);
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            throw e;
        } finally {
            closeEntityManager(em);
        }
        
        // Clear Cache.
        clearCache();
        em = createEntityManager();
        
        SimpleEntity se = null;
        SimpleLanguage slfr = null;
        try {
            beginTransaction(em);
            se = em.find(SimpleEntity.class, 102L);
            slfr = em.find(SimpleLanguage.class, "FR");
            // Remove French Language from ElementCollection.
            se.getSimpleLanguage().remove(slfr);
            commitTransaction(em);
        } catch (RuntimeException e) {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            throw e;
        } finally {
            closeEntityManager(em);
        }
        
        // Clear Cache
        clearCache();
        em = createEntityManager();
        beginTransaction(em);
        try {
            slfr = em.find(SimpleLanguage.class, "FR");
            Assert.assertNotSame("Entity used as key in Collection Map has been deleted along with data in collection table on remove.", null, slfr);
        } catch (RuntimeException e) {
           throw e;
        } finally {
            if (isTransactionActive(em)){
                rollbackTransaction(em);
            }
            closeEntityManager(em);
        }
    }
}
