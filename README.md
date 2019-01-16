# 1. 框架组合

retrofit+okhttp+rxjava负责网络请求，使用gson进行json数据解释,使用glide加载图片,使用rxbinding+BaseViewModel进行mvvm框架搭建

# 2.	数据绑定
使用google rxbinding 实现双向绑定，并扩展不支持的数据绑定

# 3.	基类封装
针对mvvm模式，封装BaseActivity、BaseFragment、BaseViewModel，ViewDataBinding与ViewModel无需重新定义，只需在BaseActivity和BaseFragment中限定范型即可使用

# 准备工作
1.	启用dataBinding
	在app工程中build.gradle的android{}中加入:
	
	```
	dataBinding {
		enabled true
	}
	
	```
	
* ViewDatabinding在kotlin中有时无法生成，可添加

```
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

kapt {
        generateStubs = true
    }
```
2.	添加依赖

在projcet的build.gradle中添加:

```
allprojects {
    repositories {
		...
        maven {
            url "https://jitpack.io"
        }
    }
}
```

在app工程中build.gradle添加依赖:

```
	implementation 'com.github.troyzqmingming:mvvmLib:x.x.x'
```

# 快速使用
## 1关联ViewModel
- 在layout.xml中添加variable

```
<layout xmlns:binding="http://schemas.android.com/apk/res-auto">
 <data>
        <variable
            name="vm" <!-- 变量名称-->
            type="mvvc.cya.lib.com.mvvmcya.module.main.vm.MainVM" /><!-- 类型全路径-->
    </data>
</layout>
```

- 继承Base
继承BaseActivity,指定对应layoutBinding及ViewModel将自动生成对象
```
class MainActivity : BaseActivity<UiActivityMainBinding, MainVM>() {}
```

重写BaseActivity的抽象方法

```
override fun getLayoutId(savedInstanceState: Bundle?) = R.layout.ui_activity_main

    override fun getVariableId() = BR.vm

    override fun getViewModel() = MainVM(this, binding)
```

# bindingAdapter
统一使用BindingCommand和BindingCommand2进行监听

```
    val maintainDateLastCommand = BindingCommand(object : BindingCommand.BindingAction {
        override fun call() {
            picker.show()
        }

    })
    
   
    val carModeChooseCommand = BindingCommand2(object : BindingCommand2.BindingConsumer<ISpinner> {

        override fun call(t: ISpinner) {
            Log.e("tag", t.getValue())
           
        }
    })
```

### View
|  XML Attribute |          | Description |
|----------|:-------------:|------:|
| binding:onClickCommand |  BindingCommand | |
| binding:clickDelayed    |    Boolean   |   防止过快点击 |


### RecyclerView
|  XML Attribute |          | Description |
|----------|:-------------:|------:|
| binding: adapter |  BaseRecyclerAdapter<*, *> | |
| binding: layoutManager    |    RecyclerView.LayoutManager   |   |



### ViewPager
|  XML Attribute |          | Description |
|----------|:-------------:|------:|
| binding: adapter |  BaseFragmentAdapter<*> | |


### Spinner
|  XML Attribute |          | Description |
|----------|:-------------:|------:|
| binding: dataList |  MutableList<ISpinner>  | 数据内容 |
|	binding:selectValue |	String |显示的内容|
| binding:onItemSelectedCommand | BindingCommand2<ISpinner> |


# Retrofit

```
interface TestService {

    @GET("action/apiv2/banner?catalog=1")
    fun demoGet(): Observable<ResponseBody>
}
```

```
RetrofitManager.build {
                            baseUrl {
                                "https://www.oschina.net/"
                            }
                            //progress //headers
                        }.doRequest(TestService::class.java, object : RetrofitSimpleRequestCallback<String, TestService>(context) {

                            override fun buildObservable(retrofitInterface: TestService): Observable<ResponseBody> {
                                return retrofitInterface.demoGet()
                            }

                            override fun onGotResponseBodySuccess(response: ResponseBody): String {
                                return response.string()
                            }

                            override fun onGotSuccess(result: String) {
                                Logger.d(result)
                            }

                        })
```

