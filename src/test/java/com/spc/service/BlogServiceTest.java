package com.spc.service;

import com.spc.dao.BlogDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogsFormDbTest(){
        blogService.getBlogs(1,10,null);
        Mockito.verify(blogDao).getBlogs(1,10,null);
    }
}
