package kz.iitu.iitu.service;

import kz.iitu.iitu.entity.News;
import kz.iitu.iitu.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNews(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public News createNews(News news) {
        news.setCreatedAt(LocalDate.now());
        return newsRepository.save(news);
    }

}
