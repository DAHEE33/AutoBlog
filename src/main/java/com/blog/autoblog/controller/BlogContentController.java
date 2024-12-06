package com.blog.autoblog.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class BlogContentController {

    @GetMapping("/fetch-content")
    public ResponseEntity<String> fetchBlogContent(@RequestParam String url) {
        try {
            // Fetch HTML from the given URL
            Document document = Jsoup.connect(url).get();

            // Select the main content (change the selector based on Naver's DOM structure)
            Element mainContent = document.selectFirst(".se-main-container");

            if (mainContent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("본문을 찾을 수 없습니다.");
            }

            // Extract and return the text from the first paragraph
            String content = mainContent.selectFirst("p").text();
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("본문을 가져오는 중 에러 발생: " + e.getMessage());
        }
    }
}
