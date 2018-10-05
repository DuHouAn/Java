package com.southeast.forInterview;

import java.io.IOException;

/**
 * Created by 18351 on 2018/8/29.
 */
class TestException5 {
    public void start() throws IOException {

    }

    public void foo() throws NullPointerException{     }
}

class TestException6 extends TestException5{
    /*public void start() throws Exception{
    //因为父类中start的方法签名与子类中的start方法签名不相同。
     我们可以修改子类的方法签名使之与超类相同，我们也可以像下面代码那样移除子类中throws关键字。
    }*/
    //移除子类中throws关键字。
    @Override
    public void start(){

    }

    public void foo() throws RuntimeException{
    }
}
