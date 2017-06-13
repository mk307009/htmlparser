package pl.m4.html.htmlparser.logic.scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.m4.html.htmlparser.logic.pod.Page;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UrlScanner implements Scanner {
    private static final Logger log = Logger.getLogger(UrlScanner.class.getCanonicalName());
    private static final String HASH_ALGORITHM = "MD5";
    private static final int MAX_THREAD = 10;
    private Document document;

    @Override
    public Page scan(String url) throws ScannerException {
        Page page = new Page(url);
        try {
            document = Jsoup.connect(page.getUrl()).get();
            page.setLinks(scanForLinks());
            page.setHash(calcHash());
            page.setContentSize(calcContentSize());
        } catch (IOException e) {
            throw new ScannerException("Can not open connection with website: "+url, e);
        } catch (NoSuchAlgorithmException e) {
            throw new ScannerException("Can not found algorithm to hash", e);
        }
        return page;
    }

    @Override
    public Queue<Page> scanPages(String url, int depth) throws ScannerException {
        Page parent = scan(url);
        Queue<Page> queue = new ConcurrentLinkedQueue<>();
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD);
        List<Future<Runnable>> futures = new ArrayList<>();
        int size = getCorrectPageCount(parent, depth);
        queue.add(parent);

        for (int i = 0; i < size; i++) {
            Future future = service.submit(new PageSearcher(parent.getLinks().get(i), queue));
            futures.add(future);
        }
        for (Future<Runnable> f : futures) {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new ScannerException("Problem with waiting for thread execution: ", e);
            }
        }
        service.shutdownNow();

        return queue;
    }

    private List<String> scanForLinks() {
        return document.select("a[href]").stream().filter(
                link -> link.attr("href").startsWith("http"))
                .map(link -> link.attr("href"))
                .collect(Collectors.toList());
    }

    private byte[] calcHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        return md.digest(document.html().getBytes());
    }

    private int calcContentSize() {
        return document.html().length();
    }

    private int getCorrectPageCount(Page parent, int count) {
        int size = count;
        if (size > parent.getLinks().size())
            size = parent.getLinks().size();
        return size;
    }

    private class PageSearcher implements Runnable {
        private String url;
        private Queue<Page> queue;

        PageSearcher(String url, Queue<Page> queue) {
            this.url = url;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                Page page = scan(url);
                queue.add(page);
            } catch (ScannerException e) {
                log.info(e.getMessage());
            }
        }
    }
}
