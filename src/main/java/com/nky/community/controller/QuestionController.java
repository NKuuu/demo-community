package com.nky.community.controller;

import com.nky.community.dto.CommentDTO;
import com.nky.community.dto.QuestionDTO;
import com.nky.community.enums.CommentTypeEnum;
import com.nky.community.service.CommentService;
import com.nky.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Auther:nky
 * @Date:2019/10/11
 * @Description:com.nky.community.controller
 * @version:1.0
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        // 根据页面传入的id获取问题的数据
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        // 获取问题的所有评论数据
        List<CommentDTO> comments = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);

        // 累加阅读数
        questionService.incView(id);
        // 问题数据返回
        model.addAttribute("question", questionDTO);
        // 评论数据返回
        model.addAttribute("comments", comments);
        //对应的关联数据
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
