package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.StringUtils;

import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
public class NodeService {
    private NodeDao nodeDao = new NodeDao();
    public List<Node> findAllNode() {
        return nodeDao.findAllNode();
    }

    public void delNodeById(String nodeid) {
        if(StringUtils.isNumeric(nodeid)){
            Node node = nodeDao.findById(Integer.valueOf(nodeid));
            if(node!=null){
                //查询节点下的帖子数量，若有不允许删除：根据topicnum或者查询Topic表
                if(node.getTopicnum()>0){
                    throw new ServiceException("节点下已经有贴，不能删除");
                }else{
                    nodeDao.delNodeById(node.getId());
                }
            }else{
                throw new ServiceException("节点不存在或已被删除");
            }
        }else {
            throw new ServiceException("参数错误");
        }
    }

    public Node findNodeById(String nodeid) {
        if(StringUtils.isNumeric(nodeid)){
            Node node = nodeDao.findById(Integer.valueOf(nodeid));
            if(node!=null){
                return node;
            }else{
                throw new ServiceException("节点不存在");
            }
        }else{
            throw new ServiceException("参数错误");
        }
    }

    public void updateNode(String nodeid, String nodename) {
        if(StringUtils.isNumeric(nodeid)){
            Node node = nodeDao.findById(Integer.valueOf(nodeid));
            if(node!=null){
                if(node.getNodename().equals(nodename)){
                    return;
                }
                Node nodeisIn = nodeDao.findByNodename(nodename);
                if(nodeisIn==null){
                    node.setNodename(nodename);
                    nodeDao.update(node);

                }else{
                    throw new ServiceException("该节点已经存在");
                }
            }else{
                throw new ServiceException("参数错误");
            }

        }else{
            throw new ServiceException("参数错误");
        }
    }
}
