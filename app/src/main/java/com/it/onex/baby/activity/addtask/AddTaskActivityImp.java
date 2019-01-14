package com.it.onex.baby.activity.addtask;

import com.it.onex.baby.base.BasePresenter;
import com.it.onex.baby.bean.AddToDoDetail;
import com.it.onex.baby.bean.DataResponse;
import com.it.onex.baby.bean.TodoTaskDetail;
import com.it.onex.baby.net.ApiService;
import com.it.onex.baby.net.RetrofitManager;
import com.it.onex.baby.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by OnexZgj on 2018/8/20:11:34.
 * des:
 */

public class AddTaskActivityImp extends BasePresenter<AddTaskActivityContract.View> implements AddTaskActivityContract.Presenter {

    @Inject
    public AddTaskActivityImp() {

    }


    @Override
    public void addTask(String title, String content, String date, String state) {
        RetrofitManager.create(ApiService.class).addTask(title,content,date,state)
                .compose(RxSchedulers.<DataResponse<TodoTaskDetail.DatasBean>>applySchedulers())
                .compose(mView.<DataResponse<TodoTaskDetail.DatasBean>>bindToLife())
                .subscribe(new Consumer<DataResponse<TodoTaskDetail.DatasBean>>() {
                    @Override
                    public void accept(DataResponse<TodoTaskDetail.DatasBean> dataResponse) throws Exception {

                        if (dataResponse.getErrorCode() == 0) {
                            mView.showAddTaskSuccess(dataResponse.getData());

                        }else{
                            mView.showFaild(dataResponse.getErrorMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFaild("添加待办失败,请重试...");
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void updateTask(int id,String title, String content, String date, int state,int type) {
        RetrofitManager.create(ApiService.class).updateTodo(id,title,content,date,state,type)
                .compose(mView.<DataResponse<TodoTaskDetail.DatasBean>>bindToLife())
                .compose(RxSchedulers.<DataResponse<TodoTaskDetail.DatasBean>>applySchedulers())
                .subscribe(new Consumer<DataResponse<TodoTaskDetail.DatasBean>>() {
                    @Override
                    public void accept(DataResponse<TodoTaskDetail.DatasBean> dataResponse) throws Exception {
                        if (dataResponse.getErrorCode()==0){
                            mView.showUpdateSuccess(dataResponse.getData());
                        }else {
                            mView.showFaild(dataResponse.getErrorMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFaild("更新失败,请重试...");
                        mView.hideLoading();
                    }
                });
    }





}
