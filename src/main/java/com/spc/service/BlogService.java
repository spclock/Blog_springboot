package com.spc.service;

import com.spc.dao.BlogDao;
import com.spc.entity.Blog;
import com.spc.entity.BlogResult;
import com.spc.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;

    @Inject
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogResult getBlogs(Integer page, Integer pagesize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pagesize, userId);

            blogs.forEach(blog -> blog.setUser(userService.getUserById(blog.getUserId())));

            int count = blogDao.count(userId);
            int pageCount = count % pagesize == 0 ? count / pagesize : count / pagesize + 1;
            return BlogResult.successBlogResult(blogs, count, page, pageCount);
        } catch (Exception e) {
            e.printStackTrace();
            return BlogResult.failBlogResult();
        }
    }
}
