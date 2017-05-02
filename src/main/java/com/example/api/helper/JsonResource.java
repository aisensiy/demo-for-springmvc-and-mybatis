package com.example.api.helper;

import org.springframework.hateoas.Link;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonResource {
    private Map<String, String> links = new HashMap<>();

    public void addLink(String ref, String url) {
        links.put(ref, url);
    }

    public void addLink(String ref, Link link) {
        links.put(ref, link.toString());
    }

    public Map<String, String> getLinks() {
        return Collections.unmodifiableMap(links);
    }
}

