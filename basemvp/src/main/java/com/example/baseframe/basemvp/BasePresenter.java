package com.example.baseframe.basemvp;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 * @desc
 */
public interface BasePresenter<T extends BaseView> {

    /**
     * 绑定View
     */
    void attachView(T view);

    /**
     * 解绑View
     */
    void detachView();
}
