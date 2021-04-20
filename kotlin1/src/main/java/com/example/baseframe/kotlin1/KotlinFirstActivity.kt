package com.example.baseframe.kotlin1

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.BaseFrame.common.Constants
import com.example.baseframe.basemvp.BaseActivity
import kotlinx.android.synthetic.main.activity_kotlin_first.*

/**
 * @author fengzhongcheng
 * @since 2021/4/20
 */
@Route(path = Constants.Kotlin1.Route.KOTLIN1)
class KotlinFirstActivity : BaseActivity() {
    private var mTitle: String? = ""

    override fun contentLayout(): Int {
        return R.layout.activity_kotlin_first
    }

    override fun init() {
        super.init()
        processArgs()
        tv_args.text = "接收到的参数为：$mTitle"
        btn_to_First.setOnClickListener {
            navigate(Constants.First.Route.FIRST).navigation(this@KotlinFirstActivity)
        }
    }

    private fun processArgs() {
        intent.extras?.let {
            mTitle = it.getString(Constants.Kotlin1.KEY_TO_KOTLIN)
        }
    }
}