package com.spc.service;

import com.spc.dao.BlogDao;
import com.spc.entity.Blog;
import com.spc.entity.BlogResult;
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

    public BlogResult getBlogs(Integer page, Integer pagesize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pagesize, userId);

            int count = blogDao.count(userId);
            int pageCount = count % page == 0 ? count / pagesize : count / pagesize + 1;
            return BlogResult.successBlogResult(blogs, count, page, pageCount);
        }catch (Exception e){
            return BlogResult.failBlogResult();
        }
    }
}
