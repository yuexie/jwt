package com.xieyue.jwt.dao.impl;

import com.wx.dbm.dao.DaoException;
import com.wx.vmind.admin.domain.Menu;
import com.wx.vmind.common.DbAppConfig;
import com.wx.vmind.common.initializer.SystemConfigInitializer;

import java.util.ArrayList;
import java.util.List;

import com.wx.dbm.dao.template.DbmDaoTemplate;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-05-11 18:02
 **/

public class MenuServiceImpl extends DbmDaoTemplate {

    private static final String LOAD_MENU = "select * from Menu where menuType = 1 order by menuId";

    public List<Menu> loadNaviMenu() throws DaoException {
        List<Menu> menus = executeQuery(DbAppConfig.getMainDbId(), Menu.class, LOAD_MENU);
        if(menus != null && menus.size() > 0){
            for(Menu m : menus){
                m.setName(SystemConfigInitializer.BUSINESS_TYPE.get(m.getName())==null?m.getName():SystemConfigInitializer.BUSINESS_TYPE.get(m.getName()));
            }
        }else{
            menus = new ArrayList<Menu>();
        }
        return menus;
    }

    @Override
    public <T> T callDao(T t, Object... objects) {
        return null;
    }
}
