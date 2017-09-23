package com.apache.fastandroid.artemis.comBridge;

/**
 * Created by jerryliu on 2017/7/11.
 */

public interface IDelegateFactory {

    IDataDelegate getDataTransfer(String action);

    IActionDelegate getActionTransfer(String action);

    IObjectDataDelegate getObjectDataTransfer(String action);


}
