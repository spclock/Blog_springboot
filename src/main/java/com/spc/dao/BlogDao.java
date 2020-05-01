package com.spc.dao;

import com.spc.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {
    private SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pagesize, Integer userId) {
        Map<String,Object> parameters =new HashMap<>();
        parameters.put("user_id",userId);
        parameters.put("offset",(page-1)*pagesize);
        parameters.put("pageSize",pagesize);
        return sqlSession.selectList("selectBlog",parameters);
    }

    public int count(Integer userId) {
        return sqlSession.selectOne("countBlog",userId);
    }
}
