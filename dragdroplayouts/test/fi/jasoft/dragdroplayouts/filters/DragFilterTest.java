package fi.jasoft.dragdroplayouts.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import junit.framework.TestCase;

import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

/**
 * Tests drag filter operations
 */
public class DragFilterTest extends TestCase {

    /**
     * Tests the premade literal filters
     */
    @Test
    public void testLiteralFilters() {
        assertTrue(DragFilter.ALL.isDraggable(null));
        assertFalse(DragFilter.NONE.isDraggable(null));
        assertTrue(fi.jasoft.dragdroplayouts.interfaces.DragFilter.ALL
                .isDraggable(null));
        assertFalse(fi.jasoft.dragdroplayouts.interfaces.DragFilter.NONE
                .isDraggable(null));
        assertSame(DragFilter.ALL, reserialize(DragFilter.ALL));
        assertSame(DragFilter.NONE, reserialize(DragFilter.NONE));
    }

    /**
     * Helper for re-serializing a filter
     * 
     * @param value
     *            The value to re-serialize
     * @return
     */
    @SuppressWarnings("unchecked")
    private static final <S> S reserialize(final S value) {
        if (value == null)
            throw new NullPointerException();

        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.close();

            final ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            final Object result = ois.readObject();
            ois.close();
            return (S) result;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
