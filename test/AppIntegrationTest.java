import org.junit.*;

import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

/**
 * Tests for status of pages 
 * @author Konstantinas
 *
 */
public class AppIntegrationTest extends AbstractTesting {

    /**
     * Check if index page is displayed
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(INDEX_PAGE_URL);
                assertThat(browser.pageSource()).contains(INDEX_PAGE_CONTAINS);
            }
        });
    }
    
    /**
     * Check if json messages page page is displayed
     */
    @Test
    public void testJsonMessagesPage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(JSON_MESSAGES_PAGE_URL);
                assertThat(browser.pageSource()).contains(JSON_CONTAINS);
            }
        });
    }
    
    /**
     * Check if websocket page page is displayed
     */
    @Test
    public void testWebSocketPage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(WEBSOCKET_PAGE_URL);
                assertThat(browser.pageSource()).contains(WEBSOCKET_PAGE_CONTAINS);
            }
        });
    }
    
    /**
     * Check if websocket page page is displayed
     */
    @Test
    public void testJsonPersonsPage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(JSON_PERSONS_PAGE_URL);
                assertThat(browser.pageSource()).contains(JSON_CONTAINS);
            }
        });
    }
}
