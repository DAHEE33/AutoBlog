package com.example.blogextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index"; // index.html 반환
    }

    @PostMapping("/extract")
    public String extractContent(@RequestParam("url") String url, Model model) {
        try {
            // URL에서 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // 본문 추출 (CSS 선택자 변경 필요)
            Elements contentElements = document.select("div.content");
            String firstContent = contentElements.isEmpty() ? "본문을 찾을 수 없습니다." : contentElements.first().text();

            model.addAttribute("content", firstContent);
        } catch (Exception e) {
            model.addAttribute("content", "오류가 발생했습니다: " + e.getMessage());
        }
        return "index"; // index.html로 결과 반환
    }
}
