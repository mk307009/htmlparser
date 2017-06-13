package pl.m4.html.htmlparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.m4.html.htmlparser.logic.pod.Page;
import pl.m4.html.htmlparser.logic.scanner.ScannerException;
import pl.m4.html.htmlparser.service.ScannerService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

@RestController("/scan")
public class ParserController {
    private static final Logger log = Logger.getLogger(ParserController.class.getName());
    private static final String DEFAULT_WEBSITE = "http://joemonster.org";

    @Autowired
    private ScannerService scannerService;

    @GetMapping()
    public Queue<Page> scanUrl(@RequestParam(value = "url", defaultValue = DEFAULT_WEBSITE) String url,
    @RequestParam(value = "depth", defaultValue = "0") Integer depth) {
        Queue<Page> pages = new LinkedList<>();
        try {
            if (depth > 0)
                pages = scannerService.scanChildrenPages(url, depth);
            else
                pages.add(scannerService.scanPage(url));
        } catch (ScannerException e) {
            log.info("Cannot connect to the url. " + e.getMessage());
        }
        return pages;
    }
}
