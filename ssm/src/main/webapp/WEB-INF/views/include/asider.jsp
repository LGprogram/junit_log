<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">
            <shiro:hasRole name="manager">
                <li class="header">设置模块</li>
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>Dashboard</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li class="active"><a href="index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
                    </ul>
                </li>
                <li class="treeview ${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                    <a href="#">
                        <i class="fa fa-cogs"></i> <span>系统设置</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li class="${param.menu == 'sys_accounts' ? 'active' : ''}">
                            <a href="/user"><i class="fa fa-circle-o"></i> 账户管理</a>
                        </li>
                        <li class="${param.menu == 'sys_device' ? 'active' : ''}">
                            <a href="/setting/device"><i class="fa fa-circle-o"></i> 设备管理</a>
                        </li>
                    </ul>
                </li>
            </shiro:hasRole>
            <li class="header">业务模块</li>
            <shiro:hasRole name="financial">
                <li class="tree-view">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>财务报表</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
            </shiro:hasRole>
            <shiro:hasRole name="market">
                <li class="tree-view">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>劳务派遣</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
                <li class="tree-view">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>设备租赁</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
            </shiro:hasRole>
            <li class="header">用户模块</li>
            <li class="treeview">
                <a href="/logout">
                    <i class="fa fa-dashboard"></i> <span>安全退出</span></i>
                </a>
            </li>
        </ul>
    </section>
</aside>