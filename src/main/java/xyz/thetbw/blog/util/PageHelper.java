package xyz.thetbw.blog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 分页工具
 * @param <T> 集合的类型
 */
public class PageHelper<T> {
    //页数索引大小
    public static final int maxIndexTagSize = 7;

    private int currentPageNum; //当前页数
    private int totalPageNums; //总共页数
    private int currentPageSize; //当前页大小
    private int maxPageLength; //总共页大小
    private int itemCount;
    private List<T> pageItems; //页的元素
    private List<Integer> pageIndexes;//页面的索引

    public PageHelper(){

    }

    public PageHelper(List<T> pageItems,int page,int length,int count){
        this.itemCount = count;
        int pageNums = (int)Math.ceil(count/(double)length);
        this.pageItems = pageItems;
        if (pageItems==null)
            pageItems=new ArrayList<>();
        this.currentPageSize = pageItems.size();
        this.maxPageLength = length;
        if (currentPageSize>maxPageLength){
            throw new ArrayIndexOutOfBoundsException();
        }
        this.currentPageNum = page;
        this.totalPageNums = pageNums;
        pageIndexes = new ArrayList<>();
        initIndexes();
    }

    public List<Pagination> genPaginations(String original, HashMap<String,Object> param,String pageFiled){
        if (original==null) return null;
        if (pageIndexes==null) return null;
        ArrayList<Pagination> p = new ArrayList<>();
        pageIndexes.forEach(i -> {
            Pagination pagination = new Pagination();
            pagination.setPage(i);
            if (i==currentPageNum)
                pagination.setCurrent(true);
            pagination.genUrl(original,param,pageFiled);
            p.add(pagination);
        });
        return p;
    }

    public void initIndexes(){
        int start=1;
        int end=1;
        if (this.currentPageNum>maxIndexTagSize/2){
            start=currentPageNum-maxIndexTagSize/2;
        }
        if (totalPageNums-currentPageNum>maxIndexTagSize/2){
            end = start+6;
        }else {
            end=currentPageNum+(totalPageNums-currentPageNum);
        }
        for (int i = start;i<=end;i++){
            pageIndexes.add(i);
        }

    }

    public static class Pagination{
        private int page;
        private boolean current;
        private String pageUrl;

        public Pagination(){

        }

        public void genUrl(String original, HashMap<String,Object> param,String pageFiled){
            StringBuilder builder = new StringBuilder();
            builder.append(original).append("?");
            builder.append(pageFiled).append("=").append(page).append("&");
            if (param!=null)
                param.forEach((s, o) -> {
                    builder.append(s).append("=").append(o).append("&");
                });
            builder.deleteCharAt(builder.length()-1);
            pageUrl = builder.toString();
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public boolean isCurrent() {
            return current;
        }

        public void setCurrent(boolean current) {
            this.current = current;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }
    }


    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public int getTotalPageNums() {
        return totalPageNums;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public int getMaxPageLength() {
        return maxPageLength;
    }

    public List<T> getPageItems() {
        return pageItems;
    }

    public List<Integer> getPageIndexes() {
        return pageIndexes;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public void setTotalPageNums(int totalPageNums) {
        this.totalPageNums = totalPageNums;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public void setMaxPageLength(int maxPageLength) {
        this.maxPageLength = maxPageLength;
    }

    public void setPageItems(List<T> pageItems) {
        this.pageItems = pageItems;
    }

    public void setPageIndexes(List<Integer> pageIndexes) {
        this.pageIndexes = pageIndexes;
    }
}
