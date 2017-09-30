/*
 * Project: nio
 * 
 * File Created at 17-9-30
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.jay.im.server.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

/**
 * @Type TopHandler.java
 * @Desc
 * @author jay
 * @date 17-9-30 下午3:42
 * @version
 */
@Service
public class TopHandler {

    private ExecutorService executor;


    public TopHandler(){
        executor = Executors.newFixedThreadPool(10);
    }


}
/**
 * Revision history
 * -------------------------------------------------------------------------
 *
 * Date Author Note
 * -------------------------------------------------------------------------
 * 17-9-30 jay create
 */
