package com.nky.community.scheduled;

import com.nky.community.cache.HotTagCache;
import com.nky.community.entity.Question;
import com.nky.community.entity.QuestionExample;
import com.nky.community.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:nky
 * @Date:2019/11/4
 * @Description:com.nky.community.scheduled
 * @version:1.0
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 10000)
//    @Scheduled()
    public void reportCurrentTime() {
        log.info("The time is now {}", new Date());
        int limit = 20;
        int offset = 0;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();

        if (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag,priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag,5 + question.getCommentCount());
                    }
                }
                offset += limit;
            }
            priorities.forEach(
                    (k,v) -> {
                        System.out.print(k);
                        System.out.print(":");
                        System.out.print(v);
                        System.out.println();
                    }
            );
            hotTagCache.updateTags(priorities);
        }
    }
}
