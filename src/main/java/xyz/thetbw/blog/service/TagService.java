package xyz.thetbw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thetbw.blog.data.dao.TagDao;
import xyz.thetbw.blog.data.entity.Tag;

import java.util.List;

/**
 * 标签相关
 */
@Service
public class TagService {

    @Autowired
    TagDao tagDao;

    public Tag get(int tag_id){
        return tagDao.get(tag_id);
    }


    public List<Tag> tags(){
        return tagDao.getAll();
    }
}
