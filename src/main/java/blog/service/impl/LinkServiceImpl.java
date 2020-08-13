package blog.service.impl;

import blog.dao.LinkDao;
import blog.entity.BlogArticle;
import blog.entity.Link;
import blog.service.LinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("linkService")
public class LinkServiceImpl implements LinkService {


    @Resource
    private LinkDao linkDao;

    @Override
    public Link findById(Integer id) {
        return linkDao.findById(id);
    }

    @Override
    public List<Link> list(Map<String, Object> map) {
        return linkDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return linkDao.getTotal(map);
    }

    @Override
    public Integer delete(Integer id) {
        return linkDao.delete(id);
    }

    @Override
    public Integer add(Link link) {
        return linkDao.add(link);
    }

    @Override
    public Integer update(int id,String linkName,String linkUrl,int orderNo) {

        Link link = new Link();
        link.setId(id);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setOrderNo(orderNo);


        return linkDao.update(link);
    }
}
