/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.soul.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.dromara.soul.admin.dto.AuthApplyDTO;
import org.dromara.soul.admin.dto.AppAuthDTO;
import org.dromara.soul.admin.dto.AuthPathDTO;
import org.dromara.soul.admin.dto.AuthPathWarpDTO;
import org.dromara.soul.admin.dto.BatchCommonDTO;
import org.dromara.soul.admin.service.AppAuthService;
import org.dromara.soul.admin.vo.AppAuthVO;
import org.dromara.soul.admin.page.CommonPager;
import org.dromara.soul.admin.page.PageParameter;
import org.dromara.soul.admin.query.AppAuthQuery;
import org.dromara.soul.admin.result.SoulAdminResult;
import org.dromara.soul.admin.utils.SoulResultMessage;
import org.dromara.soul.admin.vo.AuthPathVO;
import org.dromara.soul.common.constant.AdminConstants;
import org.dromara.soul.common.utils.DateUtils;
import org.dromara.soul.common.utils.GsonUtils;
import org.dromara.soul.common.exception.CommonErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test cases for AppAuthController.
 *
 * @author magicalxiaochen
 */
@RunWith(MockitoJUnitRunner.class)
public final class AppAuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AppAuthController appAuthController;

    @Mock
    private AppAuthService appAuthService;

    private final AppAuthVO appAuthVO = new AppAuthVO("0001", "testAppKey", "testAppSecret",
            "testUser", "18600000000", "{\"extInfo\": \"test\"}",
            true, null,
            DateUtils.localDateTimeToString(LocalDateTime.now()));

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(appAuthController).build();
    }

    @Test
    public void testApply() throws Exception {
        final AuthApplyDTO authApplyDTO = new AuthApplyDTO();
        List<String> pathList = new ArrayList<>();
        pathList.add("/test");
        authApplyDTO.setAppName("testApp");
        authApplyDTO.setUserId("testUser");
        authApplyDTO.setPhone("18600000000");
        authApplyDTO.setAppParam("{\"type\": \"test\"}");
        authApplyDTO.setExtInfo("{\"extInfo\": \"test\"}");
        authApplyDTO.setPathList(pathList);
        given(this.appAuthService.applyCreate(authApplyDTO)).willReturn(
                SoulAdminResult.success(SoulResultMessage.CREATE_SUCCESS));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(authApplyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.CREATE_SUCCESS)))
                .andReturn();
    }

    @Test
    public void testApplyWithAppKey() throws Exception {
        final AuthApplyDTO authApplyDTO = new AuthApplyDTO();
        List<String> pathList = new ArrayList<>();
        pathList.add("/test");
        authApplyDTO.setAppName("testApp");
        authApplyDTO.setUserId("testUser");
        authApplyDTO.setPhone("18600000000");
        authApplyDTO.setAppParam("{\"type\": \"test\"}");
        authApplyDTO.setAppKey("testAppKey");
        authApplyDTO.setExtInfo("{\"extInfo\": \"test\"}");
        authApplyDTO.setPathList(pathList);
        given(this.appAuthService.applyUpdate(authApplyDTO)).willReturn(
                SoulAdminResult.success(SoulResultMessage.CREATE_SUCCESS));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(authApplyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.CREATE_SUCCESS)))
                .andReturn();
    }
    
    @Test
    public void testUpdateSk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/appAuth/updateSk")
                .param("appKey", "testAppKey")
                .param("appSecret", "updateAppSecret"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testFindPageByQuery() throws Exception {
        final PageParameter pageParameter = new PageParameter();
        final AppAuthQuery appAuthQuery = new AppAuthQuery("testAppKey", "18600000000", pageParameter);
        final CommonPager<AppAuthVO> commonPager = new CommonPager<>(pageParameter, Collections.singletonList(appAuthVO));
        given(this.appAuthService.listByPage(appAuthQuery)).willReturn(commonPager);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/appAuth/findPageByQuery")
                .param("appKey", "testAppKey")
                .param("phone", "18600000000")
                .param("currentPage", pageParameter.getCurrentPage() + "")
                .param("pageSize", pageParameter.getPageSize() + ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.QUERY_SUCCESS)))
                .andExpect(jsonPath("$.data.dataList[0].appKey", is(appAuthVO.getAppKey())))
                .andReturn();
    }

    @Test
    public void testDetail() throws Exception {
        given(this.appAuthService.findById("0001")).willReturn(appAuthVO);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/appAuth/detail")
                .param("id", "0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.DETAIL_SUCCESS)))
                .andExpect(jsonPath("$.data.id", is(appAuthVO.getId())))
                .andReturn();
    }

    @Test
    public void testUpdateDetail() throws Exception {
        AppAuthDTO appAuthDTO = new AppAuthDTO();
        appAuthDTO.setId("0001");
        appAuthDTO.setPhone("18600000001");
        given(this.appAuthService.updateDetail(appAuthDTO)).willReturn(
                SoulAdminResult.success(SoulResultMessage.UPDATE_SUCCESS));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/updateDetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(appAuthDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.UPDATE_SUCCESS)))
                .andReturn();
    }

    @Test
    public void testDetailPath() throws Exception {
        final AuthPathVO authPathVO = new AuthPathVO();
        authPathVO.setId("0001");
        authPathVO.setAppName("testApp");
        authPathVO.setPath("/test");
        given(this.appAuthService.detailPath("0001")).willReturn(Collections.singletonList(authPathVO));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/appAuth/detailPath")
                .param("id", "0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.DETAIL_SUCCESS)))
                .andExpect(jsonPath("$.data[0].path", is(authPathVO.getPath())))
                .andReturn();
    }

    @Test
    public void testUpdateDetailPath() throws Exception {
        final AuthPathDTO authPathDTO = new AuthPathDTO();
        authPathDTO.setAppName("testApp");
        authPathDTO.setPath("/test");
        authPathDTO.setEnabled(true);
        List<AuthPathDTO> authPathDTOS = new ArrayList<>();
        authPathDTOS.add(authPathDTO);

        final AuthPathWarpDTO authPathWarpDTO = new AuthPathWarpDTO();
        authPathWarpDTO.setId("0001");
        authPathWarpDTO.setAuthPathDTOList(authPathDTOS);

        given(this.appAuthService.updateDetailPath(authPathWarpDTO)).willReturn(SoulAdminResult.success());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/updateDetailPath")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(authPathWarpDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(CommonErrorCode.SUCCESSFUL)))
                .andReturn();
    }

    @Test
    public void testBatchDelete() throws Exception {
        final List<String> ids = new ArrayList<>(2);
        ids.add("0001");
        ids.add("0002");
        given(this.appAuthService.delete(ids)).willReturn(2);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/batchDelete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.DELETE_SUCCESS)))
                .andExpect(jsonPath("$.data", is(2)))
                .andReturn();
    }

    @Test
    public void testBatchEnabled() throws Exception {
        final BatchCommonDTO batchCommonDTO = new BatchCommonDTO();
        batchCommonDTO.setIds(Arrays.asList("0001", "0002"));
        batchCommonDTO.setEnabled(true);
        given(this.appAuthService.enabled(batchCommonDTO.getIds(), batchCommonDTO.getEnabled()))
                .willReturn(StringUtils.EMPTY);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/batchEnabled")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(batchCommonDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SoulResultMessage.ENABLE_SUCCESS)))
                .andReturn();
    }

    @Test
    public void testBatchEnabledWithError() throws Exception {
        final BatchCommonDTO batchCommonDTO = new BatchCommonDTO();
        batchCommonDTO.setIds(Arrays.asList("0001", "0002"));
        batchCommonDTO.setEnabled(true);
        given(this.appAuthService.enabled(batchCommonDTO.getIds(), batchCommonDTO.getEnabled()))
                .willReturn(AdminConstants.ID_NOT_EXIST);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/batchEnabled")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtils.getInstance().toJson(batchCommonDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(AdminConstants.ID_NOT_EXIST)))
                .andReturn();
    }

    @Test
    public void testSyncData() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/appAuth/syncData"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
