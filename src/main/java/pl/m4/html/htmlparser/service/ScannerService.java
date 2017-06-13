package pl.m4.html.htmlparser.service;

import pl.m4.html.htmlparser.logic.pod.Page;
import pl.m4.html.htmlparser.logic.scanner.ScannerException;

import java.util.Queue;

public interface ScannerService {
    Page scanPage(String url) throws ScannerException;
    Queue<Page> scanChildrenPages(String url, int count) throws ScannerException;
}
