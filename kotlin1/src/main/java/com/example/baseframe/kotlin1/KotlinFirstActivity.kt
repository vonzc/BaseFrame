package com.example.baseframe.kotlin1

import androidx.core.content.ContextCompat
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

    override fun contentLayout(): Int {
        return R.layout.activity_kotlin_first
    }

    override fun init() {
        super.init()
        btn_to_First.apply {
            text = "跳转到first模块"
            textSize = 20f
            setOnClickListener {
                navigate(Constants.First.Route.FIRST).navigation(this@KotlinFirstActivity)
            }
        }
    }
}