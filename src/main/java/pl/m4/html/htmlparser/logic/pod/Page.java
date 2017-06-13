package pl.m4.html.htmlparser.logic.pod;

import java.util.Arrays;
import java.util.List;

public class Page {
    private String url;
    private int contentSize;
    private byte[] hash;
    private List<String> links;

    public Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Page{" +
                "url='" + url + '\'' +
                ", contentSize=" + contentSize +
                ", hash=" + Arrays.toString(hash) +
                ", links=" + links +
                '}';
    }
}
