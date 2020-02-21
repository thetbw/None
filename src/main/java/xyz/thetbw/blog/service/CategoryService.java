package xyz.thetbw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.dao.CategoryDao;
import xyz.thetbw.blog.data.entity.Category;
import xyz.thetbw.blog.data.model.admin.CategoryOptionModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ArticleService articleService;


    /**
     *根据id获取分类
     */
    public Category getCategory(int category_id){
        return categoryDao.get(category_id);
    }

    /**
     * 根绝名称获取分类
     */
    public Category getCategory(String category_name){
        return categoryDao.getByName(category_name);
    }


    /**
     *获取所有分类的个数
     * @return
     */
    public int count(){
        return categoryDao.getCount();
    }

    /**
     * 获取某个分类下的直接分类个数
     * @param parent_id 父分类，如果为-1则为根分类
     * @return
     */
    public int count(int parent_id){
        if (parent_id==-1)
            return categoryDao.getCountOfRoot();
        return categoryDao.getCountOfParent(parent_id);
    }


    /**
     * 以树状返回分类列表
     * @return
     */
    public List<Category> getCategoriesTree(){
        return categoryDao.getCategoryTree();
    }

    /**
     * 获取分类的设置项
     * @return List<CategoryOptionModel>
     */
    public Object getCategoryOptions(){
        List<Category> categories =getCategoriesTree();
        return switchCategoryTree2optionTree(categories);
    }

    /**
     * 用户转换为前端易于操作的类型
     * @param categories
     * @return
     */
    private List<CategoryOptionModel> switchCategoryTree2optionTree(List<Category> categories){
        List<CategoryOptionModel> optionModels = new ArrayList<>();
        categories.forEach((c)->{
            CategoryOptionModel optionModel = new CategoryOptionModel();
            optionModel.setValue(c.getCategory_id());
            optionModel.setLabel(c.getCategory_name());
            if (c.getCategory_children()!=null&&c.getCategory_children().size()>0){
                optionModel.setChildren(switchCategoryTree2optionTree(c.getCategory_children()));
            }
            optionModels.add(optionModel);
        });
        return optionModels;
    }



    /**
     * 获取类似 /编程/java/blog 的分类路径
     * @return
     */
    public String getCategoryPath(int category_id ,int showArray){
        StringBuilder stringPath = new StringBuilder();
        ArrayList<Integer> arrayPath = new ArrayList<>();
        Category category=categoryDao.get(category_id);
        if (showArray!=1){
            while (category!=null){
                stringPath.insert(0,"/"+category.getCategory_name());
                category =categoryDao.get(category.getCategory_parent_id());
            }
            return stringPath.toString();
        }else{
            while (category!=null){
                arrayPath.add(0,category.getCategory_id());
                category =categoryDao.get(category.getCategory_parent_id());
            }
            return arrayPath.toString();
        }
    }

    public List<Category> categories(int page, int max_page_length){
        int start = (page-1)*max_page_length;
        return categoryDao.getAllPaging(start,max_page_length);
    }

    /**
     * 获取分类列表
     * @param page 分页
     * @param max_page_length 分页大小
     * @param parent_id 父分类，如果为-1,则显示根分类列表
     * @return
     */
    public List<Category> categories(int page, int max_page_length, int parent_id){
        List<Category> categories;
        int start = (page-1)*max_page_length;
        if (parent_id==-1) {
            categories = categoryDao.getAllPaging(start, max_page_length);
        } else {
            categories = categoryDao.getCategoryByParentPaging(start, max_page_length, parent_id);
        }
        return categories;
    }


    /**
     *更新分类
     */
    public void updateCategory(Category category){
        categoryDao.update(category);
    }


    /**
     * 添加分类到父分类
     * @param parent_id 父分类id
     * @param category_name 分类名称
     */
    public void addCategory(int parent_id,String category_name){
        String name = category_name.trim();
        Category category = getCategory(name);
        if (category!=null)
            throw  new RuntimeException("分类已存在");
        if (parent_id!=0) {
            if (categoryDao.get(parent_id) == null)
                throw new RuntimeException("父分类不存在");
        }
        if (name.length()==0)
            throw new RuntimeException("名字长度不能为0");
        category = new Category();
        category.setCategory_parent_id(parent_id);
        category.setCategory_order(count(parent_id)+1);
        category.setCategory_name(name);
        category.setCategory_article_num(0);
        addCategory(category);
    }

    /**
     * 添加分类
     * @param category
     */
    public void addCategory(Category category){
        categoryDao.add(category);
    }

    /**
     * 删除分类 如果分类下有文章，则会删除失败
     * @param category
     */
    public void deleteCategory(Category category){
        List<Category> children = categoryDao.getCategoryByParent(category.getCategory_id());
        for (Category c:children){
            deleteCategory(c);
        }
        if (!articleService.articles(1,10,category).isEmpty())
            throw new RuntimeException("该分类下有文章,无法删除该分类");
        categoryDao.delete(category.getCategory_id());
    }
}
