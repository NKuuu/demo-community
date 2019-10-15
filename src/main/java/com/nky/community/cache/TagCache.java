package com.nky.community.cache;

import com.nky.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther:nky
 * @Date:2019/10/15
 * @Description:com.nky.community.cache
 * @version:1.0
 */
public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("JavaScript", "java", "php", "html", "css", "python", "node", "golong", "objective-c", "ruby", "c", "c++", "c#", "swift", "TypeScript", "shell"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台架构");
        framework.setTags(Arrays.asList("larave", "Spring", "SpringBoot", "SpringMVC", "Django", "MyBatis", "Hibernate", "Tornado", "Flask", "Shiro", "koa", "struts", "express", "ruby-no-rails", "struts", "struts2"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Nginx", "Tomcat", "Apache", "Docker", "Linux", "Ubuntu", "CentOS", "负载均衡", "Unix", "Hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDTO dataBase = new TagDTO();
        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("mysql", "radis", "sql", "oracle", "nosql", "memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(dataBase);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "visual-studio-code", "github", "sublime-text", "intellij-idea", "eclipse", "svn", "ide", "emacs", "visual-studio", "atom", "textmate"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {

        String[] sqlit = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream())
                .collect(Collectors.toList());
        String invalid = Arrays.stream(sqlit).filter(t -> !tagList.contains(t))
                .collect(Collectors.joining(","));

        return invalid;
    }
}
