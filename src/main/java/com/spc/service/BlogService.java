package com.spc.service;

import com.spc.dao.BlogDao;
import com.spc.entity.Blog;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;
    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public List<Blog> getBlogs(Integer page, Integer pagesize, Integer userId) {
        return blogDao.getBlogs(page, pagesize, userId);
    }
}
