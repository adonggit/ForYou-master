package com.it.onex.baby.activity.register;

import com.it.onex.baby.base.BasePresenter;
import com.it.onex.baby.bean.DataResponse;
import com.it.onex.baby.net.ApiService;
import com.it.onex.baby.net.RetrofitManager;
import com.it.onex.baby.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by OnexZgj on 2018/9/11:09:37.
 * des:
 */

public class RegisterActivityImp extends BasePresenter<RegisterActivityContract.View> implements RegisterActivityContract.Presenter {

    @Inject
    public RegisterActivityImp() {

    }


    @Override
    public void register(String account, String password, String rePassword) {
        mView.showLoading();
        RetrofitManager.create(ApiService.class).register(account,password,rePassword)
                .compose(mView.<DataResponse>bindToLife())
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode()!=0){
                            mView.showFaild(dataResponse.getErrorMsg().toString());
                        }else {
                            mView.showRegisterSuccess();
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFaild(throwable.getMessage().toString());
                        mView.hideLoading();
                    }
                });
    }
}
