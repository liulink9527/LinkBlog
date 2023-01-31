package com.link.job;

import com.link.constants.RedisConstants;
import com.link.domain.entity.Article;
import com.link.mapper.ArticleMapper;
import com.link.service.ArticleService;
import com.link.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Link
 * @Description
 * @date 2023-01-31 13:48
 */
@Component
public class ViewCountJob {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Scheduled(cron = "0/20 * * * * ?")
    public void viewCountJob() {
        Map<String, Integer> map = redisCache.getCacheMap(RedisConstants.VIEW_COUNT);

        List<Article> articles = map.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }
}
