package pl.m4.html.htmlparser.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.m4.html.htmlparser.logic.pod.Page;
import pl.m4.html.htmlparser.logic.scanner.ScannerException;

import java.util.Queue;

public class ScannerServiceTest {
    private ScannerService scannerService;

    @Before
    public void init() {
        scannerService = new ScannerServiceImpl();
    }

    @Test
    public void testPageLinksAreMoreThanZero() throws ScannerException {
        Page page = scannerService.scanPage("http://joemonster.org");
        Assert.assertTrue(page.getLinks().size() > 0);
    }

    @Test
    public void testScannedPagesAreEqualsTo5() throws ScannerException {
        Page page = scannerService.scanPage("http://google.pl");
        Queue<Page> pages = scannerService.scanChildrenPages(page.getUrl(), 5);
        //with parent 6
        Assert.assertTrue(pages.size() == 6);
    }

    @Test
    public void testChildrenPageOneHasLinks() throws ScannerException {
        Page page = scannerService.scanPage("http://google.pl");
        Queue<Page> pages = scannerService.scanChildrenPages(page.getUrl(), 5);
        Assert.assertTrue(pages.poll().getLinks().size() > 0);
    }
}
