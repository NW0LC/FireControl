package com.exz.carprofitmuch.config

/**
 * Created by 史忠文
 * on 2017/10/17.
 */
object Urls{
    var APP_ID = ""
    var url = "http://139.129.23.185:9654/"

    /**
     * 交换密钥接口
     */
    val changeKey=url+"user/encrypt/changeKey.jn"
    /**
     * 登录接口
     */
    val userLogin=url+"user/userLogin.jn"
    /**
     * 更新数据库在线标志（注销接口）
     */
    val updateIsOnline=url+"user/set/updateIsOnline.jn"
    /**
     * 修改密码接口
     */
    val changePwd=url+"user/set/changePwd.jn"
    /**
     * 忘记密码接口
     */
    val updatePwd=url+"user/updatePwd.jn"
    /**
     * 获取消防单位列表
     */
    val getEnterPriseAllListByPage =url+"user/getEnterPriseAllListByPage.jn"
    /**
     * 查询消防单位的图纸资料列表
     */
    val getDrawFileList=url+"user/getDrawFileList.jn"
    /**
     * 根据id获取消防单位基本信息
     */
    val getEnterPrise=url+"user/getEnterPrise.jn"
    /**
     * 获取消防员列表
     */
    val getFireManAllByPage =url+"user/getFireManAllByPage.jn"
    /**
     * 获取消防车列表
     */
    val getFireCarListByPage =url+"user/getFireCarListByPage.jn"
    /**
     * 获取消防预案、vr列表
     */
    val getEnterPriseData=url+"user/getEnterPriseData.jn"
    /**
     * 获取数据路线
     */
    val getPathPlanByRoleId=url+"user/getPathPlanByRoleId.jn"
    /**
     * 获取灾情列表
     */
    val getFireInfoListByPage =url+"user/getFireInfoListByPage.jn"
    /**
     * 根据id获取灾情详情
     */
    val getFireInfoById=url+"user/getFireInfoById.jn"
    /**
     * 获取组织机构列表
     */
    val getOrgListByPage =url+"user/getOrgListByPage.jn"
    /**
     * 获取消防大数据信息
     */
    val getFireDataList=url+"user/getFireDataList.jn"
    /**
     * 根据id获取消防员信息
     */
    val getFireManById=url+"user/getFireManById.jn"
    /**
     * 根据id获取消防车信息
     */
    val getFireCarById=url+"user/getFireCarById.jn"
    /**
     * 获取组织机构详情信息
     */
    val getOrgDetailById=url+"user/getOrgDetailById.jn"
    /**
     * 获取消防大数据详情信息
     */
    val getFireDataById=url+"user/getFireDataById.jn"
    /**
     * 获取下一级组织机构列表
     */
    val findLowerLevel=url+"user/findLowerLevel.jn"
    /**
     * 获取消防知识库类别列表
     */
    val getKnowledgeCategoryList=url+"user/getKnowledgeCategoryList.jn"
    /**
     * 获取消防知识列表（分页）
     */
    val getKnowledgeInfoList=url+"user/getKnowledgeInfoList.jn"
}