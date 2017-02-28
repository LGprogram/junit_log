<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">
           <%--账户管理模块--%>
            <shiro:hasRole name="manager">
                <li class="treeview ${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                    <a href="#">
                        <i class="fa fa-cogs"></i> <span>系统设置</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li class="${param.menu == 'sys_manager' ? 'active' : ''}">
                            <a href="/page/user"><i class="fa fa-circle-o"></i> 账户管理</a>
                        </li>
                    </ul>
                </li>
            </shiro:hasRole>
            <%--财务模块--%>
            <shiro:hasRole name="financial">
                <li class="treeview ${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                    <a href="#">
                        <i class="fa fa-pie-chart"></i>
                        <span>财务报表</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li class="${param.menu == 'sys_day' ? 'active' : ''}">
                            <a href="../charts/chartjs.html"><i class="fa fa-circle-o"></i> 日报</a>
                        </li>
                        <li class="${param.menu == 'sys_month' ? 'active' : ''}">
                            <a href="../charts/morris.html"><i class="fa fa-circle-o"></i> 月报</a>
                        </li>
                        <li class="${param.menu == 'sys_year' ? 'active' : ''}">
                            <a href="../charts/flot.html"><i class="fa fa-circle-o"></i> 年报</a>
                        </li>
                    </ul>
                </li>
            </shiro:hasRole>
            <shiro:hasRole name="market">
                <li class="treeview ${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                    <a href="#">
                        <i class="fa fa-edit"></i> <span>业务</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li class="${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                            <a href="#"><i class="fa fa-circle-o"></i> 设备租赁<i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li class="${param.menu == 'sys_out' ? 'active' : ''}" >
                                    <a href="/page/device/out"><i class="fa fa-circle-o"></i> 业务流水</a>
                                </li>
                                <li class="${param.menu == 'sys_add' ? 'active' : ''}">
                                    <a href="/page/device/out/add"><i class="fa fa-plus"></i> 新增流水 </a>
                                </li>
                            </ul>
                        </li>
                        <li class="${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                            <a href="#"><i class="fa fa-circle-o"></i> 设备管理<i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li class="${param.menu == 'sys_current' ? 'active' : ''}" >
                                    <a href="/page/device"><i class="fa fa-circle-o"></i> 设备库存</a>
                                </li>
                                <li class="${param.menu == 'sys_new' ? 'active' : ''}">
                                    <a href="/page/device/new"><i class="fa fa-plus"></i> 新增设备 </a>
                                </li>
                            </ul>
                        </li>
                        <li class="${fn:startsWith(param.menu,'sys_') ? 'active' : ''}">
                            <a href="#"><i class="fa fa-circle-o"></i> 劳务外包 <i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li class="${param.menu == 'sys_work_out' ? 'active' : ''}">
                                    <a href="#"><i class="fa fa-circle-o"></i> 业务流水</a>
                                </li>
                                <li class="${param.menu == 'sys_work_add' ? 'active' : ''}">
                                    <a href="#"><i class="fa fa-plus"></i> 新增流水 <i class="fa fa-angle-left pull-right"></i></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </shiro:hasRole>
               <li class="treeview">
                   <a href="#">
                       <i class="fa fa-folder"></i>
                       <span>公司网盘</span>
                   </a>
               </li>
            <li class="treeview">
                <a href="/logout">
                    <i class="fa fa-sign-out"></i> <span>安全退出</span></i>
                </a>
            </li>
        </ul>
    </section>
</aside>