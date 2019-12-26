package com.nky.community.controller;

import com.nky.community.dto.PaginationDTO;
import com.nky.community.entity.User;
import com.nky.community.service.NotificationService;
import com.nky.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther:nky
 * @Date:2019/10/11
 * @Description:com.nky.community.controller
 * @version:1.0
 *
 * 个人相关页 ： 个人问题、最新回复
 */
@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    /**
     * 根据不同的action（前端传入）返回不同的内容
     */
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        // 判断登录态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);

        } else if ("replies".equals(action)) {

            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            Long unreadCount = notificationService.unreadCount(user.getId());

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("unreadCount", unreadCount);
            model.addAttribute("pagination", paginationDTO);
        }

        return "profile";
    }

}
