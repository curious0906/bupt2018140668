package com.chenchao.service.impl;

import com.chenchao.service.IWebParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chenchao.model.WebParameter;
import javax.annotation.Resource;
import com.chenchao.dao.WebParameterDao;

@Service
public class WebParameterServiceImpl implements IWebParameterService {
    @Autowired
    private WebParameterDao webParameterDao;
    @Override
    public WebParameter selectWebParameter() {
        return webParameterDao.selectWebParameter();
    }
}
