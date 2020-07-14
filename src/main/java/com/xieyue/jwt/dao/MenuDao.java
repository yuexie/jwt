package com.xieyue.jwt.dao;

import com.wx.dbm.dao.DaoException;
import com.wx.vmind.admin.domain.Menu;

import java.util.List;

public interface MenuDao {
    /**
     * 获取所有导航菜单.
     *
     * @return java.util.List
     * @throws DaoException
     */
    List<Menu> loadNaviMenu() throws DaoException;
}
