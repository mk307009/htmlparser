package pl.m4.html.htmlparser.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.m4.html.htmlparser.logic.pod.Page;
import pl.m4.html.htmlparser.logic.scanner.Scanner;
import pl.m4.html.htmlparser.logic.scanner.ScannerException;
import pl.m4.html.htmlparser.logic.scanner.UrlScanner;

import java.util.Queue;
import java.util.logging.Logger;

@Service
public class ScannerServiceImpl implements ScannerService {
    private Logger log = Logger.getLogger(ScannerService.class.getCanonicalName());
    private Scanner scanner;

    public ScannerServiceImpl() {
        scanner = new UrlScanner();
    }

    @Override
    @Cacheable(cacheNames = "page", key = "#url")
    public Page scanPage(String url) throws ScannerException {
        return scanner.scan(url);
    }

    @Override
    @Cacheable(cacheNames = "pages", key = "#url")
    public Queue<Page> scanChildrenPages(String url, int count) throws ScannerException {
        Queue<Page> queue = null;
        try {
            queue = scanner.scanPages(url, count);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return queue;
    }
}
