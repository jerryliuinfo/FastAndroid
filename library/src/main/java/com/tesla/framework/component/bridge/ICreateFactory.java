package com.tesla.framework.component.bridge;

/**
 * Created by jerryliu on 2017/7/18.
 */

public interface ICreateFactory {
    /**
     * 根据模块名字创建模块工厂
     * @param groupArtifact
     * @return
     */
    IDataDelegate getDelegateFactoryName(String groupArtifact);
}
