package com.example.clone_instagram.add

import com.example.clone_instagram.common.base.BasePresenter
import com.example.clone_instagram.common.base.BaseView

interface Add {

    interface Presenter : BasePresenter{

    }

    interface View : BaseView<Presenter>
}