package edu.upvictoria.fpoo;

import junit.framework.TestCase;
import java.io.File;

public class FileManagerTest extends TestCase {

    private FileManager fileManager;
    private final String testTableName = "testTable.csv";

    protected void setUp() throws Exception {
        super.setUp();
        fileManager = new FileManager();
        new File(fileManager.getBasePath() + testTableName).delete();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        new File(fileManager.getBasePath() + testTableName).delete();
    }

    public void testUse() {
        File file = new File(fileManager.getBasePath() + testTableName);
        try {
            file.createNewFile();
            assertTrue("File should be usable", fileManager.use(testTableName));
        } catch (Exception e) {
            fail("Failed to create file for use test");
        }
    }
    public void testCreateTable() {
        String[] columns = {"id", "name"};
        assertTrue("Table should be created", fileManager.createTable(testTableName.replace(".csv", ""), columns));
        assertTrue("File should exist after creation", new File(fileManager.getBasePath() + testTableName).exists());
    }

    public void testInsertInto() {
        String[] columns = {"id", "name"};
        String[] values = {"1", "Test"};
        fileManager.createTable(testTableName.replace(".csv", ""), columns);
        assertTrue("Insert should succeed", fileManager.insertInto(testTableName.replace(".csv", ""), values));
    }

    public void testDropTable() {
        fileManager.createTable(testTableName.replace(".csv", ""), new String[]{"id", "name"});
        assertTrue("Drop should succeed", fileManager.dropTable(testTableName.replace(".csv", "")));
        assertFalse("File should not exist after drop", new File(fileManager.getBasePath() + testTableName).exists());
    }
}