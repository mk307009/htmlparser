package pl.m4.html.htmlparser.logic.scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.m4.html.htmlparser.logic.pod.Page;

public class UrlScannerTest {
    private Scanner scanner;

    @Before
    public void init() {
        scanner = new UrlScanner();
    }

    @Test
    public void testInputUrl() throws ScannerException {
        Page page = scanner.scan("http://google.pl");
        Assert.assertEquals(page.getUrl(), "http://google.pl");
    }

    @Test
    public void testPageHasMoreThanZeroLinks() throws ScannerException {
        Page page = scanner.scan("http://google.pl");
        Assert.assertTrue(page.getLinks().size() > 0);
    }

    @Test
    public void testPageHashIsNotEmpty() throws ScannerException {
        Page page = scanner.scan("http://google.pl");
        Assert.assertTrue(page.getHash().length > 0);
    }

    @Test
    public void testPageContentSizeIsNotZero() throws ScannerException {
        Page page = scanner.scan("http://google.pl");
        Assert.assertTrue(page.getContentSize() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScanPageWithoutProtocol() throws ScannerException {
        scanner.scan("google.pl");
    }

}
