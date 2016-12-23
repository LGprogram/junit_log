package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.entity.Node;

import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
public class NodeService {
    private NodeDao nodeDao = new NodeDao();
    public List<Node> findAllNode() {
        return nodeDao.findAllNode();
    }
}
