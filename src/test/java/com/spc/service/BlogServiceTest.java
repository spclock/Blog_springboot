package com.spc.service;

import com.spc.dao.BlogDao;
import com.spc.entity.BlogResult;
import org.junit.jupiter.api.Assertions;
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
    public void getBlogsFormDbTest() {
        blogService.getBlogs(1, 10, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void returnFailureExceptionTest() {
        Mockito.when(blogDao.getBlogs(Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(new RuntimeException());

        BlogResult blogResult= blogService.getBlogs(1,10,null);

        Assertions.assertEquals("fail",blogResult.getStatus());
        Assertions.assertEquals("系统异常",blogResult.getMsg());
    }
}
