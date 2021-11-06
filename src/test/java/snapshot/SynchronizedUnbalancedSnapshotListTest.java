package snapshot;

import org.junit.jupiter.api.Test;
import snapshot.SynchronizedUnbalancedSnapshotList;

import static org.junit.jupiter.api.Assertions.*;

public class SynchronizedUnbalancedSnapshotListTest {

    @Test
    void testSnapshot(){

        SnapshotList<String> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add("1");
        elements.add("2");

        assertEquals(1, elements.snapshot());
    }


    @Test
    void testIncrementalSnapShot(){

        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object obj4 = new Object();

        SnapshotList<Object> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add(obj1);
        elements.add(obj2);
        elements.snapshot();
        elements.add(obj3);
        elements.add(obj4);

        assertEquals(2, elements.snapshot());
    }

    @Test
    void testVersion(){

        SnapshotList<String> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add("1");
        elements.add("2");
        elements.snapshot();
        int version = elements.version();

        assertEquals(1, version);
    }

    @Test
    void testIncrementalVersion(){

        SnapshotList<String> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add("1");
        elements.add("2");
        elements.snapshot();
        elements.add("3");
        elements.snapshot();
        elements.snapshot();
        int version = elements.version();

        assertEquals(3, version);
    }

    @Test
    void testGetAtVersion(){

        SnapshotList<String> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add("1");
        elements.add("2");
        elements.snapshot();
        elements.add("3");
        elements.add("4");
        elements.add("5");

        String selectedElement = elements.getAtVersion(2,1);
        assertEquals("4", selectedElement);
    }

    @Test
    void testGetAtVersionReturnsNullWhenItemNotFound(){

        SnapshotList<String> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add("1");
        elements.add("2");
        elements.snapshot();
        elements.add("3");
        elements.add("4");
        elements.add("5");

        assertNull(elements.getAtVersion(5,1));
        assertNull(elements.getAtVersion(5,3));
    }

    @Test
    void testDropPriorSnapshots(){

        SnapshotList<String> elements = new SynchronizedUnbalancedSnapshotList<>();
        elements.add("1");
        elements.add("2");
        elements.snapshot();
        elements.add("3");
        elements.add("4");
        elements.add("5");
        elements.snapshot();
        elements.add("6");
        elements.add("7");
        elements.add("8");

        elements.dropPriorSnapshots(1);

        Object [] expectedResult = {"2","3","4","5","6","7","8"};
        Object [] result =  elements.toArray();

        assertArrayEquals( expectedResult, result );
    }
}
