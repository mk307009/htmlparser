package pl.m4.html.htmlparser.logic.scanner;

import pl.m4.html.htmlparser.logic.pod.Page;

import java.util.Queue;

public interface Scanner {
    Page scan(String url) throws ScannerException;
    Queue<Page> scanPages(String url, int depth) throws ScannerException;
}
