package com.nky.community.controller;

import com.nky.community.cache.TagCache;
import com.nky.community.dto.QuestionDTO;
import com.nky.community.entity.Question;
import com.nky.community.entity.User;
import com.nky.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther:nky
 * @Date:2019/10/10
 * @Description:com.nky.community.controller
 * @version:1.0
 *
 * 提交问题处理
 */
@Controller
public class PublishController {
    @Autowired
    QuestionService questionService;

    /**
     * 拿到标签数据
     */
    @GetMapping("/publish")
    public String publish(Model model) {
        // 将模拟tag缓存放到model中
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    /**
     * 发布问题接口 - POST
     */
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model) {
        // 判断用户登录态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        // 将前端拿到的问题数据回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        // 返回相应的文本编辑错误
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "问题补充不能为空！");
            return "publish";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空!");
            return "publish";
        }
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        // 发布问题成功，将问题数据存入表
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdate(question);
        model.addAttribute("tags", TagCache.get());
        return "redirect:/";
    }

    /**
     * 编辑个人问题
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
