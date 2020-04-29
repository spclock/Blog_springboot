package com.spc.entity;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;


    private BlogResult(String status, String msg) {
        super(status, msg);
    }

    private BlogResult(String status, String msg, List<Blog> blog, int total, int page, int totalPage) {
        super(status, msg, blog);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogResult failBlogResult() {
        return new BlogResult("fail", "系统异常");
    }

    public static BlogResult successBlogResult(List<Blog> blog, int total, int page, int totalPage) {
        return new BlogResult("ok", "获取成功", blog, total, page, totalPage);
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
