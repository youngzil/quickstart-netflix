package org.quickstart.netflix.ribbon.rx.transport;

import org.quickstart.netflix.ribbon.rx.RxMovieClientTestBase;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Tomasz Bak
 */
public class RxMovieTransportExampleTest extends RxMovieClientTestBase {

    @Test
    public void testTemplateExample() throws Exception {
        assertTrue(new RxMovieTransportExample(port).runExample());
    }
}