package learn.demo.month_6.test0620;

/**
 * @Description
 * @Author ZhiFei
 * @Date 2021-06-20 22:24
 *
 * 抽象类的应用:模板方法的设计模式
 **/
public class TemplateTest {
    public static void main(String[] args) {

        Template t1 = new SubTemlate();
        SubTemlate t2 = new SubTemlate();

        t1.sendTime();
        t2.sendTime();
    }
}
abstract class Template{ // 父类：抽象类

    //需求：计算某段代码执行所需花费的时间
    public void sendTime(){

        long start = System.currentTimeMillis();

        code();    //代码执行部分：不确定部分，易变的部分（不确定是哪段代码，所以定义为抽象方法）

        long end = System.currentTimeMillis();

        System.out.println("花费的时间为:" + (end - start));
    }

    public abstract void code();  // 抽象方法 — 不确定功能具体实现细节，需要子类去重写具体实现细节
}

class SubTemlate extends Template{ // 子类

    @Override
    public void code() {
        // 重写抽象方法：添加具体实习细节（某段要执行的代码里面的具体细节）
        for(int i = 2;i <= 1000;i++){
            boolean isFlag = true;
            for(int j = 2;j <= Math.sqrt(i);j++){
                if(i % j == 0){
                    isFlag = false;
                    break;
                }
            }
            if(isFlag){
                System.out.println(i);
            }
        }
    }
}